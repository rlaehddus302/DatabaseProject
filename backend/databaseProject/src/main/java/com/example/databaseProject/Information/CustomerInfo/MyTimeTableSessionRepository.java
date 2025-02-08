package com.example.databaseProject.Information.CustomerInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface MyTimeTableSessionRepository extends JpaRepository<MyTimeTableSession, Long> {
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE MY_TIME_TABLE_SESSION SET TABLE_NAME = :newName where TABLE_NAME = :oldName", nativeQuery = true)
	int updateTableNameByName(@Param("oldName") String oldName, @Param("newName") String newName);
}
