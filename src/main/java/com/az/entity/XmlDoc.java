package com.az.entity;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.az.ui.Display;

public class XmlDoc{
	private Document document;
	private Display display;
	public XmlDoc() throws ParserConfigurationException{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	    document = docBuilder.newDocument();
	}
	public void appendElement(Element element){
		this.document.appendChild(element);
	}
	public void setDocument(Document document){
		this.document = document;
	}
	public Document getDocument(){
		return this.document;
	}
	public void save(String name) throws TransformerException{
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer = transformerFactory.newTransformer();
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    DOMSource source = new DOMSource(this.document);
	    StreamResult result = new StreamResult(new File(name));
	    transformer.transform(source, result);
	}
	public void setDisplay(Display display){
		this.display=display;
	}
}
