package com.specification;

import org.springframework.data.jpa.domain.Specification;

import com.Entity.Candidate;

public class CandidateSpecification {

    public static Specification<Candidate> containsKeyword(String keyword) {
        return (root, query, builder) -> {
            if (keyword == null || keyword.isEmpty()) return builder.conjunction();

            String likePattern = "%" + keyword.toLowerCase() + "%";
            return builder.or(
                builder.like(builder.lower(root.get("name")), likePattern),
                builder.like(builder.lower(root.get("email")), likePattern)
            );
        };
    }
}
