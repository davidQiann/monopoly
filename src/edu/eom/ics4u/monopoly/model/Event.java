package edu.eom.ics4u.monopoly.model;
//Event.java: Model used for generating events and be put into roommodel

public class Event {

	public static final int EVENT_USERJOIN = 1;
	public static final int EVENT_STARTGAME = 2;
	public static final int EVENT_USERMOVE = 3;
	public static final int EVENT_BUYLAND = 4;
	public static final int EVENT_BUILDHOUSE = 5;
	public static final int EVENT_PAYRENT = 6;
	public static final int EVENT_BANKADJUST = 7;
	public static final int EVENT_QUITGAME = 8;
	public static final int EVENT_GAMEOVER = 9;
	public static final int EVENT_SELLLAND = 10;
	public static final int EVENT_COMMUNITY = 11;
	public static final int EVENT_USERBANKRUPTCY = 12;
	public static final int EVENT_COLLECTMONEY = 13;// everytime bypass start point, earn $200 award
	public static final int EVENT_LOANLAND = 14;
	public static final int EVENT_USERGO = 15;
	public static final int EVENT_GAMEINFO = 16;
	public static final int EVENT_USERWIN = 17;
	public static final int EVENT_PASSBANK = 18;
	public static final int EVENT_USERLOSE = 19;
	public static final int EVENT_GOTOHOSPITAL = 20;
	public static final int EVENT_PAYLOAN=21;
	public static final int EVENT_RECRENT = 22; //receive rent event
	
	

	

	
	private int eventid;
	private int roomid;
	private String username;
	private int landid;
	private String eventinfo;
	private int amount1;
	private int amount2;
	private int value1;
	private int value2;
	private int value3;
	private String timestamp;
	
	
	public Event( int eventid, int roomid, String username, int landid, String eventinfo, int amount1, int amount2, int value1, int value2 , int value3) {
		this.eventid = eventid;
		this.roomid = roomid;
		this.username = username;
		this.landid=landid;
		this.eventinfo = eventinfo;
		this.amount1 = amount1;
		this.amount2 = amount2;
		this.value1 = value1;
		this.value2 = value2;
		this.value3 = value3;

		Model model = Model.getInstance();
		RoomModel roommodel= model.rooms.get(roomid);
		timestamp = roommodel.getDateStr();
		
	}

	public void printInfo () {
		System.out.println(timestamp + "Event: " + eventid + " Room: " + roomid + " username: " + username + " landid: " + landid + " eventinfo: " + eventinfo + " amount1:" + amount1 + " amount2: " + amount2 + " value1:" + value1 + " value2:" + value2 + " value3: " + value3);
	}


	/**
	 * @return the timestamp
	 */
	public String getTimestamp() {
		return timestamp;
	}



	/**
	 * @return the landid
	 */
	public int getLandid() {
		return landid;
	}


	/**
	 * @param landid the landid to set
	 */
	public void setLandid(int landid) {
		this.landid = landid;
	}


	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}


	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}


	/**
	 * @return the roomid
	 */
	public int getRoomid() {
		return roomid;
	}


	/**
	 * @param roomid the roomid to set
	 */
	public void setRoomid(int roomid) {
		this.roomid = roomid;
	}


	/**
	 * @return the amount2
	 */
	public int getAmount2() {
		return amount2;
	}


	/**
	 * @param amount2 the amount2 to set
	 */
	public void setAmount2(int amount2) {
		this.amount2 = amount2;
	}


	/**
	 * @return the amount1
	 */
	public int getAmount1() {
		return amount1;
	}


	/**
	 * @param amount1 the amount1 to set
	 */
	public void setAmount1(int amount1) {
		this.amount1 = amount1;
	}


	public String getEventinfo() {
		return eventinfo;
	}


	public void setEventinfo(String eventinfo) {
		this.eventinfo = eventinfo;
	}


	public int getValue1() {
		return value1;
	}


	public void setValue1(int value1) {
		this.value1 = value1;
	}


	public int getValue2() {
		return value2;
	}


	public void setValue2(int value2) {
		this.value2 = value2;
	}


	public int getValue3() {
		return value3;
	}


	public void setValue3(int value3) {
		this.value3 = value3;
	}


	public int getEventid() {
		return eventid;
	}


	public void setEventid(int eventid) {
		this.eventid = eventid;
	}

}

