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
