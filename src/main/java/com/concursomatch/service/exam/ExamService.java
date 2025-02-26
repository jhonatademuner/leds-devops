package com.concursomatch.service.exam;

import com.concursomatch.domain.exam.Exam;
import com.concursomatch.domain.exam.dto.ExamDTO;
import com.concursomatch.domain.exam.dto.SimplifiedExamDTO;
import com.concursomatch.domain.role.Role;
import com.concursomatch.repository.exam.ExamRepository;
import com.concursomatch.service.role.RoleService;
import com.concursomatch.util.assembler.ExamAssembler;
import com.concursomatch.util.assembler.RoleAssembler;
import com.concursomatch.util.exception.ExamAlreadyExists;
import com.concursomatch.util.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class ExamService {
    
    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private RoleService roleService;

    @Transactional
    public ExamDTO create(ExamDTO examDTO){
        Optional<Exam> existingEntity = examRepository.findByCode(examDTO.getCode());
        if(existingEntity.isPresent()) throw new ExamAlreadyExists("Exam already exists with CODE:" + examDTO.getCode());
        Set<Role> roles = RoleAssembler.toEntitySet(roleService.createRolesIfNotExists(examDTO.getRoles()));
        Exam entity = examRepository.save(ExamAssembler.toEntity(examDTO, roles));
        return ExamAssembler.toDTO(entity);
    }

    public ExamDTO findById(String id){
        Exam entity = examRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found with ID: " + id));
        return ExamAssembler.toDTO(entity);
    }

    public ExamDTO update(ExamDTO examDTO) {
        Exam existingExam = examRepository.findById(UUID.fromString(examDTO.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found with ID: " + examDTO.getId()));
        Set<Role> roles = RoleAssembler.toEntitySet(roleService.createRolesIfNotExists(examDTO.getRoles()));
        Exam updatedExam = ExamAssembler.toEntity(examDTO, roles);
        return ExamAssembler.toDTO(examRepository.save(updatedExam));
    }

    public ExamDTO deleteById(String id) {
        Exam entity = examRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found with ID: " + id));
        examRepository.delete(entity);
        return ExamAssembler.toDTO(entity);
    }

	public List<SimplifiedExamDTO> matchByRoles(Set<String> roleNames) {
        List<Exam> matchedExams = examRepository.matchByRoles(roleNames);
        return ExamAssembler.toSimplifiedDTOList(matchedExams);
	}

    public ExamDTO findByCode(String examCode) {
        Exam entity = examRepository.findByCode(examCode)
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found with CODE: " + examCode));
        return ExamAssembler.toDTO(entity);
    }
}
