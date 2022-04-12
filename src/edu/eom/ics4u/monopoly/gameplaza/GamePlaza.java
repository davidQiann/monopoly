package edu.eom.ics4u.monopoly.gameplaza;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import edu.eom.ics4u.monopoly.game.GameGui;
//import javax.swing.text.html.AccessibleHTML.TableElementInfo.TableAccessibleContext;


public class GamePlaza extends JFrame implements ActionListener{
	public static final int GROOM_TABLE_X      = 200;
	public static final int GROOM_TABLE_Y      = 5;
	public static final int GROOM_TABLE_WIDTH  = 800;
	public static final int GROOM_TABLE_HEIGHT = 600;
	
	private static final int PLAZA_WIDTH  = GROOM_TABLE_X + GROOM_TABLE_WIDTH  + 20;
	private static final int PLAZA_HEIGHT = GROOM_TABLE_Y + GROOM_TABLE_HEIGHT + 93;
		
	public static final ImageIcon CHARACTER1 = new ImageIcon(GamePlaza.class.getResource("/Images/character1.png"));
	public static final ImageIcon CHARACTER2 = new ImageIcon(GamePlaza.class.getResource("/Images/character2.png"));
	public static final ImageIcon CHARACTER3 = new ImageIcon(GamePlaza.class.getResource("/Images/character3.png"));  
	
	private JLabel nameLabel;
	private JTextField nameTextField;
	private JLabel characterLabel;
	private JRadioButton radioCharacter1,radioCharacter2,radioCharacter3;
	private JLabel characterLabel1,characterLabel2,characterLabel3;
	private ButtonGroup radioCharacterGroup;
	private ButtonGroup gameModeButtonGroup;
	private JRadioButton radioGameMode1,radioGameMode2;
	private JLabel gameModeLabel;
	private JLabel normalModeLabel;
	private JLabel roundModeLabel;
	private JButton joinVisitor,joinPlayer,startGameButton;
	private JLabel infoLabel;
	
	private String name;
	private int characterId;
	private int roomId;
	private int gameMode;
	private boolean isJoinRoom = true;
	
	private GRoomTableModel gRoomTableModel;
	private JTable gRoomTable;	
	private JScrollPane roomsScrollPane;
	private Object roomDataContainer;
	
	public GamePlaza() {
		setTitle("Game Plaza");
		setBounds(0,0,PLAZA_WIDTH,PLAZA_HEIGHT);
		setLayout(null);
				
		nameLabel = new JLabel("Name: ");
		nameTextField = new JTextField();
		nameLabel.setBounds(20,5,170,25);
		nameTextField.setBounds(20,30,170,25);;
	    add(nameLabel);
	    add(nameTextField);
		
	    characterLabel = new JLabel("Choose a character:");
	    characterLabel.setBounds(20,80,170,25);
	    add(characterLabel);
		
	    radioCharacter1 = new JRadioButton(); 
	    radioCharacter2 = new JRadioButton();
	    radioCharacter3 = new JRadioButton();
	    radioCharacter1.setBounds(30,165,20,20);
	    radioCharacter2.setBounds(30,274,20,20);
	    radioCharacter3.setBounds(30,383,20,20);
	    add(radioCharacter1);
	    add(radioCharacter2);
	    add(radioCharacter3);
	    radioCharacterGroup = new ButtonGroup();
	    radioCharacterGroup.add(radioCharacter1);
	    radioCharacterGroup.add(radioCharacter2);
	    radioCharacterGroup.add(radioCharacter3);	    
	    
	    characterLabel1 = new JLabel(CHARACTER1);
	    characterLabel2 = new JLabel(CHARACTER2);
	    characterLabel3 = new JLabel(CHARACTER3);
	    characterLabel1.setBounds(70,115,62,99);
	    characterLabel2.setBounds(70,224,62,99);
	    characterLabel3.setBounds(70,333,62,99);
	    add(characterLabel1);
	    add(characterLabel2);
	    add(characterLabel3);
	    
	    
	    gameModeLabel = new JLabel("Choose a game mode:");
	    gameModeLabel.setBounds(20,453,175,25);
	    add(gameModeLabel);
	    normalModeLabel = new JLabel("Normal Mode");
	    normalModeLabel.setBounds(60,483,100,25);
	    add(normalModeLabel);
	    roundModeLabel = new JLabel("Round Mode");
	    roundModeLabel.setBounds(60,513,100,25);
	    add(roundModeLabel);
	    radioGameMode1 = new JRadioButton();
	    radioGameMode2 = new JRadioButton();
	    gameModeButtonGroup = new ButtonGroup();
	    gameModeButtonGroup.add(radioGameMode1);
	    gameModeButtonGroup.add(radioGameMode2);
	    radioGameMode1.setBounds(30,485,20,20);
	    radioGameMode2.setBounds(30,515,20,20);
	    add(radioGameMode1);
	    add(radioGameMode2);
	    
	    joinVisitor = new JButton("Visitor");
	    joinVisitor.setBounds(PLAZA_WIDTH-315,PLAZA_HEIGHT-80,90,30);
	    add(joinVisitor);
	    joinVisitor.addActionListener(this);
	    joinPlayer = new JButton("Join");
	    joinPlayer.setBounds(PLAZA_WIDTH-215,PLAZA_HEIGHT-80,90,30);
	    add(joinPlayer);
	    joinPlayer.addActionListener(this);
	    startGameButton = new JButton("Start");
	    startGameButton.setBounds(PLAZA_WIDTH-115,PLAZA_HEIGHT-80,90,30);
	    startGameButton.addActionListener(this);
	    add(startGameButton);
	    
	    
	    // information label
        infoLabel = new JLabel("Show some information at here");
        //infoLabel.setForeground(Color.red);
        infoLabel.setBounds(20, PLAZA_HEIGHT-80, PLAZA_WIDTH-80-115-100*2, 30);        
        add(infoLabel);
        
        
        gRoomTableModel = new GRoomTableModel(); 
        gRoomTable = new JTable(gRoomTableModel);
        gRoomTable.getTableHeader().setFont(new Font("Dialog", Font.PLAIN, 14));
        gRoomTable.setRowHeight(24);
        gRoomTable.getColumnModel().getColumn(gRoomTable.getColumnCount()-1).setMinWidth(100);
        gRoomTable.setFillsViewportHeight(true);        
        JScrollPane roomsScrollPane = new JScrollPane(gRoomTable);		
		roomsScrollPane.setBounds(GROOM_TABLE_X, GROOM_TABLE_Y,GROOM_TABLE_WIDTH, GROOM_TABLE_HEIGHT);
	    add(roomsScrollPane);
	    
		
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == joinPlayer) {
			System.out.println("Test click joinPlayer");
			joinPlayerAction();
		}
		
