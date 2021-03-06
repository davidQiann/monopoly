
package edu.eom.ics4u.monopoly.gameplaza;


import javax.swing.table.AbstractTableModel;

import edu.eom.ics4u.monopoly.model.Model;
import edu.eom.ics4u.monopoly.model.RoomModel;
import edu.eom.ics4u.monopoly.game.Player;

// Author: Sirui
// The GRoomTableModel manages that data of the game room table 
// that is implemented with the JTable, and actually this table 
// data is copied from the backend. 
public class GRoomTableModel extends AbstractTableModel {	
	private final int NUM_ROOMS = 50;
	
	private String[] columnNames = {"Room ID",
									"Mode",
                                    "Status",
                                    "Player 0",
                                    "Player 1",
                                    "Player 2",
                                    "Player 3",
                                    "Players",
                                    "Choose a Room"};    
	private Object[][] data = new Object [NUM_ROOMS][columnNames.length];
	
	public static final int COL_ROOM_ID = 0;
	public static final int COL_MODE    = 1;
	public static final int COL_STATUS  = 2;
	public static final int COL_PLAYER0 = 3;
	public static final int COL_PLAYER1 = 4;
	public static final int COL_PLAYER2 = 5;
	public static final int COL_PLAYER3  = 6;
	public static final int COL_NPLAYERS = 7;
	public static final int COL_CHOOSE_ROOM = 8;
	
	private int roomId = 0; 
	
	public GRoomTableModel () {
		for (int i = 0; i < NUM_ROOMS; i++) {
			data[i][COL_ROOM_ID] = Integer.valueOf(i);
			data[i][COL_MODE]    = "Online";
			data[i][COL_STATUS]  = "Empty";
			data[i][COL_PLAYER0] = "";
			data[i][COL_PLAYER1] = "";
			data[i][COL_PLAYER2] = "";
			data[i][COL_PLAYER3] = "";
			data[i][COL_NPLAYERS] = Integer.valueOf(0);
			data[i][COL_CHOOSE_ROOM] = Boolean.FALSE;
		}
		data[0][COL_MODE] = "Man vs Mechine";
		data[1][COL_MODE] = "Standalone";
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
    
    public void updRoom(int roomId) {
    	RoomModel roomModel = Model.getInstance().rooms.get(roomId);
    	
    	int nplayers = roomModel.players.size();
    	setValueAt(nplayers, roomId, COL_NPLAYERS);
    	
    	int roomStatus = roomModel.getStatus(); 
    	if (nplayers == 0) {
    		setValueAt("Empty", roomId, COL_STATUS);
    	} else if (roomStatus == RoomModel.STATUS_PENDING) {
    		if (nplayers == 4) {
    			setValueAt("Full", roomId, COL_STATUS);
    		} else {
    			setValueAt("Waiting to start", roomId, COL_STATUS);
    		}    		
    	} else if (roomStatus == RoomModel.STATUS_STARTED) { 
    		setValueAt("Playing", roomId, COL_STATUS);
    	} else {
    		setValueAt("Empty", roomId, COL_STATUS);
    	}
    	
    	// clear table view for player
    	for (int i = 0; i < 4; i++) {
    		setValueAt("", roomId, COL_PLAYER0+i);
    	}
    	
    	// update table view for player
    	int playerId;
    	String playerName;
    	for (Player player: roomModel.players.values()) {
    		playerId = player.getPlayerId();
    		playerName = player.getName();
    		setValueAt(playerName, roomId, COL_PLAYER0+playerId);
    	}     	
    }
    
}

 