package edu.eom.ics4u.monopoly.game;

import javax.swing.table.AbstractTableModel;

import edu.eom.ics4u.monopoly.model.Model;
import edu.eom.ics4u.monopoly.model.RoomModel;

public class ChartsTableModel extends AbstractTableModel {
    public static final int COL_NAME   = 0;
    public static final int COL_ESTATE = 1;
    public static final int COL_CASH   = 2;
    public static final int COL_SAVING = 3;
    public static final int COL_LOAN   = 4;
    public static final int COL_TOTAL  = 5;
    
	private String[] columnNames = {"Name", "Estate", "Cash", "Saving", "Loan", "Total"};	
	private Object[][] data = {
		{"","","","","",""},
		{"","","","","",""},
		{"","","","","",""},
		{"","","","","",""}
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
    
    public void updChartsTable(int roomId) {
    	RoomModel roomModel = Model.getInstance().rooms.get(roomId);
    	
    	for (Player player: roomModel.players.values()) {
    		int playerId = player.getPlayerId();
    		int estate = 0;
        	for (int i = 0; i < roomModel.properties.length; i++) {
        	    if (roomModel.properties[i].getOwnerName() == player.getName()) {
        	    	estate = estate + roomModel.properties[i].getValue();
        	    }
        	} 
        	
        	int total = estate + player.getCash() + player.getSaving() + player.getLoan();
        	data[playerId][COL_ESTATE] = estate;
        	data[playerId][COL_CASH] = player.getCash();
        	data[playerId][COL_SAVING] = player.getSaving();
        	data[playerId][COL_LOAN] = player.getLoan();
        	data[playerId][COL_TOTAL] = total;
        	data[playerId][COL_NAME] = player.getName();        	
    	}
    	
    	for (int row = 0; row < getRowCount(); row++) {
    		for (int col = 0; col < getColumnCount(); col++) {
    			fireTableCellUpdated(row, col);
    		}
    	}
    }
}
