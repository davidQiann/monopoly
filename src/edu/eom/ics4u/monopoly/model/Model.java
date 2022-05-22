package edu.eom.ics4u.monopoly.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

import edu.eom.ics4u.monopoly.logic.GameLogic;
import edu.eom.ics4u.monopoly.logic.RemoteLogic;

public class Model {
    public static final int NUM_ROOMS = 50;
    private static Model instance = null;
    /**
     *  The monopoly game model
     */
    
    public ArrayList <RoomModel> rooms = new ArrayList<RoomModel>();

    private Queue<Event> eventqueue = new ArrayDeque<Event>();
     
    public Model(){//add
        for (int i=0; i<NUM_ROOMS; i++) {
            RoomModel roommodel = new RoomModel(i);
            if (i==0 || i==1) {
                roommodel.setRoomlogic(GameLogic.getInstance());
            }else{
                roommodel.setRoomlogic(RemoteLogic.getInstance());
            }
            rooms.add(roommodel);
        }
    }
   
    /**
     * @return the eventqueue
     */
    public Queue<Event> getEventqueue() {
        return eventqueue;
    }

    /**
     * @param eventqueue the eventqueue to set
     */
    public void setEventqueue(Queue<Event> eventqueue) {
        this.eventqueue = eventqueue;
    }

    public static Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }
    
}
