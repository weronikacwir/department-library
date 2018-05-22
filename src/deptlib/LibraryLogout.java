package deptlib;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LibraryLogout")
public class LibraryLogout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LibraryLogout() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		// logout the user by clearing the session
		request.getSession().invalidate();
		
		// redirect to login
		response.sendRedirect("DisplayItems");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}


}
