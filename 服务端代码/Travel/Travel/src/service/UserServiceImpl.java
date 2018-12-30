package service;

import java.util.List;

import bean.LoginMessage;
import bean.User;
import dao.UserDao;
import dao.UserDaoIple;

public class UserServiceImpl implements UserService{
	UserDao dao=new UserDaoIple();
	
	@Override
	public String register(User user) {
		List<User> list = dao.findAll();
		for(int i=0;i<list.size();i++) {
			User user2=list.get(i);
			if(user2.getName().equals(user.getName())) {
				return "fail";
			}
		}
		dao.insertElement(user);
		return "success";
	}

	@Override
	public LoginMessage checkLogin(User user) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				List<User> list = dao.findAll();
				LoginMessage result=new LoginMessage();
				if(list.isEmpty()) {
					result.setCode(2);
					result.setMsg("fail");
					return result;
				}
				else {
				for(int i=0;i<list.size();i++) {
					User user2=list.get(i);
					if(user2.getName().equals(user.getName()) && user2.getPassword().equals(user.getPassword())) {
						result.setCode(1);
						result.setMsg("success");
						return result;
					}
					result.setCode(2);
					result.setMsg("fail");
					return result;
				}
				}
				result.setCode(2);
				result.setMsg("fail");
				return result;
	}

}
