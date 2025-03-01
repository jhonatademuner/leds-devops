package com.concursomatch.service.exam;

import com.concursomatch.domain.exam.Exam;
import com.concursomatch.domain.exam.dto.ExamDTO;
import com.concursomatch.domain.exam.dto.SimplifiedExamDTO;
import com.concursomatch.domain.role.Role;
import com.concursomatch.domain.role.dto.RoleDTO;
import com.concursomatch.repository.exam.ExamRepository;
import com.concursomatch.service.role.RoleService;
import com.concursomatch.util.exception.ExamAlreadyExistsException;
import com.concursomatch.util.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExamServiceTest {

	@Mock
	private ExamRepository examRepository;

	@Mock
	private RoleService roleService;

	@InjectMocks
	private ExamService examService;

	private Exam exam;
	private ExamDTO examDTO;
	private Set<Role> roles;

	@BeforeEach
	void setUp() {
		examDTO = new ExamDTO("123e4567-e89b-12d3-a456-426614174000", "DETRAN", "2/2025", "1234567", Set.of("Student"));
		roles = Set.of(Role.builder().id(UUID.fromString("f27f2315-5ef7-4802-8951-e1fba2e02ca6")).name("Student").build());
		exam = new Exam(UUID.fromString(examDTO.getId()), examDTO.getAgency(), examDTO.getNotice(), examDTO.getCode(), roles);
	}

	@Test
	void shouldCreateExamSuccessfully() {
		when(examRepository.findByCode(examDTO.getCode())).thenReturn(Optional.empty());
		when(roleService.createRolesIfNotExists(examDTO.getRoles())).thenReturn(Set.of(RoleDTO.builder().id("9f9a6bf4-7ebc-44fe-95ca-aaf2cf8c0d9b").name("Student").build()));
		when(examRepository.save(any(Exam.class))).thenReturn(exam);

		ExamDTO result = examService.create(examDTO);

		assertNotNull(result);
		assertEquals(examDTO.getId(), result.getId());
		verify(examRepository).save(any(Exam.class));
	}

	@Test
	void shouldThrowExceptionWhenExamAlreadyExists() {
		when(examRepository.findByCode(examDTO.getCode())).thenReturn(Optional.of(exam));

		assertThrows(ExamAlreadyExistsException.class, () -> examService.create(examDTO));
	}

	@Test
	void shouldFindExamById() {
		when(examRepository.findById(any(UUID.class))).thenReturn(Optional.of(exam));

		ExamDTO result = examService.findById(examDTO.getId());

		assertNotNull(result);
		assertEquals(examDTO.getId(), result.getId());
	}

	@Test
	void shouldThrowExceptionWhenExamNotFoundById() {
		when(examRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> examService.findById(examDTO.getId()));
	}

	@Test
	void shouldFindExamByCode() {
		when(examRepository.findByCode(examDTO.getCode())).thenReturn(Optional.of(exam));

		ExamDTO result = examService.findByCode(examDTO.getCode());

		assertNotNull(result);
		assertEquals(examDTO.getCode(), result.getCode());
	}

	@Test
	void shouldThrowExceptionWhenExamNotFoundByCode() {
		when(examRepository.findByCode(anyString())).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> examService.findByCode(examDTO.getCode()));
	}

	@Test
	void shouldUpdateExamSuccessfully() {
		when(examRepository.findById(any(UUID.class))).thenReturn(Optional.of(exam));
		when(roleService.createRolesIfNotExists(examDTO.getRoles())).thenReturn(Set.of(RoleDTO.builder().id("9dd0b056-1b58-47fc-8f3c-2c5a20f893b2").name("Student").build()));
		when(examRepository.save(any(Exam.class))).thenReturn(exam);

		ExamDTO result = examService.update(examDTO);

		assertNotNull(result);
		assertEquals(examDTO.getId(), result.getId());
		verify(examRepository).save(any(Exam.class));
	}

	@Test
	void shouldThrowExceptionWhenUpdatingNonExistentExam() {
		when(examRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> examService.update(examDTO));
	}

	@Test
	void shouldDeleteExamByIdSuccessfully() {
		when(examRepository.findById(any(UUID.class))).thenReturn(Optional.of(exam));
		doNothing().when(examRepository).delete(exam);

		ExamDTO result = examService.deleteById(examDTO.getId());

		assertNotNull(result);
		assertEquals(examDTO.getId(), result.getId());
		verify(examRepository).delete(exam);
	}

	@Test
	void shouldThrowExceptionWhenDeletingNonExistentExam() {
		when(examRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> examService.deleteById(examDTO.getId()));
	}

	@Test
	void shouldDeleteExamByCodeSuccessfully() {
		when(examRepository.findByCode(examDTO.getCode())).thenReturn(Optional.of(exam));
		doNothing().when(examRepository).delete(exam);

		ExamDTO result = examService.deleteByExamCode(examDTO.getCode());

		assertNotNull(result);
		assertEquals(examDTO.getCode(), result.getCode());
		verify(examRepository).delete(exam);
	}

	@Test
	void shouldThrowExceptionWhenDeletingNonExistentExamByCode() {
		when(examRepository.findByCode(anyString())).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> examService.deleteByExamCode(examDTO.getCode()));
	}


	@Test
	void shouldMatchExamsByRoles() {
		when(examRepository.matchByRoles(anySet())).thenReturn(List.of(exam));

		List<SimplifiedExamDTO> result = examService.matchByRoles(Set.of("Student"));

		assertFalse(result.isEmpty());
		verify(examRepository).matchByRoles(anySet());
	}
}