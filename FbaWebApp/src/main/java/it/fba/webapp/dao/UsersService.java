package it.fba.webapp.dao;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.fba.webapp.beans.UsersBean;

@Component
public class UsersService {
	
	private UsersDao usersDao;

	public UsersDao getUsersDao() {
		return usersDao;
	}

	@Autowired
	public void setUsersDao (UsersDao usersdao){
		this.usersDao = usersdao;
	}
	
	public void addUser(UsersBean user) throws Exception{
		usersDao.addUser(user);
	}
	
	public UsersBean findUserByUsername(String username) throws Exception{
		UsersBean user = usersDao.findUserbyUsername(username);
		
		return user;
	}
	
	public ArrayList<UsersBean>getAllUsers()throws Exception{
		ArrayList<UsersBean> usersList = usersDao.getAllUsers();
		
		return usersList;
	}
	

}
