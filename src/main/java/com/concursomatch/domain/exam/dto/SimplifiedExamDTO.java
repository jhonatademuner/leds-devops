package com.concursomatch.domain.exam.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimplifiedExamDTO {

	private String agency;
	private String notice;
	private String code;

}
