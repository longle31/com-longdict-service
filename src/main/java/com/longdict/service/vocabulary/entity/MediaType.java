package com.longdict.service.vocabulary.entity;

import lombok.Getter;

@Getter
public enum MediaType {
	MP3("MP3");
	
	private String code;
	
	MediaType(String code) {
		this.code = code;
	}
}
