package onlineticketing.controller;

import java.io.IOException;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import onlineticketing.domain.Order;
import onlineticketing.service.CustomerService;

/**
 * Servlet implementation class ViewOrderControllerServlet
 */
@WebServlet("/ViewOrderControllerServlet")
public class ViewOrderControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewOrderControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CustomerService customerService = new CustomerService();
		ArrayList<Order> orders = customerService.viewAllOrders();
		/*
		LocalDate localDate = LocalDate.now();
		Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Order order = new Order(0,100,date,0,1,"information");
		ArrayList<Order> orders = new ArrayList<Order>();
		orders.add(order);*/
		
		JSONArray listArray=JSONArray.fromObject(orders);
		
		response.getWriter().write(listArray.toString());
		response.flushBuffer();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
