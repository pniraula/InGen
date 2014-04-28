package com.az.entity;

import java.util.ArrayList;
import java.util.Iterator;


import org.apache.poi.ss.usermodel.Row;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class FieldsResource {
	String table;
	ExcelDoc excelDoc;
	
	public FieldsResource(){
	}
	public Element generate(Document document){
		try {
			
				Element rootElement = document.createElement("Fields");
				ArrayList<Row> rows = excelDoc.getRows(table);
				Iterator<Row> iterator = rows.iterator(); 
				while(iterator.hasNext()) {
					Row row = iterator.next();
					if(!excelDoc.isEmpty(row, 0)){
					    Element field = document.createElement("Field");
					    field.setAttribute("name", excelDoc.getValue(row, 7));
					    Element dataType = document.createElement("Datatype");
					    dataType.setAttribute("datatype", "0");
					    dataType.setAttribute("dataalias", "Text");
					    dataType.setAttribute("datalength", excelDoc.getValue(row, 10));
					    dataType.setAttribute("dataidentity", "no");
					    dataType.setAttribute("dataautoinc", "no");
					    dataType.setAttribute("datacurrency", "no");
					    dataType.setAttribute("datarowversion", "no");
					    dataType.setAttribute("datapadchar", " ");
					    field.appendChild(dataType);
					    rootElement.appendChild(field);
					}
				}
			return rootElement;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public void setExcelDoc(ExcelDoc excelDoc) {
		this.excelDoc = excelDoc;
	}
	public void setTable(String table) {
		this.table = table;
	}
}
