
package edu.eom.ics4u.monopoly.gameplaza;


import javax.swing.table.AbstractTableModel;

public class GRoomTableModel extends AbstractTableModel {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3009516032021471955L;

	private final int NUM_ROOMS = 50;
	
	private String[] columnNames = {"Room ID",
                                    "Status",
                                    "Player 0",
                                    "Player 1",
                                    "Player 2",
                                    "Player 3",
                                    "Players",
                                    "Choose a Room"};    
	private Object[][] data = new Object [NUM_ROOMS][columnNames.length];
	
	private static final int COL_ROOM_ID = 0;
	private static final int COL_STATUS  = 1;
	private static final int COL_PLAYER0 = 2;
	private static final int COL_PLAYER1 = 3;
	private static final int COL_PLAYER2 = 4;
	private static final int COL_PLAYER3  = 5;
	private static final int COL_NPLAYERS = 6;
	private static final int COL_CHOOSE_ROOM = 7;
	
	private int roomId = 0; 
	
	public GRoomTableModel () {
		for (int i = 0; i < NUM_ROOMS; i++) {
			data[i][COL_ROOM_ID] = Integer.valueOf(i);
			data[i][COL_STATUS]  = "Empty";
			data[i][COL_PLAYER0] = "";
			data[i][COL_PLAYER1] = "";
			data[i][COL_PLAYER2] = "";
			data[i][COL_PLAYER3] = "";
			data[i][COL_NPLAYERS] = Integer.valueOf(0);
			data[i][COL_CHOOSE_ROOM] = Boolean.FALSE;
		}
	}

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
        return getValueAt(0, c).getClass();
    }

    public boolean isCellEditable(int row, int col) {
        if (col == COL_CHOOSE_ROOM) {
        	return true;
        } else {
        	return false;
        }
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(Object value, int row, int col) {
    	if (col == COL_CHOOSE_ROOM) {
    		for(int i = 0; i < NUM_ROOMS; i++) {
    			data[i][col] = Boolean.FALSE;
    			fireTableCellUpdated(i, col);
    		}
    		
    		roomId = row;
    		data[row][col] = true;
            fireTableCellUpdated(row, col); 
    		System.out.printf("Test value- Roon ID %d is selected.\n", roomId);    		
    	} else {
    		data[row][col] = value;
            fireTableCellUpdated(row, col);
    	}
    }
    
    public int getRoomId() {
    	return roomId;
    }
    
    public String getRoomStatus(int roomId) {
    	return (String)data[roomId][COL_STATUS];
    }
    
    public boolean isRoomSelected(int roomId){
    	return (boolean)data[roomId][COL_CHOOSE_ROOM];
    }
    
}

 