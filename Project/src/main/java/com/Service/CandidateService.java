package com.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.Entity.Candidate;
import com.Repository.CandidateRepository;
import com.specification.CandidateSpecification;
import java.util.Optional;

@Service
public class CandidateService {
    
    @Autowired
    private CandidateRepository candidateRepository;
    
    public Page<Candidate> getCandidates(String search, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        return candidateRepository.findAll(CandidateSpecification.containsKeyword(search), 
                                         PageRequest.of(page, size, sort));
    }
    
    public Optional<Candidate> getCandidateById(Long id) {
        return candidateRepository.findById(id);
    }
    
    public Candidate saveCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }
    
    public Candidate updateCandidate(Long id, Candidate updated) {
        return candidateRepository.findById(id).map(candidate -> {
            candidate.setName(updated.getName());
            candidate.setEmail(updated.getEmail());
            candidate.setPhone(updated.getPhone());
            candidate.setAddress(updated.getAddress());
            return candidateRepository.save(candidate);
        }).orElseThrow(() -> new RuntimeException("Candidate not found"));
    }
    
    public boolean deleteCandidate(Long id) {
        if (candidateRepository.existsById(id)) {
            candidateRepository.deleteById(id);
            return true;
        }
        return false;
    }
}