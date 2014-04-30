package com.az.entity;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.az.config.Config;
import com.az.config.Tables;

public class SourceFields {
    String[] sheets = Config.CONFIG.getSourceSheets();
    SourceExcelDoc sourceExcelDoc;

    public SourceFields(){}

    public Element generate(Document doc, String sheetName) {
        Element fields = doc.createElement("Fields");
        System.out.println("Generating source fields for each source record");
        ArrayList<Row> rows = sourceExcelDoc.getRows(sheetName);
        for(Row row : rows) {
            String val = sourceExcelDoc.getValue(row, 0);
            Element field = doc.createElement("Field");
            Element dataType = doc.createElement("Datatype");

            field.setAttribute("name", row.getCell(0).getStringCellValue());
            dataType.setAttribute("datatype", "0");
            dataType.setAttribute("dataalias", "Text");
            dataType.setAttribute("datalength", ((Integer) ((Double) row.getCell(1).getNumericCellValue()).intValue()).toString());
            dataType.setAttribute("dataidentity", "no");
            dataType.setAttribute("dataautoinc", "no");
            dataType.setAttribute("datacurrency", "no");
            dataType.setAttribute("datarowversion", "no");
            dataType.setAttribute("datapadchar", " ");

            field.appendChild(dataType);
            fields.appendChild(field);
        }
        return fields;
    }

    public void setSourceExcelDoc(SourceExcelDoc sourceExcelDoc) {
        this.sourceExcelDoc = sourceExcelDoc;
    }

}
