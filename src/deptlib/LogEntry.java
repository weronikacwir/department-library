package deptlib;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class LogEntry {

	// database table where the log is stored
	public static final String TABLE = "library_log";

	// table column labels
	public static final String ID = "id";
	public static final String ITEM = "item_id";
	public static final String CIN = "cin";
	public static final String NAME = "name";
	public static final String BORROWED = "borrowed";
	public static final String DUE = "due";
	public static final String RETURNED = "returned";
	
	public static final DateFormat df = new SimpleDateFormat("M/d/yyyy");
	
	private int id;
	private int item;
	private int cin;
	private String name;
	private String borrowed;
	private String due;
	private String returned;
	
	public LogEntry(int id, int item, int cin, String name, 
			        Date borrowed, Date due, Date returned) {
		this.id = id;
		this.item = item;
		this.cin = cin;
		this.name = name;
		this.borrowed = (borrowed == null)? null : df.format(borrowed);
		this.due = (due == null)? null : df.format(due);
		this.returned = (returned == null)? null : df.format(returned);
	}

	public int getId() {
		return id;
	}

	public int getItem() {
		return item;
	}

	public int getCin() {
		return cin;
	}

	public String getName() {
		return name;
	}

	public String getBorrowed() {
		return borrowed;
	}

	public String getDue() {
		return due;
	}

	public String getReturned() {
		return returned;
	}
}
