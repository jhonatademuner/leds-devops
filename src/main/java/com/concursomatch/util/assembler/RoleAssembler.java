package com.concursomatch.util.assembler;

import com.concursomatch.domain.role.Role;
import com.concursomatch.domain.role.dto.RoleDTO;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class RoleAssembler {

    private RoleAssembler(){
        throw new IllegalStateException("Utility Class");
    }

    public static Role toEntity(RoleDTO dto) {
        return Role.builder()
                .id(UUID.fromString(dto.getId()))
                .name(dto.getName())
                .build();
    }

    public static Role toEntity(String roleName) {
        return Role.builder()
                .name(roleName)
                .build();
    }

    public static RoleDTO toDTO(Role role) {
        return RoleDTO.builder()
                .id(role.getId().toString())
                .name(role.getName())
                .build();
    }

    public static Set<Role> toEntitySet(Set<RoleDTO> dtoSet) {
        if (dtoSet == null) return Set.of();
        return dtoSet.stream()
                .map(RoleAssembler::toEntity)
                .collect(Collectors.toSet());
    }

    public static Set<Role> toEntitySetFromNames(Set<String> nameSet) {
        if (nameSet == null) return Set.of();
        return nameSet.stream()
                .map(RoleAssembler::toEntity)
                .collect(Collectors.toSet());
    }

    public static Set<RoleDTO> toDTOSet(Set<Role> roles) {
        if (roles == null) return Set.of();
        return roles.stream()
                .map(RoleAssembler::toDTO)
                .collect(Collectors.toSet());
    }
}
