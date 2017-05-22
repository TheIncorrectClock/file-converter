package com.theincorrectclock.fileconverter.utils;

import java.util.Arrays;
import java.util.List;

public class AbbreviationUtils {

	private static final List<String> ABBREVIATIONS = Arrays.asList("Mr.", "Mrs.", "Ms.", "Dr.", "Prof.");
	
	private AbbreviationUtils() {
	}
	
	public static boolean isWordAnAbbreviation(String word) {
		return AbbreviationUtils.ABBREVIATIONS.stream().anyMatch(form -> form.equalsIgnoreCase(word.trim()));
	}

}
