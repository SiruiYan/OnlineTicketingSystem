package onlineticketing.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import onlineticketing.onlineticketing.Params;
import onlineticketing.service.AuthenticationService;

/**
 * Servlet implementation class LogInControllerServlet
 */
@WebServlet("/LogInControllerServlet")
public class LogInControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public LogInControllerServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action.equals("login")) {
			this.login(request, response);
		}
	}
	
	private void login(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String result = null;
		AuthenticationService authenticationService = new AuthenticationService();
		int permission = authenticationService.getPermission(username, password);
		
		if (permission == Params.ADMIN_PERMISSION) {
			System.out.println("Admin");
			result = "admin";
		} else if (permission == Params.CUSTOMER_PERMISSION) {
			System.out.println("Customer");
			result = "customer";
		} else {
			System.out.println("Error");
			result = "error";
		}
		response.getWriter().write(result);
		response.flushBuffer();
	}

}
