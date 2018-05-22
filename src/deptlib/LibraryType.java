package deptlib;

public class LibraryType {
	// name of database table where library users are stored
	public static final String TABLE = "library_types";
	
	// column labels
	public static final String TYPE = "type";

	private String type;

	public LibraryType(String type) {
		this.setType(type);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
