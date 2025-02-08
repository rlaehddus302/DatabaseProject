package com.example.databaseProject.Information.CourseInfo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepositery extends JpaRepository<Course, String> {
    
	@Query(value = "SELECT * FROM COURSE WHERE name LIKE %:name%", nativeQuery = true)
	List<Course> searchCourseName(@Param("name") String name);
}
