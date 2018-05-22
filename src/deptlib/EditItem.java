package deptlib;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/EditItem")
public class EditItem extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public EditItem() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// get the item to be edited
		LibraryItem item;
		List<String> types;
		try {
			item = Library.getItem(request.getParameter("id"));
			types = Library.getItemTypes();
		} catch (SQLException e) {
			throw new ServletException(e);
		}
		
		// pass it to the jsp
		request.setAttribute("item", item);
		request.setAttribute("types", types);
		request.getRequestDispatcher("/WEB-INF/EditItem.jsp").forward(request, response);
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		if (Library.isPermittedAction(request)) {
			// get the user input
			String id = request.getParameter("id");
			String type = request.getParameter("type");
			String name = request.getParameter("name");
			String info = request.getParameter("info");
			
			// update the storage
			try {
				Library.editItem(id, type, name, info);
			} catch (SQLException e) {
				throw new ServletException(e);
			}

			// send the user back to the library catalog display
			response.sendRedirect("DisplayItems");
		}
		else {
			request.getRequestDispatcher("/WEB-INF/NoPermission.jsp").forward(request, response);
		}
	}

}
