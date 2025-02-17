package com.example.databaseProject.Information.CustomerInfo;

import com.example.databaseProject.Information.CourseInfo.Session;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class MyTimeTableSession {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "session_id")
	private Session session;
	
	@ManyToOne
	@JoinColumn(name = "tableName")
	private MyTimeTableName name;

	public MyTimeTableSession() {};
	
	public MyTimeTableSession(Session session, MyTimeTableName name) {
		this.session = session;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public MyTimeTableName getName() {
		return name;
	}

	public void setName(MyTimeTableName name) {
		this.name = name;
	}
	
	
}
