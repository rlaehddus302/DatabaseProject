package com.example.databaseProject.TDO;

import java.util.ArrayList;

public class MyTable {
	private String name;
	private ArrayList<ReturnInfo> body;
	
	public MyTable(String name, ArrayList<ReturnInfo> body) {
		this.name = name;
		this.body = body;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<ReturnInfo> getBody() {
		return body;
	}
	public void setBody(ArrayList<ReturnInfo> body) {
		this.body = body;
	}
	
	
}
