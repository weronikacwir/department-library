package deptlib;

public class LibraryItem {

//	// types of library items
//	public static final String BOOK = "Book";
//	public static final String TABLET = "Tablet";
//	public static final String[] TYPES = {BOOK, TABLET};
	
	// name of database table where the items are stored
	public static final String TABLE = "library_items";
	
	// column labels 
	public static final String ID = "id";
	public static final String TYPE = "type";
	public static final String NAME = "name";
	public static final String ADDITIONAL_INFO = "additional_info";
	public static final String AVAILABLE = "available";

	private int id;
	private String type;
	private String name;
	private String additionalInfo;
	private boolean available = true;

	public LibraryItem(int id, String type, String name, String additionalInfo, boolean available) {
		this.id = id;
		this.type = type;
		this.name = name;
		this.additionalInfo = additionalInfo;
		this.available = available;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public boolean isAvailable() {
		return available;
	}

	public int getId() {
		return id;
	}
}
