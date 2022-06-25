package edu.eom.ics4u.monopoly.model.community;

import edu.eom.ics4u.monopoly.game.Player;
import edu.eom.ics4u.monopoly.model.Event;
import edu.eom.ics4u.monopoly.model.Model;
import edu.eom.ics4u.monopoly.model.RoomModel;
// save all cash to saving
public class CommunityChest24 extends CommunityChest {
    
    public CommunityChest24(int id, String name, int amount, int step) {
        super(id, name, amount, step);
    }
  
    @Override
    public String DoEvent(int roomid, String playername, int landid) {
        Model m = Model.getInstance();
        RoomModel room = m.rooms.get(roomid);
        Player player = room.players.get(playername);
        player.setSaving(player.getCash() + player.getSaving());
        player.setCash(0);
        int saving = player.getSaving();
		int cash = player.getCash();
		int loan = player.getLoan();

        Event event = new Event(Event.EVENT_COMMUNITY, roomid,playername, player.getStep(),  playername + this.getName()  , this.getAmount(), 0-this.getAmount(),cash,saving,0);
        room.eventqueue.add(event);        
        return this.getName();
    }
}
