
package edu.eom.ics4u.monopoly.logic;

import java.util.Iterator;
import java.util.Random;
import java.util.Map.Entry;

import edu.eom.ics4u.monopoly.game.Player;
import edu.eom.ics4u.monopoly.game.Property;
import edu.eom.ics4u.monopoly.model.Event;
import edu.eom.ics4u.monopoly.model.LogicResult;
import edu.eom.ics4u.monopoly.model.Model;
import edu.eom.ics4u.monopoly.model.RoomModel;
import edu.eom.ics4u.monopoly.model.community.CommunityChest;

/**
 * @author david
 *
 */
public class GameLogic implements LogicInterface {

	public static GameLogic instance;
	/**
	 * 
	 */
	public GameLogic() {
	
	}
	
	
	public static GameLogic getInstance() {
		if (instance == null) {
			instance = new GameLogic();
		}
		return instance;
	}

	

	@Override
	public LogicResult UserJoin(int roomid, String username, int characterId) {
		Model model = Model.getInstance();
		RoomModel roommodel= model.rooms.get(roomid);
		int ownerid = roommodel.players.size();
		LogicResult result = new LogicResult(LogicResult.RESULT_SUCCESS, "");
		if ((roommodel.getStatus() != RoomModel.STATUS_STARTED) && (roommodel.players.size()<4)) {
			if (roommodel.players.get(username)!= null) {
				result.setResultcode(LogicResult.RESULT_FAIL);
				result.setMessage( "User " + username + " Join Room " + roomid + "Failed, User already joined, can not join again");
				return result;
			}
			Player player = new Player(roomid,username,characterId, ownerid,  true);
			roommodel.players.put(username,player);
			roommodel.setStatus(RoomModel.STATUS_PENDING); // added by CYY
			int roomstatus = roommodel.getStatus();

			Event event = new Event(Event.EVENT_USERJOIN, roomid, username, 0, "User " + username + " Joined Room " + roomid, ownerid,roomstatus,0,0,0);
			model.getEventqueue().add(event);
			return result;
		}
				
		result.setResultcode(LogicResult.RESULT_FAIL);
		result.setMessage( "Room already have 4 players or room already started, can not join");
		return result;
	}

	@Override
	public LogicResult StartGame(String username, int roomid) {
		
		Model model = Model.getInstance();
		RoomModel roommodel= model.rooms.get(roomid);
		LogicResult result = new LogicResult(LogicResult.RESULT_SUCCESS, "");
		if (roommodel.getStatus() != RoomModel.STATUS_PENDING) {
			result.setResultcode(LogicResult.RESULT_FAIL);
			result.setMessage("Room " + roomid +" not in pending status, can not start");
			return result;
		}
		if (roommodel.players.get(username) == null) {
			result.setResultcode(LogicResult.RESULT_FAIL);
			result.setMessage("the user not join yet, can not start the game");
			return result;
		}

		if (roommodel.players.size()>=2) {
			roommodel.setStatus(RoomModel.STATUS_STARTED);
			Event event = new Event(Event.EVENT_STARTGAME, roomid, username, 0, "User " + username + " Started game for Room " + roomid, 0,0,0,0,0);
			model.getEventqueue().add(event);

			String turnuser = username;
			Event event1 = new Event(Event.EVENT_USERGO, roomid, turnuser, 0, "User " + turnuser + " Go" , 0,0,0,0,0);
			roommodel.eventqueue.add(event1);
			return result;
		}
		

		result.setResultcode(LogicResult.RESULT_FAIL);
		result.setMessage("less than 2 player in game, can not start the game");
		return result;
	}

