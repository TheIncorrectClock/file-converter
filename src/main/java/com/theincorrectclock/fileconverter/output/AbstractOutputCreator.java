package com.theincorrectclock.fileconverter.output;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractOutputCreator implements OutputCreator {
	
	private static final Logger logger = LoggerFactory.getLogger(AbstractOutputCreator.class);
	
	protected final void writeToFile(String output, String data) {
		try {
			Files.write(Paths.get(output), Arrays.asList(data), StandardOpenOption.APPEND);
		} catch (IOException e) {
			logger.error("Couldn't write to output file.", e);
		}
		
	}
}
