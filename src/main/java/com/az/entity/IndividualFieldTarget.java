package com.az.entity;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.az.config.Config;
import com.az.config.Tables;


public class IndividualFieldTarget {
	private ExcelDoc excelDoc;
	public ArrayList<Element> generate(Document doc, String table){
		try {
			ArrayList<Element> elements = new ArrayList<Element>();
				ArrayList<Row> rows = excelDoc.getRows(table);
				Iterator<Row> iterator = rows.iterator(); 
				while(iterator.hasNext()) {
					Element recordLayout = doc.createElement("RecordLayout");
					Row row = iterator.next();
					System.out.println("Generating Record Layout for field "+Tables.fieldPrefix(table, excelDoc.getValue(row, 7)));
					recordLayout.setAttribute("name", Tables.fieldPrefix(table, excelDoc.getValue(row, 7)));
					recordLayout.setAttribute("length", "20");
					recordLayout.setAttribute("status", "0");
					
					//origin
					Element origin = doc.createElement("Origin");
					origin.setAttribute("origintype", "0");
					recordLayout.appendChild(origin);
					
					//fields
					Element rootElement = doc.createElement("Fields");
					doc.appendChild(rootElement);
					
				    rootElement.appendChild(createField1(excelDoc.getValue(row, 7), doc, excelDoc.getValue(row, 10)));
				    rootElement.appendChild(createField2("Attribute", doc));
				    recordLayout.appendChild(rootElement);
					
					//record layout options
					Element recordLayoutOptions = doc.createElement("RecordLayoutOptions");
					Element option = doc.createElement("Option");
					option.setAttribute("name", "uniquenameprefix");
					option.setAttribute("value", "FIELD11111111111111111111111111111111111111");
					recordLayoutOptions.appendChild(option);
					recordLayout.appendChild(recordLayoutOptions);
					elements.add(recordLayout);
				}
				System.out.println("RecordLayout for each fields generated.");
			return elements;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private Element createField1(String name, Document document, String length){
		Element element = document.createElement("Field");
		element.setAttribute("name", name);
		Element dataType = document.createElement("Datatype");
	    dataType.setAttribute("datatype", "0");
	    dataType.setAttribute("dataalias", "Unquoted");
	    dataType.setAttribute("datalength", length);
	    dataType.setAttribute("dataidentity", "no");
	    dataType.setAttribute("dataautoinc", "no");
	    dataType.setAttribute("datacurrency", "no");
	    dataType.setAttribute("datarowversion", "no");
	    element.appendChild(dataType);
	    return element;
	}
	private Element createField2(String name, Document document){
		Element element = document.createElement("Field");
		element.setAttribute("name", name);
		Element dataType = document.createElement("Datatype");
	    dataType.setAttribute("datatype", "0");
	    dataType.setAttribute("dataalias", "Attribute");
	    dataType.setAttribute("datalength", "16");
	    dataType.setAttribute("dataidentity", "no");
	    dataType.setAttribute("dataautoinc", "no");
	    dataType.setAttribute("datacurrency", "no");
	    dataType.setAttribute("datarowversion", "no");
	    element.appendChild(dataType);
	    return element;
	}
	public void setExcelDoc(ExcelDoc excelDoc){
		this.excelDoc = excelDoc;
	}
}
