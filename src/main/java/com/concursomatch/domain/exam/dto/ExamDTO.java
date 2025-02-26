package com.concursomatch.domain.exam.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExamDTO {

    private String id;
    private String agency;
    private String notice;
    private String code;
    private Set<String> roles = new HashSet<>();

}
