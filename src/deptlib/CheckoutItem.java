package deptlib;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CheckoutItem")
public class CheckoutItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CheckoutItem() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		// get the item
		String id = request.getParameter("id");
		LibraryItem item;
		try {
			item = Library.getItem(id);
		} catch (SQLException e) {
			throw new ServletException(e);
		}
		
		// get current date
		Date borrowed = new Date(System.currentTimeMillis());
		
		// pass both to the display
		request.setAttribute("item", item);
		request.setAttribute("borrowed", LogEntry.df.format(borrowed));
		request.getRequestDispatcher("/WEB-INF/CheckoutItem.jsp").forward(request, response);			
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		if (Library.isPermittedAction(request)) {	
			
			// get the user input
			String id = request.getParameter("id");
			String borrowed = request.getParameter("borrowed");
			String due = request.getParameter("due");
			String cin = request.getParameter("cin");
			String name = request.getParameter("name");
			
			// convert the dates from M/d/yyyy to yyyy-MM-dd format
			Date borrowedDate = null;
			Date dueDate = null;
			try {
				java.util.Date date = (java.util.Date) LogEntry.df.parse(borrowed);
				borrowedDate = new java.sql.Date(date.getTime());
				if (due != null && due.trim().length() != 0) {
					date = (java.util.Date) LogEntry.df.parse(due);
					dueDate = new java.sql.Date(date.getTime()); 
				}	
			} catch (ParseException e)  {
				throw new ServletException(e);
			}

			// update the storage
			try {
				Library.checkOutItem(id, cin, name, borrowedDate, dueDate);
			} catch (SQLException e) {
				throw new ServletException(e);
			}

			response.sendRedirect("ItemLog?id=" + id);
		}
		else {
			request.getRequestDispatcher("/WEB-INF/NoPermission.jsp").forward(request, response);
		}
	}

}
