package edu.eom.ics4u.monopoly.logic;

import edu.eom.ics4u.monopoly.model.LogicResult;

public class RemoteLogic implements LogicInterface {


    private static LogicInterface instance;
	public static LogicInterface getInstance() {
		if (instance == null) {
			instance = new GameLogic();
		}
		return instance;
	}


    @Override
    public LogicResult UserJoin(int roomid, String username, int characterId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LogicResult StartGame(String username, int roomid) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LogicResult UserMove(String username, int roomid) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LogicResult BuyLand(String username, int roomid, int landid) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LogicResult BuildHouse(String username, int roomid, int landid) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LogicResult PayRent(String username, int roomid, int landid) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LogicResult BankAdjust(String username, int roomid, int adjvalue) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LogicResult QuitGame(String username, int roomid) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LogicResult SellLand( int roomid, int landid) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LogicResult LoanLand( int roomid, int landid) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
	public LogicResult CloseGame(int roomid) {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
	public LogicResult TurnDone (String username, int roomid){
		
		return null;
	}

    @Override
    public LogicResult SyncMap(String username, int roomid) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public LogicResult Community(String username, int roomid, int landid) {
        // TODO Auto-generated method stub
        return null;
    }


	@Override
	public LogicResult PayLoan(int roomid, int landid, String username) {
		// TODO Auto-generated method stub
		return null;
	}
    
}
