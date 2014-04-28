package com.az.main;



import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.az.config.Config;
import com.az.entity.AmsDocument;
import com.az.entity.ExcelDoc;
import com.az.entity.SchemaElements;
import com.az.entity.XmlDoc;
import com.az.ui.Display;

public class App 
{
    public static void main(String[] args )
    {
    	
    	ApplicationContext context = new ClassPathXmlApplicationContext("SpringBeans.xml");
    	   	
    	SchemaElements schemaElements = (SchemaElements) context.getBean("schemaElements");
    	schemaElements.write();
    	schemaElements.save();    	
    
    }
}
