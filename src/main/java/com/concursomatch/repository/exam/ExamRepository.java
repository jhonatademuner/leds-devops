package com.concursomatch.repository.exam;

import com.concursomatch.domain.exam.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ExamRepository extends JpaRepository<Exam, UUID> {

	@Query(value = """
    SELECT e.*
    FROM texam e
    JOIN texam_role er ON e.id = er.exam_id
    JOIN trole r ON er.role_id = r.id
    WHERE r.name IN (:roleNames)
    GROUP BY e.id
    ORDER BY COUNT(r.id) DESC
    """, nativeQuery = true)
	List<Exam> matchByRoles(@Param("roleNames") Set<String> roleNames);

	Optional<Exam> findByCode(String examCode);
}
