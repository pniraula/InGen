package com.az.entity;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.az.config.Config;
import com.az.config.Tables;
import com.az.ui.Display;


public class AmsDocument {
	private ExcelDoc excelDoc;
    private SourceExcelDoc sourceExcelDoc;
	private String[] tables = Config.CONFIG.getTables();
	private Display display;
	public AmsDocument(ExcelDoc excelDoc, Display display){
		this.excelDoc = excelDoc;
		this.display = display;
	}
	public Element generate(Document doc){
		try {
						
			System.out.println("Generating Record Layout for AMS_DOCUMENT");
			Element recordLayout = doc.createElement("RecordLayout");
			recordLayout.setAttribute("name", "AMS_DOCUMENT");
			recordLayout.setAttribute("length", "112");
			recordLayout.setAttribute("status", "0");
			
			//origin
			Element origin = doc.createElement("Origin");
			origin.setAttribute("origintype", "0");
			recordLayout.appendChild(origin);
			
			Element rootElement = doc.createElement("Fields");
			ArrayList<Row> rows = excelDoc.getRows("AMS_DOCUMENT");
			Iterator<Row> iterator = rows.iterator(); 
			while(iterator.hasNext()) {
				Row row = iterator.next();
			    Element field = doc.createElement("Field");
			    field.setAttribute("name", excelDoc.getValue(row, 7));
			    Element dataType = doc.createElement("Datatype");
			    dataType.setAttribute("datatype", "0");
			    dataType.setAttribute("dataalias", "Attribute");
			    dataType.setAttribute("datalength", excelDoc.getValue(row, 10));
			    dataType.setAttribute("dataidentity", "no");
			    dataType.setAttribute("dataautoinc", "no");
			    dataType.setAttribute("datacurrency", "no");
			    dataType.setAttribute("datarowversion", "no");
			    
			    field.appendChild(dataType);
			    rootElement.appendChild(field);
			}
			for (String table: tables) {
				Element hdr = createRecords(Tables.tablePrefix(table), doc);
				rootElement.appendChild(hdr);
			}
			Element recordLayoutOptions = doc.createElement("RecordLayoutOptions");
			Element option = doc.createElement("Option");
			option.setAttribute("name", "uniquenameprefix");
			option.setAttribute("value", "FIELD111");
			recordLayoutOptions.appendChild(option);
			recordLayout.appendChild(rootElement);
			recordLayout.appendChild(recordLayoutOptions);
			return recordLayout;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	private Element createRecords(String name, Document document){
		Element element = document.createElement("Field");
		element.setAttribute("name", name);
		Element dataType = document.createElement("Datatype");
	    dataType.setAttribute("datatype", "11");
	    dataType.setAttribute("dataalias", "Record");
	    dataType.setAttribute("datalength", "0");
	    dataType.setAttribute("dataidentity", "no");
	    dataType.setAttribute("dataautoinc", "no");
	    dataType.setAttribute("datacurrency", "no");
	    dataType.setAttribute("datarowversion", "no");
	    element.appendChild(dataType);
	    return element;
	}
	public Element exportFileLayout(Document document){
		Element element = document.createElement("RecordLayout");
		Element origin = document.createElement("Origin");
		origin.setAttribute("origintype", "0");
		element.appendChild(origin);
		element.setAttribute("name", "AMS_DOC_XML_EXPORT_FILE");
		element.appendChild(exportElementData(document, "VERSION", "Attribute", "16","0"));
		element.appendChild(exportElementData(document, "EXPORT_DATE", "Attribute", "16","0"));
		element.appendChild(exportElementData(document, "AMS_DOCUMENT", "Attribute", "0","11"));
		Element recordLayoutOptions = document.createElement("RecordLayoutOptions");
		Element option = document.createElement("Option");
		option.setAttribute("name", "uniquenameprefix");
		option.setAttribute("value", "FIELD1");
		recordLayoutOptions.appendChild(option);
		element.appendChild(recordLayoutOptions);
	    return element;
	}
	
	private Element exportElementData(Document document, String fieldName, String type, String length, String dType){
		Element element = document.createElement("Field");
		element.setAttribute("name", fieldName);
		Element dataType = document.createElement("Datatype");
	    dataType.setAttribute("datatype", dType);
	    dataType.setAttribute("dataalias", type);
	    dataType.setAttribute("datalength", length);
	    dataType.setAttribute("dataidentity", "no");
	    dataType.setAttribute("dataautoinc", "no");
	    dataType.setAttribute("datacurrency", "no");
	    dataType.setAttribute("datarowversion", "no");
	    element.appendChild(dataType);
	    return element;
	}
	
}
