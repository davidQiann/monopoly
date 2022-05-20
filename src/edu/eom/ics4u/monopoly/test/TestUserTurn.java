/**
 * 
 */
package edu.eom.ics4u.monopoly.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.eom.ics4u.monopoly.game.Player;
import edu.eom.ics4u.monopoly.game.Property;
import edu.eom.ics4u.monopoly.logic.GameLogic;
import edu.eom.ics4u.monopoly.model.Event;
import edu.eom.ics4u.monopoly.model.LogicResult;
import edu.eom.ics4u.monopoly.model.Model;
import edu.eom.ics4u.monopoly.model.RoomModel;


/**
 * @author qiany
 *
 */
class TestUserTurn {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * Test method for {@link edu.eom.ics4u.monopoly.model.RoomModel#getUserTurn(java.lang.String)}.
	 */
	@Test
	final void testGetUserTurn() {//test if winner still move after game ends
		RoomModel rm =new RoomModel(0);
		Player p1 = new Player(0, "david", 0, 0, true);
		Player p2 = new Player(0, "oliver", 1, 1, true);
		
		rm.players.put("david", p1);
		rm.players.put("oliver", p2);
		rm.setStatus(rm.STATUS_STARTED);
		p1.setActive(false);
		p2.setActive(true);
		//Model model = Model.getInstance();
		String nextuser = rm.getUserTurn("oliver");
		System.out.printf("next user is " +nextuser);
		assertEquals(null,nextuser);
		p1.setActive(true);
		nextuser = rm.getUserTurn("oliver");
		assertEquals("david",nextuser);
	//	fail("Not yet implemented"); // TODO
	}
	
	@Test
	final void testGetUserTurn1() {
		RoomModel rm =new RoomModel(0);
		Player p1 = new Player(0, "david", 0, 0, true);
		
		rm.players.put("david", p1);
		rm.setStatus(rm.STATUS_STARTED);
		p1.setActive(true);
		p1.setHospitalstatus(0);
		String nextuser = rm.getUserTurn("david");
		System.out.printf("next user is " +nextuser);
		assertEquals(null,nextuser);// check if player can get turn if <2 player room
		
		Player p2 = new Player(0, "sirui", 1, 1, true);
		rm.players.put("sirui", p2);
		p2.setActive(true);
		p2.setHospitalstatus(0);
		rm.setStatus(rm.STATUS_PENDING);
		nextuser = rm.getUserTurn("sirui");
		assertEquals(null,nextuser);// check if player can get turn when room isn't started
		Player p3 = new Player(0,"darren",0,2,true);
		rm.setStatus(rm.STATUS_STARTED);
		p3.setActive(true);
		p3.setHospitalstatus(0);
		nextuser = rm.getUserTurn("darren");
		assertEquals(null,nextuser);//check if player can get turn when player not in room
		rm.players.put("darren", p3);
		p1.setActive(false);
		nextuser = rm.getUserTurn("david");
		assertEquals("sirui",nextuser);//check if player can get turn when not active
		nextuser = rm.getUserTurn("darren");
		assertEquals("sirui",nextuser);//check if nonactive player get skip when inround
		p1.setActive(true);
		p1.setHospitalstatus(3);
		nextuser = rm.getUserTurn("david");
		assertEquals("sirui",nextuser);//check if hospital player can move
		nextuser = rm.getUserTurn("darren");
		assertEquals("sirui",nextuser);// check if hospital player get skip
		p2.setHospitalstatus(3);
		nextuser = rm.getUserTurn("david");
		assertEquals("darren",nextuser);//test if player will be nonhospital player when more than 1 player is in hospital
		p1.setActive(false);
		p2.setActive(false);
		nextuser = rm.getUserTurn("darren");
		assertEquals(null,nextuser);//check if active player can move if only one active player left
	}
	
	
	
