package com.poc.services;

import org.springframework.stereotype.Service;

import com.poc.dto.SlateDTO;
import com.poc.models.Slate;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface ISlateService {
	
	Flux<Slate> getSlatesByProjectId(String projectId);
	Mono<Slate> addSlate(SlateDTO slateDTO, String projectId);
	Mono<Slate>updateSlate(SlateDTO slateDTO,String slateId, String projectId);
	Mono<Slate>getSlateById(String slateId);
	Mono<Void>deleteSlateById(String slateId);
	
	

}
