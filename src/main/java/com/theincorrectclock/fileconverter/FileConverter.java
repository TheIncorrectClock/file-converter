package com.theincorrectclock.fileconverter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.theincorrectclock.fileconverter.output.OutputCreator;
import com.theincorrectclock.fileconverter.parser.LineParser;

@Component
public class FileConverter {

	@Autowired
	private LineParser lineParser;
	
	@Autowired
	private OutputCreator xmlOutputCreator;
	
	@Autowired
	@Qualifier(value = "csv_output_creator")
	private OutputCreator csvOutputCreator;
	
	public void process(String input, String output) throws Exception {
		prepareOutputFiles(output);
		appendHeaders();
		processInputToOutputFiles(input);
		appendFooters();
		closeOutputs();
	}
	
	private void prepareOutputFiles(String output) {
		xmlOutputCreator.createOutput(output + ".xml");
		csvOutputCreator.createOutput(output + ".csv");
	}
	
	private void appendHeaders() {
		xmlOutputCreator.appendHeader();
	}
	
	private void processInputToOutputFiles(String input) throws IOException {
		Files.lines(Paths.get(input))
		.map(lineParser::parse)
		.flatMap(list -> list.stream())
		.forEach(sentence -> {
			xmlOutputCreator.appendSentence(sentence);
			csvOutputCreator.appendSentence(sentence);
		});
	}
	
	private void appendFooters() {
		xmlOutputCreator.appendFooter();
	}

	private void closeOutputs() {
		xmlOutputCreator.close();
		csvOutputCreator.close();
	}

}
