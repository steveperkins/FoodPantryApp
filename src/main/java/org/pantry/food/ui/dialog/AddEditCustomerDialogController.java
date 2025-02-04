/*
  Copyright 2011 Dave Johnson

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/
package org.pantry.food.ui.dialog;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pantry.food.ApplicationContext;
import org.pantry.food.Images;
import org.pantry.food.dao.CustomersDao;
import org.pantry.food.model.Customer;
import org.pantry.food.ui.validation.ComboInputValidator;
import org.pantry.food.ui.validation.DateValidator;
import org.pantry.food.ui.validation.NotBlankValidator;
import org.pantry.food.ui.validation.NumericValidator;
import org.pantry.food.ui.validation.TextInputFocusValidator;
import org.pantry.food.ui.validation.ValidStatusTracker;
import org.pantry.food.util.DateUtil;
import org.pantry.food.util.NumberUtil;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.util.StringConverter;

public class AddEditCustomerDialogController implements IModalDialogController<AddEditCustomerDialogInput, Customer> {
	private static final Logger log = LogManager.getLogger(AddEditCustomerDialogController.class.getName());

	@FXML
	private ComboBox<String> householdIdCbo;
	@FXML
	private TextField personIdText;
	@FXML
	private ComboBox<String> genderCbo;
	@FXML
	private TextField birthdateText;
	@FXML
	private TextField ageText;
	@FXML
	private ComboBox<String> monthRegisteredCbo;
	@FXML
	private CheckBox activeChk;
	@FXML
	private CheckBox newChk;
	@FXML
	private TextField commentsText;
	@FXML
	private Button saveBtn;
	@FXML
	private Button cancelBtn;

	private ModalDialog<AddEditCustomerDialogInput, Customer> parent;
	private AddEditCustomerDialogInput input;
	private boolean isSaved = false;
	private ValidStatusTracker validStatusTracker;
	private List<String> monthNames = new ArrayList<>(
			Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"));
	private Customer savedCustomer = null;

	@FXML
	private void initialize() {
		// Populate comboboxes with genders and months
		genderCbo.getItems().addAll("Female", "Male");

		monthRegisteredCbo.getItems().addAll(monthNames);

		// Add icons to buttons
		saveBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (!validStatusTracker.checkAll()) {
					return;
				}

				boolean isNew = savedCustomer.getCustomerId() <= 0;
				log.info("Attempting to save {} customer record {}", isNew ? "new" : "existing",
						isNew ? savedCustomer.getCustomerId() : "");

				// Attempt to save the record
				// We only ever use the CustomersDao in this handler, so no reason to make it
				// available to the rest of this class
				CustomersDao dao = ApplicationContext.getCustomersDao();
				if (isNew) {
					savedCustomer.setCustomerId(dao.getNextId());
					dao.add(savedCustomer);
				} else {
					dao.edit(savedCustomer);
				}

				try {
					dao.persist();
					isSaved = true;
					parent.close();
				} catch (IOException e) {
					String message = "Could not " + (isNew ? "add" : "edit") + " record\r\n" + e.getMessage();
					log.error(message, e);
					new Alert(AlertType.WARNING, message).show();
				}
			}

		});

		cancelBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				log.info("User canceled add/edit customer record");
				// Close the dialog
				parent.close();
			}

		});

		validStatusTracker = new ValidStatusTracker();
	}

	@Override
	public void setInput(AddEditCustomerDialogInput input) {
		this.input = input;
		boolean isNew = null == input.getCustomer();
		log.info("Opening dialog to {} a customer record", isNew ? "add" : "edit");
		savedCustomer = new Customer(input.getCustomer());

		householdIdCbo.getItems().add("");

		if (null != input.getHouseholdIds()) {
			householdIdCbo.getItems().addAll(input.getHouseholdIds());
		}

		// Bind all the inputs to their Customer properties
		personIdText.textProperty().bindBidirectional(savedCustomer.personIdProperty());
		householdIdCbo.valueProperty().bindBidirectional(savedCustomer.householdIdProperty());
		genderCbo.valueProperty().bindBidirectional(savedCustomer.genderProperty());
		birthdateText.textProperty().bindBidirectional(savedCustomer.birthDateProperty());
		ageText.textProperty().bindBidirectional(savedCustomer.ageProperty());

		// The Month Registered combobox displays "Jan"/"Feb"/etc but the actual value
		// is a number, so we have to map between them
		monthRegisteredCbo.setConverter(new MonthConverter(monthNames));
		monthRegisteredCbo.valueProperty().bindBidirectional(savedCustomer.monthRegisteredProperty());

		newChk.selectedProperty().bindBidirectional(savedCustomer.newCustomerProperty());
		activeChk.selectedProperty().bindBidirectional(savedCustomer.activeProperty());

		commentsText.textProperty().bindBidirectional(savedCustomer.commentsProperty());

		// Bind the input validators
		log.debug("Binding input validators");
		// Person IDs must be non-blank and numeric
		TextInputFocusValidator textValidator = new TextInputFocusValidator(personIdText).add(new NotBlankValidator())
				.add(new NumericValidator());
		personIdText.focusedProperty().addListener(textValidator);

		textValidator = new TextInputFocusValidator(ageText).add(new NotBlankValidator()).add(new NumericValidator())
				.add(new NotBlankValidator());
		ageText.focusedProperty().addListener(textValidator);

		// Household IDs must be number
		ComboInputValidator comboValidator = new ComboInputValidator(householdIdCbo).add(new NotBlankValidator())
				.add(new NumericValidator());
		householdIdCbo.getSelectionModel().selectedItemProperty().addListener(comboValidator);

		// Birthdate must comply with typical date format
		textValidator = new TextInputFocusValidator(birthdateText).add(new DateValidator());
		birthdateText.focusedProperty().addListener(textValidator);
		// Calculate the person's age when the birthdate changes
		birthdateText.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean wasFocused, Boolean isFocused) {
				if (wasFocused && !isFocused && DateUtil.isDate(birthdateText.getText())) {
					String birthdateStr = birthdateText.getText();
					log.trace("Birthdate text focus lost, input is {}", birthdateStr);

					try {
						LocalDate birthdate = DateUtil.toDate(birthdateStr);
						int age = calculateAge(birthdate);

						if (age < 0 || age > 120) {
							log.debug("Birthdate {} results in age {}", birthdateStr, age);
							// Try to reconcile the date by adding 1900 to the year. If the year is
							// presented in a 2-digit format, Java assumes it's a 2-digit representation of
							// a year sub-100 AD. But the user probably means a year within the 20th
							// century.
							birthdate = birthdate.plusYears(1900);
							age = calculateAge(birthdate);
							if (age < 0 || age > 120) {
								log.error("Incorrect birth year - date {} results in age {}", birthdate.toString(),
										age);
								new Alert(AlertType.WARNING, "Incorrect birth year").show();
							} else {
								ageText.setText(String.valueOf(age));
							}
						} else {
							ageText.setText(String.valueOf(age));
						}
					} catch (ParseException e) {
						log.error("Invalid birthdate - date {} could not be parsed", birthdateStr, e);
						new Alert(AlertType.WARNING, "Invalid birthdate").show();
					}
				}
			}

			private int calculateAge(LocalDate birthdate) {
				return (int) ChronoUnit.YEARS.between(birthdate, LocalDate.now());
			}
		});

		ageText.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean wasFocused, Boolean isFocused) {
				String birthdateStr = birthdateText.getText();
				// We only want to calculate the birthdate from age if focus is lost and the
				// birthdate hasn't been manually entered yet, or if the entered birthdate is
				// January 1st
				if (wasFocused && !isFocused && (null == birthdateStr || birthdateStr.isBlank())) {
					String ageStr = ageText.getText();
					if (StringUtils.isBlank(ageStr)) {
						return;
					}

					// Determine if birthdate is January 1st
					if (DateUtil.isDate(birthdateStr)) {
						try {
							LocalDate date = DateUtil.toDate(birthdateStr);
							if (date.getMonthValue() == 1 && date.getDayOfMonth() == 1) {
								// Entered birthdate is January 1, so user wants a generic birthdate and no age
								return;
							}
						} catch (ParseException e) {
							log.error("Invalid birthdate - {} could not be parsed as date", birthdateStr, e);
							birthdateText.setText("");
						}
					}

					try {
						int age = Integer.valueOf(ageStr);
						LocalDate birthdate = LocalDate.now().minusYears(age);
						birthdateText.setText(DateUtil.formatDateFourDigitYear(birthdate));
						validStatusTracker.checkAll();
					} catch (NumberFormatException e) {
						log.error("Invalid age - {} could not be parsed as integer", ageStr, e);
						new Alert(AlertType.WARNING, "Cannot calculate birthdate from age " + ageStr).show();
					}
				}
			}

		});

		// If this is a customer add, pre-select the New, Active, and Month Registered
		// values
		if (isNew) {
			newChk.setSelected(true);
			activeChk.setSelected(true);
			monthRegisteredCbo.getSelectionModel().select(String.valueOf(DateUtil.getCurrentMonth()));
		}

		// Month Registered must be selected
		comboValidator = new ComboInputValidator(monthRegisteredCbo).add(new NotBlankValidator());
		monthRegisteredCbo.getSelectionModel().selectedItemProperty().addListener(comboValidator);

		validStatusTracker.add(householdIdCbo, personIdText, genderCbo, birthdateText, ageText, monthRegisteredCbo,
				newChk, activeChk);
		saveBtn.disableProperty().bind(validStatusTracker.validProperty().not());

		if (isNew) {
			// Pre-populate the person ID input - increment highest-known ID by one - when
			// the household ID combo changes
			householdIdCbo.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
				if (null == newValue) {
					return;
				}
				Map<Integer, List<Integer>> householdToPersonMap = input.getHouseholdToPersonMap();
				if (null != householdToPersonMap) {
					List<Integer> persons = householdToPersonMap.get(Integer.valueOf(newValue));
					int lastId = 0;
					if (null != persons) {
						for (int personId : persons) {
							if (personId > lastId) {
								lastId = personId;
							}
						}
					}
					savedCustomer.setPersonId(lastId + 1);
				}
			});
		}
	}

	@Override
	public String getTitle() {
		String type = "Add";
		if (null != input.getCustomer()) {
			type = "Edit";
		}
		return type + " Customer";
	}

	@Override
	public Customer getResponse() {
		return isSaved ? savedCustomer : null;
	}

	@Override
	public boolean isPositiveResponse() {
		return isSaved;
	}

	@Override
	public Image getIcon() {
		return Images.getImageView("table_key.png").getImage();
	}

	@Override
	public void setModalDialogParent(ModalDialog<AddEditCustomerDialogInput, Customer> parent) {
		this.parent = parent;
	}

	/**
	 * Converts a month name/abbreviation to its corresponding number
	 */
	class MonthConverter extends StringConverter<String> {
		private Map<String, String> monthToNumber = new HashMap<>();

		MonthConverter(List<String> monthNumbers) {
			for (String number : monthNumbers) {
				String name = DateUtil.getMonthName(Integer.parseInt(number));
				monthToNumber.put(name, number);
			}
		}

		@Override
		public String toString(String monthName) {
			if (NumberUtil.isNumeric(monthName)) {
				return fromString(monthName);
			}
			return monthName;
		}

		@Override
		public String fromString(String monthName) {
			if (!NumberUtil.isNumeric(monthName)) {
				return toString(monthName);
			}
			for (Map.Entry<String, String> entry : monthToNumber.entrySet()) {
				if (entry.getValue().equals(monthName)) {
					return entry.getKey();
				}
			}
			return "";
		}
	}
}