	@Override
	public LogicResult UserMove(String username, int roomid) {
		
		Model model = Model.getInstance();
		RoomModel roommodel= model.rooms.get(roomid);
		LogicResult result = new LogicResult(LogicResult.RESULT_SUCCESS, "");
		
		Random rand = new Random();
		int dice1 = rand.nextInt(6) +1;
		int dice2 = rand.nextInt(6) +1;
		
			
		if (roommodel.players.get(username) == null) {
			result.setResultcode(LogicResult.RESULT_FAIL);
			result.setMessage("the user not join yet, can not throw dice");
			return result;			
		}
	
		if (roommodel.getStatus() == RoomModel.STATUS_STARTED) {
			result.setValue1(dice1);
			result.setValue2(dice2);
		}else {
			result.setResultcode(LogicResult.RESULT_FAIL);
			result.setMessage("Game not started, can not throw dice");
			return result;			
		}
		if (roommodel.players.size()<=1) {
			result.setResultcode(LogicResult.RESULT_FAIL);
			result.setMessage("only one player left on the game");
			return result;	
		}
		System.out.println("user " + username + " move " + result);
		Player player = roommodel.players.get(username);

		if (player.getHospitalstatus()>0){
			player.setHospitalstatus(player.getHospitalstatus()-1);
			result.setResultcode(LogicResult.RESULT_FAIL);
			result.setMessage("User " + username + " in hospital, can not throw dice");
			return result;
		}

		Event event = new Event(Event.EVENT_USERMOVE, roomid,player.getName(), player.getStep(), player.getName() + " throw dice " + dice1 + "," + dice2  ,dice1, dice2,0,0,0);
		roommodel.eventqueue.add(event);
		
		if (roommodel.getCount()%roommodel.players.size() == 0){
			roommodel.incrdate();
		}

		int steps = player.getStep();
		int nextstep = steps + dice1 +dice2;
		if (nextstep > 39) {  // bypass start point
			Event event1 = new Event(Event.EVENT_COLLECTMONEY, roomid,player.getName(), player.getStep(), player.getName() + "collect $200",200, 0,0,0,0);
			roommodel.eventqueue.add(event1);
			player.setCash(player.getCash()+200);
		}

		if (steps < 9 && nextstep >=9 ) {  // bypass bank
			System.out.println("Bypass bank");
			Event event1 = new Event(Event.EVENT_PASSBANK, roomid,player.getName(), player.getStep(), player.getName() + "pass bank",0,0 ,0,0,0);
			roommodel.eventqueue.add(event1);
		}
		if (nextstep == 29) {  // go to hospital
			System.out.println("Go to Hospital");
			Event event1 = new Event(Event.EVENT_GOTOHOSPITAL, roomid,player.getName(), player.getStep(), player.getName() + "go to hospital",0,0 ,0,0,0);
			roommodel.eventqueue.add(event1);
			player.setHospitalstatus(3);   // stay in hospital for 3 days
			return result;
		}
		//add one for community
		if ( (roommodel.getDate()==0 && roommodel.getMonth()!=0 ) ) {
			// bypass bank on first day of each month
			// collect interest
			int saving = player.getSaving();
			int loan = player.getLoan();
			int interest = (int)(0.1 * saving) + (int)(0.2*loan);
			System.out.println("distribute interest for " + player.getName() + " value " + interest);
			PlayerPay(roommodel, player, 0-interest);
			Event event1 = new Event(Event.EVENT_COLLECTMONEY, roomid,player.getName(), player.getStep(), player.getName() + "Monthend distribute interest $" + saving +"for " +player.getName(),0-interest,0,0,0,0);
			roommodel.eventqueue.add(event1);
		}


	
		return result;
	}

	@Override
	public LogicResult BuyLand(String username, int roomid, int landid) {
		Model model = Model.getInstance();
		RoomModel roommodel= model.rooms.get(roomid);
		LogicResult result = new LogicResult(LogicResult.RESULT_SUCCESS, "");
		
		Property p = roommodel.properties[landid];
		if (p.getOwnerName()!= null && p.getOwnerName().length()>0) {
			result.setResultcode(LogicResult.RESULT_FAIL);
			result.setMessage("The land already have owner, can not buy");
			return result;			
		}
		int level = p.getLevel();
		Player player = roommodel.players.get(username);
		if (player == null) {
			result.setResultcode(LogicResult.RESULT_FAIL);
			result.setMessage("player not exist");
			return result;			
		}


		int price = p.getPrice("land");
		int cash = player.getCash();
		if (cash < price) {
			result.setResultcode(LogicResult.RESULT_FAIL);
			result.setMessage("no enough cash, can not buy land");
			return result;			
		}
		player.setCash(cash-price);
		Event event = new Event(Event.EVENT_BUYLAND, roomid,player.getName(), landid, player.getName() + " buy land at " + p.getLocation() + " with $" +price , price, 0, cash,0,0);
		roommodel.eventqueue.add(event);
		p.updPrivateProperty(level,player.getName(),player.getImageStar());
		return result;
	}

