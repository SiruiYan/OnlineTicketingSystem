package onlineticketing.domain;

import java.util.ArrayList;

public class Customer extends User{
	private ArrayList<Order> orders;

	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ArrayList<Order> getOrders() {
		return orders;
	}

	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}
	
}
