package com.longdict.service.vocabulary.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.longdict.service.vocabulary.entity.SupportedDictionary;
import com.longdict.service.vocabulary.entity.Vocabulary;

@Repository
public interface VocabularyDao extends JpaRepository<Vocabulary, UUID> {
	@Query("SELECT vocab FROM Vocabulary vocab WHERE vocab.dictionary = :dictionary AND vocab.word = :word")
	List<Vocabulary> findByWord(@Param("dictionary") SupportedDictionary dictionary, @Param("word") String word);
	
	@Query("SELECT vocab FROM Vocabulary vocab JOIN vocab.searchReferences ref WHERE vocab.dictionary = :dictionary AND ref = :reference")
	List<Vocabulary> findBySearchReference(@Param("dictionary") SupportedDictionary dictionary, @Param("reference") String reference);
}
