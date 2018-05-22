package deptlib;

public class LibraryUser {
	// name of database table where library users are stored
	public static final String TABLE = "library_users";
	
	// column labels
	public static final String NAME = "name";
	public static final String PASSWORD = "password";
	
	private String username;
	private String password;
	
	public LibraryUser(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}
	
	public boolean validate(String pw) {
		return password.equals(pw.trim());
	}

}
