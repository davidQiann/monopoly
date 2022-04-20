package edu.eom.ics4u.monopoly.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.eom.ics4u.monopoly.model.Model;
import edu.eom.ics4u.monopoly.model.RoomModel;

public class MapPanel extends JPanel{
	private static Color bkgColor = new Color(7,44,77);
	
	public static final Image ROAD_IMAGE = new ImageIcon(MapPanel.class.getResource("/Images/road.png")).getImage();
    public static final Image [] DICE_IMAGES = {
    	new ImageIcon(MapPanel.class.getResource("/Images/dice1.png")).getImage(),
    	new ImageIcon(MapPanel.class.getResource("/Images/dice2.png")).getImage(),
    	new ImageIcon(MapPanel.class.getResource("/Images/dice3.png")).getImage(),
    	new ImageIcon(MapPanel.class.getResource("/Images/dice4.png")).getImage(),
    	new ImageIcon(MapPanel.class.getResource("/Images/dice5.png")).getImage(),
    	new ImageIcon(MapPanel.class.getResource("/Images/dice6.png")).getImage(),
    	new ImageIcon(MapPanel.class.getResource("/Images/land.png")).getImage()
    };
    public static final Image GO_ENABLE_IMAGE = new ImageIcon(MapPanel.class.getResource("/Images/goGreen.png")).getImage();
    public static final Image GO_DISABLE_IMAGE = new ImageIcon(MapPanel.class.getResource("/Images/goGray.png")).getImage();
    public static final Image PROPERTIES_IMAGE = new ImageIcon(MapPanel.class.getResource("/Images/land.png")).getImage();
    
    private static final int XROAD = GameGui.MAP_WIDTH/2 - ROAD_IMAGE.getWidth(null)/2;
    private static final int YROAD = 31;
    
    private static final int XGO = GameGui.MAP_WIDTH - 200;
    private static final int YGO = GameGui.MAP_HEIGHT - 100;
    private static final int YGO_PRESSED = YGO + 8;
    private int yGo = YGO;
    
    private static final int DICE2_X = GameGui.MAP_WIDTH/2;
    private static final int DICE2_Y = GameGui.MAP_HEIGHT/2-120;
    private static final int DICE1_X = GameGui.MAP_WIDTH/2-100;
    private static final int DICE1_Y = GameGui.MAP_HEIGHT/2-120;
    private Image imageDice1 = DICE_IMAGES[4], imageDice2 = DICE_IMAGES[3];
    private int imageCnt = 9;
    
    private static final int NUM_PROPERTY = 40;
    private Property properties [] = new Property[NUM_PROPERTY]; // 0 - 39

   // DL = Down Left, UL = Up Left, UR = Up Right, DR = Down Right
    public static final int EDGE_DL = 0, EDGE_UL = 1, EDGE_UR = 2, EDGE_DR = 3; 
    
    public static final int [] EDGE_END_XS = {XROAD+62,  XROAD+510, XROAD+897, XROAD+452};
    public static final int [] EDGE_END_YS = {YROAD+358, YROAD+46,  YROAD+317, YROAD+631}; 
    
    private int selectedPropertyId = 0;
    
    private int roomId;
    private RoomModel roomModel;
    private String turnPlayer = null;


    public MapPanel(int roomId) {
    	this.roomId = roomId;
    	roomModel = Model.getInstance().rooms.get(roomId);
    	properties = roomModel.properties;    	
    	calcPropertyCoor(XROAD,YROAD);
    }
    
    public void paint(Graphics g) {
    	g.setColor(bkgColor);
    	g.fillRect(0, 0, GameGui.MAP_WIDTH, GameGui.MAP_HEIGHT);
    	g.drawImage(ROAD_IMAGE, XROAD, YROAD, null);
    	g.drawImage(GO_ENABLE_IMAGE, XGO,yGo,null);
    	
    	// draw dices
    	g.drawImage(
    			imageDice1,
    			DICE1_X, DICE1_Y,        // dst (x1, y1)
    			DICE1_X+63, DICE1_Y+126, // dst (x2, y2)
    			imageCnt*63, 0,          // src (x1, y1)
    			(imageCnt+1)*63, 126,    // src (x2, y2)
    			null
    			);    	
    	g.drawImage(
    			imageDice2,
    			DICE2_X, DICE2_Y,        // dst (x1, y1)
    			DICE2_X+63, DICE2_Y+126, // dst (x2, y2)
    			imageCnt*63, 0,          // src (x1, y1)
    			(imageCnt+1)*63, 126,    // src (x2, y2)
    			null
    			);
        
    	
    	for (int i = 0; i < NUM_PROPERTY; i++) {
    		properties[i].drawStar(g);
    	}
    	
    	// down right edge, 9 properties, id from 8 down to 0
     	int id;
    	for (int i = 0; i < 9; i++) {
    		id = 8 - i;
    		properties[id].draw(g);
    	}
    	
    	// up left edge, 11 properties, id from 19 down to 9
    	for (int i = 0; i < 11; i++) {
    		id = 19 - i;
    		properties[id].draw(g);
    	}
    	
    	// up right edge, 9 properties, id from 20 up to 28
    	for (int i = 0; i < 9; i++) {
    		id = 20 + i;
    		properties[id].draw(g);
    	}
    	
    	// down left edge, 11 properties, id from 29 up to 39
    	for (int i = 0; i < 11; i++) {
    		id = 29 + i;
    		properties[id].draw(g);
    	} 
    	
    	// draw players
    	for (Player player: roomModel.players.values()) {
    		player.draw(g);
    	}
    	
    	// draw the selected property information
    	drawPropertyInfo(g, 5, 14);
    }
    
    
    public void calcPropertyCoor(int xRoad, int yRoad) {
    	int x;
    	int y;
    	int id;
    	
    	// down left edge, 9 properties, id from 8 down to 0
    	x = xRoad + 7;   // id = 8
    	y = yRoad + 364; // id = 8
    	for (int i = 0; i < 9; i++) {
    		id = 8 - i;
    		properties[id].setLocation(x, y); 
    		x = x + 40;
    		y = y + 28;	
    	}
    	
    	// up left edge, 11 properties, id from 19 down to 9
    	x = xRoad + 401;  // id = 19
    	y = yRoad - 29;   // id = 19
    	for (int i = 0; i < 11; i++) {
    		id = 19 - i;
    		properties[id].setLocation(x, y);
    		x = x - 40;
    		y = y + 28; 
    	}
    	
    	// up right edge, 9 properties, id from 20 up to 28
    	x = xRoad + 545; // id = 20
    	y = yRoad - 24;  // id = 20
    	for (int i = 0; i < 9; i++) {
    		id = 20 + i;
    		properties[id].setLocation(x, y);
    		x = x+40;
    		y= y+28;
    	}
    	
    	// down left edge, 11 properties, id from 29 up to 39
    	x = xRoad + 877;  // id = 29
    	y = yRoad + 320;  // id = 29
    	for (int i = 0; i < 11; i++) {
    		id = 29 + i;
    		properties[id].setLocation(x, y);
    		x = x-40;
    		y = y+28;
    	}    	
    }
    
