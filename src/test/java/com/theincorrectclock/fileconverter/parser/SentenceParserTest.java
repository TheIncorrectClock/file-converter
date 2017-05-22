package com.theincorrectclock.fileconverter.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.theincorrectclock.fileconverter.config.FileConverterTestConfig;
import com.theincorrectclock.fileconverter.model.Sentence;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {FileConverterTestConfig.class}, loader = AnnotationConfigContextLoader.class)
public class SentenceParserTest {

	@Autowired
	private ApplicationContext context;
	
	@Test
	public void should_parse_valid_text() {
		//given
		SentenceParser parser = context.getBean(SentenceParser.class);
		Sentence expected = prepareSentence("is", "sentence", "This", "valid");
		
		//when
		Sentence outcome = parser.parse("This is valid sentence.");
		
		//then
		assertEquals(4, outcome.getWordsCount());
		assertEquals(expected, outcome);
	}
	
	@Test
	public void should_parse_text_with_additional_whitespaces() {
		//given
		SentenceParser parser = context.getBean(SentenceParser.class);
		Sentence expected = prepareSentence("is", "sentence", "This", "valid");
		
		//when
		Sentence outcome = parser.parse("   This is   valid sentence    .   ");
		
		//then
		assertEquals(4, outcome.getWordsCount());
		assertEquals(expected, outcome);
	}
	
	@Test
	public void should_parse_text_with_special_characters() {
		//given
		SentenceParser parser = context.getBean(SentenceParser.class);
		Sentence expected = prepareSentence("a", "seeing", "sentence", "valid", "You're");
		
		//when
		Sentence outcome = parser.parse("You're seeing a valid sentence.");
		
		
		//then
		assertEquals(5, outcome.getWordsCount());
		assertEquals(expected, outcome);
	}
	
	@Test
	public void should_parse_empty_sentence() {
		//given
		SentenceParser parser = context.getBean(SentenceParser.class);
		Sentence expected = context.getBean(Sentence.class);
		
		//when
		Sentence outcome = parser.parse("");
		
		//then
		assertNotNull(outcome);
		assertEquals(0, outcome.getWordsCount());
		assertEquals(expected, outcome);
	}
	
	private Sentence prepareSentence(String... words) {
		Sentence expected = context.getBean(Sentence.class);
		Stream.of(words).forEach(expected::addWordOrdered);
		
		return expected;
	}
}
