package com.concursomatch.controller.role;

import com.concursomatch.domain.role.dto.RoleDTO;
import com.concursomatch.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    public ResponseEntity<Set<RoleDTO>> createRoles(@RequestBody Set<String> roleNames) {
        Set<RoleDTO> createdRoles = roleService.createRolesIfNotExists(roleNames);
        return new ResponseEntity<>(createdRoles, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable String id) {
        RoleDTO roleDTO = roleService.findById(id);
        return ResponseEntity.ok(roleDTO);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<RoleDTO> getRoleByName(@PathVariable String name) {
        RoleDTO roleDTO = roleService.findByName(name);
        return ResponseEntity.ok(roleDTO);
    }

    @PutMapping
    public ResponseEntity<RoleDTO> updateRole(@RequestBody RoleDTO roleDTO) {
        RoleDTO updatedRole = roleService.update(roleDTO);
        return ResponseEntity.ok(updatedRole);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RoleDTO> deleteRole(@PathVariable String id) {
        RoleDTO deletedRole = roleService.deleteById(id);
        return ResponseEntity.ok(deletedRole);
    }

    @DeleteMapping("/name/{name}")
    public ResponseEntity<RoleDTO> deleteRoleByName(@PathVariable String name) {
        RoleDTO deletedRole = roleService.deleteByName(name);
        return ResponseEntity.ok(deletedRole);
    }
}
