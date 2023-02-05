package com.longdict.service.vocabulary.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.longdict.service.dto.Vocabulary;
import com.longdict.service.vocabulary.service.VocabularyService;

@RestController
public class VocabularyApi implements com.longdict.service.api.VocabularyApi{

	@Autowired
	VocabularyService vocabularyService;
	
	public ResponseEntity<Vocabulary> getVocabulary(String writting) {
		return ResponseEntity.of(Optional.of(vocabularyService.get(writting)));
	}

}
