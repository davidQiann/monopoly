package edu.eom.ics4u.monopoly.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import edu.eom.ics4u.monopoly.gameplaza.PlazaEventHandler;
import edu.eom.ics4u.monopoly.model.LogicResult;
import edu.eom.ics4u.monopoly.model.Model;
import edu.eom.ics4u.monopoly.model.RoomModel;

public class GameGui extends JFrame implements WindowListener,MouseListener, ActionListener{
	
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
    public ChartsTableModel chartsTableModel;
    public MapPanel mapPanel;
    
    private JLabel myTransLabel;
    private JLabel operationLabel;
    public ArrayList <String> myTrans = new ArrayList<String>();
    public ArrayList <String> operations = new ArrayList<String>();
    
    private int roomId;
    private RoomModel roomModel;
    public String myName;
    private GameEventHandler gameEventHandler;
    
    private static int gameId = 0;
    
    TransactionRecord record;
    
    public GameGui(int roomId, String myName){
    	gameId ++;
    	
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

        operationLabel = new JLabel();
        operationLabel.setFont(new Font("Dialog", Font.PLAIN, 14));
        operationLabel.setBounds(10, 25, RIGHT_ZONE_WIDTH-20, OPERATION_HEIGHT-35);
        operationLabel.setVerticalAlignment(JLabel.BOTTOM);
        operationPanel.add(operationLabel);    	
    	
    	transitionPanel = new JPanel();
    	transitionPanel.setBounds(RIGHT_ZONE_X, MY_TRANS_Y, RIGHT_ZONE_WIDTH, MY_TRANS_HEIGHT);
    	transitionPanel.setBorder(BorderFactory.createTitledBorder(
    			BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.BLACK, Color.WHITE),
    			" My Transcations ",
    			TitledBorder.DEFAULT_JUSTIFICATION,
    			TitledBorder.DEFAULT_POSITION,
    			
    			new Font("Dialog", Font.BOLD, 16),
    			new Color(0, 0, 0)
    	)); 
    	transitionPanel.setLayout(null);
     	add(transitionPanel);
     	
        myTransLabel = new JLabel();
        myTransLabel.setFont(new Font("Dialog", Font.PLAIN, 14));
        myTransLabel.setBounds(10, 25, RIGHT_ZONE_WIDTH-20, MY_TRANS_HEIGHT-35-35);
        myTransLabel.setVerticalAlignment(JLabel.BOTTOM);
        transitionPanel.add(myTransLabel);
    	
    	exportButton = new JButton("Export");
    	exportButton.setBounds(RIGHT_ZONE_WIDTH-90,MY_TRANS_HEIGHT-35,80,25);
    	transitionPanel.add(exportButton);
    	exportButton.addActionListener(this);
    	
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
    	
    	record = new TransactionRecord(roomModel.players);
    	
    	gameEventHandler = new GameEventHandler(this, roomId, gameId);
    	Thread eventHandlerThread = new Thread(gameEventHandler);
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

    // shift and show transaction
    public void shiftShowTrans (String trans, boolean isMe) {
    	String message;
    	
    	// shift and show my transaction
    	if (isMe == true) {
     		if (myTrans.size() >= 10) {
     			myTrans.remove(0);
     		}
     		myTrans.add(trans);     		
    		message = "<html>";
    		for (int i = 0; i < myTrans.size(); i++) {
    			message = message + myTrans.get(i);
    			if (i != myTrans.size()-1) {
    				message = message + "<br>";
    			}
    		}
    		message = message + "</html>";    		
    		myTransLabel.setText(message);
    	}
    	
    	// shift and show all players' operations
    	if (operations.size() >= 20) {
 			operations.remove(0);
 		}
 		operations.add(trans); 		
 		message = "<html>";
		for (int i = 0; i < operations.size(); i++) {
			message = message + operations.get(i);
			if (i != operations.size()-1) {
				message = message + "<br>";
			}
		}
		message = message + "</html>";		
		operationLabel.setText(message);	
 	}
    
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		if (e.getWindow() == this) {
			LogicResult logicResult;
			int result = gameEventHandler.popOptionDialog("Are you sure to quit the game? Have you exported your personal transactions?");
			if (result == 0) {
				if (roomId == 0 || roomId == 1) {
					ArrayList <String> playerNames = new ArrayList<String> ();
					for (Player player: roomModel.players.values()) {
						playerNames.add(player.getName());
					}
					
					for (int i =0; i < playerNames.size(); i++) {
						logicResult = roomModel.getRoomlogic().QuitGame(playerNames.get(i), roomId);
						System.out.printf("--window closing-- game Id = %d, %s quit the game request\n", gameId, playerNames.get(i));
					}					
				} else {
					logicResult = roomModel.getRoomlogic().QuitGame(myName, roomId);
					System.out.printf("--window closing-- game Id = %d, %s quit the game request\n", gameId, myName);
				}
			}
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
			if (mapPanel.goClicked(e.getX(),e.getY()) == true && mapPanel.getGoEnable()== true){				
				LogicResult result = roomModel.getRoomlogic().UserMove(mapPanel.getTurn(), roomId);
				if (result.getResultcode() == LogicResult.RESULT_SUCCESS) {
					mapPanel.goDown();
					mapPanel.setGoEnable(false);
				}else {
					//TODO TurnDone() to switch to next player
				}
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == exportButton) {
			//System.out.println("Test click export button");
			JFileChooser fileDialog = new JFileChooser();
			fileDialog.setDialogTitle("Set transaction as:");
			fileDialog.setFileFilter(new FileNameExtensionFilter("Excel Files (*.xls)", "xls"));
			
			int ret = fileDialog.showOpenDialog(null);
            if (ret == JFileChooser.APPROVE_OPTION) {
            	String filename = fileDialog.getSelectedFile().getPath();
            	if (!filename.endsWith(".xls")) {
            		filename = filename + ".xls";
            	}
            	System.out.println("Test File name = " + filename);
            	
            	// TODO: save the transaction records to the excel file
            }
		}
	}
	
}
