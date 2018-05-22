package deptlib;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LibraryLogin")
public class LibraryLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LibraryLogin() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		request.getRequestDispatcher("/WEB-INF/LibraryLogin.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
				
		String username = request.getParameter("username").trim();
		String pw = request.getParameter("pw").trim();
		
		try {
			if (Library.validateUser(username, pw))	{
				request.getSession(true).setAttribute("user", username);
			}
		} catch (SQLException e) {
			throw new ServletException(e);
		}
		
		response.sendRedirect("DisplayItems");
	}

}
