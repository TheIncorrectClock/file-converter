package com.theincorrectclock.fileconverter.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
public class LineParserTest {

	@Autowired
	private ApplicationContext context;
	
	@Test
	public void should_parse_single_line() {
		//given
		String line = "This is simple sentence.";
		List<Sentence> expected = prepareSentences(
				Arrays.asList(
						Arrays.asList("This", "is", "simple", "sentence")
				));
		
		LineParser parser = context.getBean(LineParser.class);
		
		//when
		List<Sentence> sentences = parser.parse(line);
		
		//then
		assertNotNull(sentences);
		assertEquals(1, sentences.size());
		assertEquals(expected, sentences);
	}
	
	@Test
	public void should_parse_multiline_into_sentence() {
		//given
		String firstLine = "This is the beginning ";
		String secondLine = "of the multiline sentence.";
		
		List<Sentence> expected = prepareSentences(
				Arrays.asList(
						Arrays.asList("This", "is", "the", "beginning", "of", "the", "multiline", "sentence")
				));
		
		LineParser parser = context.getBean(LineParser.class);
		
		//when
		List<Sentence> firstSentences = parser.parse(firstLine);
		List<Sentence> secondSentences = parser.parse(secondLine);
		
		//then
		assertNotNull(firstSentences);
		assertNotNull(secondSentences);
		assertEquals(0, firstSentences.size());
		assertEquals(1, secondSentences.size());
		assertEquals(new ArrayList<>(), firstSentences);
		assertEquals(expected, secondSentences);
	}

	@Test
	public void should_parse_multiline_into_many_sentences() {
		//given
		String firstLine = "This is first sentence. And this ";
		String secondLine = "is multiline sentence. Third sentence is simple. And fourth ";
		String thirdLine = "is also multiline.";
		
		List<Sentence> firstExpected = prepareSentences(
				Arrays.asList(
						Arrays.asList("This", "is", "first", "sentence")
				));
		List<Sentence> secondExpected = prepareSentences(
				Arrays.asList(
						Arrays.asList("And", "this", "is", "multiline", "sentence"),
						Arrays.asList("Third", "sentence", "is", "simple")
				));
		List<Sentence> thirdExpected = prepareSentences(
				Arrays.asList(
						Arrays.asList("And", "fourth", "is", "also", "multiline")
				));
				
		LineParser parser = context.getBean(LineParser.class);
		
		//when
		List<Sentence> firstSentences = parser.parse(firstLine);
		List<Sentence> secondSentences = parser.parse(secondLine);
		List<Sentence> thirdSentences = parser.parse(thirdLine);
		
		//then
		assertNotNull(firstSentences);
		assertNotNull(secondSentences);
		assertNotNull(thirdSentences);
		assertEquals(1, firstSentences.size());
		assertEquals(2, secondSentences.size());
		assertEquals(1, thirdSentences.size());
		assertEquals(firstExpected, firstSentences);
		assertEquals(secondExpected, secondSentences);
		assertEquals(thirdExpected, thirdSentences);
	}
	
	private List<Sentence> prepareSentences(List<List<String>> data) {
		List<Sentence> expected = new ArrayList<>(data.size());
		
		data.stream().forEach(strArr -> {
			Sentence sentence = context.getBean(Sentence.class);
			strArr.stream().forEach(sentence::addWordOrdered);
			expected.add(sentence);
		});
		return expected;
	}
	
}