		if(e.getSource() == joinVisitor) {
			System.out.println("Test click joinVisitor");
			joinVisitorAction();
		}
		
		if(e.getSource() == startGameButton) {
			System.out.println("Test click startGameButton");
			startGameButton();
		}
	}
	
	public void joinPlayerAction () {
		// 1. check if the name was input
		name = nameTextField.getText();
		if (name.length() == 0) {
			infoLabel.setForeground(Color.red);
			infoLabel.setText("You need to input your name before joining the room.");
			return;
		}			
		
		// 2. check if a character was chosen
		if (radioCharacter1.isSelected()== true){
			characterId = 0;
		} else if (radioCharacter2.isSelected()== true){
			characterId =1;
		} else if (radioCharacter3.isSelected() == true) {
			characterId =2;
		} else {
			infoLabel.setForeground(Color.red);
			infoLabel.setText("You need to select you character before joining the room.");
			return;
        }
		
		// 3. check if a room was chosen
		roomId = gRoomTableModel.getRoomId();
		if (gRoomTableModel.isRoomSelected(roomId)==false) {
			infoLabel.setForeground(Color.red);
			infoLabel.setText("You need to select an avaliable room before joining the room.");
			return;
		} else if (gRoomTableModel.getRoomStatus(roomId)== "Full") {
			infoLabel.setForeground(Color.red);
			infoLabel.setText("The selected room is full, you can only join the room as a visitor");
			return;
		} else if (gRoomTableModel.getRoomStatus(roomId)== "Playing") {
			infoLabel.setForeground(Color.red);
			infoLabel.setText("The selected room is invaild, please select another available room");
			return;
		}
		
		// 4. send a join request to the gameLogic
		//TODO      
		
		System.out.printf("Name = %s, characterId = %d\n", name, characterId);
		infoLabel.setForeground(Color.black);
		infoLabel.setText("Join a game room ...");
	}
	
	public void joinVisitorAction() {
		// 1. check if a room was chosen, and 
		//    check if the selected room is in playing
		roomId = gRoomTableModel.getRoomId();
		if (gRoomTableModel.isRoomSelected(roomId)==false) {
			infoLabel.setForeground(Color.red);
			infoLabel.setText("You need to select an avaliable room before visiting the room.");
			return;
		} else if (gRoomTableModel.getRoomStatus(roomId)== "Playing") {
			infoLabel.setForeground(Color.red);
			infoLabel.setText("The selected room is not in Playing, cannot be visited.");
			return;
		} 
		
		// send a visitor request to the GameLogic
		// TODO		
	}
	
	public void startGameButton() {
		
		// only for test begin
		new GameGui();
		// only for test end
		
		// check if the player join a room successful or not.
		if (isJoinRoom == false) { 
			infoLabel.setForeground(Color.red);
			infoLabel.setText("You need join the room first before starting the game.");
			return;
		} else if (gRoomTableModel.getRoomStatus(roomId) != "Waiting to start") {
			infoLabel.setForeground(Color.red);
			infoLabel.setText("The selected room is invalid to be started, please select another valid room.");
			return;
		}
		
		//check if the player select a game mode or not
		if (radioGameMode1.isSelected()== true){
			gameMode = 0;
		} else if (radioGameMode2.isSelected()== true){
			gameMode = 1;
		}else {
			infoLabel.setForeground(Color.red);
			infoLabel.setText("You need select a Game Mode before start a game");
			return;
		}
		
		// send a game start request to the gameLogic
		// TODO
		
		
		System.out.printf("game mode = %d\n", gameMode);
		infoLabel.setForeground(Color.black);
		infoLabel.setText("Start the game ...");
	}
	
}
