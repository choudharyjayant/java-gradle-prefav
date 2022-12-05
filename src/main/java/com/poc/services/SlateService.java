package com.poc.services;

import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.poc.dto.SlateDTO;
import com.poc.models.Slate;
import com.poc.repositories.SlateRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SlateService implements ISlateService {

	@Autowired
	private SlateRepository slateRepository;

	@Override
	public Flux<Slate> getSlatesByProjectId(String projectId) {
		return slateRepository.findAll().filter(slate -> slate.getDistributableUrn().equals(projectId));
	}

	
	private String uuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	private LocalDateTime setDate() {
		return LocalDateTime.now();
	}
	@Override
	public Mono<Slate> addSlate(SlateDTO slateDTO, String projectId) {
		Slate slate=new Slate();
		slate.setDistributableUrn(projectId);
		slate.setEntityUrn(uuid());
		slate.setManifestUrn(uuid());
		slate.setTitle(slateDTO.getTitle());
		slate.setCreatedBy(slateDTO.getCreatedBy());
		slate.setUpdatedBy(slateDTO.getUpdatedBy());
		slate.setCreatedAt(setDate());
		slate.setUpdatedAt(setDate());
		
		return this.slateRepository.save(slate);

	}

	@Override
	public Mono<Slate> updateSlate(SlateDTO slateDTO, String slateId, String projectId) {
		Mono<Slate> existingSlate = slateRepository.findById(slateId)
				.filter(slate1 -> slate1.getDistributableUrn().equals(projectId));
		return existingSlate.flatMap(slate -> {
			slate.setEntityUrn(slateId);
			slate.setCreatedBy(slateDTO.getCreatedBy());
			slate.setCreatedAt(setDate());
			slate.setDistributableUrn(projectId);
			slate.setTitle(slateDTO.getTitle());
			slate.setUpdatedBy(slateDTO.getUpdatedBy());
			slate.setUpdatedAt(setDate());
			return slateRepository.save(slate);
		});

	}

	@Override
	public Mono<Slate> getSlateById(String slateId) {
		return this.slateRepository.findById(slateId);
	}

	public Mono<Void> deleteSlateById(String slateId) {
		return this.slateRepository.deleteById(slateId);
	}

}
