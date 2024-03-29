package onlineticketing.datasource;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import onlineticketing.domain.DomainObject;
import onlineticketing.domain.Order;

public class OrderMapper implements DataMapper{

	@Override
	public void insert(DomainObject obj) {
		// TODO Auto-generated method stub
		assert !(obj instanceof Order) : "obj is not a order object";
		Order order = (Order)obj;
		
		Order targetOrder = new Order();
		IdentityMap<Order> orderMap = IdentityMap.getInstance(targetOrder);
		
		String createOrderString = "INSERT INTO ONLINETICKETING.ORDERS "
				+ "(STATUS, CREATETIME, PAYMENT, CUSTOMERID, TICKETINFO) "
				+ "VALUES (?, ?, ?, ?, ?)";
		PreparedStatement createStatement = DBConnection.prepare(createOrderString);
		
		try {
			createStatement.setInt(1, order.getStatus());
			createStatement.setObject(2, order.getCreateTime());
			createStatement.setFloat(3, order.getPayment());
			createStatement.setInt(4, order.getCustomerId());
			createStatement.setString(5, order.getTicketInformation());
			createStatement.execute();
			System.out.println(createStatement.toString());
			
			DBConnection.close(createStatement);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		orderMap.put(id, order);
	}

	@Override
	public void update(DomainObject obj) {
		// TODO Auto-generated method stub
		assert !(obj instanceof Order) : "obj is not a order object";
		Order order = (Order)obj;
		
		Order targetOrder = new Order();
		IdentityMap<Order> orderMap = IdentityMap.getInstance(targetOrder);
		
		String updateOrderString = "UPDATE ONLINTICKETING.ORDERS SET STATUS = ? "
				+ "WHERE ORDERID = " + order.getOrderId();
		PreparedStatement updateStatement = DBConnection.prepare(updateOrderString);
		
		try {
			updateStatement.setInt(1, order.getStatus());
			updateStatement.execute();
			System.out.println(updateStatement.toString());
			
			DBConnection.close(updateStatement);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		orderMap.put(order.getId(), order);
	}

	@Override
	public void delete(DomainObject obj) {
		// TODO Auto-generated method stub
		assert !(obj instanceof Order) : "obj is not a order object";
		Order order = (Order)obj;
		
		Order targetOrder = new Order();
		IdentityMap<Order> orderMap = IdentityMap.getInstance(targetOrder);
		
		String deleteOrderString = "DELETE * FROM ONLINTICKETING.ORDERS "
				+ "WHERE ORDERID = " + order.getOrderId();
		PreparedStatement deleteStatement = DBConnection.prepare(deleteOrderString);
		
		try {
			deleteStatement.execute();
			System.out.println(deleteStatement.toString());
			
			DBConnection.close(deleteStatement);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		orderMap.put(order.getId(), null);
		
	}
	
	/**
	 * Find all the orders in the database
	 * @return a list of all the Order objects in the database
	 */
	public static ArrayList<Order> findAllOrders(){
		
		Order targetOrder = new Order();
		IdentityMap<Order> orderMap = IdentityMap.getInstance(targetOrder);
		
		ArrayList<Order> orderList = new ArrayList<Order>();
		String findAllOrdersString = "SELECT * FROM ONLINETICKETING.ORDERS";
		PreparedStatement findAllStatement = DBConnection.prepare(findAllOrdersString);
		
		try {
			ResultSet rs = findAllStatement.executeQuery();
			
			while(rs.next()) {
				Order order = loadOrder(rs);
				
				targetOrder = orderMap.get(order.getId());
				if(targetOrder == null) {
					orderMap.put(order.getId(), order);
					orderList.add(order);
				} else {
					orderList.add(targetOrder);
				}
					
			}
			DBConnection.close(findAllStatement);
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return orderList;
	}
	
	/**
	 * Generate an order object from a resultset
	 * @param rs the resultset of an order
	 * @return the order object generated by the resultset
	 */
	public static Order loadOrder(ResultSet rs) {
		
		Order order = null;
		try {
			int orderId = rs.getInt("ORDERID");
			float payment = rs.getFloat("PAYMENT");
			java.util.Date createTime = new java.util.Date(rs.getDate("CREATETIME").getTime());
			int status = rs.getInt("STATUS");
			int customerId = rs.getInt("CUSTOMERID");
			String ticketInformation = rs.getString("TICKETINFO");
			order = new Order(orderId, payment, createTime, status, customerId, ticketInformation);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return order;
	}
}
