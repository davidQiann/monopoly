package edu.eom.ics4u.monopoly.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.eom.ics4u.monopoly.game.Player;
import edu.eom.ics4u.monopoly.game.Property;
import edu.eom.ics4u.monopoly.logic.GameLogic;
import edu.eom.ics4u.monopoly.model.LogicResult;
import edu.eom.ics4u.monopoly.model.Model;
import edu.eom.ics4u.monopoly.model.RoomModel;

class TestUserMove {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
    
	@Test
	final void testUserMove() {
		Model m = Model.getInstance();
		RoomModel rm=m.rooms.get(0);
		Player p1=new Player(0,"david",0,0,true);
		rm.players.put("david", p1);
		p1.setActive(true);

		p1.setCash(100);
		p1.setSaving(0);
		p1.setLoan(100);

		Property P1= rm.properties[0];
		String owner = p1.getName();
		owner=P1.getOwnerName();
		int level = 2;
		level=P1.getLevel();
		p1.setHospitalstatus(0);
		p1.setStep(8);
		
		
		Player p2=new Player(0,"sirui",1,1,true);
		rm.players.put("sirui",p2);
		p2.setActive(true);

		p2.setCash(100);
		p2.setSaving(0);
		p2.setLoan(400);

		Property P2= rm.properties[38];
		String owner2 = p2.getName();
		owner=P2.getOwnerName();
		int level2 = 0;
		level2=P2.getLevel();
		p2.setHospitalstatus(0);
		p2.setStep(8);
		
		rm.setStatus(rm.STATUS_STARTED);
		rm.setDate(30);
		rm.setMonth(3);
		GameLogic gl = new GameLogic();
		LogicResult result = gl.UserMove("david",0);
		int newcash=p1.getCash();
		int newsaving = p1.getSaving();
		int newloan=p1.getLoan();
		assertEquals(newcash,100-0.2*100);
		assertEquals(newsaving, 0);
		assertEquals(newloan, 400);
		
		
		
		
		
		
		
		//fail("Not yet implemented");
	}

}
