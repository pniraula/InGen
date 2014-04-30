package com.az.entity;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.az.config.Config;
import com.az.ui.Display;

public class ExcelDoc {
	private Config CONFIG = Config.CONFIG;
	private Sheet sheet;
	private Display display;
	public ExcelDoc(Display display){
		FileInputStream file;
		this.display = display;
		try {
			display.addMessage("Reading Excel Doc");
			file = new FileInputStream(new File(CONFIG.getInputFile()));
			Workbook wb = WorkbookFactory.create(file);
			sheet = wb.getSheet(CONFIG.getSheet());
			display.addMessage("Excel file has been successfully read.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			display.addMessage(e.getMessage());
		}
		
	}
	public String getValue(Row row, int cellNo) {
		Cell cell = row.getCell(cellNo);
		if (cell != null) {
			if (cell.getCellType() == Cell.CELL_TYPE_STRING)
				return cell.getStringCellValue().trim().replaceAll(" ","");
			else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN)
				return Boolean.toString(cell.getBooleanCellValue())
						.toUpperCase().trim().replaceAll(" ","");
			else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
				return Integer.toString((int) cell.getNumericCellValue())
						.trim().replaceAll(" ","");
			else if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
				return "";
		}
		return "";
	}
	public Sheet getSheet(){
		return sheet;
	}
	public ArrayList<Row> getRows(String table){
        ArrayList<Row> rows = new ArrayList<Row>();
		Iterator<Row> rowIterator = this.sheet.rowIterator();
		int counter = 0;
		while (rowIterator.hasNext()) {
			if (counter >= CONFIG.getStartPoint()-1) {
				Row row = rowIterator.next();
				if(getValue(row, 5).trim().equals(table)){
					rows.add(row);
				}
			} else {
				rowIterator.next();
			}
			counter++;
		}
		return rows;
	}
	public boolean isEmpty(Row row, int number){
		if(getValue(row, number).length()>0)
			return false;
		return true;
	}
}
