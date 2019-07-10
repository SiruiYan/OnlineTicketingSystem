package onlineticketing.domain;

import onlineticketing.onlineticketing.Params;

public class User extends DomainObject {

	private String userName;
	private String password;
	private int phoneNumber;
	private boolean permission;

	public User() {
		
	}
	
	public User(String userName, String password, int phoneNumber, 
			boolean permission) {
		super();
		this.userName = userName;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.permission = permission;
	}
	
	public User(int userId, String userName, String password, int phoneNumber, 
			boolean permission) {
		super();
		this.id = Integer.toString(userId);
		this.userName = userName;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.permission = permission;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getPermission() {
		return (permission ? Params.CUSTOMER_PERMISSION : Params.ADMIN_PERMISSION);
	}

}
