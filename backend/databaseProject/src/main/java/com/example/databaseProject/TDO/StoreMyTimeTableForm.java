package com.example.databaseProject.TDO;

import java.util.ArrayList;

public class StoreMyTimeTableForm {
	private String tableName;
	private ArrayList<ReturnInfo> body;
	
	public StoreMyTimeTableForm(String tableName, ArrayList<ReturnInfo> body) {
		super();
		this.tableName = tableName;
		this.body = body;
	}

	public String getTableName() {
		return tableName;
	}

	public ArrayList<ReturnInfo> getBody() {
		return body;
	}
	
}
