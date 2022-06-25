package edu.eom.ics4u.monopoly.model.community;

import edu.eom.ics4u.monopoly.game.Player;
import edu.eom.ics4u.monopoly.logic.GameLogic;
import edu.eom.ics4u.monopoly.model.Event;
import edu.eom.ics4u.monopoly.model.Model;
import edu.eom.ics4u.monopoly.model.RoomModel;

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
        logic.PlayerPay(room, player, 0-amount);
        if (step >=0) {
            player.setStep(step);
        } 
		int saving = player.getSaving();
		int cash = player.getCash();
		int loan = player.getLoan();

        Event event = new Event(Event.EVENT_COMMUNITY, roomid,player.getName(), player.getStep(),  name  ,-amount, 0,cash,saving,0);
		room.eventqueue.add(event);
        return name;        
	
    }

    public void printInfo() {
        System.out.println(this.id + " " + this.name + " " + this.amount + " " + this.step);
    }
}
