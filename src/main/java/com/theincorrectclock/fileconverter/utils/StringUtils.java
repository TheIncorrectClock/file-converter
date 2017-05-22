package com.theincorrectclock.fileconverter.utils;

public class StringUtils {
	
	private StringUtils() {
	}
	
	public static boolean isNotNullOrEmpty(String string) {
		if(string == null) {
			return false;
		}
		return !string.trim().isEmpty();
	}

}
