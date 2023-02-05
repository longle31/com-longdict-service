package com.longdict.service.vocabulary.data.dto;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@JsonDeserialize
@JsonSerialize
@Getter
@Setter
@Builder
public class VocabularyDto {
	private static final long serialVersionUID = -4158429955400027496L;
	
	private String word;
	
	private List<String> meanings;
	
	private Map<String, List<String>> pronunciation;
	
	private List<String> examples;
	
	private Map<String, String> relatedVocabulary;
	
	private Map<String, String> extraVocabulary;
	
	private String vocabularyClass;


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public VocabularyDto() {
	}

	private VocabularyDto(String word, List<String> meanings, Map<String, List<String>> pronunciation,
			List<String> examples, Map<String, String> relatedVocabulary, Map<String, String> extraVocabulary, String vocabularyClass) {
		this.word = word;
		this.meanings = meanings;
		this.pronunciation = pronunciation;
		this.examples = examples;
		this.relatedVocabulary = relatedVocabulary;
		this.extraVocabulary = extraVocabulary;
		this.vocabularyClass = vocabularyClass;
	}
	
	
}
