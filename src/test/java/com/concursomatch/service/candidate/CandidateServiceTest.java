package com.concursomatch.service.candidate;

import com.concursomatch.domain.candidate.Candidate;
import com.concursomatch.domain.candidate.dto.CandidateDTO;
import com.concursomatch.domain.candidate.dto.SimplifiedCandidateDTO;
import com.concursomatch.domain.role.Role;
import com.concursomatch.domain.role.dto.RoleDTO;
import com.concursomatch.repository.candidate.CandidateRepository;
import com.concursomatch.service.role.RoleService;
import com.concursomatch.util.assembler.CandidateAssembler;
import com.concursomatch.util.assembler.RoleAssembler;
import com.concursomatch.util.exception.CandidateAlreadyExistsException;
import com.concursomatch.util.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CandidateServiceTest {

	@Mock
	private CandidateRepository candidateRepository;

	@Mock
	private RoleService roleService;

	@InjectMocks
	private CandidateService candidateService;

	private Candidate candidate;
	private CandidateDTO candidateDTO;
	private Set<Role> roles;

	@BeforeEach
	void setUp() {
		candidateDTO = new CandidateDTO("123e4567-e89b-12d3-a456-426614174000", "John Doe", Date.from(Instant.now()), "123456789", Set.of("Developer"));
		roles = Set.of(Role.builder().id(UUID.fromString("f27f2315-5ef7-4802-8951-e1fba2e02ca6")).name("Developer").build());
		candidate = new Candidate(UUID.fromString(candidateDTO.getId()), candidateDTO.getName(), candidateDTO.getDateOfBirth(), candidateDTO.getCitizenId(), roles);
	}

	@Test
	void shouldCreateCandidateSuccessfully() {
		when(candidateRepository.findByCitizenId(candidateDTO.getCitizenId())).thenReturn(Optional.empty());
		when(roleService.createRolesIfNotExists(candidateDTO.getRoles())).thenReturn(Set.of(RoleDTO.builder().id("6b47dd86-33bc-4ae9-b1af-668374c5b285").name("Developer").build()));
		when(candidateRepository.save(any(Candidate.class))).thenReturn(candidate);

		CandidateDTO result = candidateService.create(candidateDTO);

		assertNotNull(result);
		assertEquals(candidateDTO.getId(), result.getId());
		verify(candidateRepository).save(any(Candidate.class));
	}

	@Test
	void shouldThrowExceptionWhenCandidateAlreadyExists() {
		when(candidateRepository.findByCitizenId(candidateDTO.getCitizenId())).thenReturn(Optional.of(candidate));

		assertThrows(CandidateAlreadyExistsException.class, () -> candidateService.create(candidateDTO));
	}

	@Test
	void shouldFindCandidateById() {
		when(candidateRepository.findById(any(UUID.class))).thenReturn(Optional.of(candidate));

		CandidateDTO result = candidateService.findById(candidateDTO.getId());

		assertNotNull(result);
		assertEquals(candidateDTO.getId(), result.getId());
	}

	@Test
	void shouldThrowExceptionWhenCandidateNotFoundById() {
		when(candidateRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> candidateService.findById(candidateDTO.getId()));
	}

	@Test
	void shouldFindCandidateByCitizenId() {
		when(candidateRepository.findByCitizenId(candidateDTO.getCitizenId())).thenReturn(Optional.of(candidate));

		CandidateDTO result = candidateService.findByCitizenId(candidateDTO.getCitizenId());

		assertNotNull(result);
		assertEquals(candidateDTO.getCitizenId(), result.getCitizenId());
	}

	@Test
	void shouldThrowExceptionWhenCandidateNotFoundByCitizenId() {
		when(candidateRepository.findByCitizenId(anyString())).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> candidateService.findByCitizenId(candidateDTO.getCitizenId()));
	}

	@Test
	void shouldUpdateCandidateSuccessfully() {
		when(candidateRepository.findById(any(UUID.class))).thenReturn(Optional.of(candidate));
		when(roleService.createRolesIfNotExists(candidateDTO.getRoles())).thenReturn(Set.of(RoleDTO.builder().id("360c1bdd-15f0-4f84-b677-afe5836f6ecf").name("Developer").build()));
		when(candidateRepository.save(any(Candidate.class))).thenReturn(candidate);

		CandidateDTO result = candidateService.update(candidateDTO);

		assertNotNull(result);
		assertEquals(candidateDTO.getId(), result.getId());
		verify(candidateRepository).save(any(Candidate.class));
	}

	@Test
	void shouldThrowExceptionWhenUpdatingNonExistentCandidate() {
		when(candidateRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> candidateService.update(candidateDTO));
	}

	@Test
	void shouldDeleteCandidateByIdSuccessfully() {
		when(candidateRepository.findById(any(UUID.class))).thenReturn(Optional.of(candidate));
		doNothing().when(candidateRepository).delete(candidate);

		CandidateDTO result = candidateService.deleteById(candidateDTO.getId());

		assertNotNull(result);
		assertEquals(candidateDTO.getId(), result.getId());
		verify(candidateRepository).delete(candidate);
	}

	@Test
	void shouldThrowExceptionWhenDeletingNonExistentCandidate() {
		when(candidateRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> candidateService.deleteById(candidateDTO.getId()));
	}

	@Test
	void shouldMatchCandidatesByRoles() {
		when(candidateRepository.matchByRoles(anySet())).thenReturn(List.of(candidate));

		List<SimplifiedCandidateDTO> result = candidateService.matchByRoles(Set.of("Developer"));

		assertFalse(result.isEmpty());
		verify(candidateRepository).matchByRoles(anySet());
	}
}
