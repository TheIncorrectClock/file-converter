package com.theincorrectclock.fileconverter.parser;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.theincorrectclock.fileconverter.model.Sentence;
import com.theincorrectclock.fileconverter.utils.AbbreviationUtils;
import com.theincorrectclock.fileconverter.utils.StringUtils;

@Component
public class SentenceParser {
	
	private static final Logger logger = LoggerFactory.getLogger(SentenceParser.class);
	
	@Autowired
	private ApplicationContext context;
	
	public Sentence parse(String sentence) {
		logger.debug("processing string into sentence");
		Sentence ret = context.getBean(Sentence.class);
		
		Arrays.asList(sentence.split("(\\s+|,)")).stream()
			.map(this::removeFirstAndLastIfNotAlphanumerical)
			.filter(StringUtils::isNotNullOrEmpty)
			.forEach(word -> ret.addWordOrdered(word));
		
		return ret;
	}
	
	private String removeFirstAndLastIfNotAlphanumerical(String word) {
		String tmp = word;
		if(tmp.length() > 0) {
			boolean first = isFirstCharacterForRemoval(tmp);
			boolean last = isLastCharacterForRemoval(tmp);
			
			if(first) {
				tmp = handleFirstCharacter(tmp);
			}
			if(last) {
				tmp = handleLastCharacter(tmp);
			}
		}
		return tmp;
	}

	private boolean isFirstCharacterForRemoval(String word) {
		return !Character.isLetterOrDigit(word.charAt(0));
	}
	
	private boolean isLastCharacterForRemoval(String word) {
		return !AbbreviationUtils.isWordAnAbbreviation(word) && !Character.isLetterOrDigit(word.charAt(word.length() - 1));
	}
	
	private String handleFirstCharacter(String tmp) {
		logger.info("removing non alphanumerical prefix");
		return tmp.substring(1);
	}
	
	private String handleLastCharacter(String tmp) {
		logger.info("removing non alphanumerical suffix");
		if(tmp.length() > 0) {
			tmp = tmp.substring(0, tmp.length() - 1);
		} else {
			tmp = "";
		}
		return tmp;
	}
	
}
