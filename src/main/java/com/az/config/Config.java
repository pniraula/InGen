package com.az.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public enum Config{
	CONFIG();
	private String[] tables;
	private String inputFile;
	private String outputFile;
	private String sheet;
	private int startAt;
	private String CONFIG_FILE = "./config.properties";
	private Config(){
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(CONFIG_FILE));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.tables =  props.getProperty("TABLES").split(",");
		this.inputFile =  props.getProperty("INPUT");
		this.outputFile = props.getProperty("OUTPUT");
		this.sheet = props.getProperty("SHEET");
		this.startAt = Integer.valueOf(props.getProperty("START_ROW"));
	}
	public String tablePrefix(String fix){
		String str= "[AMS_DOCUMENT]"+fix;
		return str.trim();
	}
	public String fieldPrefix(String table, String field){
		String str = "[AMS_DOCUMENT!"+table+"]"+field;
		return str.trim();
	}
	public String[] getTables(){
		return this.tables;
	}
	public String getInputFile(){
		return this.inputFile;
	}
	public String getOutputFile(){
		return this.outputFile;
	}
	public String getSheet(){
		return this.sheet;
	}
	public int getStartPoint(){
		return this.startAt;
	}
}
