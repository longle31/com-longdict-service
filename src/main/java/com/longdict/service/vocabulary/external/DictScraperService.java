package com.longdict.service.vocabulary.external;

import java.net.URI;

import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.longdict.service.vocabulary.data.converter.VocabularyConverter;
import com.longdict.service.vocabulary.data.dto.VocabularyDto;
import com.longdict.service.vocabulary.entity.SupportedDictionary;
import com.longdict.service.vocabulary.entity.Vocabulary;

@Component
public class DictScraperService {
	@Value("${long.dict.web.scraper.url}")
	private String webScraperUrl;
	public Vocabulary get(SupportedDictionary dictionary, @NotBlank String word) {
		DictScraperClient client = new DictScraperClient();
		VocabularyDto dto = client.get(dictionary, webScraperUrl, word);
		return VocabularyConverter.create().convert(dictionary, dto);
	}
	
	private static class DictScraperClient {
		public VocabularyDto get(SupportedDictionary dictionary, @NotBlank String webScraperUrl, @NotBlank String word) {
			RestTemplate restTemplate =  new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.add("dictionary", dictionary.getCode());
			HttpEntity<Void> httpEntity = new HttpEntity<Void>(headers);
			try {
				URI url = URI.create(StringUtils.join(webScraperUrl, word));
				ResponseEntity<VocabularyDto> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, VocabularyDto.class);
				return response.getBody();
			} catch (Exception e) {
				return null;
			}
	
		}
		
	}

}
