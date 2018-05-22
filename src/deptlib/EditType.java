package deptlib;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/EditType")
public class EditType extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EditType() {
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
			String originalType = request.getParameter("originalType");
			String editedType = request.getParameter("editedType");

			// updated the edited type in the library
			try {
				Library.editItemType(originalType, editedType);
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
