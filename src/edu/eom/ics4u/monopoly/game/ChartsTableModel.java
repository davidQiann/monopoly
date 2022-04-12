package edu.eom.ics4u.monopoly.game;

import javax.swing.table.AbstractTableModel;

public class ChartsTableModel extends AbstractTableModel {
	
	private String[] columnNames = {"Name", "Estate", "Cash", "Saving", "Loan"};	
	private Object[][] data = {
		{"","","","",""},
		{"","","","",""},
		{"","","","",""},
		{"","","","",""}
	};

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    public Class getColumnClass(int c) {
    	if(c ==0) {
    		return "".getClass();
    	} else {
    		return Integer.valueOf(0).getClass();
    	}
        
    }

    public boolean isCellEditable(int row, int col) {
        return false;
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(Object value, int row, int col) {
    	data[row][col] = Boolean.FALSE;
		fireTableCellUpdated(row, col);
    }
    
}
