package com.concursomatch.util.assembler;

import com.concursomatch.domain.candidate.Candidate;
import com.concursomatch.domain.candidate.dto.CandidateDTO;
import com.concursomatch.domain.candidate.dto.SimplifiedCandidateDTO;
import com.concursomatch.domain.role.Role;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CandidateAssemblerTest {

	@Test
	void testToEntity() {
		CandidateDTO dto = new CandidateDTO(UUID.randomUUID().toString(), "John Doe", new Date(), "123456789", Set.of("Admin"));
		Set<Role> roles = Set.of(Role.builder().name("Admin").build());

		Candidate candidate = CandidateAssembler.toEntity(dto, roles);

		assertNotNull(candidate);
		assertEquals(dto.getName(), candidate.getName());
		assertEquals(dto.getDateOfBirth(), candidate.getDateOfBirth());
		assertEquals(dto.getCitizenId(), candidate.getCitizenId());
		assertEquals(1, candidate.getRoles().size());
	}

	@Test
	void testToDTO() {
		Candidate candidate = new Candidate(UUID.randomUUID(), "Jane Doe", new Date(), "987654321", Set.of(Role.builder().name("User").build()));
		CandidateDTO dto = CandidateAssembler.toDTO(candidate);

		assertNotNull(dto);
		assertEquals(candidate.getName(), dto.getName());
		assertEquals(candidate.getDateOfBirth(), dto.getDateOfBirth());
		assertEquals(candidate.getCitizenId(), dto.getCitizenId());
		assertTrue(dto.getRoles().contains("User"));
	}

	@Test
	void testToSimplifiedDTO() {
		Candidate candidate = new Candidate(UUID.randomUUID(), "Alex Smith", new Date(), "1122334455", Set.of());
		SimplifiedCandidateDTO dto = CandidateAssembler.toSimplifiedDTO(candidate);

		assertNotNull(dto);
		assertEquals(candidate.getName(), dto.getName());
		assertEquals(candidate.getDateOfBirth(), dto.getDateOfBirth());
		assertEquals(candidate.getCitizenId(), dto.getCitizenId());
	}

	@Test
	void testToEntitySet() {
		CandidateDTO dto1 = new CandidateDTO(UUID.randomUUID().toString(), "Alice", new Date(), "5566778899", Set.of("Editor"));
		CandidateDTO dto2 = new CandidateDTO(UUID.randomUUID().toString(), "Bob", new Date(), "9988776655", Set.of("Reviewer"));
		Set<Role> roles = Set.of(Role.builder().name("Editor").build(), Role.builder().name("Reviewer").build());

		Set<Candidate> candidates = CandidateAssembler.toEntitySet(Set.of(dto1, dto2), roles);

		assertNotNull(candidates);
		assertEquals(2, candidates.size());
	}

	@Test
	void testToDTOSet() {
		Candidate candidate1 = new Candidate(UUID.randomUUID(), "Charlie", new Date(), "4455667788", Set.of(Role.builder().name("Moderator").build()));
		Candidate candidate2 = new Candidate(UUID.randomUUID(), "David", new Date(), "2233445566", Set.of(Role.builder().name("Analyst").build()));

		Set<CandidateDTO> dtos = CandidateAssembler.toDTOSet(Set.of(candidate1, candidate2));

		assertNotNull(dtos);
		assertEquals(2, dtos.size());
	}

	@Test
	void testToSimplifiedDTOList() {
		Candidate candidate1 = new Candidate(UUID.randomUUID(), "Emma", new Date(), "6655443322", Set.of());
		Candidate candidate2 = new Candidate(UUID.randomUUID(), "Frank", new Date(), "1122334455", Set.of());

		List<SimplifiedCandidateDTO> dtos = CandidateAssembler.toSimplifiedDTOList(List.of(candidate1, candidate2));

		assertNotNull(dtos);
		assertEquals(2, dtos.size());
	}
}
