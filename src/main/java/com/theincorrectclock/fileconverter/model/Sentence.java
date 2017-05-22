package com.theincorrectclock.fileconverter.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Sentence {

	@Autowired
	private Comparator<String> comparator;
	
	private List<String> words = new ArrayList<>();
	
	public void addWordOrdered(String word) {
		words.add(word);
		words.sort(comparator);
	}
	
	public List<String> getWords() {
		return new ArrayList<>(words);
	}
	
	public Stream<String> getWordsAsStream() {
		return getWords().stream();
	}
	
	public int getWordsCount() {
		return words.size();
	}
	
	@Override
	public String toString() {
		return "sentence = " + words;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((words == null) ? 0 : words.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sentence other = (Sentence) obj;
		if (words == null) {
			if (other.words != null)
				return false;
		} else if (!words.equals(other.words))
			return false;
		return true;
	}
}
