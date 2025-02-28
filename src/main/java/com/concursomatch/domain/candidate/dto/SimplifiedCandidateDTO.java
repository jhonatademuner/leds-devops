package com.concursomatch.domain.candidate.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimplifiedCandidateDTO {

	private String name;
	private Date dateOfBirth;
	private String citizenId;

}
