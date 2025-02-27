package com.concursomatch.service.role;

import com.concursomatch.domain.role.Role;
import com.concursomatch.domain.role.dto.RoleDTO;
import com.concursomatch.repository.role.RoleRepository;
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
class RoleServiceTest {

	@Mock
	private RoleRepository roleRepository;

	@InjectMocks
	private RoleService roleService;

	private Role role;
	private RoleDTO roleDTO;

	@BeforeEach
	void setUp() {
		roleDTO = new RoleDTO("123e4567-e89b-12d3-a456-426614174000", "Admin");
		role = Role.builder().id(UUID.fromString(roleDTO.getId())).name(roleDTO.getName()).build();
	}

	@Test
	void shouldCreateRoleSuccessfully() {
		when(roleRepository.save(any(Role.class))).thenReturn(role);

		RoleDTO result = roleService.create(roleDTO.getName());

		assertNotNull(result);
		assertEquals(roleDTO.getName(), result.getName());
		verify(roleRepository).save(any(Role.class));
	}

	@Test
	void shouldFindRoleById() {
		when(roleRepository.findById(any(UUID.class))).thenReturn(Optional.of(role));

		RoleDTO result = roleService.findById(roleDTO.getId());

		assertNotNull(result);
		assertEquals(roleDTO.getId(), result.getId());
	}

	@Test
	void shouldThrowExceptionWhenRoleNotFoundById() {
		when(roleRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> roleService.findById(roleDTO.getId()));
	}

	@Test
	void shouldFindRoleByName() {
		when(roleRepository.findByName(roleDTO.getName())).thenReturn(Optional.of(role));

		RoleDTO result = roleService.findByName(roleDTO.getName());

		assertNotNull(result);
		assertEquals(roleDTO.getName(), result.getName());
	}

	@Test
	void shouldThrowExceptionWhenRoleNotFoundByName() {
		when(roleRepository.findByName(anyString())).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> roleService.findByName(roleDTO.getName()));
	}

	@Test
	void shouldUpdateRoleSuccessfully() {
		when(roleRepository.findById(any(UUID.class))).thenReturn(Optional.of(role));
		when(roleRepository.save(any(Role.class))).thenReturn(role);

		RoleDTO result = roleService.update(roleDTO);

		assertNotNull(result);
		assertEquals(roleDTO.getId(), result.getId());
		verify(roleRepository).save(any(Role.class));
	}

	@Test
	void shouldThrowExceptionWhenUpdatingNonExistentRole() {
		when(roleRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> roleService.update(roleDTO));
	}

	@Test
	void shouldDeleteRoleByIdSuccessfully() {
		when(roleRepository.findById(any(UUID.class))).thenReturn(Optional.of(role));
		doNothing().when(roleRepository).delete(role);

		RoleDTO result = roleService.deleteById(roleDTO.getId());

		assertNotNull(result);
		assertEquals(roleDTO.getId(), result.getId());
		verify(roleRepository).delete(role);
	}

	@Test
	void shouldThrowExceptionWhenDeletingNonExistentRole() {
		when(roleRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> roleService.deleteById(roleDTO.getId()));
	}

	@Test
	void shouldCreateRolesIfNotExists() {
		when(roleRepository.findByName(anyString())).thenReturn(Optional.empty());
		when(roleRepository.save(any(Role.class))).thenReturn(role);

		Set<RoleDTO> result = roleService.createRolesIfNotExists(Set.of("Admin"));

		assertFalse(result.isEmpty());
		verify(roleRepository).save(any(Role.class));
	}

	@Test
	void shouldDeleteRoleByNameSuccessfully() {
		when(roleRepository.findByName(roleDTO.getName())).thenReturn(Optional.of(role));
		doNothing().when(roleRepository).delete(role);

		RoleDTO result = roleService.deleteByName(roleDTO.getName());

		assertNotNull(result);
		assertEquals(roleDTO.getName(), result.getName());
		verify(roleRepository).delete(role);
	}
}