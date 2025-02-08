package com.example.databaseProject.Information.CustomerInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyTimeTableNameRepository extends JpaRepository<MyTimeTableName, String> {
	
	
}
