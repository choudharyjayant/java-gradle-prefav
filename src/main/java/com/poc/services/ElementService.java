package com.poc.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.poc.dto.ElementDto;
import com.poc.models.Element;
import com.poc.repositories.ElementRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ElementService implements IElementService {

	@Autowired
	private ElementRepository elementRepository;

	private String uuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	private String date() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
	}

	// POST /projects/{projectId}/slates/{slateId}/elements

	@Override
	public Mono<Element> addElementByProjectandSlateId(String projectId, String slateId, ElementDto elementDto) {

		Element element = new Element();
		element.setWorkUrn(uuid());
		element.setEntityUrn(uuid());
		element.setText(elementDto.getText());
		element.setSlatemanifestUrn(uuid());
		element.setType(elementDto.getType());
		element.setCreatedBy(elementDto.getCreatedBy());
		element.setUpdatedBy(elementDto.getUpdatedBy());
		element.setCreatedAt(date());
		element.setUpdatedAt(date());
		return elementRepository.save(element);
	}

	// GET /projects/{projectId}/slates/{slateId}/elements

	public Flux<Element> getElementByProjectIdandSlateId(String projectId, String slateId) {
		// return elementRepository.findAll().filter(element ->
		// element.getSlatemanifestUrn().equals(slateId));

		return elementRepository.findAll().filter(element -> !Objects.isNull(element.getSlatemanifestUrn())
				&& element.getSlatemanifestUrn().equalsIgnoreCase(slateId));
	}

	// PUT /projects/{projectId}/slates/{slateId}/elements/{elementId}

	public Mono<Element> updateElement(String projectId, String slateId, String elementId, ElementDto elementDto) {
		Mono<Element> existingElement = elementRepository.findById(elementId);
		return existingElement.flatMap(element -> {
			element.setWorkUrn(elementId);
			element.setEntityUrn(uuid());
			element.setText(elementDto.getText());
			element.setSlatemanifestUrn(slateId);
			element.setType(elementDto.getType());
			element.setCreatedBy(elementDto.getCreatedBy());
			element.setUpdatedBy(elementDto.getUpdatedBy());
			element.setCreatedAt(date());
			element.setUpdatedAt(date());
			return elementRepository.save(element);
		});
	}

	// DELETE /projects/{projectId}/slates/{slateId}/elements/{elementId}

	public Mono<Void> deleteElementById(String projectId, String slateId, String elementId) {
	 return elementRepository.deleteById(elementId);
	 
		

	}

	// GET /projects/{projectId}/slates/{slateId}/elements/{elementId}

	@Override
	public Mono<Element> getElementsByElementId(String projectId, String slateId, String elementId) {
		return elementRepository.findById(elementId).filter(element -> element.getSlatemanifestUrn().equals(slateId));
	}

}
