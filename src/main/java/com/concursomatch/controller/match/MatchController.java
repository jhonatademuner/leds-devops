package com.concursomatch.controller.match;

import com.concursomatch.domain.candidate.dto.SimplifiedCandidateDTO;
import com.concursomatch.domain.exam.dto.SimplifiedExamDTO;
import com.concursomatch.service.match.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/match")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @GetMapping("/candidate/{citizenId}")
    public ResponseEntity<List<SimplifiedExamDTO>> matchExamWithCandidate(@PathVariable String citizenId){
        List<SimplifiedExamDTO> matchedExams = matchService.matchCandidateWithExamByCitizenId(citizenId);
        return ResponseEntity.ok(matchedExams);
    }

    @GetMapping("/exam/{examCode}")
    public ResponseEntity<List<SimplifiedCandidateDTO>> matchCandidateWithExam(@PathVariable String examCode){
        List<SimplifiedCandidateDTO> matchedCandidates = matchService.matchExamWithCandidateByExamCode(examCode);
        return ResponseEntity.ok(matchedCandidates);
    }

}
