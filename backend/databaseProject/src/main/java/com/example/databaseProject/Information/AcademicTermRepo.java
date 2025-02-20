package com.example.databaseProject.Information;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AcademicTermRepo extends JpaRepository<AcademicTerm, Long> {
	
	@Query(value = "SELECT * FROM academic_term WHERE academic_year = :year and semester = :semester", nativeQuery = true)
	public Optional<AcademicTerm> findByYearAndSemester (@Param("year") int year,@Param("semester") int semester);
}
