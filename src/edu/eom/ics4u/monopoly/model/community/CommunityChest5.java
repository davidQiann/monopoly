package edu.eom.ics4u.monopoly.model.community;

import edu.eom.ics4u.monopoly.game.Player;
import edu.eom.ics4u.monopoly.logic.GameLogic;
import edu.eom.ics4u.monopoly.model.Event;
import edu.eom.ics4u.monopoly.model.Model;
import edu.eom.ics4u.monopoly.model.RoomModel;
//Birthday, gather $50 gift from each player
public class CommunityChest5 extends CommunityChest {
    
    public CommunityChest5(int id, String name, int amount, int step) {
        super(id, name, amount, step);
    }
  
    @Override
    public String DoEvent(int roomid, String playername, int landid) {
        Model m = Model.getInstance();
        RoomModel room = m.rooms.get(roomid);
        Player player = room.players.get(playername);
        GameLogic logic = GameLogic.getInstance();
        int total = 0;
        for (Player pl : room.players.values()) {
            if (pl.isActive() == true && (pl.getName().equals(playername) == false)) {
                logic.PlayerPay(room, pl, this.getAmount());
                player.setCash(player.getCash() + this.getAmount());
                Event event = new Event(Event.EVENT_COMMUNITY, roomid,pl.getName(), pl.getStep(),  "Player " + pl.getName() +"pay birthday gift for " + playername  , this.getAmount(), 0,-1,0,0);
                room.eventqueue.add(event);        
                total += this.getAmount();
            }
        }
        Event event = new Event(Event.EVENT_COMMUNITY, roomid,player.getName(), player.getStep(), this.getName()  , total, 0,0,0,0);
        room.eventqueue.add(event);        

        return this.getName();

    }
}