	@Test
	final void testUserMove() {
		Model m =  Model.getInstance();
		
		RoomModel rm = m.rooms.get(0);
		Player p1 = new Player(0, "david", 0, 0, true);
		
		rm.players.put("david", p1);
		rm.setStatus(rm.STATUS_STARTED);
		p1.setActive(true);
		p1.setHospitalstatus(0);
		p1.setCash(100);
		p1.setSaving(1000);
		p1.setLoan(900);
		Property property1=rm.properties[0];
		Property property2=rm.properties[2];
		String name1=p1.getName();
		name1=property1.getOwnerName();
		p1.setStep(0);
		rm.setDate(18);
		rm.setMonth(2);
		
		LogicResult result1 = new LogicResult(LogicResult.RESULT_SUCCESS, "");
		LogicResult result2 = new LogicResult(LogicResult.RESULT_FAIL, "the user not join yet, can not throw dice");
		LogicResult result3 = new LogicResult(LogicResult.RESULT_FAIL, "Game not started, can not throw dice");
		LogicResult result4 = new LogicResult(LogicResult.RESULT_FAIL, "only one player left on the game");
		LogicResult result5 = new LogicResult(LogicResult.RESULT_FAIL, "User " + p1.getName() + " in hospital, can not throw dice");
		LogicResult result6 = new LogicResult(LogicResult.RESULT_FAIL, "Not enough player in game, player can't move");
		LogicResult result7 = new LogicResult(LogicResult.RESULT_FAIL, "User is not active, user can't move");
		GameLogic gl = new GameLogic();

		rm.setStatus(rm.STATUS_PENDING);
		LogicResult result = gl.UserMove("david", rm.getRoomid());//check if player can move when room is not started 
		assertEquals(result3.getValue1(),result.getValue1());
		assertEquals(result3.getValue2(),result.getValue2());
		assertEquals(result3.getMessage(),result.getMessage());
		assertEquals(result3.getResultcode(),result.getResultcode());
		
		
		rm.setStatus(rm.STATUS_STARTED);
		Player p2 = new Player(0, "sirui", 1, 1, true);
		p2.setActive(true);
		p2.setHospitalstatus(0);
		p2.setCash(100);
		p2.setSaving(1000);
		p2.setLoan(0);
		p2.setStep(1);
		String name2=p2.getName();
		name2=property2.getOwnerName();
		//rm.players.put("sirui", p2);
		
		Player p3 = new Player(0, "darren", 1, 2, true);
		p3.setActive(true);
		p3.setHospitalstatus(0);
		p3.setCash(100);
		p3.setSaving(1000);
		p3.setLoan(0);
		p3.setStep(1);
		result = gl.UserMove("david", rm.getRoomid());//check if player can move when <2player in game
//		assertEquals(result4.getValue1(),result.getValue1());//no need to check dice, dice work as intended
//		assertEquals(result4.getValue2(),result.getValue2());
		assertEquals(result4.getMessage(),result.getMessage());
		assertEquals(result4.getResultcode(),result.getResultcode());
		
		result= gl.UserMove("sirui", rm.getRoomid());//check if player can move when not in room
		assertEquals(result2.getValue1(),result.getValue1());
		assertEquals(result2.getValue2(),result.getValue2());
		assertEquals(result2.getMessage(),result.getMessage());
		assertEquals(result2.getResultcode(),result.getResultcode());
		
		rm.players.put("sirui", p2);
		rm.players.put("darren", p3);
		p1.setActive(false);
		result = gl.UserMove("david", rm.getRoomid());//check if player can move when player is not active 
		assertEquals(result7.getMessage(),result.getMessage());
		assertEquals(result7.getResultcode(),result.getResultcode());
		
		p1.setActive(true);
		p1.setHospitalstatus(2);
		result = gl.UserMove("david", rm.getRoomid());//check if player can move when player is in hospital
		assertEquals(result5.getMessage(),result.getMessage());
		assertEquals(result5.getResultcode(),result.getResultcode());
		
		p2.setActive(false);
		p3.setActive(false);
		p1.setHospitalstatus(0);
		p2.setHospitalstatus(0);
		p3.setHospitalstatus(0);
		result = gl.UserMove("david", rm.getRoomid());//check if player can move when one active player left in game*fail, but no impact on game, this is checked in getUserTurn
		System.out.println("SSSSS");
		p2.setActive(true);
		p3.setActive(true);
		p1.setStep(20);
		result = gl.UserMove("david", rm.getRoomid());
		Event[] events = new Event[rm.eventqueue.size()];
		events=rm.eventqueue.toArray(events);
		for (int i=0; i< events.length;i++) {
			System.out.println("EVENT:"+ events[i].getEventinfo());
		}
		
//		Event event = new Event(Event.EVENT_USERMOVE, rm.getRoomid(),p1.getName(), p1.getStep(), p1.getName() + " throw dice " + dice1 + "," + dice2  ,dice1, dice2,0,0,0);
		
		//check if bypass bank
		//check if player bypass startpoint and collect 200
		//check if player bypass community and recieve community events
		//check if player step on hospital and is set to stay in hospital
		//check if date increase properly normally
		//check if date increases into month when last day of month
		//check if player collect interest properly on the first day of month(excepet first month)
		//check if player collect interets on first day of first month
		//check if player can buy land in an empty land
		//check if player has correct step and nextstep value
	}
	@Test
	final void testUserMove1() {
		Model m =  Model.getInstance();
		
		RoomModel rm = m.rooms.get(0);
		Player p1 = new Player(0, "david", 0, 0, true);
		Player p2 = new Player(0, "oliver", 1, 1, true);
		
		rm.players.put("david", p1);
		rm.players.put("oliver", p2);
		rm.setStatus(rm.STATUS_STARTED);
		p1.setActive(true);
		p2.setActive(true);
		p1.setCash(100);
		p2.setCash(100);
		p1.setSaving(500);
		p2.setSaving(0);
		p1.setLoan(0);
		p2.setLoan(300);
		//get ownership of land
		Property P1=rm.properties[0];
		Property P2=rm.properties[38];
		String name1=p1.getName();
		name1=P1.getOwnerName();
		String name2=p2.getName();
		name2=P2.getOwnerName();
		p1.setHospitalstatus(0);
		p2.setHospitalstatus(0);
		p1.setStep(8);
		p2.setStep(8);
	
		rm.setDate(30);
		rm.setMonth(1);
		GameLogic gl = new GameLogic();
		LogicResult result = gl.UserMove("david", 0);
		
		int newcash= p1.getCash();
		int newsaving = p1.getSaving();
		int newloan= p1.getLoan();
		
		
		assertEquals(newcash, 0.1*500+100);
		assertEquals(newsaving,500);
		assertEquals(newloan, 0);
	
	}
	
	
	@Test
	final void testUserMove2() {
		Model m =  Model.getInstance();
		
		RoomModel rm = m.rooms.get(0);
		Player p1 = new Player(0, "david", 0, 0, true);
		Player p2 = new Player(0, "oliver", 1, 1, true);
		
		rm.players.put("david", p1);
		rm.players.put("oliver", p2);
		rm.setStatus(rm.STATUS_STARTED);
		p1.setActive(true);
		p2.setActive(true);
		p1.setCash(100);
		p2.setCash(100);
		p1.setSaving(500);
		p2.setSaving(0);
		p1.setLoan(0);
		p2.setLoan(300);
		//get ownership of land
		Property P1=rm.properties[0];
		Property P2=rm.properties[38];
		String name1=p1.getName();
		name1=P1.getOwnerName();
		String name2=p2.getName();
		name2=P2.getOwnerName();
		p1.setHospitalstatus(0);
		p2.setHospitalstatus(0);
		p1.setStep(8);
		p2.setStep(8);
	
		rm.setDate(30);
		rm.setMonth(1);
		GameLogic gl = new GameLogic();
		LogicResult result = gl.UserMove("oliver", 0);
		
		int newcash= p2.getCash();
		int newsaving = p2.getSaving();
		int newloan= p2.getLoan();
		
		
		assertEquals(newcash, 100-0.2*300);
		assertEquals(newsaving,0);
		assertEquals(newloan, 300);
	
	}
	

}
