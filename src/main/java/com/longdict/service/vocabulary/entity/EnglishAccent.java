package com.longdict.service.vocabulary.entity;

import lombok.Getter;

@Getter
public enum EnglishAccent {
	UK("UK"), US("US");
	
	private String code;
	EnglishAccent(String code) {
		this.code = code;
	}
}
