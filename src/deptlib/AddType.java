package deptlib;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/AddType")
public class AddType extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddType() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		List<String> types;
		try {
			types = Library.getItemTypes();
		} catch (SQLException e) {
			throw new ServletException(e);
		}
		
		request.setAttribute("types", types);
		request.getRequestDispatcher("/WEB-INF/AddAndEditType.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		if (Library.isPermittedAction(request)) {
			// get the user input
			String newType = request.getParameter("newType");

			// add the new type to the library
			try {
				Library.addItemType(newType);
			} catch (SQLException e) {
				throw new ServletException(e);
			}

			// send the user back to the library catalog page
			response.sendRedirect("EditType");
		}
		else {
			request.getRequestDispatcher("/WEB-INF/NoPermission.jsp").forward(request, response);
		}
	}

}
