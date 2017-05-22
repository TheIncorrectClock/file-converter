package com.theincorrectclock.fileconverter.comparator;

import java.util.Comparator;

import org.springframework.stereotype.Component;

@Component
public class WordComparator implements Comparator<String> {

	Comparator<String> baseComparator = String.CASE_INSENSITIVE_ORDER;
	
	public int compare(String o1, String o2) {
		if(o1.equalsIgnoreCase(o2)) {
			return -o1.compareTo(o2);
		}
		return baseComparator.compare(o1, o2);
	}

}
