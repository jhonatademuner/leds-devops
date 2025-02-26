package com.concursomatch.util.assembler;

import com.concursomatch.domain.exam.Exam;
import com.concursomatch.domain.exam.dto.ExamDTO;
import com.concursomatch.domain.exam.dto.SimplifiedExamDTO;
import com.concursomatch.domain.role.Role;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class ExamAssembler {

    public static Exam toEntity(ExamDTO dto, Set<Role> roles) {
        return Exam.builder()
                .id(dto.getId() == null ? null : UUID.fromString(dto.getId()))
                .agency(dto.getAgency())
                .notice(dto.getNotice())
                .code(dto.getCode())
                .roles(roles)
                .build();
    }

    public static ExamDTO toDTO(Exam exam) {
        Set<String> roleIds = exam.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        return ExamDTO.builder()
                .id(exam.getId() == null ? null : exam.getId().toString())
                .agency(exam.getAgency())
                .notice(exam.getNotice())
                .code(exam.getCode())
                .roles(roleIds)
                .build();
    }

    public static SimplifiedExamDTO toSimplifiedDTO(Exam exam) {
        Set<String> roleIds = exam.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        return SimplifiedExamDTO.builder()
                .agency(exam.getAgency())
                .notice(exam.getNotice())
                .code(exam.getCode())
                .build();
    }

    public static Set<Exam> toEntitySet(Set<ExamDTO> dtoSet, Set<Role> roles) {
        if (dtoSet == null) return Set.of();
        return dtoSet.stream()
                .map(dto -> toEntity(dto, roles))
                .collect(Collectors.toSet());
    }

    public static Set<ExamDTO> toDTOSet(Set<Exam> exams) {
        if (exams == null) return Set.of();
        return exams.stream()
                .map(ExamAssembler::toDTO)
                .collect(Collectors.toSet());
    }

    public static List<SimplifiedExamDTO> toSimplifiedDTOList(List<Exam> exams) {
        if (exams == null) return List.of();
        return exams.stream()
                .map(ExamAssembler::toSimplifiedDTO)
                .collect(Collectors.toList());
    }
}
