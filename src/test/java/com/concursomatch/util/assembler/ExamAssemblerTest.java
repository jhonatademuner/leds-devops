package com.concursomatch.util.assembler;

import com.concursomatch.domain.exam.Exam;
import com.concursomatch.domain.exam.dto.ExamDTO;
import com.concursomatch.domain.exam.dto.SimplifiedExamDTO;
import com.concursomatch.domain.role.Role;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ExamAssemblerTest {

	@Test
	void toEntity_ShouldConvertDTOToEntity() {
		ExamDTO dto = ExamDTO.builder()
				.id(UUID.randomUUID().toString())
				.agency("AgencyX")
				.notice("NoticeX")
				.code("EX-123")
				.roles(Set.of("Role1", "Role2"))
				.build();
		Set<Role> roles = Set.of(Role.builder().name("Role1").build(), Role.builder().name("Role2").build());

		Exam entity = ExamAssembler.toEntity(dto, roles);

		assertNotNull(entity);
		assertEquals(dto.getAgency(), entity.getAgency());
		assertEquals(dto.getNotice(), entity.getNotice());
		assertEquals(dto.getCode(), entity.getCode());
	}

	@Test
	void toDTO_ShouldConvertEntityToDTO() {
		Exam entity = Exam.builder()
				.id(UUID.randomUUID())
				.agency("AgencyY")
				.notice("NoticeY")
				.code("EX-456")
				.roles(Set.of(Role.builder().name("Role3").build()))
				.build();

		ExamDTO dto = ExamAssembler.toDTO(entity);

		assertNotNull(dto);
		assertEquals(entity.getAgency(), dto.getAgency());
		assertEquals(entity.getNotice(), dto.getNotice());
		assertEquals(entity.getCode(), dto.getCode());
	}

	@Test
	void toSimplifiedDTO_ShouldConvertEntityToSimplifiedDTO() {
		Exam entity = Exam.builder()
				.agency("AgencyZ")
				.notice("NoticeZ")
				.code("EX-789")
				.build();

		SimplifiedExamDTO simplifiedDTO = ExamAssembler.toSimplifiedDTO(entity);

		assertNotNull(simplifiedDTO);
		assertEquals(entity.getAgency(), simplifiedDTO.getAgency());
		assertEquals(entity.getNotice(), simplifiedDTO.getNotice());
		assertEquals(entity.getCode(), simplifiedDTO.getCode());
	}

	@Test
	void toEntitySet_ShouldConvertDTOSetToEntitySet() {
		Set<ExamDTO> dtoSet = Set.of(
				ExamDTO.builder().id(UUID.randomUUID().toString()).agency("A1").notice("N1").code("C1").roles(Set.of("RoleA")).build(),
				ExamDTO.builder().id(UUID.randomUUID().toString()).agency("A2").notice("N2").code("C2").roles(Set.of("RoleB")).build()
		);
		Set<Role> roles = Set.of(Role.builder().name("RoleA").build(), Role.builder().name("RoleB").build());

		Set<Exam> entitySet = ExamAssembler.toEntitySet(dtoSet, roles);

		assertNotNull(entitySet);
		assertEquals(dtoSet.size(), entitySet.size());
	}

	@Test
	void toDTOSet_ShouldConvertEntitySetToDTOSet() {
		Set<Exam> entitySet = Set.of(
				Exam.builder().id(UUID.randomUUID()).agency("A3").notice("N3").code("C3").roles(Set.of(Role.builder().name("RoleC").build())).build()
		);

		Set<ExamDTO> dtoSet = ExamAssembler.toDTOSet(entitySet);

		assertNotNull(dtoSet);
		assertEquals(entitySet.size(), dtoSet.size());
	}

	@Test
	void toSimplifiedDTOList_ShouldConvertEntityListToSimplifiedDTOList() {
		List<Exam> entityList = List.of(
				Exam.builder().agency("A4").notice("N4").code("C4").build()
		);

		List<SimplifiedExamDTO> dtoList = ExamAssembler.toSimplifiedDTOList(entityList);

		assertNotNull(dtoList);
		assertEquals(entityList.size(), dtoList.size());
	}
}