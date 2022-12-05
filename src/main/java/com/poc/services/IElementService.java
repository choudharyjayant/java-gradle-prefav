package com.poc.services;

import com.poc.dto.ElementDto;
import com.poc.models.Element;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IElementService {

	Mono<Element> addElementByProjectandSlateId(String projectId, String slateId, ElementDto elementDto);

	Flux<Element> getElementByProjectIdandSlateId(String projectId, String slateId);

	Mono<Element> updateElement(String projectId, String slateId, String elementId, ElementDto elementDto);

	Mono<Void> deleteElementById(String projectId, String slateId, String elementId);

	Mono<Element> getElementsByElementId(String projectId, String slateId, String elementId);

}
