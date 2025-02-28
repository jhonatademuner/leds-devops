package com.concursomatch.service.candidate;

import com.concursomatch.domain.candidate.Candidate;
import com.concursomatch.domain.candidate.dto.CandidateDTO;
import com.concursomatch.domain.candidate.dto.SimplifiedCandidateDTO;
import com.concursomatch.domain.role.Role;
import com.concursomatch.repository.candidate.CandidateRepository;
import com.concursomatch.service.role.RoleService;
import com.concursomatch.util.assembler.CandidateAssembler;
import com.concursomatch.util.assembler.RoleAssembler;
import com.concursomatch.util.exception.CandidateAlreadyExistsException;
import com.concursomatch.util.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private RoleService roleService;

    public CandidateDTO create(CandidateDTO candidateDTO) {
        Optional<Candidate> existingEntity = candidateRepository.findByCitizenId(candidateDTO.getCitizenId());
        if(existingEntity.isPresent()) throw new CandidateAlreadyExistsException("Candidate already exists with CITIZEN_ID:" + candidateDTO.getCitizenId());
        Set<Role> roles = RoleAssembler.toEntitySet(roleService.createRolesIfNotExists(candidateDTO.getRoles()));
        Candidate entity = candidateRepository.save(CandidateAssembler.toEntity(candidateDTO, roles));
        return CandidateAssembler.toDTO(entity);
    }

    public CandidateDTO findById(String id) {
        Candidate entity = candidateRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with ID: " + id));
        return CandidateAssembler.toDTO(entity);
    }

    public CandidateDTO findByCitizenId(String id) {
        Candidate entity = candidateRepository.findByCitizenId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with CITIZEN_ID: " + id));
        return CandidateAssembler.toDTO(entity);
    }

    public CandidateDTO update(CandidateDTO candidateDTO) {
        candidateRepository.findById(UUID.fromString(candidateDTO.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with ID: " + candidateDTO.getId()));
        Set<Role> roles = RoleAssembler.toEntitySet(roleService.createRolesIfNotExists(candidateDTO.getRoles()));
        Candidate updatedCandidate = CandidateAssembler.toEntity(candidateDTO, roles);
        return CandidateAssembler.toDTO(candidateRepository.save(updatedCandidate));
    }

    public CandidateDTO deleteById(String id) {
        Candidate entity = candidateRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with ID: " + id));
        candidateRepository.delete(entity);
        return CandidateAssembler.toDTO(entity);
    }

    public CandidateDTO deleteByCitizenId(String id) {
        Candidate entity = candidateRepository.findByCitizenId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with ID: " + id));
        candidateRepository.delete(entity);
        return CandidateAssembler.toDTO(entity);
    }

    public List<SimplifiedCandidateDTO> matchByRoles(Set<String> roleNames) {
        List<Candidate> matchedCandidates = candidateRepository.matchByRoles(roleNames);
        return CandidateAssembler.toSimplifiedDTOList(matchedCandidates);
    }
}
