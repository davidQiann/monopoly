package edu.eom.ics4u.monopoly.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import edu.eom.ics4u.monopoly.gameplaza.PlazaEventHandler;
import edu.eom.ics4u.monopoly.model.Model;
import edu.eom.ics4u.monopoly.model.RoomModel;

public class GameGui extends JFrame implements WindowListener,MouseListener{
	
	public static final int MAP_X = 5;
    public static final int MAP_Y = 5;
	public static final int MAP_WIDTH = 970;
    public static final int MAP_HEIGHT = 725;
    
    public static final int RIGHT_ZONE_X = MAP_X + MAP_WIDTH + 5;    
    public static final int RIGHT_ZONE_WIDTH = 420;
    
    public static final int OPERATION_Y = 5;
    public static final int OPERATION_HEIGHT = 280+24*3;
    
    public static final int CHARTS_Y = OPERATION_Y + OPERATION_HEIGHT + 10;
    public static final int CHARTS_HEIGHT    = 24*5+10;
    
    public static final int MY_TRANS_Y = CHARTS_Y + CHARTS_HEIGHT + 5;
    public static final int MY_TRANS_HEIGHT  = 180+24*2;
    
    public static final int GUI_WIDTH  = MAP_WIDTH + RIGHT_ZONE_WIDTH + 30;
    public static final int GUI_HEIGHT = MAP_HEIGHT + 48;
    
    private JPanel operationPanel, transitionPanel;
    private JTable chartsTable;
    private JButton exportButton;
    private ChartsTableModel chartsTableModel;
    public MapPanel mapPanel;
    private int turns = 0;
    
    private JTextArea goEnableArea = new JTextArea();;
    
    private int roomId;
    private RoomModel roomModel;
    private String myName;
    
    public GameGui(int roomId, String myName){
    	this.roomId = roomId;
    	roomModel = Model.getInstance().rooms.get(roomId);
    	this.myName = myName;
    	
    	setBounds(0,0,GUI_WIDTH,GUI_HEIGHT);
    	setLayout(null);
    	
    	mapPanel = new MapPanel(roomId);
    	mapPanel.setBounds(MAP_X, MAP_Y, MAP_WIDTH, MAP_HEIGHT);
    	add(mapPanel);
    	
    	operationPanel = new JPanel();
    	operationPanel.setBounds(RIGHT_ZONE_X, OPERATION_Y, RIGHT_ZONE_WIDTH, OPERATION_HEIGHT);
        operationPanel.setBorder(BorderFactory.createTitledBorder(
    			BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.BLACK, Color.WHITE),
    			" Operations ",
    			TitledBorder.DEFAULT_JUSTIFICATION,
    			TitledBorder.DEFAULT_POSITION,
    			new Font("Dialog", Font.BOLD, 16),
    			new Color(0, 0, 0)
    	)); 
        operationPanel.setLayout(null);
    	add(operationPanel);
    	
    	transitionPanel = new JPanel();
    	transitionPanel.setBounds(RIGHT_ZONE_X, MY_TRANS_Y, RIGHT_ZONE_WIDTH, MY_TRANS_HEIGHT);
    	transitionPanel.setBorder(BorderFactory.createTitledBorder(
    			BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.BLACK, Color.WHITE),
    			" My Transition ",
    			TitledBorder.DEFAULT_JUSTIFICATION,
    			TitledBorder.DEFAULT_POSITION,
    			new Font("Dialog", Font.BOLD, 16),
    			new Color(0, 0, 0)
    	)); 
    	transitionPanel.setLayout(null);
     	add(transitionPanel);
    	
    	exportButton = new JButton("Export");
    	exportButton.setBounds(RIGHT_ZONE_WIDTH-90,MY_TRANS_HEIGHT-35,80,25);
    	transitionPanel.add(exportButton);
    	
    	chartsTableModel = new ChartsTableModel();
    	chartsTable = new JTable(chartsTableModel);
    	chartsTable.getTableHeader().setBounds(RIGHT_ZONE_X, CHARTS_Y, GameGui.RIGHT_ZONE_WIDTH, 24);
    	chartsTable.setBounds(RIGHT_ZONE_X, CHARTS_Y+24, GameGui.RIGHT_ZONE_WIDTH, 24*4);
    	chartsTable.getTableHeader().setFont(new Font("Dialog", Font.PLAIN, 14));
    	chartsTable.setRowHeight(24);
    	add(chartsTable.getTableHeader());
    	add(chartsTable); 
    	
    	
    	addWindowListener(this);
    	setVisible(true);
    	setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    	
    	Thread paintThread = new Thread (new PaintThread());
    	paintThread.start ();
    	
    	mapPanel.addMouseListener(this);
    	
    	Thread eventHandlerThread = new Thread(new GameEventHandler(this, roomId));
		eventHandlerThread.start();
    	
    }
    
    private class PaintThread extends Thread {
    	public void run() {
    		while (true) {

    			//System.out.println("Thread Test");                
    			mapPanel.repaint();
    			
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    //               
                }
            }
    	}
    }

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		if (e.getWindow() == this) {
			this.dispose();
		}
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == mapPanel) {
			if (mapPanel.goClicked(e.getX(),e.getY()) == true){
				mapPanel.goDown();
				
				// for test begin
				mapPanel.testGo();
				// for test end
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("Test mouse released");
		mapPanel.goUp();
    }

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
