package com.longdict.service.vocabulary.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "vocabulary")
@Builder
@Getter
@Setter
public class Vocabulary {	
	@Id @Column
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private UUID id;
	
	@Column
	private String word;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinTable(name = "vocabulary_pronunciations",
		joinColumns = @JoinColumn(name = "vocabulary_id"),
		inverseJoinColumns = @JoinColumn(name = "prounciation_id")
	)
	private List<Pronunciation> pronunciations = new ArrayList<>(); 
	
	@Column(columnDefinition = "TEXT")
	private String meaninngs;
	
	@Column(columnDefinition = "TEXT")
	private String examples;

	@Column(columnDefinition = "TEXT")
	private String extraVocabulary;
	
	@Column(columnDefinition = "TEXT")
	private String relatedVocabulary;
	
	@Column
	private String vocabularyClass;
	
	@Column
	private SupportedDictionary dictionary;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@Column(name = "search_reference")
	@CollectionTable(name = "search_references", joinColumns = @JoinColumn(name = "vocabulary_id"))
	private Set<String> searchReferences = new HashSet<>();
	
	public Vocabulary() {
	}
	
	private Vocabulary(UUID id, String word, List<Pronunciation> pronunciations, String meaninngs, String examples,
			String extraVocabulary, String relatedVocabulary, String vocabularyClass, SupportedDictionary dictionary, Set<String> refSet) {
		this.id = id;
		this.word = word;
		this.pronunciations = pronunciations;
		this.meaninngs = meaninngs;
		this.examples = examples;
		this.extraVocabulary = extraVocabulary;
		this.relatedVocabulary = relatedVocabulary;
		this.vocabularyClass = vocabularyClass;
		this.dictionary = dictionary;
		this.searchReferences = refSet;
	}
}
