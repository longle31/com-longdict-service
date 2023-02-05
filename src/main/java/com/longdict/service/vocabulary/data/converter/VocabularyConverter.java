package com.longdict.service.vocabulary.data.converter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.longdict.service.vocabulary.data.dto.VocabularyDto;
import com.longdict.service.vocabulary.entity.EnglishAccent;
import com.longdict.service.vocabulary.entity.Pronunciation;
import com.longdict.service.vocabulary.entity.PronunciationVoice;
import com.longdict.service.vocabulary.entity.SupportedDictionary;
import com.longdict.service.vocabulary.entity.Vocabulary;

public class VocabularyConverter {
	
	private static final String DELIMITER = "|";
	private static final String DELIMITER_PATTERN = "\\|";

	public static VocabularyConverter create() {
		return new VocabularyConverter();
	}

	public VocabularyDto convert(Vocabulary model) {
		if(model == null) {
			return null;
		}
		return VocabularyDto.builder().word(model.getWord())
				.meanings(splitString(model.getMeaninngs()))
				.pronunciation(toMap(model.getPronunciations()))
				.examples(splitString(model.getExamples()))
				.relatedVocabulary(toMap(model.getRelatedVocabulary()))
				.extraVocabulary(toMap(model.getExtraVocabulary()))
				.vocabularyClass(model.getVocabularyClass())
				.build();
	}
	
	public Vocabulary convert(SupportedDictionary dictionary, VocabularyDto dto) {
		if(dto ==  null) {
			return null;
		}
		return Vocabulary.builder().word(dto.getWord())
				.meaninngs(joinStringList(dto.getMeanings(),DELIMITER))
				.pronunciations(toPronunciations(dto.getPronunciation()))
				.examples(joinStringList(dto.getExamples(), DELIMITER))
				.relatedVocabulary(toJson(dto.getRelatedVocabulary()))
				.extraVocabulary(toJson(dto.getExtraVocabulary()))
				.vocabularyClass(dto.getVocabularyClass())
				.dictionary(dictionary)
				.build();
	}

	private static List<Pronunciation> toPronunciations(Map<String, List<String>> pronunciations) {
		return pronunciations.entrySet().stream().map(VocabularyConverter::toPronunciation).collect(Collectors.toList());
	}

	private static Pronunciation toPronunciation(Map.Entry<String, List<String>> entry) {
		Pronunciation pronunciation = new Pronunciation();
		pronunciation.setIpa(entry.getKey());
		List<PronunciationVoice> voices = entry.getValue().stream().map(VocabularyConverter::toVoice).collect(Collectors.toList());
		pronunciation.setVoices(voices);
		return pronunciation;
	}
	
	private static PronunciationVoice toVoice(String url) {
		PronunciationVoice voice = new PronunciationVoice();
		voice.setAccent(EnglishAccent.US);
		voice.setUrl(url);
		return voice ;
	}
	
	private static Map<String, List<String>> toMap(Collection<Pronunciation> pronunciations) {
		if(CollectionUtils.isEmpty(pronunciations)) {
			return Collections.emptyMap();
		}
		return pronunciations.stream().filter(Objects::nonNull).collect(Collectors.toMap(Pronunciation::getIpa, p -> getUrls(p)));
	}
	
	private static List<String> getUrls(Pronunciation pronunciation) {
		return pronunciation.getVoices().stream().map(PronunciationVoice::getUrl).collect(Collectors.toList());
	}
	
	private static String joinStringList(List<String> stringList, String demliter) {
		if(CollectionUtils.isEmpty(stringList)) {
			return StringUtils.EMPTY;
		}
		return stringList.stream().filter(StringUtils::isNotBlank).collect(Collectors.joining(demliter));
	}
	
	private static List<String> splitString(String string) {
		if(StringUtils.isBlank(string)) {
			return Collections.emptyList();
		}
		return Arrays.asList(string.split(DELIMITER_PATTERN));
	}
	
	private static String toJson(Map<?, ?> map) {
		try {
			return new ObjectMapper().writeValueAsString(map);
		} catch (JsonProcessingException e) {
			return StringUtils.EMPTY;
		}
	}
	
	private <K, V>  Map<K, V> toMap(String data) {
		if(StringUtils.isEmpty(data)) {
			return Collections.emptyMap(); 
		}
		try {
			TypeReference<HashMap<K, V>> typeRef = new TypeReference<HashMap<K,V>>() {};
			return new ObjectMapper().readValue(data, typeRef);
		} catch (JsonMappingException e) {
			return Collections.emptyMap();
		} catch (JsonProcessingException e) {
			return Collections.emptyMap();
		}
	}
	
	

}
