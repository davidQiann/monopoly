package edu.eom.ics4u.monpoly.game;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Property {
    private String type;// public and private
    private String name;
    private String ownerName;
    private int id;
    private int level;
    private int edge; // down left = 0 up  left  = 1  up right = 2 down right = 3
    private int xProperty, yProperty;
    private int xStar, yStar;
	private int xPlayer,yPlayer;
    private boolean isOwned;
   	private Image image, imageStar;
	
	// 0 - land, 1~4 - house, 5 - hotel
	public static final Image [] LHH_BLUE_IMAGES = {
		new ImageIcon(Property.class.getResource("/Images/landBlue.png")).getImage(),
	   	new ImageIcon(Property.class.getResource("/Images/houseBlue1.png")).getImage(),
	   	new ImageIcon(Property.class.getResource("/Images/houseBlue2.png")).getImage(),
	   	new ImageIcon(Property.class.getResource("/Images/houseBlue3.png")).getImage(),
	   	new ImageIcon(Property.class.getResource("/Images/houseBlue4.png")).getImage(),
	   	new ImageIcon(Property.class.getResource("/Images/hotelBlue.png")).getImage()
	};
	
	// 0 - land, 1~4 - house, 5 - hotel	
	public static final Image [] LHH_RED_IMAGES = {
		new ImageIcon(Property.class.getResource("/Images/landRed.png")).getImage(),	    	
	   	new ImageIcon(Property.class.getResource("/Images/houseRed1.png")).getImage(),
	   	new ImageIcon(Property.class.getResource("/Images/houseRed2.png")).getImage(),
	   	new ImageIcon(Property.class.getResource("/Images/houseRed3.png")).getImage(),
	   	new ImageIcon(Property.class.getResource("/Images/houseRed4.png")).getImage(),
	   	new ImageIcon(Property.class.getResource("/Images/hotelRed.png")).getImage()
	};
	
	public static final Image UNOWNED_LAND_IMAGE = new ImageIcon(Property.class.getResource("/Images/land.png")).getImage();
	public static final Image MALL_IMAGE         = new ImageIcon(Property.class.getResource("/Images/mallBlue.png")).getImage();    
	public static final Image PARK_IMAGE         = new ImageIcon(Property.class.getResource("/Images/parkBlue.png")).getImage();    
	public static final Image HOSPITAL_IMAGE     = new ImageIcon(Property.class.getResource("/Images/hospital.png")).getImage();
	public static final Image BANK_IMAGE         = new ImageIcon(Property.class.getResource("/Images/bank.png")).getImage();
     
	
 
    // DL = Down Left, UL = Up Left, UR = Up Right, DR = Down Right
    public static final int EDGE_DL = 0, EDGE_UL = 1, EDGE_UR = 2, EDGE_DR = 3;
    
	public static final int HOTEL_LEVEL = 5;
	
	private static final int [] STAR_XOFFSETS   = { 59,  68,  -9, -4};
	private static final int [] STAR_YOFFSETS   = {  3,  63,  65,  3};	
	private static final int [] PLAYER_XOFFSETS = { 85,  90,  -6,  1};
	private static final int [] PLAYER_YOFFSETS = { 15,  88,  91, 11};
	
	public Property(int id, String type, String name) {
		this.id = id;
	    this.type = type;
		this.name = name;
		if (type == "public") {
			if (name == "bank") {
				image = BANK_IMAGE;
			} else if (name =="hospital") {
				image = HOSPITAL_IMAGE;
			} else if (name == "park") {
				image = PARK_IMAGE;
			} else if (name == "mall") {
				image = MALL_IMAGE;
			} else {
				image = null;
			}
		} else {
			image = UNOWNED_LAND_IMAGE;
			this.isOwned = false;
		}
		
		if (id <=8 && id >=0) {
			edge = EDGE_DL;
		} else if (id <=19 && id >=9) {
			edge = EDGE_UL;
		} else if (id <=28 && id >=20) {
			edge = EDGE_UR;
		} else {
			edge = EDGE_DR;
		} 
	}
	
   
	public void setLocation(int x, int y) {
		xProperty = x;
		yProperty = y;
		xPlayer = x+ PLAYER_XOFFSETS[edge];
		yPlayer = y+ PLAYER_YOFFSETS[edge];
		xStar = x+ STAR_XOFFSETS[edge];
		yStar = y+ STAR_YOFFSETS[edge];
	}

	public int getXplayer() {
		return xPlayer;
	}
	
	public int getYplayer() {
		return yPlayer;
	}
	
	public int getEdge() {
		return edge;
	}
	

	public void updPrivateProperty(int level, String ownerName, Image imageStar) {
		this.level = level;
		this.ownerName = ownerName;
		this.imageStar = imageStar;
		image = LHH_BLUE_IMAGES[level];
		isOwned = true;
		
		if (level ==0) {
			name = "land";
		}else if (level <5) {
			name = "house level " + level;
		}else {
			name = "hotel";
		}
		
	}
	
	public void draw(Graphics g) {
		g.drawImage(image,xProperty,yProperty,null);
		
	}
	
	
	public void drawStar(Graphics g) {
		// TODO
  	
		for (int i = 0; i < 39; i++) {
			if(isOwned ==true) {
				g.drawImage(Player.STAR_IMAGES[0], xStar,yStar,null); // TODO : find owner's id
		    }
		}
	}
	
}
