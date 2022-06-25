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
        int cash0 = player.getCash();
        int saving0 = player.getSaving();
        gameLogic.PlayerPay(room, player, 10*houses);
        int saving1 = player.getSaving();
		int cash1 = player.getCash();
		int loan = player.getLoan();

        Event event = new Event(Event.EVENT_COMMUNITY, roomid,player.getName(), player.getStep(),  playername  , cash0-cash1, saving0-saving1,cash1,saving1,0);
		room.eventqueue.add(event); 
        return this.getName();   
    }
}
