package com.example.databaseProject.generateTimeTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.databaseProject.Information.Course;
import com.example.databaseProject.Information.CourseRepositery;
import com.example.databaseProject.Information.Customer;
import com.example.databaseProject.Information.CustomerRepository;
import com.example.databaseProject.TDO.ReceivedValue;
import com.example.databaseProject.TDO.ReturnInfo;

import jakarta.servlet.http.HttpSession;

@RestController
public class TimeTable {
	@Autowired
	CourseRepositery courseRepository;
	@Autowired
	Caculator caculator;
	@Autowired
	CustomerRepository customerRepository;
	
	@PostMapping(path = "/basicOauth")
	public String login()
	{
		return "Success";
	}
	@PostMapping(path = "/exit")
	public String logOut(HttpSession session)
	{
		session.invalidate();
		return "Success";
	}
	@GetMapping(path = "/course")
	public List<Course> course()
	{
		return courseRepository.findAll();
	}
	@PostMapping(path = "/courseSearch")
	public List<Course> search(@RequestBody String name)
	{
		name = name.substring(1, name.length() - 1);
		return courseRepository.searchCourseName(name);
	}
	
	@PostMapping(path = "/caculate")
	public void selectedCourse(@RequestBody String[] courses)
	{
		caculator.setCourses(courses);
	}
	
	@PostMapping(path = "/SetCondition")
	public void setFreePeriod(@RequestBody ReceivedValue value)
	{
		caculator.setFreePeriod(value.getFreePeriod());
		caculator.setValue(value);
	}
	
	@PostMapping(path = "/register")
	public ResponseEntity<String> signUp(@RequestBody Customer customer)
	{
		customerRepository.save(customer);
		return ResponseEntity.status(HttpStatus.CREATED).body("create success");
	}
	
	@GetMapping(path = "/table")
	public ArrayList<ArrayList<ReturnInfo>> Table()
	{
		ArrayList<ArrayList<ReturnInfo>> tables = caculator.generateTable();
		return tables;
	}
}
