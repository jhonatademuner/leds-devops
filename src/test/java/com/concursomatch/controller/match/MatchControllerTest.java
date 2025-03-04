package com.concursomatch.controller.match;

import com.concursomatch.domain.candidate.dto.SimplifiedCandidateDTO;
import com.concursomatch.domain.exam.dto.SimplifiedExamDTO;
import com.concursomatch.service.match.MatchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MatchControllerTest {

	@Mock
	private MatchService matchService;

	@InjectMocks
	private MatchController matchController;

	private List<SimplifiedExamDTO> examDTOList;
	private List<SimplifiedCandidateDTO> candidateDTOList;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		examDTOList = List.of(new SimplifiedExamDTO()); // Initialize with necessary test data
		candidateDTOList = List.of(new SimplifiedCandidateDTO()); // Initialize with necessary test data
	}

	@Test
	void matchExamWithCandidate_ShouldReturnMatchedExams() {
		String citizenId = "12345";
		when(matchService.matchCandidateWithExamByCitizenId(citizenId)).thenReturn(examDTOList);

		ResponseEntity<List<SimplifiedExamDTO>> response = matchController.matchExamWithCandidate(citizenId);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(examDTOList, response.getBody());
	}

	@Test
	void matchCandidateWithExam_ShouldReturnMatchedCandidates() {
		String examCode = "EX-123";
		when(matchService.matchExamWithCandidateByExamCode(examCode)).thenReturn(candidateDTOList);

		ResponseEntity<List<SimplifiedCandidateDTO>> response = matchController.matchCandidateWithExam(examCode);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(candidateDTOList, response.getBody());
	}
}
