package edu.eom.ics4u.monopoly.model.community;

import edu.eom.ics4u.monopoly.game.Player;
import edu.eom.ics4u.monopoly.logic.GameLogic;
import edu.eom.ics4u.monopoly.model.Event;
import edu.eom.ics4u.monopoly.model.Model;
import edu.eom.ics4u.monopoly.model.RoomModel;
//Noise harrasment, pay $25 to each player
public class CommunityChest15 extends CommunityChest {
    
    public CommunityChest15(int id, String name, int amount, int step) {
        super(id, name, amount, step);
    }
  
    @Override
    public String DoEvent(int roomid, String playername, int landid) {
        Model m = Model.getInstance();
        RoomModel room = m.rooms.get(roomid);
        Player player = room.players.get(playername);
        GameLogic logic = GameLogic.getInstance();
        int total = 0;
        int cash = player.getCash();
    	int saving = player.getSaving();
        for (Player pl : room.players.values()) {
            if (pl.isActive()== true && (pl.getName().equals(playername) == false)) {
            	int cash0 = player.getCash();
            	int saving0 = player.getSaving();
                logic.PlayerPay(room, player, this.getAmount());
                pl.setCash(pl.getCash() + this.getAmount());
                
        		int saving1 = player.getSaving();
        		int cash1 = player.getCash();
        		int loan = player.getLoan();

                Event event = new Event(Event.EVENT_COMMUNITY, roomid,pl.getName(), pl.getStep(),  playername + this.getName()  , cash0-cash1, saving0-saving1,cash1,saving1,0);
                room.eventqueue.add(event);        
                total += this.getAmount();
            }
        }
        int saving1 = player.getSaving();
		int cash1 = player.getCash();
        Event event = new Event(Event.EVENT_COMMUNITY, roomid,player.getName(), player.getStep(), this.getName()  , cash-cash1, saving-saving1,cash1,saving1,0);
        room.eventqueue.add(event);  
        return this.getName();      
    }
}
