package edu.eom.ics4u.monopoly.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import edu.eom.ics4u.monopoly.model.Model;
import edu.eom.ics4u.monopoly.model.RoomModel;

public class Player {
	private int roomId;
	private RoomModel roomModel;
	
	private String name;
	public int playerId;
	private boolean isMe;
	private boolean isActive = true;
	private int cash = 2000;
	private int saving = 0;
	private int loan = 0;
	private int hospitalStatus = 0;
	
    public static final Image [] [] CHARACTERS = {
    	{ new ImageIcon(Player.class.getResource("/Images/character1DL.png")).getImage(),
    	  new ImageIcon(Player.class.getResource("/Images/character1UL.png")).getImage(),
    	  new ImageIcon(Player.class.getResource("/Images/character1UR.png")).getImage(),
    	  new ImageIcon(Player.class.getResource("/Images/character1DR.png")).getImage()},
    	
    	{ new ImageIcon(Player.class.getResource("/Images/character2DL.png")).getImage(),
      	  new ImageIcon(Player.class.getResource("/Images/character2UL.png")).getImage(),
      	  new ImageIcon(Player.class.getResource("/Images/character2UR.png")).getImage(),
      	  new ImageIcon(Player.class.getResource("/Images/character2DR.png")).getImage()},
    	
    	{ new ImageIcon(Player.class.getResource("/Images/character3DL.png")).getImage(),
    	  new ImageIcon(Player.class.getResource("/Images/character3UL.png")).getImage(),
    	  new ImageIcon(Player.class.getResource("/Images/character3UR.png")).getImage(),
    	  new ImageIcon(Player.class.getResource("/Images/character3DR.png")).getImage()}
    };
    
    public static final Image [] STAR_IMAGES = {
        	new ImageIcon(Player.class.getResource("/Images/star1.png")).getImage(),
        	new ImageIcon(Player.class.getResource("/Images/star2.png")).getImage(),
        	new ImageIcon(Player.class.getResource("/Images/star3.png")).getImage(),
        	new ImageIcon(Player.class.getResource("/Images/star4.png")).getImage()
    };
    
    private Image [] images;
    private int characterId;
    private int step;
    private int edge;
	private int xFoot, yFoot;
	private int shiftCnt;
	
	private static final int [] X_DELTAS = {-7,  7, 7, -7};
    private static final int [] Y_DELTAS = {-5, -5, 5,  5};
    
    public Player(int roomId, String name, int characterId, int playerId, boolean isMe) {
    	this.roomId = roomId;
    	roomModel = Model.getInstance().rooms.get(roomId);
    	
    	this.name = name;
    	this.characterId = characterId;
    	this.playerId = playerId;
    	this.isMe = isMe;
    	
    	images = CHARACTERS[characterId];
    	xFoot = MapPanel.EDGE_END_XS[3];
    	yFoot = MapPanel.EDGE_END_YS[3];
    	edge = 0;
    	shiftCnt = 0;
    	
    	step = -1;
    }
    
    
    public Image getImageStar() {
    	return STAR_IMAGES[playerId];
    }
    
    public int getStep() {
    	return step;
    }
    
    public void setStep(int step) {
    	this.step = step;
    }
    
    public boolean getIsMe() {
    	return isMe;
    }

    public String getName() {
    	return name;
    }
    
    public void setPlayerId(int id) {
		 playerId  = id;
	}
    
    public int getPlayerId() {
    	return playerId;
    }
    
    public boolean isActive() {
    	return isActive;
    }
    
    public void setActive(boolean isActive) {
    	this.isActive = isActive;
    }
    
    public int getCash() {
    	return cash;
    }
    
    public void setCash(int cash){
    	this.cash = cash;
    }
    
    public int getSaving() {
    	return saving;
    }
    
    public void setSaving(int saving) {
    	this.saving = saving;
    }
    
    public int getLoan() {
    	return loan;
    }
    
    public void setLoan(int loan) {
    	this.loan = loan;
    }
    
    public int getHospitalstatus() {
    	return hospitalStatus;
    }
    
    public void setHospitalstatus(int hospitalStatus) {
    	this.hospitalStatus = hospitalStatus;
    }
    
