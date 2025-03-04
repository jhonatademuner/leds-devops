package com.concursomatch.controller.role;

import com.concursomatch.domain.role.dto.RoleDTO;
import com.concursomatch.service.role.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleControllerTest {

	@Mock
	private RoleService roleService;

	@InjectMocks
	private RoleController roleController;

	private RoleDTO roleDTO;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		roleDTO = new RoleDTO(); // Initialize with necessary test data
	}

	@Test
	void createRoles_ShouldReturnCreatedRoles() {
		Set<String> roleNames = Set.of("ADMIN", "USER");
		Set<RoleDTO> createdRoles = Set.of(roleDTO);
		when(roleService.createRolesIfNotExists(roleNames)).thenReturn(createdRoles);

		ResponseEntity<Set<RoleDTO>> response = roleController.createRoles(roleNames);

		assertEquals(201, response.getStatusCodeValue());
		assertEquals(createdRoles, response.getBody());
	}

	@Test
	void getRoleById_ShouldReturnRole() {
		String id = "123";
		when(roleService.findById(id)).thenReturn(roleDTO);

		ResponseEntity<RoleDTO> response = roleController.getRoleById(id);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(roleDTO, response.getBody());
	}

	@Test
	void getRoleByName_ShouldReturnRole() {
		String name = "ADMIN";
		when(roleService.findByName(name)).thenReturn(roleDTO);

		ResponseEntity<RoleDTO> response = roleController.getRoleByName(name);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(roleDTO, response.getBody());
	}

	@Test
	void updateRole_ShouldReturnUpdatedRole() {
		when(roleService.update(roleDTO)).thenReturn(roleDTO);

		ResponseEntity<RoleDTO> response = roleController.updateRole(roleDTO);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(roleDTO, response.getBody());
	}

	@Test
	void deleteRole_ShouldReturnDeletedRole() {
		String id = "123";
		when(roleService.deleteById(id)).thenReturn(roleDTO);

		ResponseEntity<RoleDTO> response = roleController.deleteRole(id);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(roleDTO, response.getBody());
	}

	@Test
	void deleteRoleByName_ShouldReturnDeletedRole() {
		String name = "ADMIN";
		when(roleService.deleteByName(name)).thenReturn(roleDTO);

		ResponseEntity<RoleDTO> response = roleController.deleteRoleByName(name);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(roleDTO, response.getBody());
	}
}