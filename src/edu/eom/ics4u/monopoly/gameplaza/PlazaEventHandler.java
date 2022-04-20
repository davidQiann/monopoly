package edu.eom.ics4u.monopoly.gameplaza;

import edu.eom.ics4u.monopoly.model.Model;
import edu.eom.ics4u.monopoly.game.GameGui;
import edu.eom.ics4u.monopoly.model.Event;

public class PlazaEventHandler extends Thread{
    private GamePlaza plaza;
    private Model model;
    
    public PlazaEventHandler(GamePlaza plaza) {
    	this.plaza = plaza;
    	model = Model.getInstance();
    }
    
    public void run() {
        System.out.println("Game Plaza event handler thread is runing.");
        
        while (true) {
        	// for each event type
        	Event event = model.getEventqueue().poll();
        	if (event != null) {
        		int roomId = event.getRoomid();
				String playerName = event.getUsername();
				System.out.printf("Game Plaza receive a event, room id = %d, player name = %s, event id = %d\n", roomId, playerName, event.getEventid());
				
        		switch (event.getEventid()) {
        			case Event.EVENT_USERJOIN:
        				userJoinHandler(event);
        				break;
        				
        			case Event.EVENT_STARTGAME:
        				startGameHandler(event);
        				break;
        			
        			case Event.EVENT_QUITGAME:
        				quitGameHandler(event);
        				break;
        		}
        	}

        	try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                //
            }
        }    	
    }
    
    public void userJoinHandler(Event event) {
    	int roomId = event.getRoomid();
    	String playerName = event.getUsername();
    	plaza.gRoomTableModel.updRoom(roomId); 
    	
    	if ((roomId == plaza.roomId) && (playerName == plaza.name)) {
    		plaza.isJoinRoom = true;
    	}
    }
    
    public void startGameHandler(Event event) {
    	int roomId = event.getRoomid();
    	String playerName = event.getUsername();
    	plaza.gRoomTableModel.updRoom(roomId); 
    	
    	if (roomId == plaza.roomId) {
    		GameGui game = new GameGui(roomId,playerName);
    		game.mapPanel.setTurn(playerName);
    	}
    }
    
    public void quitGameHandler(Event event){
    	int roomId = event.getRoomid();
    	String playerName = event.getUsername();
    	plaza.gRoomTableModel.updRoom(roomId); 
    	
    	if ((roomId == plaza.roomId) && (playerName == plaza.name)) {
    		plaza.isJoinRoom = false;
    	}     	
    }
    
    
    
}
