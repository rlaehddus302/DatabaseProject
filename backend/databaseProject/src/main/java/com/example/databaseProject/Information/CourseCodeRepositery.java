package com.example.databaseProject.Information;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseCodeRepositery extends JpaRepository<CourseCode, String> {

}
