package deptlib;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AddItems")
public class AddItems extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public AddItems() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		List<String> types;
		try {
			types = Library.getItemTypes();
		} catch (SQLException e) {
			throw new ServletException(e);
		}
		
		request.setAttribute("types", types);
		request.getRequestDispatcher("/WEB-INF/AddItems.jsp").forward(request, response);
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		if (Library.isPermittedAction(request)) {
			// get the user input
			String type = request.getParameter("type");
			String name = request.getParameter("name");
			String additionalInfo = request.getParameter("info");
			int num = Integer.parseInt(request.getParameter("copies"));

			// add the items to the library
			try {	
				for (int i = 0; i < num; i++) {
					Library.addItem(type, name, additionalInfo);
				}
			} catch (SQLException e) {
					throw new ServletException(e);
			}

			// send the user back to the library catalog page
			response.sendRedirect("DisplayItems");
		}
		else {
			request.getRequestDispatcher("/WEB-INF/NoPermission.jsp").forward(request, response);
		}
	}

}