	@Override
	public LogicResult BuildHouse(String username, int roomid, int landid) {
		Model model = Model.getInstance();
		RoomModel roommodel= model.rooms.get(roomid);
		Property p = roommodel.properties[landid];
		Player player = roommodel.players.get(username);
		LogicResult result = new LogicResult(LogicResult.RESULT_SUCCESS, "");

		if (player == null) {
			result.setResultcode(LogicResult.RESULT_FAIL);
			result.setMessage("player not exist");
			return result;			
		}
		if ((p.getOwnerName() == null)|| (! p.getOwnerName().equals(username))) {
			result.setResultcode(LogicResult.RESULT_FAIL);
			result.setMessage("the land " + landid + " not owned by user " + username);
			return result;			
		}
		boolean isLoaned = p.isLoaned();
		if (isLoaned==true) {
			result.setResultcode(LogicResult.RESULT_FAIL);
			result.setMessage("the land is being loaned by " + username+ ",can not upgrade");
			return result;			
		}
		int level = p.getLevel();
		int price = p.getPrice("house");
		if (level >=4) {
			price = p.getPrice("hotel");
		}
		if (level >= 5) {
			result.setResultcode(LogicResult.RESULT_FAIL);
			result.setMessage("already highest level, can not upgrade");
			return result;			
		}
		int cash = player.getCash();
		if (cash < price) {
			result.setResultcode(LogicResult.RESULT_FAIL);
			result.setMessage("Build house failed, no enough cash");
			return result;			
		}
		player.setCash(cash-price);
		p.updPrivateProperty(level + 1 ,player.getName(),player.getImageStar());
		Event event = new Event(Event.EVENT_BUILDHOUSE, roomid,player.getName(), landid, player.getName() + " build house at " + p.getLocation() + " with $" +price , price, 0, cash,0,0);
		roommodel.eventqueue.add(event);
		return result;
		
	}

	@Override
	public LogicResult PayRent(String username, int roomid, int landid) {
		Model model = Model.getInstance();
		RoomModel roommodel= model.rooms.get(roomid);
		Property p = roommodel.properties[landid];
		Player player = roommodel.players.get(username);
		LogicResult result = new LogicResult(LogicResult.RESULT_SUCCESS, username + " payrent for land " + p.getName()  );
		String ownername = p.getOwnerName();
		if (ownername == null) {
			// land is not owned by anyone, no need pay rent
			return result;
		}
		Player owner = roommodel.players.get(ownername);
		if (owner == null ) {
			result.setResultcode(LogicResult.RESULT_FAIL);
			result.setMessage("owner quit game, no need to pay rental");
			return result;
		}
		if (username.equals(ownername)) {
			result.setResultcode(LogicResult.RESULT_FAIL);
			result.setMessage("the land owned by user, no need pay rent");
			return result;
		}
		if (owner.getHospitalstatus() >0) {
			result.setResultcode(LogicResult.RESULT_FAIL);
			result.setMessage("owner in hospital, no need pay rent");
			return result;
		}
		boolean isLoaned = p.isLoaned();
		if (isLoaned==true) {
			result.setResultcode(LogicResult.RESULT_FAIL);
			result.setMessage("the land is being loaned by " + username+ ", no jneed pay rent");
			return result;					
		}
		int rent = p.getRent(p.getLevel());
		owner.setCash(owner.getCash()+rent);

		if (PlayerPay(roommodel,player, rent) <0 ) {
			//bankrupt
			result.setResultcode(LogicResult.RESULT_FAIL);
			result.setMessage("Player bankrupt");
			return result;
		}

		Event event = new Event(Event.EVENT_PAYRENT, roomid,player.getName(), landid, player.getName() + " pay rent $" +rent + " for land " + p.getLocation() +" to " + owner.getName() , rent, 0, 0,0,0);
		roommodel.eventqueue.add(event);
		return result;
	}

	@Override
	public LogicResult BankAdjust(String username, int roomid, int adjvalue) {
		Model model = Model.getInstance();
		RoomModel roommodel= model.rooms.get(roomid);
		Player player = roommodel.players.get(username);
		LogicResult result = new LogicResult(LogicResult.RESULT_SUCCESS, "");
	
		int cash = player.getCash();
		int saving = player.getSaving();
		if (adjvalue >0) {
			if (adjvalue > cash) {
				result.setResultcode(LogicResult.RESULT_FAIL);
				result.setMessage("cash balance not enough for transfer");
				return result;
			}
		}else {
			if ((0-adjvalue) > saving) {
				result.setResultcode(LogicResult.RESULT_FAIL);
				result.setMessage("saving balance not enough for transfer");
				return result;
			}
		}
		cash = cash - adjvalue;
		saving = saving + adjvalue;

		
		player.setCash(cash);
		player.setSaving(saving);

		Event event = new Event(Event.EVENT_BANKADJUST, roomid,player.getName(), 0, player.getName() + " Adjust bank account with cash " + cash + " saving " + saving , adjvalue,0,cash,saving,0);
		roommodel.eventqueue.add(event);
		return result;
	}

