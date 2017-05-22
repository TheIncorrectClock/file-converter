package com.theincorrectclock.fileconverter.output;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.theincorrectclock.fileconverter.model.Sentence;

public interface OutputCreator {

	static final Logger logger = LoggerFactory.getLogger(OutputCreator.class);
	
	default void createOutput(String output) {
		try {
			Files.write(Paths.get(output), Arrays.asList(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			logger.error("Couldn't create output file.", e);
		}
	}
	
	void appendHeader();
	
	void appendFooter();
	
	void appendSentence(Sentence sentence);
	
	void close();
	
}
