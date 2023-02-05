package com.longdict.service.vocabulary.entity;

import java.util.stream.Stream;

import lombok.Getter;

@Getter
public enum SupportedDictionary {
	CAMBRIDGE("CAMBRIDGE"),
	OXFORD("OXFORD");
	
	String code;
	
	SupportedDictionary(String code) {
		this.code = code;
	}
	
	SupportedDictionary get(String code) {
		return Stream.of(values()).filter(dic -> dic.code.equalsIgnoreCase(code)).findAny().orElse(null);
	}
}
