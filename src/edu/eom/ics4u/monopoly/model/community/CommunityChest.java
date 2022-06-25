package edu.eom.ics4u.monopoly.model.community;

import edu.eom.ics4u.monopoly.game.Player;
import edu.eom.ics4u.monopoly.logic.GameLogic;
import edu.eom.ics4u.monopoly.model.Event;
import edu.eom.ics4u.monopoly.model.Model;
import edu.eom.ics4u.monopoly.model.RoomModel;
//The community is to generate some random event when player bypass community center. 
//The common community event is to handle user earn /lose some money with bank and go to some specific location. 

public class CommunityChest {
    private int id;
    private String name;
    private int amount;
    private int step;


    public CommunityChest(int id, String name, int amount, int step) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.setStep(step);
    }

    /**
     * @return the step
     */
    public int getStep() {
        return step;
    }

    /**
     * @param step the step to set
     */
    public void setStep(int step) {
        this.step = step;
    }

    /**
     * @return the amount
     */
    public int getAmount() {
        return amount;
    }
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @param amount the amount to set
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String DoEvent(int roomid, String playername, int landid) {
        Model m = Model.getInstance();
        RoomModel room = m.rooms.get(roomid);
        Player player = room.players.get(playername);
        GameLogic logic = GameLogic.getInstance();
		int cash0 = player.getCash();
		int saving0 = player.getSaving();

        logic.PlayerPay(room, player, 0-amount);
        if (step >=0) {
            player.setStep(step);
        } 
		int saving1 = player.getSaving();
		int cash1 = player.getCash();
		int loan = player.getLoan();

        Event event = new Event(Event.EVENT_COMMUNITY, roomid,player.getName(), player.getStep(),  name  ,cash0-cash1, saving0-saving1,cash1,saving1,0);
		room.eventqueue.add(event);
        return name;        
	
    }

    public void printInfo() {
        System.out.println(this.id + " " + this.name + " " + this.amount + " " + this.step);
    }
}
