package com.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Entity.Candidate;
import com.Service.CandidateService;

@RestController
@RequestMapping("/api/candidates")
public class CandidateController {
    
    @Autowired
    private CandidateService candidateService;
    
    @GetMapping
    public Page<Candidate> getAllCandidates(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "candidateId") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        
        return candidateService.getCandidates(search, page, size, sortBy, direction);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Candidate> getCandidate(@PathVariable Long id) {
        return candidateService.getCandidateById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Candidate createCandidate(@RequestBody Candidate candidate) {
        candidate.setCandidateId(null);
        return candidateService.saveCandidate(candidate);
    }
    
    @PutMapping("/{id}")
    public Candidate updateCandidate(@PathVariable Long id, @RequestBody Candidate candidate) {
        return candidateService.updateCandidate(id, candidate);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long id) {
        return candidateService.deleteCandidate(id) 
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}