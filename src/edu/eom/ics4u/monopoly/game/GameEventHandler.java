package edu.eom.ics4u.monopoly.game;

import edu.eom.ics4u.monopoly.game.GameGui;
import edu.eom.ics4u.monopoly.game.Player;
import edu.eom.ics4u.monopoly.model.Model;
import edu.eom.ics4u.monopoly.model.Event;
import edu.eom.ics4u.monopoly.model.RoomModel;

public class GameEventHandler extends Thread{
    private GameGui game;
    private int roomId;
    public RoomModel roomModel;
    //private Model model= Model.getInstance();
    
    public GameEventHandler(GameGui gameGui, int roomId) {
    	this.roomId = roomId;
    	game = gameGui;
    	roomModel = Model.getInstance().rooms.get(roomId);
    }
    
    public void run() {
    	System.out.println("Game Event handler thread is runing.");
    	Event event;
    	
    	while (true) {
    		event = roomModel.eventqueue.poll();
    		
    		if (event != null) {
    			String playerName = event.getUsername();
        		Player player = roomModel.players.get(playerName);
        		if ((player == null) && (event.getEventid() != Event.EVENT_QUITGAME)) {
        			continue;
        		}
        		
    			switch(event.getEventid()) {
    				case Event.EVENT_USERGO:
    					userGoHandle(player, event);
    					break;    					
    				case Event.EVENT_USERMOVE:
    					// TODO
    					break;    					
    				case Event.EVENT_BUYLAND:
    					// TODO
    					break;    				
    				case Event.EVENT_BUILDHOUSE:
    					// TODO
    					break;    					
    				case Event.EVENT_PAYRENT:
    					// TODO
    					break;    					
    				case Event.EVENT_BANKADJUST:
    					// TODO
    					break;    					
    				case Event.EVENT_SELLLAND:
    					// TODO
    					break;    					
    				case Event.EVENT_COMMUNITY:
    					// TODO
    					break;    					
    				case Event.EVENT_USERBANKRUPTCY:
    					// TODO
    					break;    					
    				case Event.EVENT_COLLECTMONEY:
    					// TODO
    					break;    				
    				case Event.EVENT_LOANLAND:
    					// TODO
    					break;    					
    				case Event.EVENT_GAMEINFO:
    					// TODO
    					break;    					
    				case Event.EVENT_USERWIN:
    					// TODO
    					break;    				
    				case Event.EVENT_PASSBANK:
    					// TODO
    					break;    					
    				case Event.EVENT_USERLOSE:
    					// TODO
    					break;    					
    				case Event.EVENT_GOTOHOSPITAL:
    					// TODO
    					break;
    			}
    			
    			// 
    		}
    		
   		 	try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                               
            }
       }
    }
    
    public void userGoHandle(Player player, Event event) {
    	System.out.printf("<<< Game GUI receives a User Go event, room id = %d, user name = %s, info = %s, ts = %s\n", event.getRoomid(), event.getUsername(), event.getEventinfo(), event.getTimestamp());
    	
    	if (player.getIsMe() == true) {
    		game.mapPanel.setGoEnable(true);    		
    	}
    	game.mapPanel.setTurn(player.getName());
    }

}
