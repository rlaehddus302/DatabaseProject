package com.example.databaseProject.generateTimeTable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.databaseProject.Information.CourseInfo.ClassTimeAndLocation;
import com.example.databaseProject.Information.CourseInfo.Course;
import com.example.databaseProject.Information.CourseInfo.CourseRepositery;
import com.example.databaseProject.Information.CourseInfo.Session;
import com.example.databaseProject.TDO.ReceivedValue;
import com.example.databaseProject.TDO.ReturnInfo;
import com.example.databaseProject.TDO.TimeBitmask;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.regex.*;


@Service
public class CaculatorService {
	@Autowired
	private CourseRepositery courseRepository;
	private String[] courses = {};
	private ArrayList<ArrayList<ReturnInfo>> timeTable;
	private TimeBitmask[] freePeriod = new TimeBitmask[5];
	private ReceivedValue value;
	private static final Logger logger = LoggerFactory.getLogger(CaculatorService.class);
	
	public void setValue(ReceivedValue value) {
		this.value = value;
		setFreePeriod(value.getFreePeriod());
	}

	public void setFreePeriod(int[][] freePeriod) {

        for (int i = 0; i < freePeriod.length; i++) 
        {
            this.freePeriod[i] = new TimeBitmask();
			BigInteger bitmask = BigInteger.ZERO;
            for(int j=0;j<freePeriod[i].length;j++)
            {
            	if(freePeriod[i][j]==1)
            	{
            		for(int k=j*6;k<j*6+6;k++)
            		{
                    	bitmask = bitmask.setBit(k);            		
                    }
            	}
            }
        	this.freePeriod[i].setTime(bitmask);
        	this.freePeriod[i].setWeek((int)Math.pow(2, i));
        	bitmask = BigInteger.ONE;
        }
	}

	public ArrayList<ArrayList<ReturnInfo>> generateTable()
	{
		timeTable = new ArrayList<ArrayList<ReturnInfo>>();
		if(courses.length==0)
		{
			return timeTable;
		}
		Optional<Course> courseOption = courseRepository.findById(courses[0]);
		Course course = courseOption.get();
		List<Session> sessions = course.getSession();
		sessions.stream().forEach(session -> {
			ReturnInfo returnInfo = makeReturnInfo(session);
			if(IsNotFreePeriodOverlap(returnInfo))
			{
				ArrayList<ReturnInfo> subjects = new ArrayList<ReturnInfo>();
				subjects.add(returnInfo);
				timeTable.add(subjects);
			}
		});
		for(int i=1;i<courses.length;i++)
		{
			courseOption = courseRepository.findById(courses[i]);
			course = courseOption.get();
			sessions = course.getSession();
			ArrayList<ArrayList<ReturnInfo>> timeTableCopy = new ArrayList<ArrayList<ReturnInfo>>();
			for(Session session : sessions)
			{
				ReturnInfo returnInfo = makeReturnInfo(session);
				if(IsNotFreePeriodOverlap(returnInfo))
				{
					timeTable.stream().forEach(subjects1 -> {
						ArrayList <ReturnInfo> subjectsCopy = (ArrayList<ReturnInfo>)subjects1.clone();
						if(subjects1.stream().allMatch(subject -> {return IsNotAnyScheduleOverlap(subject,returnInfo);}))
						{
							subjectsCopy.add(returnInfo);
							timeTableCopy.add(subjectsCopy);
						}
					});
				}
			}
			timeTable= timeTableCopy;
		}
		conditionCheck(timeTable);
		return timeTable;
	}
	
