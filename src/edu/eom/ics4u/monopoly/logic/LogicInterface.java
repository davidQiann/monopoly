
package edu.eom.ics4u.monopoly.logic;

import edu.eom.ics4u.monopoly.model.LogicResult;


public interface LogicInterface {
	
	
	/**
	 * User join request, the user join some room, the room must in empty or pending status.
	 * @param roomid
	 * @param username
	 * @param characterId
	 * @return logic result
	 */
	public LogicResult UserJoin(int roomid, String username, int characterId);
	
	/**
	 * When game is in pending status and more than 2 people in game, any one could select to start the game
	 * @param username
	 * @param roomid
	 * @return logicresult
	 */
	public LogicResult StartGame(String username, int roomid);
	/**
	 * When user throw dice, call this method to get dice result
	 * @param username
	 * @param roomid
	 * @return
	 */
	public LogicResult UserMove(String username,int roomid);   // return two dice values  like "1,6"
	
	/**
	 * When user is in a land, could have option to buy this land
	 * @param username
	 * @param roomid
	 * @param landid
	 * @return
	 */
	public LogicResult BuyLand(String username, int roomid,int landid);
	
	/**
	 * when user in his own land, he could have option to build or upgrade house in this land
	 * @param username
	 * @param roomid
	 * @param landid
	 * @return
	 */
	public LogicResult BuildHouse(String username, int roomid,int landid);
	

	/**
	 * 
	 * @param username
	 * @param roomid
	 * @param landid
	 * @return
	 */
	public LogicResult PayRent(String username, int roomid,int landid);
	

	/**
	 * 
	 * @param username
	 * @param roomid
	 * @param cash
	 * @param saving
	 * @return
	 */
	public LogicResult BankAdjust (String username, int roomid,int adjvalue);
	

	public LogicResult Community(String username, int roomid,int landid);


	/**
	 * 
	 * @param username
	 * @param roomid
	 * @return
	 */
	public LogicResult QuitGame(String username,int roomid);
	

	/**
	 * 
	 * @param roomid
	 * @return
	 */
	public LogicResult CloseGame(int roomid);
	

	/**
	 * 
	 * @param roomid
	 * @param landid
	 * @return
	 */
	public LogicResult SellLand(int roomid,int landid);
	
	/***
	 * when user pass the bank, he have option to loan the land and get cash back
	 * @param roomid
	 * @param landid
	 * @return
	 */
	public LogicResult LoanLand(int roomid,int landid);

	/**
	 * when some user rejoin the game, he may request sync the map again
	 * @param username
	 * @param roomid
	 * @return
	 */
	public LogicResult SyncMap(String username,int roomid);
    /**
	 * when some user finish his part of session, call turndone to notify next player to play
	 * @param username
	 * @param roomid
	 * @return
	 */
	public LogicResult TurnDone (String username, int roomid);
	
	public LogicResult PayLoan(int roomid, int landid, String username);

	

}
