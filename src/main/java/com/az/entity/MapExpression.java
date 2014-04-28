package com.az.entity;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.az.config.Config;
import com.az.config.Tables;
public class MapExpression {
	String[] tables = Config.CONFIG.getTables();
	ExcelDoc excelDoc;
	public MapExpression(){
		
	}
	public Element generate(Document doc){
		try {
			ArrayList<Element> elements = new ArrayList<Element>();
			System.out.println("Generating MapExpressions for each fields");
			Element mapExpressions = doc.createElement("MapExpressions");
			//for AMS DOCUMENT
			/*
			mapExpressions.appendChild(createField1("VERSION", "AMS_DOC_XML_EXPORT_FILE", doc, "<![CDATA[\"1.0\"]]>"));
			mapExpressions.appendChild(createField1("EXPORT_DATE", "AMS_DOC_XML_EXPORT_FILE", doc, "<![CDATA[Dim sTextLine sTextline= DateConvert(\"yyyy/mm/dd\",\"yyyy-mm-dd\", Date())& \" \" & Time()&\".000000\"sTextLine]]>"));
			ArrayList<Row> rowsi = excelDoc.getRows("AMS_DOCUMENT");
			Iterator<Row> docIterator = rowsi.iterator(); 
			while(docIterator.hasNext()) {
				Row row = docIterator.next();															
				mapExpressions.appendChild(createField1(excelDoc.getValue(row, 7), "AMS_DOCUMENT", doc, "<![CDATA[null]]>"));
			}*/
			for(String table: tables){
				ArrayList<Row> rows = excelDoc.getRows(table);
				Iterator<Row> iterator = rows.iterator(); 
				while(iterator.hasNext()) {
					Row row = iterator.next();		
					String val = excelDoc.getValue(row, 7);
					mapExpressions.appendChild(createField1(val, Tables.fieldPrefix(table, val), doc, "<![CDATA[<![CDATA[null\\U+005d]>]]>"));
					mapExpressions.appendChild(createField2("Attribute", Tables.fieldPrefix(table, val), doc, "<![CDATA[\"Y\"]]>"));
				}
			}
			System.out.println("MapExpressions generated successfully.");
			return mapExpressions;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	private Element createField1(String fieldName, String recordLayoutName, Document document, String value){
		Element element = document.createElement("MapExpression");
		element.setAttribute("language", "DJScript");
		element.setAttribute("recordlayoutname", recordLayoutName);
		element.setAttribute("fieldname", fieldName.trim());
		element.setTextContent(value);
	    return element;
	}
	private Element createField2(String fieldName, String recordLayoutName, Document document, String value){
		Element element = document.createElement("MapExpression");
		element.setAttribute("language", "DJScript");
		element.setAttribute("recordlayoutname", recordLayoutName);
		element.setAttribute("fieldname", fieldName);
		element.setTextContent(value);
	    return element;
	}
	public void setExcelDoc(ExcelDoc excelDoc){
		this.excelDoc = excelDoc;
	}
}
