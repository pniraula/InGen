package com.az.entity;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.az.config.Config;

public class SchemaElements {
	private ExcelDoc excelDoc;
    private SourceExcelDoc sourceExcelDoc;
	private Config CONFIG = Config.CONFIG;
	private AmsDocument amsDocument;
	private FieldsTarget fieldsTarget;
	private FieldsResource fieldsResource;
	private RecordLayoutEvents recordLayoutEvents;
    private SourceFields sourceFields;
	private MapExpression mapExpression;
	private XmlDoc xmlDoc;
	private Document document;
	public SchemaElements(){
		try {
			this.xmlDoc = new XmlDoc();
			this.document = xmlDoc.getDocument();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public Element map(){
		Element map = document.createElement("Map");
		map.setAttribute("schemaVersion", "1.2");
		map.setAttribute("version", "1.53");
		map.setAttribute("creator", "CGI");
		map.setAttribute("datecreated", "2013-11-27T14:50:54Z");
		map.setAttribute("author", "CGI");
		map.setAttribute("datemodified", "2014-03-14T20:50:12Z");
		return map;
	}	
	public Element description(){
		//<Description><![CDATA[0.1 - COA elements not mapped; defaulting to null]]></Description>
		Element description = document.createElement("Description");
		description.appendChild(document.createCDATASection("0.1 - COA elements not mapped; defaulting to null"));
		return description;
	}
	public Element variables(){
		Element variables = document.createElement("Variables");
		variables.appendChild(document.createComment(" ========Add your variables in here================ "));
		return variables;
	}
	public Element sources(){
		//records
		Element mapSources = document.createElement("MapSources");
		Element mapSource = document.createElement("MapSource");
		mapSource.setAttribute("name", "MapSource1");
		Element mapSchema = document.createElement("MapSchema");
		Element schema = sourceSchema();
		Element recordLayouts = document.createElement("RecordLayouts");
		for(String sheetName: CONFIG.getSourceSheets()){
			recordLayouts.appendChild(sourceRecords(sheetName));
		}
		schema.appendChild(recordLayouts);
		mapSchema.appendChild(schema);
		mapSource.appendChild(mapSchema);
		
		for(String table: CONFIG.getTables()){
			mapSource.appendChild(sourceEvents(table));
		}
		mapSources.appendChild(mapSource);
		System.out.println(mapSources);
		return mapSources;
	}
	private Element sourceSchema(){Element schema = document.createElement("Schema");
		schema.setAttribute("creator", "e161664");
		schema.setAttribute("datecreated", "2004-12-22T24:26:45Z");
		schema.setAttribute("connectorname", "Binary Line Sequential");
		schema.setAttribute("designedfor", "Source");
		return schema;
	}
	private Element sourceRecords(String sheetName){
		Element header = document.createElement("RecordLayout");
		header.setAttribute("name", sheetName);
		header.setAttribute("length", ((Integer) sourceExcelDoc.getTotalLength(sheetName)).toString());
		header.setAttribute("status", "0");
		
		Element origin = document.createElement("Origin");
		origin.setAttribute("origintype", "0");
		header.appendChild(origin);
		
		header.appendChild(sourceFields.generate(document, sheetName));
		
		Element recordLayoutOptions = document.createElement("RecordLayoutOptions");
		Element option = document.createElement("Option");
		option.setAttribute("name", "uniquenameprefix");
		option.setAttribute("value", "FIELD1");
		recordLayoutOptions.appendChild(option);
		
		header.appendChild(recordLayoutOptions);
		return header;
	}
	private Element sourceEvents(String table){
		return recordLayoutEvents.generate(document, table);		
	}
	public Element mapTarget(){
		   Element mapTargets = document.createElement("MapTargets");
	       Element mapTarget = document.createElement("MapTarget");
	       mapTarget.setAttribute("name", "MapTarget1");
	       Element mapSchema = document.createElement("MapSchema");
	       Element schema = document.createElement("Schema");
	       schema.setAttribute("creator", "Admin");
	       schema.setAttribute("dateCreated", "2005-01-13T19:15:13Z");
	       schema.setAttribute("connectorname", "XML");
	       schema.setAttribute("designedfor", "MapTarget");
	       Element layouts = document.createElement("RecordLayouts");
	       
	       layouts.appendChild(amsDocument.exportFileLayout(document));
	       layouts.appendChild(amsDocument.generate(document));
	       for(Element element:fieldsTarget.generate(document)){
	    	   layouts.appendChild(element);
	       }
	       schema.appendChild(layouts);
	       mapSchema.appendChild(schema);
	       mapSchema.appendChild(mapExpression.generate(document));
	       mapTarget.appendChild(mapSchema);
	       mapTargets.appendChild(mapTarget);
	       return mapTargets;
	}
	public void write(){
		Element map = this.map();
    	map.appendChild(this.description());
    	map.appendChild(this.variables());
    	map.appendChild(this.sources());
    	map.appendChild(this.mapTarget());
    	document.appendChild(map);
	}
	public void save(){
		xmlDoc.setDocument(document);
    	try {
			xmlDoc.save(CONFIG.getOutputFile());
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setAmsDocument(AmsDocument amsDocument){
		this.amsDocument = amsDocument;
	}
	public void setFieldsTarget(FieldsTarget fieldsTarget){
		this.fieldsTarget = fieldsTarget;
	}
	
	public void setFieldsResource(FieldsResource fieldsResource){
		this.fieldsResource = fieldsResource;
	}
	public void setRecordLayoutEvents(RecordLayoutEvents recordLayoutEvents){
		this.recordLayoutEvents = recordLayoutEvents;
	}
	public void setExcelDoc(ExcelDoc excelDoc){
		this.excelDoc = excelDoc;
	}
	public void setMapExpression(MapExpression mapExpression){
		this.mapExpression = mapExpression;
	}
	public void setSourceFields(SourceFields sourceFields) {
        this.sourceFields = sourceFields;
    }
    public void setSourceExcelDoc(SourceExcelDoc sourceExcelDoc) {
        this.sourceExcelDoc = sourceExcelDoc;
    }
}
