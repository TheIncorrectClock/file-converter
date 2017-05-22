package com.theincorrectclock.fileconverter.output;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.theincorrectclock.fileconverter.model.Sentence;

@Component
@Qualifier(value = "csv_output_creator")
public class CsvOutputCreator extends AbstractOutputCreator {

	private static final Logger logger = LoggerFactory.getLogger(CsvOutputCreator.class);
	
	private int sentenceCount;
	private int maxWords = -1;

	private String output;
	private String tmpOutput;
	
	@Override
	public void createOutput(String output) {
		sentenceCount = 1;
		this.output = output;
		this.tmpOutput = output + UUID.randomUUID();
		super.createOutput(tmpOutput);
	}

	@Override
	public void appendHeader() {
		writeToFile(output, IntStream.range(1, maxWords + 1).mapToObj(String::valueOf).collect(Collectors.joining(", Word ", ", Word ", "")));
	}

	@Override
	public void appendFooter() {
	}

	@Override
	public void appendSentence(Sentence sentence) {
		writeToFile(tmpOutput, convertToCvsString(sentence));
		maxWords = Math.max(maxWords, sentence.getWordsCount());
	}
	
	private String convertToCvsString(Sentence sentence) {
		return sentence.getWordsAsStream()
				.collect(Collectors.joining(", ", "Sentence " + sentenceCount++ + ", ", ""));
	}
	
	@Override
	public void close() {
		rewriteWithHeader();
		try {
			Files.delete(Paths.get(tmpOutput));
		} catch (IOException e) {
			logger.error("Couldn't delete temporary CSV file.", e);
		}
	}

	private void rewriteWithHeader() {
		super.createOutput(output);
		try {
			appendHeader();
			Files.lines(Paths.get(tmpOutput)).forEach(line -> writeToFile(output, line));
		} catch (IOException e) {
			logger.error("Couldn't rewrite to output CSV file.", e);
		}
	}
	
}
