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
package org.pantry.food.ui.frame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import org.pantry.food.dao.VisitorsDao;
import org.pantry.food.model.Visit;
import org.pantry.food.ui.common.FormState;
import org.pantry.food.ui.common.ProblemTableCellRender;
import org.pantry.food.ui.dialog.AddEditVisitDialog;
import org.pantry.food.util.DateUtil;

/**
 * The Class FrameVisits.
 *
 * @author mcfarland_davej
 */
@SuppressWarnings("unused")
public class FrameVisits extends javax.swing.JInternalFrame {

	/** The Constant log. */
	private final static Logger log = Logger.getLogger(FrameVisits.class.getName());

	/** The j button 1. */
	private JButton jButton1;

	/** The j button 2. */
	private JButton jButton2;

	/** The j button 3. */
	private JButton jButton3;

	/** The j scroll pane 1. */
	private JScrollPane jScrollPane1;

	/** The j table 1. */
	private JTable jTable1;

	/** The j tool bar 1. */
	private JToolBar jToolBar1;

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2823894451351907109L;

	/** The desktop. */
	private JDesktopPane desktop;

	/**
	 * Creates new form FrameVisits.
	 *
	 * @param desktop the desktop
	 */
	public FrameVisits(JDesktopPane desktop) {
		this.desktop = desktop;
		initComponents();
		this.jTable1.setDefaultRenderer(Object.class, new ProblemTableCellRender());
		loadVisits();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings({ "unchecked", "serial" })
	private void initComponents() {

		jToolBar1 = new javax.swing.JToolBar();
		jButton1 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton();
		jButton3 = new javax.swing.JButton();
		jScrollPane1 = new javax.swing.JScrollPane();

		jTable1 = new javax.swing.JTable();
		jTable1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() == 2) {
					jButtonEditActionPerformed(null);
				}
			}
		});

		setClosable(true);
		setTitle("Visits");
		setFrameIcon(new ImageIcon(FrameVisits.class.getResource("/org/pantry/food/resources/images/cart.png")));
		setName("Form");
		addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
			public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
			}

			public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
			}

			public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
				formInternalFrameClosed(evt);
			}

			public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
			}

			public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
			}

			public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
			}

			public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
			}
		});

		jToolBar1.setFloatable(false);
		jToolBar1.setRollover(true);
		jToolBar1.setName("jToolBar1");

		jButton1.setIcon(
				new ImageIcon(FrameVisits.class.getResource("/org/pantry/food/resources/images/cart_add.png")));
		jButton1.setText("Add Visit");
		jButton1.setFocusable(false);
		jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton1.setName("btnAdd");
		jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonAddActionPerformed(evt);
			}
		});
		jToolBar1.add(jButton1);

		jButton2.setIcon(
				new ImageIcon(FrameVisits.class.getResource("/org/pantry/food/resources/images/cart_edit.png")));
		jButton2.setText("Edit Visit");
		jButton2.setFocusable(false);
		jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton2.setName("btnEdit");
		jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonEditActionPerformed(evt);
			}
		});
		jToolBar1.add(jButton2);

		jButton3.setIcon(
				new ImageIcon(FrameVisits.class.getResource("/org/pantry/food/resources/images/cart_delete.png")));
		jButton3.setText("Deactivate Visit");
		jButton3.setFocusable(false);
		jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton3.setName("btnDelete");
		jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jButton3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonDeleteActionPerformed(evt);
			}
		});
		jToolBar1.add(jButton3);

		getContentPane().add(jToolBar1, java.awt.BorderLayout.NORTH);

		jScrollPane1.setName("jScrollPane1");

		jTable1.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "Visit ID", "HouseHold Id", "New?", "# Adults", "# Kids", "# Seniors", "Working Income",
				"Other Income", "No Income", "Date", "Week Number" }) {
			@SuppressWarnings("rawtypes")
			Class[] types = new Class[] { java.lang.Integer.class, java.lang.Integer.class, java.lang.Boolean.class,
					java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Boolean.class,
					java.lang.Boolean.class, java.lang.Boolean.class, java.lang.String.class, java.lang.Integer.class };
			boolean[] canEdit = new boolean[] { false, false, false, false, false, false, false, false, false, false,
					false };

			@SuppressWarnings("rawtypes")
			@Override
			public Class getColumnClass(int columnIndex) {
				return types[columnIndex];
			}

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		jTable1.setGridColor(new java.awt.Color(0, 0, 0));
		jTable1.setName("jTable1");
		jTable1.setShowGrid(true);
		jTable1.getTableHeader().setReorderingAllowed(false);
		jScrollPane1.setViewportView(jTable1);
		jTable1.getColumnModel().getColumn(0).setHeaderValue("Visit ID");
		jTable1.getColumnModel().getColumn(1).setHeaderValue("HouseHold Id");
		jTable1.getColumnModel().getColumn(2).setHeaderValue("New?");
		jTable1.getColumnModel().getColumn(3).setHeaderValue("# Adults");
		jTable1.getColumnModel().getColumn(4).setHeaderValue("# Kids");
		jTable1.getColumnModel().getColumn(5).setHeaderValue("# Seniors");
		jTable1.getColumnModel().getColumn(6).setHeaderValue("Working Income");
		jTable1.getColumnModel().getColumn(7).setHeaderValue("Other Income");
		jTable1.getColumnModel().getColumn(8).setHeaderValue("No Income");
		jTable1.getColumnModel().getColumn(9).setHeaderValue("Date");
		jTable1.getColumnModel().getColumn(10).setHeaderValue("Week Number");
		jTable1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evnt) {
				if (evnt.getClickCount() == 2) {
					openCustomers();
				}
			}
		});

		getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

		pack();
	}

	/**
	 * J button add action performed.
	 *
	 * @param evt the evt
	 */
	private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {

		AddEditVisitDialog dial = new AddEditVisitDialog(null, true);
		dial.setVisitorList(currentVisitors);
		Visit vis = new Visit();
		vis.setVisitId(this.nextVisitId);
		vis.setVisitDate(DateUtil.getCurrentDateString());
		dial.setNewVisit(vis);
		dial.setLocationRelativeTo(this);

		dial.setVisible(true);

		if (dial.getOkCancel()) {

			visIo.add(dial.getNewVisit());

			DefaultTableModel model = (DefaultTableModel) this.jTable1.getModel();
			model.addRow(dial.getNewVisit().getVisitObject());

			saveVisits();
			nextVisitId++;
		}

	}

	/**
	 * J button edit action performed.
	 *
	 * @param evt the evt
	 */
	private void jButtonEditActionPerformed(java.awt.event.ActionEvent evt) {

		Visit newVis = getSelectedVisit();

		if (newVis != null) {
			AddEditVisitDialog dial = new AddEditVisitDialog(null, true);
			dial.setVisitorList(currentVisitors);
			dial.setNewVisit(newVis);
			dial.setLocationRelativeTo(this);
			dial.setVisible(true);

			if (dial.getOkCancel()) {
				visIo.edit(dial.getNewVisit());
				saveVisits();
				loadVisits();
			}

		} else {
			JOptionPane.showMessageDialog(this, "Please select a visit to edit.");
		}

	}

	/**
	 * J button delete action performed.
	 *
	 * @param evt the evt
	 */
	private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {

		Visit newVisit = getSelectedVisit();

		if (newVisit != null) {

			int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to deactivate the selected visit?",
					"Confirm", JOptionPane.YES_NO_OPTION);

			if (result == JOptionPane.YES_OPTION) {
				// visIo.deleteVisit(newVisit);
				newVisit.setActive((false));
				visIo.edit(newVisit);

				saveVisits();
				loadVisits();
			}

		} else {
			JOptionPane.showMessageDialog(this, "Please select a visit to delete.");
		}
	}

	/**
	 * Open customers.
	 */
	private void openCustomers() {
		Visit vis = getSelectedVisit();

		if (vis != null) {
			try {
				// System.out.println("customer:" + vis.getHouseholdId());
				FrameCustomers customers = new FrameCustomers(vis.getHouseholdId());
				desktop.add(customers);
				customers.setMaximum(true);
				customers.setVisible(true);
				FormState.getInstance().setFormOpen(true, customers);

				this.dispose();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}

	}

	/**
	 * Form internal frame closed.
	 *
	 * @param evt the evt
	 */
	private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {

		FormState.getInstance().setFormOpen(false);

	}

	/** The vis io. */
	private VisitorsDao visIo = new VisitorsDao();

	/** The next visit id. */
	private int nextVisitId = 0;

	/** The current visitors. */
	private ArrayList<String> currentVisitors = new ArrayList<String>();

	/**
	 * Load visits.
	 */
	private void loadVisits() {
		try {
			visIo.read();

			DefaultTableModel model = (DefaultTableModel) this.jTable1.getModel();
			model.setRowCount(0);

			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Calendar cal = Calendar.getInstance();
			Calendar visCal = Calendar.getInstance();

			for (int i = 0; i < visIo.getVisitCount(); i++) {
				Visit vis = visIo.getVisitList().get(i);

				Date visDate = dateFormat.parse(vis.getVisitDate());
				visCal.setTime(visDate);

				if (FormState.getInstance().isShowInactiveVisits()) {

					if (FormState.getInstance().isShowAllYearVisits()) {
						model.addRow(vis.getVisitObject());
					} else {
						if (visCal.get(Calendar.MONTH) == cal.get(Calendar.MONTH)) {
							model.addRow(vis.getVisitObject());
						}
					}

				} else {
					if (vis.isActive()) {
						if (FormState.getInstance().isShowAllYearVisits()) {
							model.addRow(vis.getVisitObject());
						} else {
							if (visCal.get(Calendar.MONTH) == cal.get(Calendar.MONTH)) {
								model.addRow(vis.getVisitObject());
							}
						}
					}
				}

				currentVisitors.add("" + vis.getHouseholdId());

				if (vis.getVisitId() >= nextVisitId) {
					nextVisitId = vis.getVisitId() + 1;
				}
			}

		} catch (ArrayIndexOutOfBoundsException ex) {
			log.log(Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(this, "Vistors file found, but it is incorrect.\n" + ex.getMessage());
		} catch (FileNotFoundException ex) {
			log.log(Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(this, "Vistors file not found./n" + ex.getMessage());
		} catch (IOException ex) {
			log.log(Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(this, "Problem opening file/n" + ex.getMessage());
		} catch (java.text.ParseException ex) {
			log.log(Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(this, "Problem opening file/n" + ex.getMessage());
		}

	}

	/**
	 * Save visits.
	 */
	private void saveVisits() {
		try {
			visIo.persist();
		} catch (IOException ex) {
			log.log(Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(this, "Problem saving file/n" + ex.getMessage());
		}

	}

	/**
	 * Gets the selected visit.
	 *
	 * @return the selected visit
	 */
	private Visit getSelectedVisit() {

		Visit visit = null;

		int irow = this.jTable1.getSelectedRow();

		if (irow > -1) {
			int iVisitId = Integer.parseInt(this.jTable1.getModel().getValueAt(irow, 0).toString());

			for (int i = 0; i < this.visIo.getVisitCount(); i++) {
				Visit vis = this.visIo.getVisitList().get(i);
				if (vis.getVisitId() == iVisitId) {
					visit = vis;
					break;
				}
			}

		}

		return visit;
	}

}
