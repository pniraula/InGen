package com.az.entity;

import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.az.config.Config;
import com.az.config.Tables;

public class FieldsTarget {
	private ExcelDoc excelDoc;
	private String[] tables = Config.CONFIG.getTables();
	private IndividualFieldTarget individualFieldTarget;

	public ArrayList<Element> generate(Document doc) {
		try {
			ArrayList<Element> elements = new ArrayList<Element>();
			for (String table : tables) {
				Element recordLayout = doc.createElement("RecordLayout");
				recordLayout.setAttribute("name", Tables.tablePrefix(table));
				recordLayout.setAttribute("length", "0");
				recordLayout.setAttribute("status", "0");

				// origin
				Element origin = doc.createElement("Origin");
				origin.setAttribute("origintype", "0");
				recordLayout.appendChild(origin);

				// fields
				Element rootElement = doc.createElement("Fields");
                Element amsDataObjectField = generateField(doc, "AMSDataObject", "Attribute", "0");
                rootElement.appendChild(amsDataObjectField);

				ArrayList<Row> rows = excelDoc.getRows(table);
				Iterator<Row> iterator = rows.iterator();
				while (iterator.hasNext()) {
					Row row = iterator.next();
					Element field = generateField(doc, Tables.fieldPrefix(table,
                            excelDoc.getValue(row, 7)), "Record", "11");
					rootElement.appendChild(field);
				}
				recordLayout.appendChild(rootElement);
				Element recordLayoutOptions = doc
						.createElement("RecordLayoutOptions");
				Element option = doc.createElement("Option");
				option.setAttribute("name", "uniquenameprefix");
				option.setAttribute("value", "FIELD11111");
				recordLayoutOptions.appendChild(option);
				recordLayout.appendChild(rootElement);
				elements.add(recordLayout);
				ArrayList<Element> inFields = individualFieldTarget.generate(
						doc, table);
				if (inFields != null) {
					for (Element element : inFields) {
						elements.add(element);
					}
				}
			}
			System.out.println("RecordLayout for each tables generated.");
			return elements;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

    public Element generateField(Document doc, String fieldName, String dataAlias, String type) {
        Element field = doc.createElement("Field");
        field.setAttribute("name", fieldName);
        Element dataType = doc.createElement("Datatype");
        dataType.setAttribute("datatype", type);
        dataType.setAttribute("dataalias", dataAlias);
        dataType.setAttribute("datalength", "0");
        dataType.setAttribute("dataidentity", "no");
        dataType.setAttribute("dataautoinc", "no");
        dataType.setAttribute("datacurrency", "no");
        dataType.setAttribute("datarowversion", "no");
        field.appendChild(dataType);

        return field;
    }

	public static String getValue(Row row, int cellNo) {
		Cell cell = row.getCell(cellNo);
		if (cell != null) {
			if (cell.getCellType() == Cell.CELL_TYPE_STRING)
				return cell.getStringCellValue().trim().replace(" ", "");
			else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN)
				return Boolean.toString(cell.getBooleanCellValue())
						.toUpperCase().trim().replace(" ", "");
			else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
				return Integer.toString((int) cell.getNumericCellValue())
						.trim().replace(" ", "");
			else if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
				return "";
		}
		return "";
	}

	public void setIndividualFieldTarget(
			IndividualFieldTarget individualFieldTarget) {
		this.individualFieldTarget = individualFieldTarget;
	}

	public void setExcelDoc(ExcelDoc excelDoc) {
		this.excelDoc = excelDoc;
	}
}
