package com.concursomatch.repository.candidate;

import com.concursomatch.domain.candidate.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, UUID> {

    @Query(value = """
    SELECT c.*
    FROM tcandidate c
    JOIN tcandidate_role cr ON c.id = cr.candidate_id
    JOIN trole r ON cr.role_id = r.id
    WHERE r.name IN (:roleNames)
    GROUP BY c.id
    ORDER BY COUNT(r.id) DESC
    """, nativeQuery = true)
    List<Candidate> matchByRoles(@Param("roleNames") Set<String> roleNames);

    Optional<Candidate> findByCitizenId(String citizenId);
}
