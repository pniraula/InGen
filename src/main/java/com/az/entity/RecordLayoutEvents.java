package com.az.entity;

import java.util.ArrayList;
import java.util.Iterator;



import org.apache.poi.ss.usermodel.Row;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.az.config.Tables;


public class RecordLayoutEvents {
	private ExcelDoc excelDoc;
    private static boolean amsDocumentGenerated = false;

	public RecordLayoutEvents() {
	}
	public Element generate(Document document, String table){
		try {

			Element rootElement = document.createElement("RecordLayoutEvents");
			rootElement.setAttribute("recordlayoutname", table);
			Element event = document.createElement("Event");
			event.setAttribute("name", "AfterEveryRecord");
			ArrayList<Row> rows = excelDoc.getRows(table);
			Iterator<Row> iterator = rows.iterator();

            // If this if statement evaluates to true, the fields for the header line are currently being written.
            if(!amsDocumentGenerated) {
                generateField(event, document, "AMS_DOCUMENT");
                Element changeTargetEvent = document.createElement("Event");
                changeTargetEvent.setAttribute("name", "BeforeFirstRecord");
                Element action = document.createElement("Action");
                action.setAttribute("name", "ChangeTarget");
                action.appendChild(generateParam(document, "0", "target name", "Target"));
                action.appendChild(generateParam(document, "1", "connection string",
                        "\"+ProcessingInstructions='<AMS_DOC_XML_EXPORT_FILE VERSION=\"\"1.0\"\" EXPORT_DATE=\"\"\" " +
                                "& DateConvert(\"yyyy/mm/dd\",\"yyyy-mm-dd\", Date())& \" \" & Time()&\".000000\" &\"\"\">';" +
                                "DoctypeName=AMS_DOC_XML_EXPORT_FILE\""));
                action.appendChild(generateParam(document, "2", "append", "False"));
                action.appendChild(generateParam(document, "3", "clear records", "True"));
                changeTargetEvent.appendChild(action);
                rootElement.appendChild(changeTargetEvent);
                amsDocumentGenerated = true;
            }

            generateField(event, document, Tables.tablePrefix(table));

            while(iterator.hasNext()) {
                Row row = iterator.next();
                generateField(event, document, Tables.fieldPrefix(table, excelDoc.getValue(row, 7)));
            }
            rootElement.appendChild(event);

            return rootElement;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

    public Element generateParam(Document document, String position, String name, String data) {
        Element param = document.createElement("Parameter");
        param.setAttribute("position", position);
        param.setAttribute("name", name);
        param.appendChild(document.createCDATASection(data));

        return param;
    }

	public void setExcelDoc(ExcelDoc excelDoc){
		this.excelDoc = excelDoc;
	}

    public void generateField(Element event, Document document, String fieldValue) {
        Element field = document.createElement("Action");
        field.setAttribute("name", "ClearMapPut Record");
        Element child1 = document.createElement("Parameter");
        child1.setAttribute("position", "0");
        child1.setAttribute("name", "target name");
        child1.appendChild(document.createCDATASection("Target"));

        Element child2 = document.createElement("Parameter");
        child2.setAttribute("position", "1");
        child2.setAttribute("name", "record layout");
        child2.appendChild(document.createCDATASection(fieldValue));

        Element child3 = document.createElement("Parameter");
        child3.setAttribute("position", "2");
        child3.setAttribute("name", "count");
        child3.appendChild(document.createCDATASection("WRITE_HEADER"));

        Element child4 = document.createElement("Parameter");
        child4.setAttribute("position", "4");
        child4.setAttribute("name", "buffered");
        child4.appendChild(document.createCDATASection("false"));

        field.appendChild(child1);
        field.appendChild(child2);
        field.appendChild(child3);
        field.appendChild(child4);

        event.appendChild(field);
    }
}
