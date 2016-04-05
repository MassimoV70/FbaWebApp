package it.fba.webapp.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import it.fba.webapp.beans.UsersBean;


@Repository("UsersDaoImpl")
@Transactional()
public class UsersDaoImpl implements UsersDao{

	@PersistenceContext 
	private EntityManager entityManager;
	
	private final String queryAll = "Select u from UsersBean u where role='ROLE_USER'";
	
	
	
	public void addUser(UsersBean user){
		entityManager.persist(user);
	}
	
	public UsersBean findUserbyUsername(String username){
		 UsersBean user = entityManager.find(UsersBean.class, username);
		return user;
	}
	
	public ArrayList<UsersBean> getAllUsers(){
		
		Query query = entityManager.createQuery(queryAll);
		@SuppressWarnings("unchecked")
		ArrayList<UsersBean> resultList = (ArrayList<UsersBean>)query.getResultList();
		return resultList;
	}

	@Override
	public UsersBean updateUser(UsersBean user) throws SQLException {
		// TODO Auto-generated method stub
		entityManager.find(UsersBean.class, user.getUsername());
		entityManager.merge(user);
		return null;
	}
	
	
	

}
