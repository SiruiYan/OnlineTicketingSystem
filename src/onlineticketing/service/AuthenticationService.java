package onlineticketing.service;

import onlineticketing.datasource.UserMapper;
import onlineticketing.domain.User;
import onlineticketing.onlineticketing.Params;

public class AuthenticationService {

	/**
	 * Get the permission of a user with input username and password
	 * @param username  the input username
	 * @param password  the input password
	 * @return the permission of the user with input username and password
	 */
	public int getPermission(String username, String password) {
		User user = UserMapper.findUser(username, password);
		if (user == null)
			return Params.INVALID_PERMISSION;
		else
			return user.getPermission();
	}
}
