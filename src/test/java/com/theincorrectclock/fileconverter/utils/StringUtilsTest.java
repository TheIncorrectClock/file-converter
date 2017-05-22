package com.theincorrectclock.fileconverter.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StringUtilsTest {

	@Test
	public void should_return_true_when_not_null_or_empty() {
		boolean outcome = StringUtils.isNotNullOrEmpty("not null");
		
		assertTrue(outcome);
	}
	
	@Test
	public void should_return_false_when_empty() {
		boolean outcome = StringUtils.isNotNullOrEmpty("");
		
		assertFalse(outcome);
	}
	
	@Test
	public void should_return_false_when_null() {
		boolean outcome = StringUtils.isNotNullOrEmpty(null);
		
		assertFalse(outcome);
	}
	
}
