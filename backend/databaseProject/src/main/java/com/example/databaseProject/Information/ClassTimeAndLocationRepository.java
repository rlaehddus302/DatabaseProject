package com.example.databaseProject.Information;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassTimeAndLocationRepository extends JpaRepository<ClassTimeAndLocation, String> {

}
