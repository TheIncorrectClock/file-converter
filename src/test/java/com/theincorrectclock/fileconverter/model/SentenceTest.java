package com.theincorrectclock.fileconverter.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.theincorrectclock.fileconverter.config.FileConverterTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {FileConverterTestConfig.class}, loader = AnnotationConfigContextLoader.class)
public class SentenceTest {
	
	@Autowired
	private ApplicationContext context;
	
	@Test
	public void should_add_words_ordered() {
		//given
		Sentence sentence = context.getBean(Sentence.class);
		
		//when
		sentence.addWordOrdered("This");
		sentence.addWordOrdered("is");
		sentence.addWordOrdered("simple");
		sentence.addWordOrdered("sentence");
		
		//then
		assertEquals(4, sentence.getWordsCount());
		assertEquals("is", sentence.getWords().get(0));
		assertEquals("sentence", sentence.getWords().get(1));
		assertEquals("simple", sentence.getWords().get(2));
		assertEquals("This", sentence.getWords().get(3));
	}

}
