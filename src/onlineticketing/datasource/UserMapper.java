package onlineticketing.datasource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import onlineticketing.domain.DomainObject;
import onlineticketing.domain.User;
import onlineticketing.onlineticketing.Params;

public class UserMapper implements DataMapper{
	
	
	/**
	 * Create a new user in database
	 * @param obj the user to be created
	 */
	public void insert(DomainObject obj) {
		assert !(obj instanceof User) : "obj is not a user object";
		User user = (User)obj;
		
//		User targetUser = new User();
//		IdentityMap<User> userMap = IdentityMap.getInstance(targetUser);
		
		String createUserString = "INSERT INTO ONLINETICKETING.USERS "
				+ "(USERNAME, PASSWORD, PHONENUMBER, PERMISSION) "
				+ "VALUES (?, ?, ?, ?)";
		PreparedStatement createStatement = DBConnection.prepare(createUserString);
		
		try {
			createStatement.setString(1, user.getUserName());
			createStatement.setString(2, user.getPassword());
			createStatement.setInt(3, user.getPhoneNumber());
			createStatement.setInt(4, user.getPermission());
			createStatement.execute();
			System.out.println(createStatement.toString());
			
			DBConnection.close(createStatement);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		userMap.put(id, obj);
		
	}
	
	/**
	 * Find all users in the database
	 * @return a list of all the users in the database
	 */
	public static ArrayList<User> findAllUsers(){
		User targetUser = new User();
		IdentityMap<User> userMap = IdentityMap.getInstance(targetUser);
		
		String findAllUsersString = "SELECT * FROM ONLINETICKETING.USERS";
		PreparedStatement findAllStatement = DBConnection.prepare(findAllUsersString);
		ArrayList<User> userList = new ArrayList<User>();
		
		try {
			ResultSet rs = findAllStatement.executeQuery();
			
			while(rs.next()) {
				User user = loadUser(rs);
				targetUser = userMap.get(user.getId());
				if (targetUser == null) {
					userList.add(user);
					userMap.put(user.getId(), user);
				} else {
					userList.add(targetUser);
				}
				
			}
			DBConnection.close(findAllStatement);
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return userList;
	}
	
	/**
	 * Find a user in the databse by username
	 * @param username  the input username
	 * @return the user which has the username passed in, returns null if 
	 * 		   there does not exist a user with the username passed in
	 */
	public static User findUserByUsername(String username) {
		User user = null;
		String findUserString = "SELECT * FROM ONLINETICKETING.USERS "
				+ "WHERE USERNAME = '" + username + "'";
		PreparedStatement findStatement = DBConnection.prepare(findUserString);
		
		try {
			ResultSet rs = findStatement.executeQuery();
			
			while(rs.next()) {
				user = loadUser(rs);
			}
			DBConnection.close(findStatement);
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return user;
	}
	
	
	/**
	 * Find a user in the databse by username and password
	 * @param username  the input username
	 * @param password  the input password
	 * @return the user which has the username and password passed in, returns null 
	 * 		   if there does not exist a user with the username and password passed in
	 */
	public static User findUser(String username, String password) {
		User user = null;
		String findUserString = "SELECT * FROM ONLINETICKETING.USERS "
				+ "WHERE USERNAME = '" + username + "'";
		PreparedStatement findStatement = DBConnection.prepare(findUserString);
		
		try {
			ResultSet rs = findStatement.executeQuery();
			
			while(rs.next()) {
				user = loadUser(rs);
				if (!user.getPassword().equals(password))
					user = null;
			}
			DBConnection.close(findStatement);
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return user;
	}
	
	@Override
	public void update(DomainObject obj) {
		// TODO Auto-generated method stub
		assert !(obj instanceof User) : "obj is not a user object";
		User user = (User)obj;
		
		User targetUser = new User();
		IdentityMap<User> userMap = IdentityMap.getInstance(targetUser);
		
		String updateUserString = "UPDATE ONLINETICKETING.USERS SET PASSWORD = ?, "
				+ "PHONENUMBER = ? WHERE USERID = '" + user.getId() + "'";
		PreparedStatement updateStatement = DBConnection.prepare(updateUserString);
		
		try {
			updateStatement.execute();
			DBConnection.close(updateStatement);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		userMap.put(user.getId(), user);
	}

	@Override
	public void delete(DomainObject obj) {
		// TODO Auto-generated method stub
		assert !(obj instanceof User) : "obj is not a user object";
		User user = (User)obj;
		
		User targetUser = new User();
		IdentityMap<User> userMap = IdentityMap.getInstance(targetUser);
		
		String deleteUserString = "DELETE * FROM ONLINETICKETING.USERS WHERE "
				+ "USERID = '" + user.getId() + "'";
		PreparedStatement deleteStatement = DBConnection.prepare(deleteUserString);
		
		try {
			deleteStatement.execute();
			DBConnection.close(deleteStatement);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		userMap.put(user.getId(), null);
	}
	
	/**
	 * Generate a user object from a resultset
	 * @param rs the resultset of a user
	 * @return the user object generated by the resultset
	 */
	public static User loadUser(ResultSet rs) {
		
		User user = null;
		try {
			int userId = rs.getInt("USERID");
			String userName = rs.getString("USERNAME");
			String password = rs.getString("PASSWORD");
			int phoneNumber = rs.getInt("PHONENUMBER");
			boolean permission = (rs.getInt("PERMISSION") == 
					Params.ADMIN_PERMISSION ? false : true);
			user = new User(userId, userName, password, phoneNumber, permission);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return user;
	}

}
