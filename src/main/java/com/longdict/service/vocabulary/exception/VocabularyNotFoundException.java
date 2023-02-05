package com.longdict.service.vocabulary.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No vocabulary found")
public class VocabularyNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2225421670501741658L;
}
