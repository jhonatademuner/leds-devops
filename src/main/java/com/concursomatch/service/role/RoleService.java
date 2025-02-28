package com.concursomatch.service.role;

import com.concursomatch.domain.exam.dto.ExamDTO;
import com.concursomatch.domain.role.Role;
import com.concursomatch.domain.role.dto.RoleDTO;
import com.concursomatch.repository.role.RoleRepository;
import com.concursomatch.util.assembler.RoleAssembler;
import com.concursomatch.util.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public RoleDTO create(String roleName){
        Role entity = roleRepository.save(RoleAssembler.toEntity(roleName));
        return RoleAssembler.toDTO(entity);
    }

    public RoleDTO findById(String id){
        Role entity = roleRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with ID: " + id));
        return RoleAssembler.toDTO(entity);
    }

    public RoleDTO findByName(String name) {
        Role entity = roleRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with NAME: " + name));
        return RoleAssembler.toDTO(entity);
    }

    public RoleDTO update(RoleDTO roleDTO) {
        roleRepository.findById(UUID.fromString(roleDTO.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with ID: " + roleDTO.getId()));
        Role updatedRole = RoleAssembler.toEntity(roleDTO);
        return RoleAssembler.toDTO(roleRepository.save(updatedRole));
    }

    public RoleDTO deleteById(String id) {
        Role entity = roleRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with ID: " + id));
        roleRepository.delete(entity);
        return RoleAssembler.toDTO(entity);
    }

    public Set<RoleDTO> createRolesIfNotExists(Set<String> roles){
        Set<RoleDTO> result = new HashSet<>();
        for(String role : roles){
            RoleDTO dto = this.createIfNotExists(role);
            if(dto == null) continue;
            result.add(dto);
        }
        return result;
    }

    @Transactional
    private RoleDTO createIfNotExists(String roleName) {
        Optional<Role> existingEntity = roleRepository.findByName(roleName);
        if(existingEntity.isPresent()){
            return RoleAssembler.toDTO(existingEntity.get());
        }
        return this.create(roleName);

    }

    public RoleDTO deleteByName(String name) {
        Role entity = roleRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with NAME: " + name));
        roleRepository.delete(entity);
        return RoleAssembler.toDTO(entity);
    }

}
