package com.poc.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.poc.dto.ElementDto;
import com.poc.models.Element;
import com.poc.repositories.ElementRepository;
import com.poc.utility.Constants;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(MockitoJUnitRunner.class)
public class ElementServiceTest {

	private static final String WORK_URN = "workUrn";
	private static final String ENTITY_URN = "entityUrn";
	private static final String TYPE = "type";
	private static final String SLATE_MANIFEST_URN = "slateManifestUrn";
	private static final String TEXT = "text";
	private static final String CREATED_BY = "createdBy";
	private static final String CREATED_AT = "createdAt";
	private static final String UPDATED_BY = "updatedBy";
	private static final String UPDATED_AT = "updatedAt";
	
	@Mock
	private ElementRepository elementRepository;

	@InjectMocks
	private ElementService elementService;

	@Test
	public void addElementByProjectandSlateIdTest() {
		
		Element element = new Element("1", "2", "abc", "3", "def", "rishit", "rishit", "may", "may");
		
		Mockito.when(elementRepository.save(ArgumentMatchers.any(Element.class))).thenReturn(Mono.just(element));
		
		ElementDto elementDto = new ElementDto("1", "2", "abc", "3", "def", "rishit", "rishit", "may", "may");
		
		Mono<Element> elementMono = elementService.addElementByProjectandSlateId("", "", elementDto);
		
		assertEquals(true, elementMono.toString().contains("MonoJust"));
		assertNotNull(elementMono);
	}

	@Test
	public void getElementsByElementIdTest() {
		Element element = element();
		
		Mockito.when(elementRepository.findById(ArgumentMatchers.anyString())).thenReturn(Mono.just(element));
		
		Mono<Element> response = elementService.getElementsByElementId(Constants.PROJECT_ID,
				element.getSlatemanifestUrn(), Constants.ELEMENT_ID);
		
		response.subscribe(res -> {
			Assert.assertEquals(WORK_URN, res.getWorkUrn());
		});
		
		Mockito.verify(elementRepository).findById(ArgumentMatchers.anyString());

	}

	@Test
	public void updateElementTest() {
		
		Element element = element();
		
		ElementDto elementDto = elementDto();
		
		Mockito.when(elementRepository.findById(Constants.ELEMENT_ID)).thenReturn(Mono.just(element));
		
		Mockito.when(elementRepository.save(ArgumentMatchers.any(Element.class))).thenReturn(Mono.just(element));
		
		Mono<Element> commentMono = elementService.updateElement(Constants.PROJECT_ID, Constants.SLATE_ID,Constants.ELEMENT_ID, elementDto);
		
		StepVerifier.create(commentMono).expectNextMatches(res -> res.getText().contains(TEXT)).verifyComplete();
	}

	@Test
	public void getElementByProjectIdandSlateIdTest() {

		Element element = element();
		
		Mockito.when(elementRepository.findAll()).thenReturn(Flux.just(element));
		
		Flux<Element> response = elementService.getElementByProjectIdandSlateId(Constants.PROJECT_ID, element.getSlatemanifestUrn());
		
		response.subscribe(res -> {
			
		Assert.assertEquals(SLATE_MANIFEST_URN, res.getSlatemanifestUrn());
		
		});
		
	}

	@Test
	public void deleteCommentByIdTest() {

		Mockito.when(elementRepository.deleteById(Constants.ELEMENT_ID)).thenReturn(Mono.empty());
		
		Mono<Void> result = elementService.deleteElementById(Constants.PROJECT_ID, Constants.SLATE_ID,Constants.ELEMENT_ID);
		
		StepVerifier.create(result).expectNext().verifyComplete();
	}

	private Element element() {
		return new Element(WORK_URN, ENTITY_URN, TYPE, SLATE_MANIFEST_URN, TEXT, CREATED_BY, CREATED_AT, UPDATED_BY,
				UPDATED_AT);
	}

	private ElementDto elementDto() {
		return new ElementDto(WORK_URN, ENTITY_URN, TYPE, SLATE_MANIFEST_URN, TEXT, CREATED_BY, CREATED_AT, UPDATED_BY,
				UPDATED_AT);
	}
}