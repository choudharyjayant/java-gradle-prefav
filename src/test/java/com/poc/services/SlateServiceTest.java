package com.poc.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.poc.dto.SlateDTO;
import com.poc.models.Slate;
import com.poc.repositories.SlateRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(MockitoJUnitRunner.class)
public class SlateServiceTest {

	private static final String TITLE = "title";
	private static final String MANIFEST_URN="08b4d1325ad444aa8b8446cd70cbe155";
    private static final String PROJECT_ID="21";
	
    @Mock
	SlateRepository slateRepository;

	@InjectMocks
	SlateService slateService;

	 
	
    private LocalDateTime setDate() {
		return LocalDateTime.now();
	}

	private String uuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	@Test
	public void testGetSlateById() {
		Slate slate = new Slate();
		slate.setManifestUrn(MANIFEST_URN);
		slate.setDistributableUrn(uuid());
		slate.setEntityUrn(uuid());
		slate.setTitle("abc");
		slate.setCreatedBy("user");
		slate.setCreatedAt(setDate());
		slate.setUpdatedBy("user1");
		slate.setUpdatedAt(setDate());
        when(slateRepository.findById(MANIFEST_URN)).thenReturn(Mono.just(slate));
		Mono<Slate> slateMono = slateService.getSlateById(MANIFEST_URN);
		slateMono.subscribe(res -> {
			assertEquals("08b4d1325ad444aa8b8446cd70cbe155", res.getManifestUrn());
		});

	}
	 
	 @Test
	  public void testGetSlatesByProjectId() {

		 Slate slate = new Slate("1", "1", "1", "firstTest", "testUser", setDate(), "12PM", setDate());
		 Mockito.when(slateRepository.findAll()).thenReturn(Flux.just(slate));
		 Flux<Slate> result = slateService.getSlatesByProjectId(PROJECT_ID);
	        System.out.println(result);
	        assertEquals(true, result.toString().contains("FluxFilterFuseable"));
	        assertNotNull(result);
	        }

	 @Test
	 public void testDeleteSlate() {
		 Mockito.when(slateService.deleteSlateById( "1")).thenReturn(Mono.empty());
	 StepVerifier.create(slateService.deleteSlateById("1")).expectNext().verifyComplete();

	 }
	 
	 @Test
	 public void testAddSlate() {
		  Slate slate = new Slate("1", "1", "1", TITLE, "testUser", setDate(), "12PM", setDate());
		  SlateDTO slateDTO = new SlateDTO("1", "1", TITLE, "firstTest", "testUser", setDate(), "12PM", setDate());
		  when(slateRepository.save(any(Slate.class))).thenReturn(Mono.just(slate));
		  Mono<Slate> resultMono = slateService.addSlate(slateDTO,anyString());
		  resultMono.subscribe(res->{
	        	assertTrue(res.getTitle().contains(TITLE));
	        });
	 }
	 
	 @Test
	    public void updateSlateTest() {
	        Slate slate = new Slate(TITLE, TITLE, TITLE, TITLE, TITLE, setDate(), TITLE, setDate());
	        SlateDTO slatetDTO =new  SlateDTO("1", "1", TITLE, "firstTest", "testUser", setDate(), "12PM", setDate());
	        Mockito.when(slateRepository.findById(anyString())).thenReturn(Mono.just(slate));
	        Mono<Slate> slateMono =slateService.updateSlate(slatetDTO,MANIFEST_URN,PROJECT_ID);
	        slateMono.subscribe(res->{
	        	assertTrue(res.getTitle().contains(TITLE));
	        });
	    }
	 
}