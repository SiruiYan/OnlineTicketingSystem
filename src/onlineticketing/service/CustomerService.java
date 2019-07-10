package onlineticketing.service;

import java.util.ArrayList;

import onlineticketing.datasource.OrderMapper;
import onlineticketing.datasource.UserMapper;
import onlineticketing.domain.Order;
import onlineticketing.domain.User;

public class CustomerService {

	/**
	 * View the informaiton of all user in the database
	 * @return  the list of all the user in the database
	 */
	public ArrayList<User> viewAllUserInformation(){
		ArrayList<User> userList = UserMapper.findAllUsers();
		return userList;
	}
	
	
	/**
	 * View the information of all orders in the database
	 * @return  the list of all the orders in the database
	 */
	public ArrayList<Order> viewAllOrders(){
		ArrayList<Order> orderList = OrderMapper.findAllOrders();
		return orderList;
	}
	
}
