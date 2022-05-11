package edu.eom.ics4u.monopoly.model.community;

import edu.eom.ics4u.monopoly.game.Player;
import edu.eom.ics4u.monopoly.model.Model;
import edu.eom.ics4u.monopoly.model.RoomModel;
//go to hospital
public class CommunityChest1 extends CommunityChest {
    
    public CommunityChest1(int id, String name, int amount, int step) {
        super(id, name, amount, step);
    }
  
    @Override
    public String DoEvent(int roomid, String playername, int landid) {
        super.DoEvent(roomid, playername, landid);
        Model m = Model.getInstance();
        RoomModel room = m.rooms.get(roomid);
        Player player = room.players.get(playername);
        player.setHospitalstatus(2);
        return this.getName();
    }
}
