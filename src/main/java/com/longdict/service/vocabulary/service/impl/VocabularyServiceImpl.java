package com.longdict.service.vocabulary.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.longdict.service.vocabulary.dao.PronunciationDao;
import com.longdict.service.vocabulary.dao.PronunciationVoiceDao;
import com.longdict.service.vocabulary.dao.VocabularyDao;
import com.longdict.service.vocabulary.entity.Pronunciation;
import com.longdict.service.vocabulary.entity.SupportedDictionary;
import com.longdict.service.vocabulary.entity.Vocabulary;
import com.longdict.service.vocabulary.external.DictScraperService;

@Component
public class VocabularyServiceImpl {

	@Autowired
	private VocabularyDao repo;
	
	@Autowired
	private PronunciationDao pronunciationRepo;
	
	@Autowired
	private DictScraperService scraperService;
	
	@Autowired
	private PronunciationVoiceDao pronunciationVoiceRepo;
	
	@Transactional
	public Vocabulary get(SupportedDictionary dictionary, @NotBlank String word) {
		List<Vocabulary> vocabularies = repo.findBySearchReference(dictionary, word);
		if(CollectionUtils.isNotEmpty(vocabularies)) {
			return vocabularies.get(0);
		}
		return getFromDictScraperService(dictionary, word);
	}

	private Vocabulary getFromDictScraperService(SupportedDictionary dictionary, @NotBlank String word) {
		Vocabulary vocabulary = scraperService.get(dictionary, word);
		if(vocabulary == null) {
			return null;
		}
		Optional<Vocabulary> existingVocabulary = repo.findByWord(dictionary, vocabulary.getWord()).stream().findFirst();
		if(existingVocabulary.isPresent()) {
			Vocabulary vocab = existingVocabulary.get();
			boolean isAlreadySearched = vocab.getSearchReferences().contains(word);
			if(!isAlreadySearched) {
				vocab.getSearchReferences().add(word);
			}
			return vocab;
		}
		saveNewVocabulary(dictionary, word, vocabulary);
		
		return vocabulary;
	}

	private void saveNewVocabulary(SupportedDictionary dictionary, String word, Vocabulary vocabulary) {
		vocabulary.setSearchReferences(new HashSet<>());
		vocabulary.getSearchReferences().add(word);
		persistPronunciation(dictionary, vocabulary);
		repo.save(vocabulary);
	}

	
	private void persistPronunciation(SupportedDictionary dictionary, Vocabulary vocabulary) {
		List<Pronunciation> pronunciations = vocabulary.getPronunciations();
		if(CollectionUtils.isEmpty(pronunciations)) {
			return;
		}
		
		List<String> ipas = pronunciations.stream().map(Pronunciation::getIpa).collect(Collectors.toList());
		List<Pronunciation> existingPronunciationsInDB = pronunciationRepo.findByIpas(dictionary, ipas);
		
		if(CollectionUtils.isEmpty(existingPronunciationsInDB)) {
			pronunciations.stream().peek(pronunciation -> pronunciation.setDictionary(dictionary));
			pronunciationRepo.saveAll(pronunciations);
			return;
		}
		
		List<String> existingIpas = existingPronunciationsInDB.stream().map(Pronunciation::getIpa).collect(Collectors.toList());
		List<Pronunciation> unPersistedPronunciations = pronunciations.stream().filter(p -> !existingIpas.contains(p.getIpa())).collect(Collectors.toList());
		if(CollectionUtils.isNotEmpty(unPersistedPronunciations)) {			
			unPersistedPronunciations.stream().peek(pronunciation -> pronunciation.setDictionary(dictionary));
			pronunciationRepo.saveAll(unPersistedPronunciations);
		} else {
			vocabulary.setPronunciations(existingPronunciationsInDB);
		}

	}

// TODO: implement save and return mp3
//	@Transactional
//	public byte[] getPronciationVoice(@NotBlank String ipa) {
//		Optional<Pronunciation> pronunciation = pronunciationRepo.findByIpa(ipa).stream().findFirst();
//		
//		if(!pronunciation.isPresent()) {
//			return null;
//		}
//		
//		PronunciationVoice firstVoice = pronunciation.get().getVoices().stream().findFirst().orElse(null);
//		
//		if(firstVoice == null) {
//			return null;
//		}
//		
//		Optional<byte[]> firstVoiceSound = Optional.ofNullable(firstVoice).map(PronunciationVoice::getVoiceSound);
//		
//		if(firstVoiceSound.isPresent()) {
//			return firstVoiceSound.get();
//		}
//		
//		byte[] voiceFromWeb = FileFetchingService.get(firstVoice.getUrl());
//		firstVoice.setVoiceSound(voiceFromWeb);
//		pronunciationVoiceRepo.save(firstVoice);
//		return voiceFromWeb;
//	}

}
