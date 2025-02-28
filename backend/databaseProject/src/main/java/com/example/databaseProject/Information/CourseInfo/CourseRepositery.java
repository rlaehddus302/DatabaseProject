package com.example.databaseProject.Information.CourseInfo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepositery extends JpaRepository<Course, Long> {
    
	@Query(value = "SELECT * FROM COURSE WHERE name LIKE %:name% and ACADEMIC_TERM_ID = :academicTemrID", nativeQuery = true)
	List<Course> searchCourseName(@Param("name") String name, @Param("academicTemrID") long academicTemrID );
	
    Optional<Course> findByName(String username);

}
