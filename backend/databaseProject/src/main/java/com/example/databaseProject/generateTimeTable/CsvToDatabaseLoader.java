package com.example.databaseProject.generateTimeTable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import com.example.databaseProject.Information.AcademicTerm;
import com.example.databaseProject.Information.CourseInfo.ClassTimeAndLocation;
import com.example.databaseProject.Information.CourseInfo.Course;
import com.example.databaseProject.Information.CourseInfo.Session;

@Service
public class CsvToDatabaseLoader {
	
	private final int periods[][] = {
		    { 800,  830}, { 830,  900},
		    { 900,  930}, { 930, 1000},
		    {1000, 1030}, {1030, 1100},
		    {1100, 1130}, {1130, 1200},
		    {1200, 1230}, {1230, 1300},
		    {1300, 1330}, {1330, 1400},
		    {1400, 1430}, {1430, 1500},
		    {1500, 1530}, {1530, 1600},
		    {1600, 1630}, {1630, 1700},
		    {1700, 1730}, {1730, 1800},
		    {1800, 1825}, {1825, 1850},
		    {1850, 1915}, {1915, 1940},
		    {1940, 2005}, {2005, 2030},
		    {2040, 2105}, {2105, 2130},
		    {2130, 2155}, {2155, 2220},
		    {2220, 2245}, {2245, 2310}
		};
	
	
	public AcademicTerm insertDataFromCsv(CSVParser csvParser, AcademicTerm academicTerm)
	{
		String prevName = "";
		Course course = new Course();
		ArrayList<Course> courses = new ArrayList<Course>();
		academicTerm.setCourse(courses);
        for (CSVRecord record : csvParser) {
        	String curriculum = record.get("교과과정");
        	String area = record.get("영역");
            String time = record.get("요일/교시");
            String sessionCODE = record.get("학수번호");
            String name = record.get("교과목명");
            String professor = record.get("담당교원");
            String location = record.get("강의실");
            String credit = record.get("학점");
            String remarks = record.get("비고");
            if(!prevName.equals(name))
            {
            	prevName = name;
            	ArrayList<Session> sessions = new ArrayList<Session>();
            	course = new Course(name, (int)Double.parseDouble(credit), sessionCODE.split("-")[0], curriculum, area, academicTerm, sessions);
            	courses.add(course);
            }
            if(!time.equals(""))
            {
                Session session = new Session(course, null, remarks, professor, sessionCODE);
                ArrayList<ClassTimeAndLocation> tnls = convertPeriodToTNL(time,location, session);
                session.setClassTimeAndLocation(tnls);
                course.getSession().add(session);
            }
        }
        return academicTerm;
	}
	
	public ArrayList<ClassTimeAndLocation> convertPeriodToTNL(String time, String location, Session session)
	{
        Pattern pattern = Pattern.compile("(월|화|수|목|금|토|일)|\\d+(\\.\\d+)?(?=교시)");
        Matcher matcher = pattern.matcher(time);
        ArrayList<ClassTimeAndLocation> tnls = new ArrayList<ClassTimeAndLocation>();
        int count = -1;
        while (matcher.find()) {
        	++count;
            String match = matcher.group();
            if (match.matches("(월|화|수|목|금|토|일)")) 
            {
            	ClassTimeAndLocation tnl = new ClassTimeAndLocation();
            	tnl.setWeek(match);
            	tnl.setLocation(location.trim());
            	tnl.setSession(session);
            	tnls.add(tnl);
            } 
            else 
            {
            	double period = Double.parseDouble(match.trim());
            	int index = (int) (period*2);
            	for(ClassTimeAndLocation tnl : tnls)
            	{
                	if(count % 2 == 0)
                	{
                		if(tnl.getStartTime() == -1)
                		{
                			
                			tnl.setStartTime(periods[index][0]);
                		}
                	}
                	else
                	{
                		if(tnl.getEndTime() == -1)
                		{
                			tnl.setEndTime(periods[index][1]);
                		}
                	}
            	}
            }
        }
        return tnls;
	}
	
}
