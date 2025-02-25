package com.example.databaseProject.generateTimeTable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.databaseProject.ExceptionHandle.CustomException.CourseNotFoundException;
import com.example.databaseProject.Information.AcademicTerm;
import com.example.databaseProject.Information.AcademicTermRepo;
import com.example.databaseProject.Information.CourseInfo.ClassTimeAndLocation;
import com.example.databaseProject.Information.CourseInfo.ClassTimeAndLocationRepository;
import com.example.databaseProject.Information.CourseInfo.Course;
import com.example.databaseProject.Information.CourseInfo.CourseRepositery;
import com.example.databaseProject.Information.CourseInfo.Session;
import com.example.databaseProject.Information.CourseInfo.SessionRepository;
import com.example.databaseProject.Information.CustomerInfo.Customer;
import com.example.databaseProject.Information.CustomerInfo.CustomerRepository;
import com.example.databaseProject.Information.CustomerInfo.MyTimeTableName;
import com.example.databaseProject.Information.CustomerInfo.MyTimeTableNameRepository;
import com.example.databaseProject.Information.CustomerInfo.MyTimeTableSession;
import com.example.databaseProject.Information.CustomerInfo.MyTimeTableSessionRepository;
import com.example.databaseProject.TDO.MyTable;
import com.example.databaseProject.TDO.ReceivedValue;
import com.example.databaseProject.TDO.ReturnInfo;
import com.example.databaseProject.TDO.StoreMyTimeTableForm;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@RestController
public class TimeTable {
	@Autowired
	CourseRepositery courseRepository;
	@Autowired
	CaculatorService caculator;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	MyTimeTableSessionRepository myTimeTableSessionRepository;
	@Autowired
	MyTimeTableNameRepository myTimeTableNameRepository;
	@Autowired
	SessionRepository sessionRepository;
	@Autowired
	ClassTimeAndLocationRepository classTimeNLocationRepo;
	@Autowired
	AcademicTermRepo academicTermRepo;
	@Autowired
	CsvToDatabaseLoader csvToDatabaseLoader;
	
