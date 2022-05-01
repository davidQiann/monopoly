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
    private int gameId;
    
    public GameEventHandler(GameGui gameGui, int roomId, int gameId) {
    	this.gameId = gameId;
    	this.roomId = roomId;
    	game = gameGui;
    	roomModel = Model.getInstance().rooms.get(roomId);
    	game.chartsTableModel.updChartsTable(roomId);
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
    					buyLandHandler(player, event);
    					break;    				
    				case Event.EVENT_BUILDHOUSE:
    					buildHouseHandler(player, event);
    					break;    					
    				case Event.EVENT_PAYRENT:
    					payRentHandler(player, event);
    					break;    					
    				case Event.EVENT_BANKADJUST:
    					bankAdjustHandler(player, event);
    					break;    					
    				case Event.EVENT_SELLLAND:
    					sellLandHandler(player, event);
    					break;    					
    				case Event.EVENT_COLLECTMONEY:
    					colletMoneyHandler(player,event);
    					break;    				
    				case Event.EVENT_USERWIN:
    					userWinHandler(player, event);
    					break;    				
    				case Event.EVENT_USERBANKRUPTCY:
    					userBankruptcyHandler(player, event);
    					break;    					
    				case Event.EVENT_QUITGAME:
    					quitGameHandler(event);
    					break;
    				case Event.EVENT_COMMUNITY:
    					// TODO
    					break;    					
    				case Event.EVENT_LOANLAND:
    					// not used?
    					break;    					
    				case Event.EVENT_GAMEINFO:
    					// not used?
    					break;    					
    				case Event.EVENT_PASSBANK:
    					// not used
    					break;    					
    				case Event.EVENT_USERLOSE:
    					// not used?
    					break;    					
    				case Event.EVENT_GOTOHOSPITAL:
    					// not used?
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
    	System.out.printf("\n<<< Game Id = %d, Game GUI receives a User Go event, room id = %d, user name = %s, info = %s, ts = %s\n", gameId, event.getRoomid(), event.getUsername(), event.getEventinfo(), event.getTimestamp());
    	
    	if (player.getIsMe() == true) {
    		game.mapPanel.setGoEnable(true);    		
    	}
    	game.mapPanel.setTurn(player.getName());
    }
    
    public void userMoveHandler(Player player, Event event) {
    	System.out.printf("<<< Game Id = %d, Game GUI receives a User Move event, room id = %d, user name = %s, info = %s, ts = %s\n", gameId, event.getRoomid(), event.getUsername(), event.getEventinfo(), event.getTimestamp());
    	
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
				
				// if passing the bank, stop at the bank first and do the bank transfer
				if (player.getIsMe() == true) {
					//System.out.println("test is me");
					if (((nextStep-nStep) < BANK_ID) && (nextStep > BANK_ID)) {
						//System.out.println("test bank");
						player.move(BANK_ID);
						playerActions(player, BANK_ID);
					}
				}
			    
				// move the target location - nextSteps
				player.move(nextStep);
				if (player.getIsMe() == true) {
					game.mapPanel.selectedPropertyId = nextStep;
					playerActions(player, nextStep);					
				}
				
				LogicResult result = roomModel.getRoomlogic().TurnDone(playerName, roomId); 
				if (result.getResultcode() != LogicResult.RESULT_SUCCESS) {
					popConfirmDialog(result.getMessage());
				}
			}
			
		});
		t.start();
		
    }
    
    public void buyLandHandler(Player player, Event event) {
    	System.out.printf("\n<<< Game Id = %d, Game GUI receives a Buy Land event, room id = %d, user name = %s, info = %s, ts = %s\n", gameId, event.getRoomid(), event.getUsername(), event.getEventinfo(), event.getTimestamp());
    	game.shiftShowTrans(event.getTimestamp()+ ": " +event.getEventinfo(), player.getIsMe());
    	game.chartsTableModel.updChartsTable(roomId);
    }
    
    public void buildHouseHandler(Player player, Event event) {
    	System.out.printf("\n<<< Game Id = %d, Game GUI receives a Bulid House event, room id = %d, user name = %s, info = %s, ts = %s\n", gameId, event.getRoomid(), event.getUsername(), event.getEventinfo(), event.getTimestamp());
    	game.shiftShowTrans(event.getTimestamp()+ ": " +event.getEventinfo(), player.getIsMe());
    	game.chartsTableModel.updChartsTable(roomId);
    } 
    
    public void payRentHandler(Player player, Event event) {
    	System.out.printf("\n<<< Game Id = %d, Game GUI receives a Pay Rent event, room id = %d, user name = %s, info = %s, ts = %s\n", gameId, event.getRoomid(), event.getUsername(), event.getEventinfo(), event.getTimestamp());
    	game.shiftShowTrans(event.getTimestamp()+ ": " +event.getEventinfo(), player.getIsMe());
    	game.chartsTableModel.updChartsTable(roomId);
    }
    
    public void bankAdjustHandler(Player player, Event event) {
    	System.out.printf("\n<<< Game Id = %d, Game GUI receives a Bank Adjust event, room id = %d, user name = %s, info = %s, ts = %s\n", gameId, event.getRoomid(), event.getUsername(), event.getEventinfo(), event.getTimestamp());
    	game.shiftShowTrans(event.getTimestamp()+ ": " +event.getEventinfo(), player.getIsMe());
    	game.chartsTableModel.updChartsTable(roomId);
    }
    
    public void sellLandHandler(Player player, Event event) {
    	System.out.printf("\n<<< Game Id = %d, Game GUI receives a Sell Land event, room id = %d, user name = %s, info = %s, ts = %s\n", gameId, event.getRoomid(), event.getUsername(), event.getEventinfo(), event.getTimestamp());
    	game.shiftShowTrans(event.getTimestamp()+ ": " +event.getEventinfo(), player.getIsMe());
    	game.chartsTableModel.updChartsTable(roomId);
    }
    
    public void colletMoneyHandler(Player player, Event event) {
    	System.out.printf("\n<<< Game Id = %d, Game GUI receives a Collet Money event, room id = %d, user name = %s, info = %s, ts = %s\n", gameId, event.getRoomid(), event.getUsername(), event.getEventinfo(), event.getTimestamp());
    	game.shiftShowTrans(event.getTimestamp()+ ": " +event.getEventinfo(), player.getIsMe());
    	game.chartsTableModel.updChartsTable(roomId);
    }
    
    public void userWinHandler(Player player, Event event) {
    	System.out.printf("\n<<< Game Id = %d, Game GUI receives a User Win event, room id = %d, user name = %s, info = %s, ts = %s\n", gameId, event.getRoomid(), event.getUsername(), event.getEventinfo(), event.getTimestamp());
    	game.shiftShowTrans(event.getTimestamp()+ ": " +event.getEventinfo(), player.getIsMe());
    	game.chartsTableModel.updChartsTable(roomId);
    	if (player.getIsMe() == true) {
    		String win = player.getName() + " wins . Please export your personal transaction through clicking the Export button.";
    		popConfirmDialog(win);	
    	}
    }
    
    public void userBankruptcyHandler(Player player, Event event) {
    	System.out.printf("\n<<< Game Id = %d, Game GUI receives a User Bankruptcy event, room id = %d, user name = %s, info = %s, ts = %s\n", gameId, event.getRoomid(), event.getUsername(), event.getEventinfo(), event.getTimestamp());
    	game.shiftShowTrans(event.getTimestamp()+ ": " +event.getEventinfo(), player.getIsMe());
    	game.chartsTableModel.updChartsTable(roomId);
    	if (player.getIsMe() == true) {
    		String win = player.getName() + " lose . Please export your personal transaction through clicking the Export button.";
    		popConfirmDialog(win);	
    	}
    }
    
    public void quitGameHandler(Event event) {
    	System.out.printf("\n<<< Game Id = %d, Game GUI receives a Quit Game event, room id = %d, user name = %s, info = %s, ts = %s\n", gameId, event.getRoomid(), event.getUsername(), event.getEventinfo(), event.getTimestamp());
    	game.shiftShowTrans(event.getTimestamp()+ ": " +event.getEventinfo(), false);
    	game.chartsTableModel.updChartsTable(roomId);
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
			} else if (pName == "mall") {
				// TODO
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
    	int oldCash = player.getCash();
    	int oldSaving = player.getSaving();
    	int amount;
    	
    	TransferDialog transferDialog = new TransferDialog(oldCash, oldSaving);
    	if (transferDialog.transferReq == true) {
    		if(transferDialog.fromAccount == "Cash") {
    			amount = transferDialog.amount;
    		}else {
    			amount = 0 - transferDialog.amount;
    		}
    		
    		System.out.printf(">>> Game Id = %d, Game GUI call BankAdjust API, name = %s, room id = %d.\n", gameId, player.getName(), roomId);
			LogicResult result = roomModel.getRoomlogic().BankAdjust(player.getName(), roomId, amount);
			if (result.getResultcode() != LogicResult.RESULT_SUCCESS) {
				popConfirmDialog(result.getMessage());
			}
    	}
    }
    
    public void buyLandReq(Player player, int propertyId) {
		Property property = roomModel.properties[propertyId];		
		int price = property.getPrice("land");
		String location = property.getLocation();
        
		String str;
		str = "The land on the " + location + " cost $" + price +", do you want to purchase?";
		int input = popOptionDialog(str);		
		if (input == 0) {
			System.out.printf(">>> Game Id = %d, Game GUI call BuyLand API, name = %s, room id = %d, land id = %d.\n", gameId, player.getName(), roomId, propertyId);
			LogicResult result = roomModel.getRoomlogic().BuyLand(player.getName(), roomId, propertyId);
			if (result.getResultcode() != LogicResult.RESULT_SUCCESS) {
				popConfirmDialog(result.getMessage());
			}
        }
    }
    
    public void buildHouseReq(Player player, int propertyId) {
		Property property = roomModel.properties[propertyId];
		int newLevel = property.getLevel() + 1;
		int price = property.getPrice(newLevel);		
		String location = property.getLocation();
        
		String str;
		str = "Do you want to upgrade the building on the " + location + "? it will cost $" + price + ".";
		int input = popOptionDialog(str);		
		if (input == 0) {
			System.out.printf(">>> Game Id = %d, Game GUI call BuildHouse API, name = %s, room id = %d, land id = %d.\n", gameId, player.getName(), roomId, propertyId);
			LogicResult result = roomModel.getRoomlogic().BuildHouse(player.getName(), roomId, propertyId);
			if (result.getResultcode() != LogicResult.RESULT_SUCCESS) {
				popConfirmDialog(result.getMessage());
			}
        }
    }
    
    public void payRentReq(Player player, int propertyId) {
		Property property = roomModel.properties[propertyId];
		int rent = property.getRent(property.getLevel());
		String oName = property.getOwnerName();
        
		String str;
		str = "You need to pay rent $" + rent + " to " + oName + ".";
		popConfirmDialog(str);		
		System.out.printf(">>> Game Id = %d, Game GUI call PayRent API, name = %s, room id = %d, land id = %d.\n", gameId, player.getName(), roomId, propertyId);
		LogicResult result = roomModel.getRoomlogic().PayRent(player.getName(), roomId, propertyId);
		if (result.getResultcode() != LogicResult.RESULT_SUCCESS) {
			popConfirmDialog(result.getMessage());
		}
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
	public int popOptionDialog(String str) {
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
