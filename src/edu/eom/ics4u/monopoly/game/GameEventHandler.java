package edu.eom.ics4u.monopoly.game;

import javax.swing.JOptionPane;

import edu.eom.ics4u.monopoly.game.GameGui;
import edu.eom.ics4u.monopoly.game.Player;
import edu.eom.ics4u.monopoly.model.Model;
import edu.eom.ics4u.monopoly.model.Event;
import edu.eom.ics4u.monopoly.model.LogicResult;
import edu.eom.ics4u.monopoly.model.RoomModel;

public class GameEventHandler extends Thread{
    private GameGui game;
    private int roomId;
    public RoomModel roomModel;
    public static final int BANK_ID = 9;
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
    					userGoHandler(player, event);
    					break;    					
    				case Event.EVENT_USERMOVE:
    					userMoveHandler(player, event);
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
    
    public void userGoHandler(Player player, Event event) {
    	System.out.printf("<<< Game GUI receives a User Go event, room id = %d, user name = %s, info = %s, ts = %s\n", event.getRoomid(), event.getUsername(), event.getEventinfo(), event.getTimestamp());
    	
    	if (player.getIsMe() == true) {
    		game.mapPanel.setGoEnable(true);    		
    	}
    	game.mapPanel.setTurn(player.getName());
    }
    
    public void userMoveHandler(Player player, Event event) {
    	System.out.printf("<<< Game GUI receives a User Move event, room id = %d, user name = %s, info = %s, ts = %s\n", event.getRoomid(), event.getUsername(), event.getEventinfo(), event.getTimestamp());
    	
		Thread t = new Thread(new Runnable(){

			@Override
			public void run() {
				String playerName = event.getUsername();
				int dice1 = event.getAmount1();
				int dice2 = event.getAmount2();
				game.mapPanel.rollDice(dice1, dice2);
				
				int nStep = dice1+dice2;
				int currStep = player.getStep();
				int nextStep = nStep + currStep;
				if (nextStep >= MapPanel.NUM_PROPERTY) {
					nextStep = nextStep - MapPanel.NUM_PROPERTY;
				}
				
				// if passing the bank, stop at the bank and do the bank transfer
				if (player.getIsMe() == true) {
					System.out.println("test is me");
					if (((nextStep-nStep) < BANK_ID) && (nextStep > BANK_ID)) {
						System.out.println("test bank");
						player.move(BANK_ID);
						playerActions(player, BANK_ID);
					}
				}
			    
				// move the target location - nextSteps
				player.move(nextStep);
				if (player.getIsMe() == true) {
					playerActions(player, nextStep);
				}
				
				LogicResult result = roomModel.getRoomlogic().TurnDone(playerName, roomId); 
				if (result.getResultcode() != LogicResult.RESULT_SUCCESS) {
					//TODO
				}
			}
			
		});
		t.start();
		
    }
    
    public void playerActions(Player player, int propertyId) {
		Property property = roomModel.properties[propertyId];
		
		String pType = property.getPropertyType();
		String pOwnerName = property.getOwnerName();
		String pName = property.getName();
				
		// public properties
		if (pType == "public") {
			if (pName == "bank") {
				bankTansferReq(player);
			}
			return;
		}
		
		// un-owned property
		if (pOwnerName == null) {
			buyLandReq(player, propertyId);
			return;
		}
		
		// my property
		if (player.getName() == pOwnerName) {
			buildHouseReq(player, propertyId);
			return;
		}
		
		// other owner's property
		if (player.getName() != pOwnerName) {
			payRentReq(player, propertyId);
			return;
		}    	
    }
    
    public void bankTansferReq(Player player) {
    	//TODO
    }
    
    public void buyLandReq(Player player, int propertyId) {
    	//TODO
    }
    
    public void buildHouseReq(Player player, int propertyId) {
    	//TODO
    }
    
    public void payRentReq(Player player, int propertyId) {
    	//TODO
    }
    
    // Pop up a confirm dialog to tell the player something. 
    // For example, tell the player “no enough cash, cannot buy the land”.
	public void popConfirmDialog(String str) {		
			Object [] options2 = {"OK"};		
			JOptionPane.showOptionDialog(
				null, 
                str, 
                "Please Confirm:",
                JOptionPane.YES_NO_CANCEL_OPTION, 
                JOptionPane.WARNING_MESSAGE,
                null,
                options2,
                options2[0]);
	}
	
	// Pop up a options selection dialog.
	// For example,  for the player to choose to buy a land or not. 
	public int showOptionDialog(String str) {
		Object [] options = {"Yes", "No"};		
		int input = JOptionPane.showOptionDialog(
				null, 
                str, 
                "Select an Option...",
                JOptionPane.YES_NO_CANCEL_OPTION, 
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
		return input;
	}
    
}
