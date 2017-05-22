package com.theincorrectclock.fileconverter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.BeforeClass;
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
public class FileConverterTest {
	
	@Autowired
	private ApplicationContext context;
	
	private static final String INPUT = "src/test/resources/test_input/input.in";
	private static final String XML_REFERENCE = "src/test/resources/ref_output/output.xml";
	private static final String CSV_REFERENCE = "src/test/resources/ref_output/output.csv";
	private static final String OUTPUT_DIR = "src/test/resources/test_output";
	private static final String OUTPUT = OUTPUT_DIR + "/output";
	
	@BeforeClass
	public static void setup() {
		File outputDir = new File(OUTPUT_DIR);
		outputDir.mkdir();
	}
	
	@After
	public void tearDown() {
		File xmlFile = new File(OUTPUT + ".xml");
		xmlFile.delete();
		File csvFile = new File(OUTPUT + ".csv");
		csvFile.delete();
	}
	
	@Test
	public void should_process_data_into_csv_and_xml() throws Exception {
		//given
		FileConverter converter = context.getBean(FileConverter.class);
		
		//when
		converter.process(INPUT, OUTPUT);
		
		//then
		File xmlFile = new File(OUTPUT + ".xml");
		File csvFile = new File(OUTPUT + ".csv");
		assertTrue(xmlFile.exists());
		assertTrue(csvFile.exists());
		
		List<String> xmllLines = Files.lines(Paths.get(OUTPUT + ".xml")).collect(Collectors.toList());
		List<String> refXmlLines = Files.lines(Paths.get(XML_REFERENCE)).collect(Collectors.toList());
		assertEquals(refXmlLines, xmllLines);
		
		List<String> csvlLines = Files.lines(Paths.get(OUTPUT + ".csv")).collect(Collectors.toList());
		List<String> refCsvLines = Files.lines(Paths.get(CSV_REFERENCE)).collect(Collectors.toList());
		assertEquals(refCsvLines, csvlLines);
	}
}
