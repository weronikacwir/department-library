package deptlib;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/DisplayItems", loadOnStartup = 2)
public class DisplayItems extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public DisplayItems() {
		super();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {

		super.init(config);
		
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch( ClassNotFoundException e) {
            throw new ServletException( e );
        }
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			List<LibraryItem> items = Library.getItemList();
			request.setAttribute("items", items);
		}
		catch (SQLException sqle) {
			throw new ServletException(sqle);
		}
		
		request.getRequestDispatcher("/WEB-INF/DisplayItems.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
