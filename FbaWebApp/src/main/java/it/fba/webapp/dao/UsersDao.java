package it.fba.webapp.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import it.fba.webapp.beans.UsersBean;

public interface UsersDao {
	
	void addUser(UsersBean user) throws SQLException;
	
	ArrayList<UsersBean> getAllUsers() throws SQLException;
	
	UsersBean findUserbyUsername(String usernme) throws SQLException;
	
	UsersBean updateUser(UsersBean user) throws SQLException;

}
