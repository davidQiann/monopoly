package edu.eom.ics4u.monopoly.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class TransferDialog extends JDialog implements ActionListener {
	private JLabel fromLabel, toLabel, amountLabel, errInfoLable;
	private JButton confirmButton, cannelButton;
	private JTextField amountTextField;
	private JComboBox<String> fromComboBox, toComboBox;
	
	private String[] fromListData = new String[]{"Cash", "Saving"};
	private String[] toListData = new String[]{"Saving", "Cash"};
	private int fromSelectedId = 0, toSelectedId = 0;
	
	public String fromAccount = "Cash", toAccount = "Saving";
	public int amount = 0;
	public boolean transferReq = false;
	
	int oldCash;
	int oldSaving;
	
	public TransferDialog (int oldCash, int oldSaving) {
		this.oldCash = oldCash;
		this.oldSaving = oldSaving;
		
		setTitle("Bank Transfer:");
		setModal(true);
		setSize(380, 170);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		
		fromLabel = new JLabel("From");
		toLabel = new JLabel("to");
		amountLabel = new JLabel("Amount");
		
		JComboBox<String> fromComboBox = new JComboBox<String>(fromListData);
		JComboBox<String> toComboBox = new JComboBox<String>(toListData);
		fromComboBox.setSelectedIndex(fromSelectedId);
		toComboBox.setSelectedIndex(toSelectedId);
		
		fromComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                	fromSelectedId = fromComboBox.getSelectedIndex();
                }
            }
        });
		
		toComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                	toSelectedId = toComboBox.getSelectedIndex();
                }
            }
        });	
		
		amountTextField = new JTextField(8);		
		fromLabel.setBounds      ( 10, 20,  50, 20);
		fromComboBox.setBounds   ( 60, 20, 120, 20);
		toLabel.setBounds        (190, 20,  25, 20);
		toComboBox.setBounds     (215, 20, 120, 20);
		amountLabel.setBounds    ( 10, 50,  50, 20);		
		amountTextField.setBounds( 60, 50, 120, 20);
		
		confirmButton = new JButton("Confirm");
		cannelButton = new JButton("Cannel");
		confirmButton.setBounds(190-80-5, 95, 80, 30);
		cannelButton.setBounds (190+5,    95, 80, 30);
		
		errInfoLable = new JLabel("");
		errInfoLable.setForeground(Color.red);
		errInfoLable.setFont(new Font("Dialog", Font.PLAIN, 12));
		errInfoLable.setBounds(10, 77, 350, 10);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);		
		panel.add(fromLabel);
		panel.add(toLabel);
		panel.add(fromComboBox);
		panel.add(toComboBox);
		panel.add(amountLabel);
		panel.add(amountTextField);		
		panel.add(confirmButton);
		panel.add(cannelButton);
		panel.add(errInfoLable);
		
		confirmButton.addActionListener(this);
		cannelButton.addActionListener(this);
		
		add(panel);
		setVisible(true);
	}
    
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == confirmButton) {
			
			fromAccount = fromListData[fromSelectedId];
			toAccount   = toListData[toSelectedId];
			amount = 0;
			try {
				amount = Integer.parseInt(amountTextField.getText());
			} catch (NumberFormatException e1) {
                //System.err.println("Exception: " + e1);
				amount = -1;
				String text = String.format("Your input must be digital.");
				errInfoLable.setText(text);
			}
			
			transferReq = false;
			if (fromAccount.equals(toAccount)) {
				errInfoLable.setText("The two accounts must be different!");
			} else if (fromAccount.equals("Cash") && (amount > oldCash)) {
				String text = String.format("Your Cash funds ($%d) is insufficient to transfer", oldCash);
				errInfoLable.setText(text);
			} else if (fromAccount.equals("Saving") && (amount > oldSaving)) {
				String text = String.format("Your Saving funds ($%d) is insufficient to transfer", oldSaving);
				errInfoLable.setText(text);
			} else if (amount > 0){
				transferReq = true;
				this.dispose();
			}
		}
		
		if (e.getSource() == cannelButton) {
			transferReq = false;
			this.dispose();
		}
		
	}
}