	public void conditionCheck(ArrayList<ArrayList<ReturnInfo>> timeTable)
	{
		double consecutiveClassTime = value.getConsecutiveClassTime();
		double intervalTime = value.getIntervalTime();
		boolean check;
		BigInteger dayTable[] = new BigInteger [17];
		int week[] = {1,2,4,8,16};
		for(int i=0;i<5;i++)
		{
			dayTable[week[i]] = BigInteger.ZERO;
		}
		Iterator<ArrayList<ReturnInfo>> iterator = timeTable.iterator();
		while(iterator.hasNext())
		{
			check = true;
			for(int i=0;i<5;i++)
			{
				dayTable[week[i]] = BigInteger.ZERO;
			}
			ArrayList<ReturnInfo> sessions = iterator.next();
			sessions.stream().forEach(session -> {
				session.getTimeBitmasks().stream().forEach(TNL ->{
					int index = TNL.getWeek();
					dayTable[index] = dayTable[index].or(TNL.getTime());
				});
			});
			if(consecutiveClassTime!=0)
			{
				for(int i=0;i<5;i++)
				{
					String[] onesBlocks = dayTable[week[i]].toString(2).split("0+");
					List<String> list = Arrays.asList(onesBlocks);
					if(list.stream().anyMatch(t -> {return t.length()>consecutiveClassTime*12;}))
					{
						iterator.remove();
						check = false;
						break;
					}
				}
			}
			if(check && intervalTime != 0)
			{
	        	logger.info("간격체크");
				for(int i=0;i<5;i++)
				{
					Pattern pattern = Pattern.compile("1(0+)1");
			        Matcher matcher = pattern.matcher(dayTable[week[i]].toString(2));
			        List<String> list = new ArrayList<String>();
			        while (matcher.find()) {
			        	logger.info("나온 값입니다: "+matcher.group(1));
			        	list.add(matcher.group(1)); 
			        }
			        if(list.stream().anyMatch(t -> {return t.length()>intervalTime*12;}))
					{
						iterator.remove();
						break;
					}
				}
			}
		}
		logger.info(dayTable[1].toString(2));
		logger.info(dayTable[2].toString(2));
		logger.info(dayTable[4].toString(2));
		logger.info(dayTable[8].toString(2));
		logger.info(dayTable[16].toString(2));
	}
	
	public void setCourses(String[] courses) {
		this.courses = courses;
	}

	private boolean IsNotFreePeriodOverlap(ReturnInfo returnInfo)
	{
		List<TimeBitmask> comparisonTarget = returnInfo.getTimeBitmasks();
		for(TimeBitmask day : freePeriod)
		{
			if(isScheduleOverlap(day, comparisonTarget))
			{
				return false;
			}
		}
		return true;
	}
	
	private boolean IsNotAnyScheduleOverlap(ReturnInfo subject, ReturnInfo returnInfo )
	{
		List<TimeBitmask> times = subject.getTimeBitmasks();
		for(TimeBitmask time : returnInfo.getTimeBitmasks())
		{
			if(isScheduleOverlap(time, times))
			{
				return false;
			}
		}
		return true;
	}
	
	private boolean isScheduleOverlap(TimeBitmask time, List<TimeBitmask> times)
	{
		for(TimeBitmask time1 : times)
		{
			if((time1.getWeek() & time.getWeek()) !=0)
			{
				if(!(time1.getTime().and(time.getTime()).equals(BigInteger.ZERO)))
				{
					return true;
				}
			}
		}
		return false;
		
	}
	
	public ReturnInfo makeReturnInfo(Session session)
	{
		List<ClassTimeAndLocation> TNL = session.getClassTimeAndLocation();
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setSession(session);
		returnInfo.setName(session.getCourse().getName());
		returnInfo.setArea(session.getCourse().getArea());
		returnInfo.setCurriculum(session.getCourse().getCurriculum());;
		TNL.stream().forEach(t -> {
			TimeBitmask timeBitmask = new TimeBitmask();
			timeBitmask.setTime(setTimeRange(t.getStart(), t.getEnd()));
			timeBitmask.setWeek(setWeek(t.getWeek()));
			returnInfo.add(timeBitmask);
		});
		return returnInfo;
	}
	
	 private int timeToIndex(int time) 
	 {
	        int hours = time / 100;
	        int minutes = time % 100;
	        return ((hours - 8) * 12) + (minutes / 5);
	 }
	 
	 private BigInteger setTimeRange(int start, int end) 
	 	{
			BigInteger bitmask = BigInteger.ZERO;
	        int startIndex = timeToIndex(start);
	        int endIndex = timeToIndex(end);

	        for (int i = startIndex; i < endIndex; i++) {
	            bitmask = bitmask.setBit(i);
	        }
	        return bitmask;
	    }
	 
	 private int setWeek(String day) 
	 	{
	        if(day.equals("월"))
	        {
	        	return 1;
	        }
	        if(day.equals("화"))
	        {
	        	return 2;
	        }
	        if(day.equals("수"))
	        {
	        	return 4;
	        }
	        if(day.equals("목"))
	        {
	        	return 8;
	        }
	        if(day.equals("금"))
	        {
	        	return 16;
	        }
	        System.out.println("오류");
	        return 0;
	    }
	
}
