package com.concursomatch.util.assembler;

import com.concursomatch.domain.role.Role;
import com.concursomatch.domain.role.dto.RoleDTO;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RoleAssemblerTest {

	@Test
	void toEntity_ShouldConvertDTOToEntity() {
		String id = UUID.randomUUID().toString();
		RoleDTO dto = RoleDTO.builder().id(id).name("ADMIN").build();

		Role role = RoleAssembler.toEntity(dto);

		assertNotNull(role);
		assertEquals(id, role.getId().toString());
		assertEquals("ADMIN", role.getName());
	}

	@Test
	void toEntity_ShouldConvertRoleNameToEntity() {
		String roleName = "USER";

		Role role = RoleAssembler.toEntity(roleName);

		assertNotNull(role);
		assertNull(role.getId());
		assertEquals(roleName, role.getName());
	}

	@Test
	void toDTO_ShouldConvertEntityToDTO() {
		Role role = Role.builder().id(UUID.randomUUID()).name("MANAGER").build();

		RoleDTO dto = RoleAssembler.toDTO(role);

		assertNotNull(dto);
		assertEquals(role.getId().toString(), dto.getId());
		assertEquals("MANAGER", dto.getName());
	}

	@Test
	void toEntitySet_ShouldConvertDTOSetToEntitySet() {
		Set<RoleDTO> dtoSet = Set.of(
				RoleDTO.builder().id(UUID.randomUUID().toString()).name("ADMIN").build(),
				RoleDTO.builder().id(UUID.randomUUID().toString()).name("USER").build()
		);

		Set<Role> roleSet = RoleAssembler.toEntitySet(dtoSet);

		assertNotNull(roleSet);
		assertEquals(2, roleSet.size());
	}

	@Test
	void toEntitySetFromNames_ShouldConvertNameSetToEntitySet() {
		Set<String> nameSet = Set.of("ADMIN", "USER");

		Set<Role> roleSet = RoleAssembler.toEntitySetFromNames(nameSet);

		assertNotNull(roleSet);
		assertEquals(2, roleSet.size());
		assertTrue(roleSet.stream().anyMatch(role -> "ADMIN".equals(role.getName())));
		assertTrue(roleSet.stream().anyMatch(role -> "USER".equals(role.getName())));
	}

	@Test
	void toDTOSet_ShouldConvertEntitySetToDTOSet() {
		Set<Role> roleSet = Set.of(
				Role.builder().id(UUID.randomUUID()).name("ADMIN").build(),
				Role.builder().id(UUID.randomUUID()).name("USER").build()
		);

		Set<RoleDTO> dtoSet = RoleAssembler.toDTOSet(roleSet);

		assertNotNull(dtoSet);
		assertEquals(2, dtoSet.size());
	}
}