    public void goDown() {
    	yGo = YGO_PRESSED; 
    }
    
    public void goUp() {
    	yGo = YGO; 
    }
    
    
    public boolean goClicked(int x, int y) {
		Rectangle goArea = new Rectangle(XGO,YGO,GO_ENABLE_IMAGE.getWidth(null), GO_ENABLE_IMAGE.getHeight(null));
		
		if (goArea.contains(x,y)) {
			return true;
		}
		else {
			return false;
		}
	}
   
    public void rollDice(int dice1,int dice2) {
        imageDice1 = DICE_IMAGES[dice1-1];
    	imageDice2 = DICE_IMAGES[dice2-1];
    	
    	for (imageCnt = 0; imageCnt < 9; imageCnt++) {
    		try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                //               
            }
    	}
    }
    
	public void drawPropertyInfo(Graphics g, int x, int y) {
		Color oldColor = g.getColor();
        g.setColor(new Color(255, 255, 255));  // white
        g.setFont(new Font("Courier New", Font.PLAIN, 13));
                
        String [] infos = {        	
    		"Name: " + properties[selectedPropertyId].getName(),            	
    		"Owner: " + properties[selectedPropertyId].getOwnerName(), 
        	"Location: " + properties[selectedPropertyId].getLocation(),
        	"Rent:",
        	"- Land          $" + properties[selectedPropertyId].getRent(0),
        	"- House Level 1 $" + properties[selectedPropertyId].getRent(1),
        	"- House Level 2 $" + properties[selectedPropertyId].getRent(2),
        	"- House Level 3 $" + properties[selectedPropertyId].getRent(3),
        	"- House Level 4 $" + properties[selectedPropertyId].getRent(4),
        	"- Hotel         $" + properties[selectedPropertyId].getRent(5),
        	"Cost:",
        	"- Land          $" + properties[selectedPropertyId].getPrice("land"),
        	"- House         $" + properties[selectedPropertyId].getPrice("house"),
        	"- Hotel         $" + properties[selectedPropertyId].getPrice("hotel")
        };
        
        int rows = infos.length;
        if (properties[selectedPropertyId].getPropertyType() == "public") {
        	rows = 3;
        }
        
        int strY = y;        
        for (int i = 0; i < rows; i++) {        	
        	g.drawString(infos[i], x, strY);
        	strY += 14;        	
        }
        
        g.setColor(oldColor);
	}
	
	public void setTurn(String playerName) {
		turnPlayer = playerName;
	}
	
	public String getTurn() {
		return turnPlayer;
	}
	
    ////////////////////////////////////////////////////////////////////////////////////////////
    // Test
    ////////////////////////////////////////////////////////////////////////////////////////////    
	public void testGo() {
        
		Thread t = new Thread(new Runnable(){
			 public void run(){
				Player player = roomModel.players.get(turnPlayer);
				
				Random rand = new Random();
				int dice1 = rand.nextInt(6) + 1;
				int dice2 = rand.nextInt(6) + 1;				
				rollDice(dice1, dice2);				
				int currStep = player.getStep(); // players.get(playerNum).getStep();
				int nextStep = currStep + dice1 + dice2;
				if (nextStep >= NUM_PROPERTY) {
					nextStep = nextStep - NUM_PROPERTY;
				}
				selectedPropertyId = nextStep;
				player.move(nextStep, properties);
				
				// next player
				turnPlayer = roomModel.getUserTurn(turnPlayer);				
			}
		});
		t.start(); 
    }

}
