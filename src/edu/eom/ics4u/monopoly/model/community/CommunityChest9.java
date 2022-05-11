package edu.eom.ics4u.monopoly.model.community;

import edu.eom.ics4u.monopoly.game.Player;
import edu.eom.ics4u.monopoly.logic.GameLogic;
import edu.eom.ics4u.monopoly.model.Event;
import edu.eom.ics4u.monopoly.model.Model;
import edu.eom.ics4u.monopoly.model.RoomModel;
//Collect property Tax, $10 per house
public class CommunityChest9 extends CommunityChest {
    
    public CommunityChest9(int id, String name, int amount, int step) {
        super(id, name, amount, step);
    }
  
    @Override
    public String DoEvent(int roomid, String playername, int landid) {
        Model m = Model.getInstance();
        RoomModel room = m.rooms.get(roomid);
        Player player = room.players.get(playername);
        int houses = 0;
        for (int i=0; i< room.properties.length; i++) {
            if (room.properties[i].getOwnerName().equals(playername)) {
                houses += room.properties[i].getLevel();
            }
        }
        GameLogic gameLogic = GameLogic.getInstance();
        gameLogic.PlayerPay(room, player, 10*houses);
        Event event = new Event(Event.EVENT_COMMUNITY, roomid,player.getName(), player.getStep(),  playername  ,10*houses, 0,-1,0,0);
		room.eventqueue.add(event); 
        return this.getName();   
    }
}
