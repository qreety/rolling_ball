/*
 * Name: Wendi Du
 * NetID: wxd140330
 * Date started: Feb.2nd, 2015
 * For Assignment 2, CS6301.001
 * This class contains the UI and related methods
 */
package uiproject1;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Wendi
 */
public class ProjectGUI extends javax.swing.JFrame {

	List<User> shw;
	JTextField [] jtx = new JTextField[10];  //The array of all the input fields
	boolean [] ept = new boolean[]{false,false, true, false, true, false, false, false, false, false}; //The array of whether an input field can be left blank or not
	int [] len = new int[]{20, 20, 1, 35, 35, 25, 2, 9, 21, 1}; //The array of each input field's max length
	JLabel[] jlb = new JLabel[10]; //The array of all the input fields' labels
	List<JTextField> ltx = new LinkedList<>();
	boolean edt = true; //To indicate whether the program is in edit status
	int cs;
	
    /**
     * Creates new form ProjectGUI and initialize variables
     * @param user The list of all user information
     */
    public ProjectGUI(List<User> user) {
        initComponents();
        setLocationRelativeTo(null);
        shw = user;
        jtx = new JTextField[]{fnField, lnField, mField, ad1Field, ad2Field, cField, sField, zField, pnField, gField};
        jlb = new JLabel[]{fnLb, lnLb, mLb, ad1Lb, ad2Lb, cLb, sLb, zLb, pnLb, gLb};
        for (int j = 0; j < jtx.length; j++)
        	ltx.add(jtx[j]);
        setTB();
        fnField.requestFocus();
    }
    
    /**
     * Save the data into the List of users
     * @param u The entered data
     */
    private void save(String[] u) {
        User us = new User(u[0], u[1], u[2], u[3], u[4], u[5], u[6], u[7], u[8], u[9]);
        shw.add(us);
    }

