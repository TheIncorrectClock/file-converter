package com.theincorrectclock.fileconverter.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.theincorrectclock.fileconverter.model.Sentence;
import com.theincorrectclock.fileconverter.utils.AbbreviationUtils;

@Component
public class LineParser {
	
	private static final Logger logger = LoggerFactory.getLogger(LineParser.class);
	
	@Autowired
	private SentenceParser sentenceParser;
	
	private String partialSentence = "";
	private String lastWord = "";
	
	private List<Sentence> sentences;
	
	public List<Sentence> parse(String line) {
		logger.info("Transforming line to list of sentences");
		
		sentences = new ArrayList<>();
		
		Arrays.asList(line.split("")).stream()
			.filter(c -> !c.isEmpty())
			.map(c -> c.replace("\u2019", "'"))
			.forEach(this::processCharacter);
		
		return sentences;
	}
	
	private void processCharacter(String c) {
		if(isEndingSeparator(c) && !AbbreviationUtils.isWordAnAbbreviation(lastWord + c)) {
			logger.info("found end of sentence");
			finalizeSentence();
		} else {
			continueSentence(c);
		}
	}
	
	private boolean isEndingSeparator(String c) {
		if(c.equals(".") || c.equals("!") || c.equals("?")) {
			return true;
		}
		return false;
	}
	
	private void finalizeSentence() {
		sentences.add(sentenceParser.parse(partialSentence));
		partialSentence = "";
		lastWord = "";
	}

	private void continueSentence(String c) {
		if(Character.isWhitespace(c.charAt(0))) {
			lastWord = "";
		}
		partialSentence += c;
		lastWord += c;
	}
}