	@PostMapping(path = "/basicOauth")
	public String login()
	{
		return "Success";
	}
	@PostMapping(path = "/exit")
	public String logOut(HttpSession session)
	{
		return "Success";
	}
	@GetMapping(path = "/course")
	public List<Course> course(@RequestParam int year, int semester)
	{
		List<Course> courses = new ArrayList<Course>();
		Optional<AcademicTerm> academicTerm = academicTermRepo.findByYearAndSemester(year,semester);
		if(academicTerm.isPresent())
		{
			courses = academicTerm.get().getCourse();
		}
		return courses;
	}
	@PostMapping(path = "/courseSearch")
	public List<Course> search(@RequestBody String name, @RequestParam int year, int semester)
	{
		name = name.substring(1, name.length() - 1);
		Optional<AcademicTerm> academicTerm = academicTermRepo.findByYearAndSemester(year,semester);
		if(academicTerm.isEmpty())
		{
			List<Course> courses = new ArrayList<Course>();
			return courses;
		}
		return courseRepository.searchCourseName(name,academicTerm.get().getId());
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
		System.out.println(customer.getId());
		String encodedPassword = passwordEncoder.encode(customer.getPassword());
		Customer newCustomer = new Customer(customer.getId(), customer.getStudentNumber(), encodedPassword, customer.getName(), "ROLE_USER");
		customerRepository.save(newCustomer);
		return ResponseEntity.status(HttpStatus.CREATED).body("create success");
	}
	@PostMapping(path = "/idDuplicate")
	public ResponseEntity<String> checkIdDuplicate(@RequestBody String id)
	{
		Optional<Customer> exist = customerRepository.findById(id);
		if(exist.isPresent())
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("already exist");
		}
		else
		{
			return ResponseEntity.ok("ok");
		}
	}
	@GetMapping(path = "/table")
	public ArrayList<ArrayList<ReturnInfo>> Table()
	{
		ArrayList<ArrayList<ReturnInfo>> tables = caculator.generateTable();
		return tables;
	}
	
	@PostMapping(path = "/storeMyTimeTable")
	public ResponseEntity<String> storeMyTimeTable(@RequestBody StoreMyTimeTableForm data,@AuthenticationPrincipal UserDetails userDetails)
			throws UsernameNotFoundException
	{
		String userId = userDetails.getUsername();
		Optional<Customer> customer = customerRepository.findById(userId);
		String tableName = data.getTableName();
		if(myTimeTableNameRepository.findById(tableName).isPresent())
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Already Exist");
		}
		MyTimeTableName myTimeTableName = new MyTimeTableName(tableName,customer.get());
		myTimeTableNameRepository.save(myTimeTableName);
		ArrayList<ReturnInfo> body = data.getBody();
		body.stream().forEach(t -> {
			Session session = t.getSession();
			MyTimeTableSession myTimeTableSession = new MyTimeTableSession(session, myTimeTableName);
			myTimeTableSessionRepository.save(myTimeTableSession);
		});
		return ResponseEntity.status(HttpStatus.CREATED).body("Created Success");
	}
	
	@GetMapping(path = "/myTable")
	public ArrayList<MyTable> myTable(@AuthenticationPrincipal UserDetails userDetails)
	{
		ArrayList<MyTable> myTables = new ArrayList<MyTable>();
		String id = userDetails.getUsername();
		Customer customer = customerRepository.findById(id).get();
		List<MyTimeTableName> tableNames = customer.getTableNames();
		tableNames.stream().forEach(tableName -> {
			List<MyTimeTableSession> mySessions = tableName.getSessions();
			ArrayList<ReturnInfo> returnInfoes = new ArrayList<ReturnInfo>();
			mySessions.stream().forEach(mySession -> {
				Session session = mySession.getSession();
				ReturnInfo returnInfo = caculator.makeReturnInfo(session);
				returnInfoes.add(returnInfo);
			});
			MyTable myTable = new MyTable(tableName.getName(), returnInfoes);
			myTables.add(myTable);
		});
		return myTables;
	}
	
	@PatchMapping(path = "/updateName")
	@Transactional
	public ResponseEntity<String> updateName(@RequestBody Map<String, String> requestData)
	{
		String oldName = requestData.get("oldName");
	    String newName = requestData.get("newName");
	    if(myTimeTableNameRepository.findById(newName).isPresent())
	    {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("already exist");
	    }
	    MyTimeTableName myTable = myTimeTableNameRepository.findById(oldName).get();
	    MyTimeTableName changeName = new MyTimeTableName(newName, myTable.getCustomer());
	    myTimeTableNameRepository.save(changeName);
	    myTimeTableSessionRepository.updateTableNameByName(oldName, newName);
	    myTimeTableNameRepository.deleteById(oldName);
		return ResponseEntity.status(HttpStatus.CREATED).body("created success");
	}
	
	@DeleteMapping(path = "/deleteTable")
	public void deleteTable(@RequestBody String name)
	{
		myTimeTableNameRepository.deleteById(name);
	}
	
	@PutMapping(path = "/updateCourse")
	@Transactional
	public void updateCourse(@RequestBody Course course)
	{
		Course existCourse = courseRepository.findById(course.getId()).get();
		existCourse.setName(course.getName());
		existCourse.setCode(course.getCode());
		existCourse.setArea(course.getArea());
		existCourse.setCredit(course.getCredit());
		existCourse.setCurriculum(course.getCurriculum());
		courseRepository.save(existCourse);
		List<Session> sessions = existCourse.getSession();
		sessions.stream().forEach(session -> {
			String sessionCode = session.getSessionCODE();
			session.setSessionCODE(existCourse.getCode()+"-"+sessionCode.split("-")[1]);
			sessionRepository.save(session);
		});
	}
	
	@DeleteMapping(path = "/deleteCourse")
	@Transactional
	public void deleteCourse(@RequestBody String index)
	{
		int id = Integer.parseInt(index);
		courseRepository.deleteById((long) id);
	}
	
	@PostMapping(path = "/adminCourse")
	public List<Course> adminCourse(@RequestBody AcademicTerm requestData)
	{
		List<Course> courses = new ArrayList<Course>();
		Optional<AcademicTerm> academicTerm = academicTermRepo.findByYearAndSemester(requestData.getAcademic_year(), requestData.getSemester());
		if(academicTerm.isPresent())
		{
			courses = academicTerm.get().getCourse();
		}
		return courses;
	}
	
	@GetMapping(path = "/adminCourse/{id}")
	public Course getSession(@PathVariable long id)
	{
		Course course =courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException("course Not Exist"));
		return course;
	}
	
	@PutMapping(path = "/updateSession")
	@Transactional
	public void updateSession(@RequestBody Session session)
	{
		Session existSession = sessionRepository.findById(session.getId()).get();
		existSession.setProfessor(session.getProfessor());
		existSession.setRemarks(session.getRemarks());
		existSession.setSessionCODE(session.getSessionCODE());
		sessionRepository.save(existSession);
		List<ClassTimeAndLocation> tnls = existSession.getClassTimeAndLocation();
		for(int i=0;i<tnls.size();i++)
		{
			ClassTimeAndLocation tnl = tnls.get(i);
			tnl.setEndTime(session.getClassTimeAndLocation().get(i).getEndTime());
			tnl.setLocation(session.getClassTimeAndLocation().get(i).getLocation());
			tnl.setStartTime(session.getClassTimeAndLocation().get(i).getStartTime());
			tnl.setWeek(session.getClassTimeAndLocation().get(i).getWeek());
			classTimeNLocationRepo.save(tnl);
		}
	}
	
	@DeleteMapping(path = "/deleteSession")
	public void deleteSession(@RequestBody String index)
	{
		int id = Integer.parseInt(index);
		sessionRepository.deleteById((long) id);
	}
	
	@PostMapping(path = "/csv")
	@Transactional
    public ResponseEntity<String> uploadFile(@RequestParam("csv") MultipartFile file, @RequestParam("academic_year") int academic_year, 
    		@RequestParam("semester") int semester) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("파일이 없습니다.");
        }
        String fileName = file.getOriginalFilename();
        if (fileName == null || !fileName.toLowerCase().endsWith(".csv")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CSV 파일만 업로드 가능합니다.");
        }
        
        Optional<AcademicTerm> academicTerm = academicTermRepo.findByYearAndSemester(academic_year, semester);
        if(academicTerm.isPresent())
        {
        	academicTermRepo.deleteById(academicTerm.get().getId());
        }
        
        AcademicTerm makeTerm = new AcademicTerm(academic_year, semester);
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build());
        
        makeTerm = csvToDatabaseLoader.insertDataFromCsv(csvParser, makeTerm);
        
        academicTermRepo.save(makeTerm);
        
        return ResponseEntity.status(HttpStatus.CREATED).body("생성되었습니다.");
    }
}
