package com.concursomatch.controller.exam;

import com.concursomatch.domain.exam.dto.ExamDTO;
import com.concursomatch.service.exam.ExamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExamControllerTest {

	@Mock
	private ExamService examService;

	@InjectMocks
	private ExamController examController;

	private ExamDTO examDTO;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		examDTO = new ExamDTO(); // Initialize with necessary test data
	}

	@Test
	void createExam_ShouldReturnCreatedExam() {
		when(examService.create(examDTO)).thenReturn(examDTO);

		ResponseEntity<ExamDTO> response = examController.createExam(examDTO);

		assertEquals(201, response.getStatusCodeValue());
		assertEquals(examDTO, response.getBody());
	}

	@Test
	void getExamById_ShouldReturnExam() {
		String id = "123";
		when(examService.findById(id)).thenReturn(examDTO);

		ResponseEntity<ExamDTO> response = examController.getExamById(id);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(examDTO, response.getBody());
	}

	@Test
	void updateExam_ShouldReturnUpdatedExam() {
		when(examService.update(examDTO)).thenReturn(examDTO);

		ResponseEntity<ExamDTO> response = examController.updateExam(examDTO);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(examDTO, response.getBody());
	}

	@Test
	void deleteExamById_ShouldReturnDeletedExam() {
		String id = "123";
		when(examService.deleteById(id)).thenReturn(examDTO);

		ResponseEntity<ExamDTO> response = examController.deleteExamById(id);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(examDTO, response.getBody());
	}

	@Test
	void getExamByExamCode_ShouldReturnExam() {
		String examCode = "EX-123";
		when(examService.findByCode(examCode)).thenReturn(examDTO);

		ResponseEntity<ExamDTO> response = examController.getExamByExamCode(examCode);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(examDTO, response.getBody());
	}

	@Test
	void deleteExamByExamCode_ShouldReturnDeletedExam() {
		String examCode = "EX-123";
		when(examService.deleteByExamCode(examCode)).thenReturn(examDTO);

		ResponseEntity<ExamDTO> response = examController.deleteExamByExamCode(examCode);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(examDTO, response.getBody());
	}
}
