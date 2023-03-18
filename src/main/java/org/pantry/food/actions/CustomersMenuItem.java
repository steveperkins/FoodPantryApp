package org.pantry.food.actions;

import org.pantry.food.Images;

import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

/**
 * Customers menu display configuration 
 */
public class CustomersMenuItem extends MenuItem {
	public static final String ACTION_ID = "open.customers";

	public CustomersMenuItem() {
		setId(ACTION_ID);
        setAccelerator(KeyCombination.keyCombination("shortcut+c"));
        setGraphic(Images.getImageView("table_key.png"));
        setText("Customers");
	}
	
}