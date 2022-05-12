package edu.eom.ics4u.monopoly.game;

import edu.eom.ics4u.monopoly.model.Event;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class TransactionRecord {
	public HSSFWorkbook workbook;
	public HSSFSheet sheets [] = new HSSFSheet [4];
	private int rowIds [] = {2,2,2,2};
	
	public TransactionRecord (HashMap<String, Player> players) {
		workbook = new HSSFWorkbook();
		for (Player player: players.values()) {
			sheets[player.getPlayerId()] = workbook.createSheet(player.getName());
        	sheets[player.getPlayerId()].setColumnWidth(0, 12*256);
        	sheets[player.getPlayerId()].setColumnWidth(1, 12*256);
        	sheets[player.getPlayerId()].setColumnWidth(2, 12*256);        	
        	sheets[player.getPlayerId()].setColumnWidth(3, 12*256);
        	sheets[player.getPlayerId()].setColumnWidth(4, 12*256);
        	sheets[player.getPlayerId()].setColumnWidth(5, 12*256);
        	sheets[player.getPlayerId()].setColumnWidth(6, 25*256);
        	
			HSSFRow rowhead = sheets[player.getPlayerId()].createRow(0);
			rowhead.createCell(0).setCellValue("Date");
			rowhead.createCell(1).setCellValue("Name");
			rowhead.createCell(2).setCellValue("Account");
			rowhead.createCell(3).setCellValue("Withrawals");
			rowhead.createCell(4).setCellValue("Deposits");
			rowhead.createCell(5).setCellValue("Balance");
			rowhead.createCell(6).setCellValue("Transition Description");
			
			HSSFRow row = sheets[player.getPlayerId()].createRow(1);
			row.createCell(0).setCellValue("JAN 1,2099");
			row.createCell(1).setCellValue(player.getName());
			row.createCell(2).setCellValue("Cash");
			row.createCell(3).setCellValue(0);
			row.createCell(4).setCellValue(2000);
			row.createCell(5).setCellValue(2000);
			row.createCell(6).setCellValue("Inital funding");
		}
	}
	
	// write a record to the excel file
	public void writeRecord(Player player, Event event) {
		String name = player.getName();
		int playerId = player.getPlayerId();
		String ts = event.getTimestamp();		
		String desc = event.getEventinfo();
		int cashAmount = event.getAmount1();
		int savingAmount = event.getAmount2();
		int cash = event.getValue1();
		int saving = event.getValue2();		
		int rowId = rowIds[playerId];
		HSSFRow row;
		
		// cash account
		if (cashAmount != 0) {
			row = sheets[playerId].createRow(rowId);
			row.createCell(0).setCellValue(ts);
			row.createCell(1).setCellValue(name);
			row.createCell(2).setCellValue("Cash");
			row.createCell(5).setCellValue(cash);
			row.createCell(6).setCellValue(desc);
			if (cashAmount > 0) {
				row.createCell(3).setCellValue(cashAmount);    // withdrawal
				row.createCell(4).setCellValue(0);             // deposit
			} else {
				row.createCell(3).setCellValue(0);             // withdrawal
				row.createCell(4).setCellValue(0-cashAmount);  // deposit				
			}
			rowIds[playerId] += 1;
		}
		
		// saving account
		if (savingAmount != 0) {
			row = sheets[playerId].createRow(rowId);
			row.createCell(0).setCellValue(ts);
			row.createCell(1).setCellValue(name);
			row.createCell(2).setCellValue("Saving");
			row.createCell(5).setCellValue(saving);
			row.createCell(6).setCellValue(desc);
			if (savingAmount > 0) {
				row.createCell(3).setCellValue(savingAmount);    // withdrawal
				row.createCell(4).setCellValue(0);               // deposit
			} else {
				row.createCell(3).setCellValue(0);               // withdrawal
				row.createCell(4).setCellValue(0-savingAmount);  // deposit				
			}
			rowIds[playerId] += 1;
		}
	}
}