	@Override
	public LogicResult QuitGame(String username, int roomid) {
		Model model = Model.getInstance();
		RoomModel roommodel= model.rooms.get(roomid);
		Player player = roommodel.players.get(username);
		LogicResult result = new LogicResult(LogicResult.RESULT_SUCCESS, "");
		if (player == null) {
			result.setResultcode(LogicResult.RESULT_FAIL);
			result.setMessage("player not in game");
			return result;
		}
		
		roommodel.players.remove(username); // remove user from list, but keep its properties.

		Event event = new Event(Event.EVENT_QUITGAME, roomid,player.getName(), 0, player.getName() + " Quit Game " + roomid, 0,0,0,0,0);
		roommodel.eventqueue.add(event);
		model.getEventqueue().add(event);
		
		if (roommodel.players.size() ==0) {
			//  close game when all people quit
			return CloseGame(roomid);
		}

		Iterator < Entry < String, Player >> iterator = roommodel.players.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry < String, Player > entry = iterator.next();
			Player p = entry.getValue();
			if (p.getPlayerId() > player.getPlayerId()) {
				p.setPlayerId(p.getPlayerId() -1);
			}
		}
		return result;
	}

	@Override
	public LogicResult SellLand(int roomid, int landid) {
		// TODO Auto-generated method stub
		Model model = Model.getInstance();
		RoomModel roommodel= model.rooms.get(roomid);
		Property p = roommodel.properties[landid];
		LogicResult result = new LogicResult(LogicResult.RESULT_SUCCESS, "");
		if (p.getOwnerName() == null) {
			result.setResultcode(LogicResult.RESULT_FAIL);
			result.setMessage("property is not owned by any player");
			return result;
		}
		Player player = roommodel.players.get(p.getOwnerName());
		if (player == null) {
			
			result.setResultcode(LogicResult.RESULT_FAIL);
			result.setMessage("owner not exist");
			return result;
		}
		boolean isLoaned = p.isLoaned();
		if (isLoaned==true) {
			result.setResultcode(LogicResult.RESULT_FAIL);
			result.setMessage("the land is being loaned, can not be selled");
			return result;					
		}
		int value = p.getValue()/2;
		player.setCash(player.getCash() + value);
		
		Event event = new Event(Event.EVENT_SELLLAND, roomid,player.getName(), landid, "Land  " + p.getLocation() + " Selled by " + player.getName() + " for $" + value, value,0,0,0,0);
		roommodel.eventqueue.add(event);
		p.Reset();
		return result;
	}


	
	@Override
	public LogicResult LoanLand(int roomid, int landid) {
		// TODO Auto-generated method stub
		Model model = Model.getInstance();
		RoomModel roommodel= model.rooms.get(roomid);
		Property p = roommodel.properties[landid];
		LogicResult result = new LogicResult(LogicResult.RESULT_SUCCESS, "");
		if (p.getOwnerName() == null) {
			result.setResultcode(LogicResult.RESULT_FAIL);
			result.setMessage("property is not owned by any player");
			return result;
		}
		Player player = roommodel.players.get(p.getOwnerName());
		int value = p.getValue()/2;
		player.setCash(player.getCash() + value);
		player.setLoan(player.getLoan() + value);
		Event event = new Event(Event.EVENT_LOANLAND, roomid,player.getName(), landid, "Land  " + p.getLocation() + " Loaned by " + player.getName() + " for $" + value, value,0,0,0,0);
		roommodel.eventqueue.add(event);
		p.setLoaned(true);
		return result;
	}

	public LogicResult PayLoan(int roomid, int landid, String username) {
		Model model = Model.getInstance();
		RoomModel roommodel= model.rooms.get(roomid);
		Property p = roommodel.properties[landid];
		Player player = roommodel.players.get(username);
		LogicResult result = new LogicResult(LogicResult.RESULT_SUCCESS, username + " payloan for land " + p.getName()  );
		String ownername = p.getOwnerName();
	if (player.isActive()==true) {
		if(ownername.equals(username)) {
			int value = p.getValue()/2;
			int cash = player.getCash();
			if(cash>=value) {
				player.setCash(cash-value);
				p.setLoaned(false);
				player.setLoan(player.getLoan()- value);
				Event event = new Event(Event.EVENT_PAYLOAN, roomid,player.getName(), landid, "Land  " + p.getLocation() + " is pay Loaned by " + player.getName() + " for $" + value, value,0,0,0,0);
				roommodel.eventqueue.add(event);
				return result;
			}
			result.setResultcode(LogicResult.RESULT_FAIL);
			result.setMessage("player has not enough cash to pay back loaned house");
			return result;
		}
		result.setResultcode(LogicResult.RESULT_FAIL);
		result.setMessage("property is not owned by this player");
		return result;
		}
		result.setResultcode(LogicResult.RESULT_FAIL);
		result.setMessage("player is not active");
		return result;
	}

	@Override
	public LogicResult CloseGame(int roomid) {
		Model model = Model.getInstance();
		RoomModel roommodel= model.rooms.get(roomid);
		LogicResult result = new LogicResult(LogicResult.RESULT_SUCCESS, "");
		roommodel.players.clear();
		for (int i=0; i< roommodel.properties.length;i++) {
			roommodel.properties[i].Reset();
		}
		roommodel.setDate(0);
		roommodel.setMonth(0);
		roommodel.setStatus(0);
		Event event = new Event(Event.EVENT_GAMEOVER, roomid, "", 0, "game for Room " + roomid + " Closed", 0,0,0,0,0);
		model.getEventqueue().add(event);

		return result;
	}


	@Override
	public LogicResult TurnDone (String username, int roomid){
		Model model = Model.getInstance();
		RoomModel roommodel= model.rooms.get(roomid);
		LogicResult result = new LogicResult(LogicResult.RESULT_SUCCESS, "");
		String turnuser = roommodel.getUserTurn(username);
		if (turnuser != null) {
			Event event1 = new Event(Event.EVENT_USERGO, roomid, turnuser, 0, "User " + turnuser + " Go" , 0,0,0,0,0);
			roommodel.eventqueue.add(event1);
			
		}
		else {
			result.setResultcode(LogicResult.RESULT_FAIL);
			result.setMessage("failed to find next player");
		}	
		
		return result;
	}




	@Override
	public LogicResult SyncMap(String username, int roomid) {
		// TODO Auto-generated method stub
		LogicResult result = new LogicResult(LogicResult.RESULT_SUCCESS, "");
		return result;
	}


	public int PlayerPay (RoomModel roommodel, Player player, int value) {
		int cash = player.getCash();
		if (cash < value) {
			int saving = player.getSaving();
			if (saving < value-cash) {
				int total = value - cash - saving;
				for (int i=0; i< roommodel.properties.length; i++) {
					Property p1 = roommodel.properties[i];
					if (p1.getOwnerName() == null) {
						continue;
					}
					if (p1.getOwnerName().equals(player.getName())) {
						// the land owned by user
						int price = p1.getValue()/2;
						total -= price;
						p1.Reset();
						Event event = new Event(Event.EVENT_SELLLAND, roommodel.getRoomid(),player.getName(), i, player.getName() + " force to sell land " +p1.getLocation() + " for $" + price , price, 0, 0,0,0);
						roommodel.eventqueue.add(event);
						if (total <= 0) {
							break;
						}
					}
				}
			
				if(total >=0) {
					Event event = new Event(Event.EVENT_USERBANKRUPTCY, roommodel.getRoomid(),player.getName(), 0, player.getName() + " Bankruptcy!!! ", 0, 0, 0,0,0);
					roommodel.eventqueue.add(event);		
					player.setActive(false);
					player.setCash(0);
					player.setSaving(0);
					player.setLoan(0);
					
					int activecount = 0;
					Player winner = null;
					for (Player pl : roommodel.players.values()) {
						if (pl.isActive()) {
							activecount++;
							winner = pl;
						}
					}
					if (activecount == 1 ) { // only one winner remain
						event = new Event(Event.EVENT_USERWIN, roommodel.getRoomid(),winner.getName(), 0, winner.getName() + " Win!!! ", 0, 0, 0,0,0);
						roommodel.eventqueue.add(event);
					}	
					return -1;
				}
				else {
					player.setCash(0-total);
					player.setSaving(0);
					return 0-total;
				}
				
			}
			else {
				player.setSaving(saving + cash - value);
				player.setCash(0);
				return saving+cash-value;
			}
		}
		player.setCash(cash - value);

		return cash - value;
	}

	@Override
	public LogicResult Community(String username, int roomid, int landid) {
		
		Random rand = new Random();
		Model model = Model.getInstance();
		RoomModel roommodel= model.rooms.get(roomid);
		LogicResult result = new LogicResult(LogicResult.RESULT_SUCCESS, "");
		Player player = roommodel.players.get(username);
		
		int commchest = rand.nextInt(24);
		CommunityChest chest = roommodel.communityevents[commchest];
		result.setMessage(chest.DoEvent(roomid, username, player.getStep()));
		if (chest.getId()==0||chest.getId()==1||chest.getId()==24) {
			result.setValue1(1);   // go to step position
		}else {
			result.setValue1(0);	// do nothing
		}
		return result;
	}

}
