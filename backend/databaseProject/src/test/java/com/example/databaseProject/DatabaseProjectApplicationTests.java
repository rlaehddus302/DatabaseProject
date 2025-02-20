package com.example.databaseProject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.databaseProject.Information.CourseInfo.ClassTimeAndLocation;
import com.example.databaseProject.Information.CourseInfo.Course;
import com.example.databaseProject.Information.CourseInfo.CourseRepositery;
import com.example.databaseProject.Information.CourseInfo.Session;
import com.example.databaseProject.Information.CustomerInfo.CustomerRepository;
import com.example.databaseProject.TDO.ReturnInfo;
import com.example.databaseProject.generateTimeTable.CaculatorService;
import com.example.databaseProject.generateTimeTable.CsvToDatabaseLoader;

import jakarta.transaction.Transactional;

@SpringBootTest
class DatabaseProjectApplicationTests {
	
}