    @SuppressWarnings("serial")
	private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        showTB = new javax.swing.JTable();
        info = new javax.swing.JPanel();
        fnLb = new javax.swing.JLabel();
        fnField = new javax.swing.JTextField();
        lnLb = new javax.swing.JLabel();
        lnField = new javax.swing.JTextField();
        mLb = new javax.swing.JLabel();
        mField = new javax.swing.JTextField();
        ad1Lb = new javax.swing.JLabel();
        ad1Field = new javax.swing.JTextField();
        ad2Lb = new javax.swing.JLabel();
        ad2Field = new javax.swing.JTextField();
        cLb = new javax.swing.JLabel();
        cField = new javax.swing.JTextField();
        sLb = new javax.swing.JLabel();
        zLb = new javax.swing.JLabel();
        zField = new javax.swing.JTextField();
        pnLb = new javax.swing.JLabel();
        pnField = new javax.swing.JTextField();
        gLb = new javax.swing.JLabel();
        sField = new javax.swing.JTextField();
        gField = new javax.swing.JTextField();
        entBtn = new javax.swing.JButton();
        delBtn = new javax.swing.JButton();
        newBtn = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        status = new javax.swing.JTextArea();
        saveBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        
        

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        showTB.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "First Name", "Last Name", "M.", "Phone #"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        showTB.getTableHeader().setReorderingAllowed(false);
        showTB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                showTBKeyPressed(evt);
            }
        });
        showTB.addMouseListener(new java.awt.event.MouseAdapter() {
        	public void mouseClicked(java.awt.event.MouseEvent evt){
        		showTBMouseClicked(evt);
			}
        });
        
        
        jScrollPane1.setViewportView(showTB);
        if (showTB.getColumnModel().getColumnCount() > 0) {
            showTB.getColumnModel().getColumn(0).setResizable(false);
            showTB.getColumnModel().getColumn(0).setPreferredWidth(90);
            showTB.getColumnModel().getColumn(1).setResizable(false);
            showTB.getColumnModel().getColumn(1).setPreferredWidth(90);
            showTB.getColumnModel().getColumn(2).setResizable(false);
            showTB.getColumnModel().getColumn(2).setPreferredWidth(30);
            showTB.getColumnModel().getColumn(3).setResizable(false);
            showTB.getColumnModel().getColumn(3).setPreferredWidth(90);
        }

        info.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Details"));

        fnLb.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        fnLb.setText("First Name*");

        fnField.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N

        lnLb.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        lnLb.setText("Last Name*");

        lnField.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N

        mLb.setText("M.I.");

        ad1Lb.setText("Address Line 1*");

        ad2Lb.setText("Address Line 2");

        cLb.setText("City*");

        sLb.setText("State*");

        zLb.setText("ZipCode*");

        pnLb.setText("Phone Number*");

        gLb.setText("Gender*");

        entBtn.setMnemonic(KeyEvent.VK_ENTER);
        entBtn.setText("Enter");
        entBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                entBtnMouseClicked(evt);
            }
        });

        entBtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                entBtnKeyPressed(evt);
            }
        });

        delBtn.setText("Delete");
        delBtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                delBtnKeyPressed(evt);
            }
        });
        
        delBtn.addMouseListener(new java.awt.event.MouseAdapter(){
        	public void mouseClicked(java.awt.event.MouseEvent evt){
        		delBtnMouseClicked(evt);
        	}
        	 /**
        	  * Delete the selected contact and
        	  * clear all input fields to input new contact
        	  * @param evt
        	  */
			private void delBtnMouseClicked(MouseEvent evt) {
				delB();
			}
        });

        newBtn.setText("New");
        newBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newBtnMouseClicked(evt);
            }

            /**
             * clear all input fields and set the status to new
             * @param evt
             */
			private void newBtnMouseClicked(MouseEvent evt) {
				newB();
			}
        });

        newBtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                newBtnKeyPressed(evt);
            }
        });

        status.setEditable(false);
        status.setColumns(20);
        status.setRows(5);
        jScrollPane3.setViewportView(status);

        saveBtn.setText("Save");
        saveBtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                saveBtnKeyPressed(evt);
            }
        });
        
        saveBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveBtnMouseClicked(evt);
            }

            /**
             * Write the contact information into the txt file
             * @param evt
             */
			private void saveBtnMouseClicked(MouseEvent evt) {
				try {
	                IOreader.wrt(shw);
	            } catch (IOException ex) {
	                Logger.getLogger(ProjectGUI.class.getName()).log(Level.SEVERE, null, ex);
	            }
				status.setText("Record saved!");
				fnField.requestFocus();
			}
        });
    

        jLabel1.setText("Fields with asterisk (*) are mandatory.");

        javax.swing.GroupLayout infoLayout = new javax.swing.GroupLayout(info);
        info.setLayout(infoLayout);
        infoLayout.setHorizontalGroup(
            infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(infoLayout.createSequentialGroup()
                        .addComponent(fnLb)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fnField, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lnLb)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lnField, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mLb)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21))
                    .addGroup(infoLayout.createSequentialGroup()
                        .addGroup(infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(infoLayout.createSequentialGroup()
                                    .addGroup(infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(ad1Lb)
                                        .addComponent(ad2Lb))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(ad2Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(ad1Field, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(infoLayout.createSequentialGroup()
                                    .addComponent(cLb)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(sLb)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(sField, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(zLb)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(zField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(infoLayout.createSequentialGroup()
                                    .addComponent(pnLb)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(pnField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(gLb)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(gField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(infoLayout.createSequentialGroup()
                                    .addComponent(entBtn)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(newBtn)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(delBtn)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(saveBtn))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(infoLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        infoLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cField, fnField, lnField, pnField, zField});

        infoLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {ad1Field, ad2Field});

        infoLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {entBtn, delBtn, newBtn});

        infoLayout.setVerticalGroup(
            infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fnLb)
                    .addComponent(fnField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lnLb)
                    .addComponent(lnField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mLb)
                    .addComponent(mField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ad1Lb)
                    .addComponent(ad1Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ad2Lb, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ad2Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cLb)
                    .addComponent(cField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sLb)
                    .addComponent(zLb)
                    .addComponent(zField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pnLb)
                    .addComponent(pnField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gLb))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(entBtn)
                    .addComponent(newBtn)
                    .addComponent(delBtn)
                    .addComponent(saveBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        infoLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {delBtn, newBtn});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(info, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(info, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }

    /**
     * When the new button is pressed,
     * all the input fields are emptied
     * and the focus is on the first field
     * ready to input data
     * @param evt
     */
    
    private void newBtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_newBtnKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {  //get the names the user edit them
        	newB();
        }
        if (evt.getKeyCode() == KeyEvent.VK_LEFT)   //allow user to use left key to go back to add button
            entBtn.requestFocus();
    }//GEN-LAST:event_newBtnKeyPressed

    
    /**
     * If the current status is new,
     * enter new contact.
     * if the current status is edit,
     * then edit the selected contact
     * @param evt
     */
    private void entBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_entBtnMouseClicked
        	entB();
    }//GEN-LAST:event_entBtnMouseClicked

    /**
     * check the current mode is new or edit 
     * and respond
     */
    private void entB ()
    {
    	if ( showTB.getSelectedRow() == -1 && edt == true){
		        etrData();
		        setTB();
			}
			else {
				if (showTB.getSelectedRow() != -1)
					cs = showTB.getSelectedRow();
				String[] name = new String[3];
	            name[0] = fnField.getText();
	            name[1] = lnField.getText();
	            name[2] = mField.getText();
	            
	            editData(name,cs);
	            setTB();
	            }
    }
    
    /**
     * clear all the input fields,
     * set the mode to new
     * dis-select the contact
     * clear the status window
     */
    private void newB(){
    	setEmpty();
		fnField.requestFocus();
		status.setText(null);
		edt = true;
		showTB.clearSelection();
    }
    
    /**
     * delete the selected contact, 
     * set the mode to new
     */
    private void delB(){
    	int rr = showTB.getSelectedRow();
            shw.remove(rr);
            setEmpty();
            setTB();
            fnField.requestFocus();
            status.setText(null);
            edt = true;
    }
    
    /**
     * Delete the selected contact
     * and clear the input fields
     * @param evt
     */
    private void delBtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_delBtnKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) { //delete the user of the selected row
            delB();
        }
        if (evt.getKeyCode() == KeyEvent.VK_LEFT)
            newBtn.requestFocus();
    }//GEN-LAST:event_delBtnKeyPressed

    /**
     * if the current status is new,
     * enter new contact.
     * if the current status is edit,
     * then edit the selected contact
     * @param evt
     */
    private void entBtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_entBtnKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        	entB();
        }
        if (evt.getKeyCode() == KeyEvent.VK_LEFT)
            gField.requestFocus();
       
    }//GEN-LAST:event_entBtnKeyPressed

    /**
     * KeyEvent for the Table, 
     * when TAB key is pressed, user can edit the current selected contact,
     * User can use Arrow keys to go through the list 
     * and read the contact information of the selected contact
     * 
     * @param evt
     */
    private void showTBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_showTBKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
        	fnField.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            int rr = showTB.getSelectedRow() + 1;
            if (rr >= shw.size()) {
                rr = shw.size() - 1;
            }
            shwFd(rr);
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            int rr = showTB.getSelectedRow() - 1;
            if (rr < 0) {
                rr = 0;
            }
            shwFd(rr);
        }
    }//GEN-LAST:event_showTBKeyPressed

    /**
     * Click on the contact can show detail information
     * about the contact
     * @param evt
     */
    private void showTBMouseClicked(java.awt.event.MouseEvent evt) {                                    
        int r = showTB.getSelectedRow();
        shwFd(r);
    }   
    
    /**
     * Write the contact information into the txt file
     * @param evt
     */
    
    private void saveBtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_exiBtnKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER){
            try {
                IOreader.wrt(shw);
            } catch (IOException ex) {
                Logger.getLogger(ProjectGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            status.setText("Record saved!");
            fnField.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_LEFT)
        delBtn.requestFocus();
    }//GEN-LAST:event_exiBtnKeyPressed
        	
    
    /**
     * Checks if all the required fields are filled,
     * if a field does not meet the requirement,
     * the focus will go back to the field
     * @param a The entered contact information
     * @param r The current selected contact
     * @return if the contact information entered is valid
     */
    private boolean chkD(String[] a) {
        boolean blk = true; //indicate if there is empty field
        boolean gd;  //indicate if the gender is M or F
        boolean lg = true;  //indicate if the input has valid length
        ArrayList<JTextField> bnk = new ArrayList<>();
        
        for (int i = 0; i < 10; i++) {  //check if each field is nonempty and shorter than the max length
            if( ept[i] == false && a[i].isEmpty()){
            	status.append(jlb[i].getText() + " cannot be empty! \n");
            	blk = false;
            	bnk.add(jtx[i]);
            }

            	if( a[i].length() > len[i]){
            	status.append(jlb[i].getText() + " cannot be longer than " +len[i] +" character(s)! \n");
            	lg = false;
            	bnk.add(jtx[i]);
            	}
        }
       
        if (!isNumeric(a[7])){  //check if Zipcode is numeric
        	status.append("Invalid ZipCode! \n");   
        	blk = false;
        	bnk.add(jtx[7]);
        } 
        
        if (!isNumeric(a[8])){ // check if Phone number is numeric
        	status.append("Invalid Phone Number! \n");   
        	blk = false;
        	bnk.add(jtx[8]);
        } 
        
        if (a[9].equals("M") || a[9].equals("F")) {  //check if gender is M or F
            gd = true;
        } else {
            status.append("Gender must be M or F! \n");
            bnk.add(gField);
            gd = false;
        }
        
        if (!bnk.isEmpty()) { //Set the focus to the first invalid field
            ((JTextField) bnk.get(0)).requestFocus();
        }
 
        return blk && gd && lg; 
    }
    /**
     * Check whether a String contains only numbers
     * @param str The input String
     * @return if it only contains numbers
     */
    private boolean isNumeric(String str) {
    	  Pattern pattern = Pattern.compile("[0-9]*"); 
          return pattern.matcher(str).matches(); 
	}

	/**
     * Check if the contact already exist
     * @param a The entered contact information
     * @return if the contact entered is already recorded
     */
    private boolean chkE(String[] a) {
        boolean ex = true;
        
        if (shw.isEmpty())
        	return true;
        
        else{
        for (User n : shw) {  //check the first name, last name and mid initial
            if (n.firstName.equals(a[0]) && n.lastName.equals(a[1]) && n.mid.equals(a[2])) {
                ex = false;
                status.append("The person is already in record! \n");
                fnField.requestFocus();
            }
        }
        return ex;}
    }

    /**
    * Clean all fields
    */
    private void setEmpty() {
        for(int i = 0; i < 10; i++)
        	jtx[i].setText(null);
    }

    /**
     * Show the detail information about a contact 
     * in the Fields
     * @param a The index of the selected contact
     */
    private void shwFd(int a) {
        fnField.setText(shw.get(a).firstName);
        lnField.setText(shw.get(a).lastName);
        mField.setText(shw.get(a).mid);
        ad1Field.setText(shw.get(a).addLine1);
        ad2Field.setText(shw.get(a).addLine2);
        cField.setText(shw.get(a).city);
        sField.setText(shw.get(a).state);
        zField.setText(shw.get(a).zip);
        pnField.setText(shw.get(a).phoneNum);
        gField.setText(shw.get(a).gender);
    }

    /**
     * Get the newest contact list
     * and show the name and phone number
     * of the contacts on the table
     */
    private void setTB() {
        String[][] usr = new String[shw.size()][4];

        {
            for (int i = 0; i < shw.size(); i++) {
                usr[i][0] = shw.get(i).firstName;
                usr[i][1] = shw.get(i).lastName;
                usr[i][2] = shw.get(i).mid;
                usr[i][3] = shw.get(i).phoneNum;
            }
        }

        DefaultTableModel md = new DefaultTableModel(usr, new String[]{"First name", "Last name", "Mid.", "Phone number"});
        showTB.setModel(md);
    }

    /**
     * Check and save new contact
     */
    private void etrData() {
        status.setText(null);

        String[] u = new String[10];
        for( int i = 0; i < 10; i++)  //get the input from each field
        {
        	u[i] = jtx[i].getText();
        }

        boolean ex = chkE(u);
        boolean sv = chkD(u);

        if (sv && ex == true) {
            status.append("Contact added! \n");
            save(u);
            setEmpty();
            fnField.requestFocus();
        }
    }

    /**
     * Edit existed contact information
     * @param n The name of the contact before modification
     */
    private void editData(String[] n, int c) {
        status.setText(null);
        
        String[] u = new String[10];
        for( int i = 0; i < 10; i++)
        {
        	u[i] = jtx[i].getText();
        }
        
        if (u[0].equals(n[0]) && u[1].equals(n[1]) && u[2].equals(n[2])) {
		}
		else chkE(u);


        if (chkD(u)) { //if the edit is saved, remain the new mode
            User usr = shw.get(c);
            usr.firstName = u[0];
            usr.lastName = u[1];
            usr.mid = u[2];
            usr.addLine1 = u[3];
            usr.addLine2 = u[4];
            usr.city = u[5];
            usr.state = u[6];
            usr.zip = u[7];
            usr.phoneNum = u[8];
            usr.gender = u[9];
            
            status.append("Contact Saved!");
            setEmpty();
            fnField.requestFocus();
            edt =  true;
        }
        else
        	edt = false;  //if the edit is not valid, then change to edit mode
        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ad1Field;
    private javax.swing.JLabel ad1Lb;
    private javax.swing.JTextField ad2Field;
    private javax.swing.JLabel ad2Lb;
    private javax.swing.JButton entBtn;
    private javax.swing.JTextField cField;
    private javax.swing.JLabel cLb;
    private javax.swing.JButton delBtn;
    private javax.swing.JButton saveBtn;
    private javax.swing.JTextField fnField;
    private javax.swing.JLabel fnLb;
    private javax.swing.JTextField gField;
    private javax.swing.JLabel gLb;
    private javax.swing.JPanel info;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lnLb;
    private javax.swing.JTextField lnField;
    private javax.swing.JTextField mField;
    private javax.swing.JLabel mLb;
    private javax.swing.JTextField pnField;
    private javax.swing.JLabel pnLb;
    private javax.swing.JTextField sField;
    private javax.swing.JLabel sLb;
    private javax.swing.JButton newBtn;
    private javax.swing.JTable showTB;
    private javax.swing.JTextArea status;
    private javax.swing.JTextField zField;
    private javax.swing.JLabel zLb;
    // End of variables declaration//GEN-END:variables

}
