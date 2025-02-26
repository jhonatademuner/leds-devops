package com.concursomatch.service.match;

import com.concursomatch.domain.candidate.dto.CandidateDTO;
import com.concursomatch.domain.candidate.dto.SimplifiedCandidateDTO;
import com.concursomatch.domain.exam.dto.ExamDTO;
import com.concursomatch.domain.exam.dto.SimplifiedExamDTO;
import com.concursomatch.service.candidate.CandidateService;
import com.concursomatch.service.exam.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class MatchService {

    @Autowired
    private ExamService examService;

    @Autowired
    private CandidateService candidateService;

    public List<SimplifiedExamDTO> matchCandidateWithExamByCitizenId(String citizenId) {
        CandidateDTO candidate = candidateService.findByCitizenId(citizenId);
        Set<String> candidateRoles = candidate.getRoles();
		return examService.matchByRoles(candidateRoles);

    }

    public List<SimplifiedCandidateDTO> matchExamWithCandidateByExamCode(String examCode) {
        ExamDTO exam = examService.findByCode(examCode);
        Set<String> examRoles = exam.getRoles();
        return candidateService.matchByRoles(examRoles);
    }
}
