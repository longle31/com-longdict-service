package com.longdict.service.vocabulary.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.longdict.service.vocabulary.entity.Pronunciation;
import com.longdict.service.vocabulary.entity.SupportedDictionary;

@Repository
public interface PronunciationDao extends JpaRepository<Pronunciation, UUID> {
	
	@Query("SELECT p FROM Pronunciation p WHERE p.dictionary = :dictionary AND p.ipa = :ipa")
	List<Pronunciation> findByIpa(SupportedDictionary dictionary, String ipa);

	@Query("SELECT p FROM Pronunciation p WHERE p.dictionary = :dictionary AND p.ipa IN :ipas")
	List<Pronunciation> findByIpas(SupportedDictionary dictionary, List<String> ipas);
}
