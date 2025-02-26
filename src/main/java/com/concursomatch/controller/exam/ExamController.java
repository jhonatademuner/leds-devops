package com.concursomatch.controller.exam;

import com.concursomatch.domain.exam.dto.ExamDTO;
import com.concursomatch.service.exam.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exam")
public class ExamController {

    @Autowired
    private ExamService examService;

    @PostMapping
    public ResponseEntity<ExamDTO> createExam(@RequestBody ExamDTO examDTO) {
        ExamDTO createdExam = examService.create(examDTO);
        return new ResponseEntity<>(createdExam, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamDTO> getExamById(@PathVariable String id) {
        ExamDTO examDTO = examService.findById(id);
        return ResponseEntity.ok(examDTO);
    }

    @PutMapping
    public ResponseEntity<ExamDTO> updateExam(@RequestBody ExamDTO examDTO) {
        ExamDTO updatedExam = examService.update(examDTO);
        return ResponseEntity.ok(updatedExam);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ExamDTO> deleteExam(@PathVariable String id) {
        ExamDTO deletedExam = examService.deleteById(id);
        return ResponseEntity.ok(deletedExam);
    }
}
