package com.concursomatch.controller;

import com.concursomatch.domain.candidate.dto.CandidateDTO;
import com.concursomatch.domain.exam.dto.ExamDTO;
import com.concursomatch.service.candidate.CandidateService;
import com.concursomatch.service.exam.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/file")
public class FileController {

	@Autowired
	private ExamService examService;

	@Autowired
	private CandidateService candidateService;

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

	@PostMapping(value = "/candidate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> populateCandidates(@RequestParam MultipartFile file) {
		List<CandidateDTO> parsedCandidates = parseCandidates(file);
		return ResponseEntity.ok().build();
	}

	@PostMapping(value = "/exam", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> populateExam(@RequestParam MultipartFile file) {
		List<ExamDTO> parsedExams = parseExams(file);
		return ResponseEntity.ok().build();
	}

	public List<CandidateDTO> parseCandidates(MultipartFile file) {
		List<CandidateDTO> candidates = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
			String line;
			while ((line = reader.readLine()) != null) {
				CandidateDTO candidate = parseCandidateLine(line);
				if (candidate != null) {
					CandidateDTO savedCandidate = candidateService.create(candidate);
					candidates.add(savedCandidate);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return candidates;
	}

	public List<ExamDTO> parseExams(MultipartFile file) {
		List<ExamDTO> exams = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
			String line;
			while ((line = reader.readLine()) != null) {
				ExamDTO exam = parseExamLine(line);
				if (exam != null) {
					ExamDTO savedExam = examService.create(exam);
					exams.add(savedExam);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return exams;
	}

	private CandidateDTO parseCandidateLine(String line) {
		try {
			String[] parts = line.split(" ", 6);
			if (parts.length < 5) return null;

			boolean isThreeNames = isFirstCharacterLetter(parts[2]);

			String name = parts[0] + " " + parts[1];
			int dateOfBirthIndex = 2;
			int citizenIdIndex = 3;
			if(isThreeNames){
				name = parts[0] + " " + parts[1] + " " + parts[2];
				dateOfBirthIndex++;
				citizenIdIndex++;
			}

			Date dateOfBirth = DATE_FORMAT.parse(parts[dateOfBirthIndex]);
			String citizenId = parts[citizenIdIndex].replace(".", "").replace("-", "");

			String rolesPart = line.substring(line.indexOf("[") + 1, line.indexOf("]"));
			Set<String> roles = Arrays.stream(rolesPart.split(", "))
					.map(String::trim)
					.collect(Collectors.toSet());

			return CandidateDTO.builder()
					.name(name)
					.dateOfBirth(dateOfBirth)
					.citizenId(citizenId)
					.roles(roles)
					.build();
		} catch (ParseException | StringIndexOutOfBoundsException e) {
			e.printStackTrace();
			return null;
		}
	}

	private ExamDTO parseExamLine(String line) {
		try {
			String[] parts = line.split(" ", 4);
			if (parts.length < 4) return null;


			String agency = parts[0];
			String notice = parts[1];
			String examCode = parts[2];

			String rolesPart = line.substring(line.indexOf("[") + 1, line.indexOf("]"));
			Set<String> roles = Arrays.stream(rolesPart.split(", "))
					.map(String::trim)
					.collect(Collectors.toSet());

			return ExamDTO.builder()
					.agency(agency)
					.notice(notice)
					.code(examCode)
					.roles(roles)
					.build();
		} catch (StringIndexOutOfBoundsException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean isFirstCharacterLetter(String str) {
		return str != null && !str.isEmpty() && Character.isLetter(str.charAt(0));
	}


}
