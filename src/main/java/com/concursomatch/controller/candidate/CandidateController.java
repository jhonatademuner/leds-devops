package com.concursomatch.controller.candidate;

import com.concursomatch.domain.candidate.dto.CandidateDTO;
import com.concursomatch.service.candidate.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/candidate")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @PostMapping
    public ResponseEntity<CandidateDTO> createCandidate(@RequestBody CandidateDTO candidateDTO) {
        CandidateDTO createdCandidate = candidateService.create(candidateDTO);
        return new ResponseEntity<>(createdCandidate, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateDTO> getCandidateById(@PathVariable String id) {
        CandidateDTO candidateDTO = candidateService.findById(id);
        return ResponseEntity.ok(candidateDTO);
    }

    @PutMapping
    public ResponseEntity<CandidateDTO> updateCandidate(@RequestBody CandidateDTO candidateDTO) {
        CandidateDTO updatedCandidate = candidateService.update(candidateDTO);
        return ResponseEntity.ok(updatedCandidate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CandidateDTO> deleteCandidateById(@PathVariable String id) {
        CandidateDTO deletedCandidate = candidateService.deleteById(id);
        return ResponseEntity.ok(deletedCandidate);
    }

    @GetMapping
    public ResponseEntity<CandidateDTO> getCandidateByCitizenId(@RequestParam String citizenId) {
        CandidateDTO candidateDTO = candidateService.findByCitizenId(citizenId);
        return ResponseEntity.ok(candidateDTO);
    }

    @DeleteMapping
    public ResponseEntity<CandidateDTO> deleteCandidateByCitizenId(@RequestParam String citizenId) {
        CandidateDTO deletedCandidate = candidateService.deleteByCitizenId(citizenId);
        return ResponseEntity.ok(deletedCandidate);
    }



}
