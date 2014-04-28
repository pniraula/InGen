package com.az.config;

public final class Tables {
	public static String tablePrefix(String fix){
		String str= "[AMS_DOCUMENT]"+fix;
		return str.trim();
	}
	public static String fieldPrefix(String table, String field){
		String str = "[AMS_DOCUMENT!"+table+"]"+field;
		return str.trim();
	}
	
}
