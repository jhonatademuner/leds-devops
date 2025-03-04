package com.concursomatch.controller.candidate;

import com.concursomatch.domain.candidate.dto.CandidateDTO;
import com.concursomatch.service.candidate.CandidateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CandidateControllerTest {

	@Mock
	private CandidateService candidateService;

	@InjectMocks
	private CandidateController candidateController;

	private CandidateDTO candidateDTO;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		candidateDTO = new CandidateDTO(); // Initialize with necessary test data
	}

	@Test
	void createCandidate_ShouldReturnCreatedCandidate() {
		when(candidateService.create(candidateDTO)).thenReturn(candidateDTO);

		ResponseEntity<CandidateDTO> response = candidateController.createCandidate(candidateDTO);

		assertEquals(201, response.getStatusCodeValue());
		assertEquals(candidateDTO, response.getBody());
	}

	@Test
	void getCandidateById_ShouldReturnCandidate() {
		String id = "123";
		when(candidateService.findById(id)).thenReturn(candidateDTO);

		ResponseEntity<CandidateDTO> response = candidateController.getCandidateById(id);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(candidateDTO, response.getBody());
	}

	@Test
	void updateCandidate_ShouldReturnUpdatedCandidate() {
		when(candidateService.update(candidateDTO)).thenReturn(candidateDTO);

		ResponseEntity<CandidateDTO> response = candidateController.updateCandidate(candidateDTO);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(candidateDTO, response.getBody());
	}

	@Test
	void deleteCandidateById_ShouldReturnDeletedCandidate() {
		String id = "123";
		when(candidateService.deleteById(id)).thenReturn(candidateDTO);

		ResponseEntity<CandidateDTO> response = candidateController.deleteCandidateById(id);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(candidateDTO, response.getBody());
	}

	@Test
	void getCandidateByCitizenId_ShouldReturnCandidate() {
		String citizenId = "CID-123";
		when(candidateService.findByCitizenId(citizenId)).thenReturn(candidateDTO);

		ResponseEntity<CandidateDTO> response = candidateController.getCandidateByCitizenId(citizenId);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(candidateDTO, response.getBody());
	}

	@Test
	void deleteCandidateByCitizenId_ShouldReturnDeletedCandidate() {
		String citizenId = "CID-123";
		when(candidateService.deleteByCitizenId(citizenId)).thenReturn(candidateDTO);

		ResponseEntity<CandidateDTO> response = candidateController.deleteCandidateByCitizenId(citizenId);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(candidateDTO, response.getBody());
	}
}
