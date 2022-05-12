package edu.eom.ics4u.monopoly.model;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Queue;
import java.util.Map.Entry;

import edu.eom.ics4u.monopoly.game.Player;
import edu.eom.ics4u.monopoly.game.Property;
import edu.eom.ics4u.monopoly.logic.LogicInterface;
import edu.eom.ics4u.monopoly.model.community.CommunityChest;

public class RoomModel {
	public static final int STATUS_EMPTY  = 0;
	public static final int STATUS_PENDING  = 1;
	public static final int STATUS_STARTED  = 2;


	private static int monthday [] = {31,28,31,30,31,30,31,31,30,31,30,31};
	public HashMap <String, Player> players = new HashMap<String,Player> ();
	public Queue<Event> eventqueue = new ArrayDeque<Event>();
	public Property properties [] = new Property [40];   
	public CommunityChest communityevents [] = new CommunityChest [32];
	private int roomid = 0;
	private int status = 0;    // 0: empty  1: pending to start  2: started 
	private int count =0;
	private int month = 0;
	private int date = 0;
	   
	private LogicInterface roomlogic = null;
	private static final String SM[] = {"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
	
	public RoomModel(int roomid) {
		this.roomid = roomid;
		for (int i=0;i<9 ;i++) {
			properties[ i] = new Property( i, "private", "unowned land");	
		}
		
    	// initialize properties at the up left edge
    	properties[ 9] = new Property( 9, "public",  "bank");		
		for (int i=10;i<19 ;i++) {
			properties[ i] = new Property( i, "private", "unowned land");	
		}
		
    	properties[19] = new Property(19, "public",  "park");
		for (int i=20;i<29 ;i++) {
			properties[ i] = new Property( i, "private", "unowned land");	
		}
		
    	// initialize properties at the up right edge
    	
    	// initialize properties at the down right edge
    	properties[29] = new Property(29, "public",  "hospital");
    	for (int i=30;i<39 ;i++) {
			properties[ i] = new Property( i, "private", "unowned land");	
		}
    	properties[39] = new Property(39, "public",  "go");


		communityevents[0] = new CommunityChest(0, "Advance to Go and collect 200",  200, 39);   // "go property" id is 39 
		communityevents[1] = new CommunityChest(1, "Go to Hospital, pay $50 hospital fee",  -50, 29);    //special event, need to be handled in the subclass logic
		communityevents[2] = new CommunityChest(2, "Bite by dog, pay doctor fee $100",  -100, -1);
		communityevents[3] = new CommunityChest(3, "Sell Stock, earn $50",  50, -1);
		communityevents[4] = new CommunityChest(4, "Income Tax refund $100",  100, -1);
		communityevents[5] = new CommunityChest(5, "Birthday, gather $50 gift from each player",  50, -1);//special event, need to be handled in the subclass logic
		communityevents[6] = new CommunityChest(6, "Holiday fund matures. Receive $100",  100, -1);
		communityevents[7] = new CommunityChest(7, "Life insurance matures. Collect $100",  100,-1);
		communityevents[8] = new CommunityChest(8, "Receive $25 consultancy fee", 25, -1);
		communityevents[9] = new CommunityChest(9, "Collect property Tax, $10 per house, $50 per hotel", 10, -1);    //special event, need to be handled in the subclass logic
		communityevents[10] = new CommunityChest(10, "Pay School fee of $150", -150, -1);
		communityevents[11] = new CommunityChest(11, "You inherit $100", 100, -1);
		communityevents[12] = new CommunityChest(12, "Bank error in your favor, collect $200", 200, -1);
		communityevents[13] = new CommunityChest(13, "Won Second of Beauity contest, earn $115", 115, -1);
		communityevents[14] = new CommunityChest(14, "Crash the car, pay $150", -150, -1);
		communityevents[15] = new CommunityChest(15, "Noise harrasment, pay $25 to each player", -25, -1);  //special event, need to be handled in the subclass logic
		communityevents[16] = new CommunityChest(16, "Repair your properties, pay $200", -200, -1);
		communityevents[17] = new CommunityChest(17, "Donate $40 to charity", -40, -1);
		communityevents[18] = new CommunityChest(18, "Loan matures, earn $50 award from bank", 50, -1);
		communityevents[19] = new CommunityChest(19, "Pay poor tax $50", -50, -1);
		communityevents[20] = new CommunityChest(20, "Speeding fine $15", -15, -1);
		communityevents[21] = new CommunityChest(21, "Award by city for planting trees, earn $35", 35, -1);
		communityevents[22] = new CommunityChest(22, "Grocery shopping pay $55", -55, -1);
		communityevents[23] = new CommunityChest(23, "find $100 on the street", 100, -1);
		communityevents[24] = new CommunityChest(24, "Goto bank and save all cash to saving", 0, 9);   //special event, need to be handled in the subclass logic
		
		
	
		
	}


	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}


	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}


	/**
	 * @return the date
	 */
	public int getDate() {
		return date;
	}


	/**
	 * @param date the date to set
	 */
	public void setDate(int date) {
		this.date = date;
	}


	/**
	 * @return the month
	 */
	public int getMonth() {
		return month;
	}

	public String getDateStr () {
		return( SM[month] + " " + (date +1)  + ",2099");
	}
	public void incrdate () {
		this.date = this.date + 1;
		if (date >= (monthday[month]-1)) {
			date = 0;
			month = month +1;
		}
	}

	/**
	 * @param month the month to set
	 */
	public void setMonth(int month) {
		this.month = month;
	}


	public String getUserTurn (String username) {
		int playerNum = 0;
		setCount(getCount() + 1);
		if (players.size()<2 || this.status!=STATUS_STARTED) {
			System.out.println("Can not run the game");
			return null;
		}

		Player player = players.get(username);
		if (player == null){
			System.out.println("Can not find player");
			return null;
		}
		int nextid = player.getPlayerId() +1;
		if (nextid >= players.size()) {
			nextid = 0;
		}

		Iterator < Entry < String, Player >> iterator = players.entrySet().iterator();
		
		
		while (iterator.hasNext() == true) {
	            Entry < String, Player > entry = iterator.next();
				Player p = entry.getValue();
				if (p.getPlayerId() == nextid) {
					if (p.isActive() == false) {
						return getUserTurn(p.getName());
					}
					if(p.getHospitalstatus()>0) {
						playerNum++;
						p.setHospitalstatus(p.getHospitalstatus()-1);
						return getUserTurn(p.getName());
						
					}
					if (p.getName().equals(username)) {
						return null;
					}
					if(playerNum==players.size()) {
						while (iterator.hasNext() == true) {
							p.setHospitalstatus(0);
						}
					}
					return p.getName();
				}
		}

	
		return null;
		
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}


	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}


	public int getRoomid() {
		return roomid;
	}


	public void setRoomid(int roomid) {
		this.roomid = roomid;
	}


	public LogicInterface getRoomlogic() {
		return roomlogic;
	}


	public void setRoomlogic(LogicInterface roomlogic) {
		this.roomlogic = roomlogic;
	}

}
