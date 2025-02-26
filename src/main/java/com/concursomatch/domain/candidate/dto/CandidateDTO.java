package com.concursomatch.domain.candidate.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CandidateDTO {

    private String id;
    private String name;
    private Date dateOfBirth;
    private String citizenId;
    private Set<String> roles;

}
