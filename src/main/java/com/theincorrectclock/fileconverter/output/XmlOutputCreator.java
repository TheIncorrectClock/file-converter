package com.theincorrectclock.fileconverter.output;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.theincorrectclock.fileconverter.model.Sentence;

@Component
@Primary
@Qualifier(value = "xml_output_creator")
public class XmlOutputCreator extends AbstractOutputCreator {
	
	private String output;

	@Override
	public void createOutput(String output) {
		this.output = output;
		super.createOutput(output);
	}
	
	@Override
	public void appendHeader() {
		writeToFile(output, "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n<text>");
	}

	@Override
	public void appendFooter() {
		writeToFile(output, "</text>");
	}

	@Override
	public void appendSentence(Sentence sentence) {
		writeToFile(output, convertToXmlString(sentence));
	}
	
	private String convertToXmlString(Sentence sentence) {
		return sentence.getWordsAsStream()
				.map(word -> word.replaceAll("'", "&apos;"))
				.collect(Collectors.joining("</word><word>", "<sentence><word>", "</word></sentence>"));
	}

	@Override
	public void close() {
	}
}
