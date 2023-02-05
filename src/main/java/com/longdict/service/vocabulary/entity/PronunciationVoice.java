package com.longdict.service.vocabulary.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
@Table(name = "pronunciation_voices")
public class PronunciationVoice {

	@Id @Column
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private UUID id;
	
	@Column
	private EnglishAccent accent;
	
	@Column
	private MediaType mediaType = MediaType.MP3; 
	
	@Column(length = 2000)
	private String url;
	
	@Column
	private SupportedDictionary dictionary;
	
// TODO: 	
//	@Lob
//	@Basic(fetch = FetchType.LAZY)
//	private byte[] voiceSound;
}
