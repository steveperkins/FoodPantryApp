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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import org.pantry.food.dao.CustomersDao;
import org.pantry.food.model.Customer;
import org.pantry.food.ui.common.FormState;
import org.pantry.food.ui.dialog.AddEditCustomerDialog;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The Class FrameCustomers.
 *
 * @author Dave Johnson
 */
public class FrameCustomers extends javax.swing.JInternalFrame 
{
	
	/** The Constant log. */
	private final static Logger log = Logger.getLogger(FrameCustomers.class.getName());
	
    /** The btn add. */
    private JButton btnAdd;
    
    /** The btn delete. */
    private JButton btnDelete;
    
    /** The btn edit. */
    private JButton btnEdit;
    
    /** The j scroll pane 1. */
    private JScrollPane jScrollPane1;
    
    /** The j table 1. */
    private JTable jTable1;
    
    /** The j tool bar 1. */
    private JToolBar jToolBar1;

    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = -792105112714075751L;

	/**
	 *  Creates new form FrameCustomers.
	 */
    public FrameCustomers() {
        initComponents();
        loadCustomers();
    }
    
    /**
     * Instantiates a new frame customers.
     *
     * @param houseHoldId the house hold id
     */
    public FrameCustomers(int houseHoldId)
    {
    	initComponents();
        loadCustomers();
        findHouseholdById(houseHoldId);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings({ "unchecked", "serial" })
    private void initComponents() {
    	
        jToolBar1 = new JToolBar();
        btnAdd = new JButton();
        btnEdit = new JButton();
        btnDelete = new JButton();
        jScrollPane1 = new JScrollPane();
        jTable1 = new JTable();
        jTable1.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent evt) {
        		if (evt.getClickCount() == 2) 
        		{
        			btnEditActionPerformed(null); 
        	    }
        	}
        });

        setClosable(true);
        setTitle("Customers"); 
        setFrameIcon(new ImageIcon(FrameCustomers.class.getResource("/org/pantry/food/resources/images/table_key.png"))); 
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

        btnAdd.setIcon(new ImageIcon(FrameCustomers.class.getResource("/org/pantry/food/resources/images/table_add.png"))); 
        btnAdd.setText("Add Customer"); 
        btnAdd.setFocusable(false);
        btnAdd.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAdd.setName("btnAdd"); 
        btnAdd.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAdd);

        btnEdit.setIcon(new ImageIcon(FrameCustomers.class.getResource("/org/pantry/food/resources/images/table_edit.png"))); 
        btnEdit.setText("Edit Customer"); 
        btnEdit.setFocusable(false);
        btnEdit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEdit.setName("btnEdit"); 
        btnEdit.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        jToolBar1.add(btnEdit);

        btnDelete.setIcon(new ImageIcon(FrameCustomers.class.getResource("/org/pantry/food/resources/images/table_delete.png"))); 
        btnDelete.setText("Deactivate Customer"); 
        btnDelete.setFocusable(false);
        btnDelete.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDelete.setName("btnDelete"); 
        btnDelete.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jToolBar1.add(btnDelete);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.NORTH);

        jScrollPane1.setName("jScrollPane1"); 

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Customer Id", "HouseHold Id", "Person Id", "Gender", "Birth Date", "Age", "Month Registered", "New?", "Comments", "Active?"
            }
        ) {
            @SuppressWarnings("rawtypes")
			Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Boolean.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            @SuppressWarnings("rawtypes")
			@Override
			public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            @Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setName("jTable1"); 
        jTable1.setShowGrid(true);
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pack();
    }

    /**
     * Btn add action performed.
     *
     * @param evt the evt
     */
    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {

        AddEditCustomerDialog dial = new AddEditCustomerDialog(null, true);
        dial.setExistingCustomerIds(currentCustomers);
        dial.getNewCustomer().setCustomerId(this.nextCustomerId);
        dial.getNewCustomer().setActive(true);
        dial.setLocationRelativeTo(this);

        dial.setVisible(true);

        if (dial.getOkCancel()){

            custIo.add(dial.getNewCustomer());

            DefaultTableModel model = (DefaultTableModel)this.jTable1.getModel();
            model.addRow(dial.getNewCustomer().getCustomerObject());

            saveCustomers();
            nextCustomerId++;
        }
    }

    /**
     * Btn edit action performed.
     *
     * @param evt the evt
     */
    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {

        Customer newCust = getSelectedCustomer();

        if (newCust != null){
            AddEditCustomerDialog dial = new AddEditCustomerDialog(null, true);
            dial.setExistingCustomerIds(currentCustomers);
            dial.setNewCustomer(newCust);
            dial.setLocationRelativeTo(this);
            dial.setVisible(true);

            if (dial.getOkCancel()){
                custIo.edit(dial.getNewCustomer());
                saveCustomers();
                loadCustomers();
            }

        } else {
            JOptionPane.showMessageDialog(this, "Please select a customer to edit.");
        }
    }

    /**
     * Btn delete action performed.
     *
     * @param evt the evt
     */
    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {

        Customer newCust = getSelectedCustomer();

        if (newCust != null){

            int result = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to deactivate the selected customer?",
                "Confirm", JOptionPane.YES_NO_OPTION);

            if (result == JOptionPane.YES_OPTION){
                //custIo.deleteCustomer(newCust);
                newCust.setActive(false);
                custIo.edit(newCust);

                saveCustomers();
                loadCustomers();
            }

        } else {
            JOptionPane.showMessageDialog(this, "Please select a customer to delete.");
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

    /** The cust io. */
    private CustomersDao custIo = new CustomersDao();
    
    /** The next customer id. */
    private int nextCustomerId = 0;
    
    /** The current customers. */
    private ArrayList<String> currentCustomers = new ArrayList<String>();

    /**
     * Load customers.
     */
    private void loadCustomers(){

        try {
            custIo.read();
            DefaultTableModel model = (DefaultTableModel)this.jTable1.getModel();
            model.setRowCount(0);

            for (int i = 0; i < custIo.getCustomerCount(); i++){
                Customer cust = custIo.getCustomerList().get(i);

                if (FormState.getInstance().isShowInactiveCustomers()){
                    model.addRow(cust.getCustomerObject());
                } else {
                    if (cust.isActive()){
                        model.addRow(cust.getCustomerObject());
                    }
                }

                currentCustomers.add("" + cust.getHouseholdId());

                if (cust.getCustomerId() >= nextCustomerId){
                    nextCustomerId = cust.getCustomerId() + 1;
                }
            }

        } catch (ArrayIndexOutOfBoundsException ex){
        	log.log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this,"Customer file found, but it is incorrect.\n" + ex.getMessage());
        } catch (FileNotFoundException ex) {
            log.log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this,"Customer file not found/n" + ex.getMessage());
        } catch (IOException ex) {
            log.log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this,"Problem opening file/n" + ex.getMessage());
        }

    }

    /**
     * Save customers.
     */
    private void saveCustomers() 
    {
        try 
        {
            custIo.persist();
        } catch (IOException ex) {
            log.log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this,"Problem saving file/n" + ex.getMessage());
        }

    }

    /**
     * Gets the selected customer.
     *
     * @return the selected customer
     */
    private Customer getSelectedCustomer()
    {
        Customer customer = null;

        int irow = this.jTable1.getSelectedRow();

        if (irow > -1){
            int iId = Integer.parseInt(this.jTable1.getModel().getValueAt(irow, 0).toString());

            for (int i = 0; i < this.custIo.getCustomerCount(); i++){
                Customer cust = this.custIo.getCustomerList().get(i);
                if (cust.getCustomerId() == iId){
                    customer = cust;
                    break;
                }
            }

        }

        return customer;
    }
    
    /**
     * Find household by id.
     *
     * @param houseHoldId the house hold id
     */
    private void findHouseholdById(int houseHoldId)
    {
    	jTable1.clearSelection();
    	
    	for (int i = 0; i < jTable1.getRowCount(); i++)
    	{
    		int iId = Integer.parseInt(jTable1.getModel().getValueAt(i, 1).toString());
    		if (iId == houseHoldId)
    		{
    			jTable1.setRowSelectionInterval(i, i);
    			break;
    		}
    		
    	}
    }

}
