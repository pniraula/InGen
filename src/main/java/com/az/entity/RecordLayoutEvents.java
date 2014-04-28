package com.az.entity;

import java.util.ArrayList;
import java.util.Iterator;



import org.apache.poi.ss.usermodel.Row;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.az.config.Tables;


public class RecordLayoutEvents {
	private ExcelDoc excelDoc;
	public RecordLayoutEvents() {
	}
	public Element generate(Document document, String table){
		try {
			
			Element rootElement = document.createElement("RecordLayoutEvents");
			rootElement.setAttribute("name", table);
			Element event = document.createElement("Event");
			event.setAttribute("name", "AfterEveryRecord");
			ArrayList<Row> rows = excelDoc.getRows(table);
			Iterator<Row> iterator = rows.iterator(); 
				while(iterator.hasNext()) {
					Row row = iterator.next();
				    Element field = document.createElement("Action");
				    field.setAttribute("name", "ClearMapPut Record");
				    Element child1 = document.createElement("Parameter");
				    child1.setAttribute("position", "0");
				    child1.setAttribute("name", "target name");
				    child1.setTextContent("<![CDATA[Target]]>");
				    
				    Element child2 = document.createElement("Parameter");
				    child2.setAttribute("position", "1");
				    child2.setAttribute("name", "record layout");
				    child2.setTextContent("<![CDATA["+Tables.fieldPrefix(table, excelDoc.getValue(row, 7))+"]]>");
				    
				    Element child3 = document.createElement("Parameter");
				    child3.setAttribute("position", "2");
				    child3.setAttribute("name", "count");
				    child3.setTextContent("<![CDATA[WRITE_HEADER]]>");
				    
				    Element child4 = document.createElement("Parameter");
				    child4.setAttribute("position", "4");
				    child4.setAttribute("name", "buffered");
				    child4.setTextContent("<![CDATA[false]]>");
				    
				    field.appendChild(child1);
				    field.appendChild(child2);
				    field.appendChild(child3);
				    field.appendChild(child4);

				    event.appendChild(field);
				}
				rootElement.appendChild(event);
				return rootElement;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public void setExcelDoc(ExcelDoc excelDoc){
		this.excelDoc = excelDoc;
	}
}
