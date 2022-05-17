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
	final void testGetUserTurn() {
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
