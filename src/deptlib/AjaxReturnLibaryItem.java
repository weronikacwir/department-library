package deptlib;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AjaxReturnLibaryItem")
public class AjaxReturnLibaryItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AjaxReturnLibaryItem() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		if (Library.isPermittedAction(request)) {	
			
			String id = request.getParameter("id");
			Date date = new Date(System.currentTimeMillis());

			try {
				Library.returnItem(id, date);
			} catch (SQLException e) {
				throw new ServletException(e);
			}
		}
		else {
			request.getRequestDispatcher("/WEB-INF/NoPermission.jsp").forward(request, response);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		doGet(request, response);
	}

}