	// the thread need to call this method to move the player to the destination
	public void move (int step) {
		this.step = step;
		
		// set the destination coordinates 
		int xDst   = roomModel.properties[step].getXplayer();
		int yDst   = roomModel.properties[step].getYplayer();
		int edgeDst = roomModel.properties[step].getEdge();	
		
		boolean arrived = false;
		while (arrived == false) {
	    	xFoot = xFoot + X_DELTAS[edge];
	    	yFoot = yFoot + Y_DELTAS[edge];
	    	
	    	switch (edge) {
			    case MapPanel.EDGE_DL:  // the player at the Down Left edge
			    	// check if the player arrives at the destination
			    	if ((yFoot < yDst) && (edge == edgeDst)) {
			    		xFoot = xDst;
			    		yFoot = yDst;
			    		arrived = true;		    		
			    	}
			    	
			    	// check if the player arrives at the end of the edge?
			    	// if yes, switch to next edge
			    	if (yFoot < MapPanel.EDGE_END_YS[MapPanel.EDGE_DL]) {			    		
			    		xFoot = MapPanel.EDGE_END_XS[MapPanel.EDGE_DL];
			    		yFoot = MapPanel.EDGE_END_YS[MapPanel.EDGE_DL];
			    		edge = MapPanel.EDGE_UL;
			    	}
			    	break;
			    	
			    case MapPanel.EDGE_UL: // the player at the Up Left edge
			    	// check if the player arrives at the destination
			    	if ((yFoot < yDst) && (edge == edgeDst)) {
			    		xFoot = xDst;
			    		yFoot = yDst;
			    		arrived = true;	
			    	}
			    	
			    	// check if the player arrives at the end of the edge?
			    	// if yes, switch to next edge
			    	if (yFoot < MapPanel.EDGE_END_YS[MapPanel.EDGE_UL]) {
			    		xFoot = MapPanel.EDGE_END_XS[MapPanel.EDGE_UL];
			    		yFoot = MapPanel.EDGE_END_YS[MapPanel.EDGE_UL];
			    		edge = MapPanel.EDGE_UR;			    		
			    	}
			    	break;
			    	
			    case MapPanel.EDGE_UR: // the player at the Up Right edge
			    	// check if the player arrives at the destination
			    	if ((yFoot > yDst) && (edge == edgeDst)) {
			    		xFoot = xDst;
			    		yFoot = yDst;
			    		arrived = true;	
			    	}
			    	
			    	// check if the player arrives at the end of the edge?
			    	// if yes, switch to next edge
			    	if (yFoot > MapPanel.EDGE_END_YS[MapPanel.EDGE_UR]) {
			    		xFoot = MapPanel.EDGE_END_XS[MapPanel.EDGE_UR];
			    		yFoot = MapPanel.EDGE_END_YS[MapPanel.EDGE_UR];
			    		edge = MapPanel.EDGE_DR;			    		
			    	}
			    	break;
			    	
			    case MapPanel.EDGE_DR: // the player at the Down Right edge
			    	// check if the player arrives at the destination
			    	if ((yFoot > yDst) && (edge == edgeDst)) {
			    		xFoot = xDst;
			    		yFoot = yDst;
			    		arrived = true;	
			    	}
			    	
			    	// check if the player arrives at the end of the edge?
			    	// if yes, switch to next edge
			    	if (yFoot > MapPanel.EDGE_END_YS[MapPanel.EDGE_DR]) {
			    		xFoot = MapPanel.EDGE_END_XS[MapPanel.EDGE_DR];
			    		yFoot = MapPanel.EDGE_END_YS[MapPanel.EDGE_DR];
			    		edge = MapPanel.EDGE_DL;			    		
			    	}
			    	break;
		    }
			
			// shift the frame of player image to make it animated
			shiftCnt = shiftCnt + 1;
			if (shiftCnt >= 4) {
				shiftCnt = 0;
			}
			
			// sleep
			try	{
    		    Thread.sleep(50);
    		} catch(InterruptedException ex) {
    		    Thread.currentThread().interrupt();
    		}
		}
	}
	
	public void jump () {
		// set the destination coordinates 
		xFoot = roomModel.properties[step].getXplayer();
		yFoot = roomModel.properties[step].getYplayer();
		edge  = roomModel.properties[step].getEdge();
	}
	
	public void draw(Graphics g) {
		g.drawImage(
				images[edge], 
				xFoot-20, yFoot-61,        // dst (x1, y1)
				xFoot-20+40, yFoot-61+62,  // dst (x2, y2)
				shiftCnt*40, 0,            // src (x1, y1) 
				(shiftCnt+1)*40, 62,       // src (x2, y2)
				null);
		
		// draw debug information
        Color oldColor = g.getColor();        
        g.setColor(new Color(255, 255, 255));  // white
	    g.setFont(new Font("Dialog", Font.BOLD, 18));
	    g.drawString(name, xFoot-20+3, yFoot-61-4);
        g.setColor(oldColor);
	}   
    
	
}
