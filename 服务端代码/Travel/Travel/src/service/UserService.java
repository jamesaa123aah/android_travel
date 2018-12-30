package service;

import bean.LoginMessage;
import bean.User;

public interface UserService {
	public LoginMessage checkLogin(User user);

	public String register(User user);
}
