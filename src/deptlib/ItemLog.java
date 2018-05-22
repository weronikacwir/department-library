package deptlib;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ItemLog")
public class ItemLog extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ItemLog() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		// get the item and its log
		String id = request.getParameter("id");
		LibraryItem item;
		List<LogEntry> log;
		try {
			item = Library.getItem(id);
			log = Library.getItemLog(id);
		} catch (SQLException e) {
			throw new ServletException(e);
		}
		
		// pass it to the jsp
		request.setAttribute("item", item);
		request.setAttribute("log", log);
		request.getRequestDispatcher("/WEB-INF/ItemLog.jsp").forward(request, response);	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}

}
