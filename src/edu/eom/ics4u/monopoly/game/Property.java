package edu.eom.ics4u.monopoly.game;

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

	public static final String [] LOCATIONS = {
			"Goldridge Dr",   //  0
			"Grengold Way",   //  1
			"Hemlo Cres",     //  2
			"Insmill Cres",   //  3
			"Cheltonia Way",  //  4
			"Cambior Cres",   //  5
			"Battersea Cres", //  6
			"Ingersoll Cres", //  7
			"Keno Way",       //  8
			"Kanata Ave",     //  9, public
			"Torbec Ave",     // 10
			"Fletcher Cir",   // 11
			"Keyrock Dr",     // 12
			"Brunskill Way",  // 13
			"Badgeley Ave",   // 14
			"Remnor Ave",     // 15
			"Doyonn Ave",     // 16
			"Mancuso Ct",     // 17
			"Manning Ct",     // 18
			"Leverton Rd",    // 19, public
			"Campeau Dr",     // 20
			"The Pkwy",       // 21
			"Leacock Dr",     // 22
			"Reaney Ct",      // 23
			"Bellrock Dr",    // 24
			"Kettleby St",    // 25
			"Teeswater St",   // 26
			"Shearer Cres",   // 27
			"Lombardo Dr",    // 28
			"Stratas Ct",     // 29, public
			"Millman Ct",     // 30
			"Pickford Dr",    // 31
			"Hewitt Way",     // 32
			"Chimo Dr",       // 33
			"Anik Way",       // 34
			"Kakulu Rd",      // 35
			"Belleview Dr",   // 36
			"Pineview Rd",    // 37
			"Vanstone Dr",    // 38
			"START"           // 39	
		};

		private static final int [][] RENTS = {
		//	land   h1   h2   h3    h4  hotel
			{ 26, 130, 390, 900, 1100, 1275}, //  0
			{  2,  10,  30,  90,  160,  250}, //  1
			{ 22, 110, 330, 800,  975, 1150}, //  2
			{ 16,  80, 220, 600,  800, 1000}, //  3
			{  4,  20,  60, 180,  320,  450}, //  4
			{ 24, 120, 360, 850, 1025, 1200}, //  5
			{  6,  30,  90, 270,  400,  550}, //  6
			{  8,  40, 100, 300,  450,  600}, //  7
			{ 22, 110, 330, 800,  975, 1150}, //  8
			{  0,   0,   0,   0,    0,    0}, //  9, public
			{ 10,  50, 150, 450,  625,  750}, // 10
			{ 12,  60, 180, 500,  700,  900}, // 11
			{ 14,  70, 200, 550,  750,  950}, // 12
			{ 35, 175, 500,1100, 1300, 1500}, // 13
			{ 50, 200, 600,1400, 1700, 2000}, // 14
			{ 18,  90, 250, 700,  875, 1050}, // 15
			{ 20, 100, 300, 750,  925, 1100}, // 16
			{ 26, 130, 390, 900, 1100, 1275}, // 17
			{ 18,  90, 250, 700,  875, 1050}, // 18
			{  0,   0,   0,   0,    0,    0}, // 19, public
			{ 28, 150, 450,1000, 1200, 1400}, // 20
			{ 14,  70, 200, 550,  750,  950}, // 21
			{ 10,  50, 150, 450,  625,  750}, // 22
			{  6,  30,  90, 270,  400,  550}, // 23
			{ 16,  80, 220, 600,  800, 1000}, // 24
			{ 10,  50, 150, 450,  625,  750}, // 25
			{ 26, 130, 390, 900, 1100, 1275}, // 26
			{ 26, 130, 390, 900, 1100, 1275}, // 27
			{ 18,  90, 250, 700,  875, 1050}, // 28
			{  0,   0,   0,   0,   0,    0},  // 29, public
			{ 10,  50, 150, 450,  625,  750}, // 30
			{  2,  10,  30,  90,  160,  250}, // 31
			{ 22, 110, 330, 800,  975, 1150}, // 32
			{ 16,  80, 220, 600,  800, 1000}, // 33
			{  4,  20,  60, 180,  320,  450}, // 34
			{ 24, 120, 360, 850, 1025, 1200}, // 35
			{  6,  30,  90, 270,  400,  550}, // 36
			{ 22, 110, 330, 800,  975, 1150}, // 37
			{ 26, 130, 390, 900, 1100, 1275}, // 38	
			{  0,   0,   0,   0,    0,    0}  // 39, public
		};
		
		private static final int [][] PRICES = {
		//	land  house  hotel
			{300,   200,  200}, //  0
			{ 60,    50,   50}, //  1
			{260,   150,  150}, //  2
			{200,   100,  100}, //  3
			{ 60,    50,   50}, //  4
			{280,   150,  150}, //  5
			{100,    50,   50}, //  6
			{120,    50,   50}, //  7
			{260,   150,  150}, //  8
			{  0,     0,    0}, //  9, public
			{140,   100,  100}, // 10
			{160,   100,  100}, // 11
			{180,   100,  100}, // 12
			{350,   200,  200}, // 13
			{400,   200,  200}, // 14
			{220,   150,  150}, // 15
			{240,   150,  150}, // 16
			{300,   200,  200}, // 17
			{220,   150,  150}, // 18
			{  0,     0,    0}, // 19, public
			{320,   200,  200}, // 20
			{180,   100,  100}, // 21
			{140,   100,  100}, // 22
			{100,    50,   50}, // 23
			{200,   100,  100}, // 24
			{140,   100,  100}, // 25
			{300,   200,  200}, // 26
			{300,   200,  200}, // 27
			{220,   150,  150}, // 28
			{  0,     0,    0}, // 29, public
			{140,   100,  100}, // 30
			{ 60,    50,   50}, // 31
			{260,   150,  150}, // 32
			{200,   100,  100}, // 33
			{ 60,    50,   50}, // 34
			{280,   150,  150}, // 35
			{100,    50,   50}, // 36
			{260,   150,  150}, // 37
			{200,   100,  100}, // 38
			{  0,     0,    0}  // 39, public
		};
		
	public Property(int id, String type, String name) {
		this.id = id;
	    this.type = type;
		this.name = name;
		ownerName = null;
		imageStar = null;
		level = 0;
		
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
	
	public int getLevel() {
		return level;
	}
	
	public String getLocation() {
		return LOCATIONS[id];
	}
	
	public int getRent(int level) {
		return RENTS[id][level];
	}
	
	public int getPrice(String name) {
		if (name == "land") {
			return PRICES[id][0];
		}else if (name == "house"){
			return PRICES[id][1];
		}else {
			return PRICES[id][2];
		}
	}
	
	public int getPrice(int level) {
		if (level == 0) {
			return PRICES[id][0];
		}else if(level <5) {
			return PRICES[id][1];
		}else {
			return PRICES[id][2];
		}
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
		if (image != null) {
			g.drawImage(image,xProperty,yProperty,null);
		}
	}
	
	
	public void drawStar(Graphics g) {
		if (type == "private" && imageStar != null) {
			g.drawImage(imageStar, xStar,yStar,null);
		}
	}
	
}
