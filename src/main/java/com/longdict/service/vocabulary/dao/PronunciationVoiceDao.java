package com.longdict.service.vocabulary.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.longdict.service.vocabulary.entity.PronunciationVoice;

public interface PronunciationVoiceDao extends JpaRepository<PronunciationVoice, UUID> {
	@Query("SELECT v FROM PronunciationVoice v WHERE v.url = :url")
	List<PronunciationVoice> findByUrl(String url);
}
