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


public class SourceExcelDoc {
    private Config CONFIG = Config.CONFIG;
    private Display display;
    private Workbook workbook;
    public SourceExcelDoc(Display display) {
        FileInputStream file;
        this.display = display;
        try {
            display.addMessage("Reading Source Excel Doc");
            file = new FileInputStream(new File(CONFIG.getSourceInputFile()));
            workbook = WorkbookFactory.create(file);
            display.addMessage("Source Excel file successfully read");
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

    public Sheet getSheet(String sheetName){
        return workbook.getSheet(sheetName);
    }

    public ArrayList<Row> getRows(String sheetName){
        Sheet sheet = getSheet(sheetName);
        ArrayList<Row> rows = new ArrayList<Row>();
        Iterator<Row> rowIterator = sheet.rowIterator();
        int counter = 0;
        Cell cell;
        while (rowIterator.hasNext()) {
            if (counter >= CONFIG.getSourceStartPoint()-1) {
                Row row = rowIterator.next();
                if(!row.getCell(0).getStringCellValue().equals(""))
                    rows.add(row);
                else
                    break;
            } else {
                rowIterator.next();
            }
            counter++;
        }
        return rows;
    }

    public boolean isEmpty(Row row, int number){
        return getValue(row, number).length() <= 0;
    }

    public int getTotalLength(String sheetName) {
        ArrayList<Row> rows = getRows(sheetName);
        int totalLength = 0;
        for(Row row : rows) {
            if(!row.getCell(0).getStringCellValue().equals("")) {
                totalLength += row.getCell(1).getNumericCellValue();
            }
            else {
                break;
            }
        }
        return totalLength;
    }
}
