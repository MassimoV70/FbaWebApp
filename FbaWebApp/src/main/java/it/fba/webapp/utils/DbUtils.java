package it.fba.webapp.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

public class DbUtils {
	
	
	private DataSource dataSource;
	
	
	public DataSource getDataSource(){
		return dataSource;
	}
	
	public void setDataSource (DataSource dataSource){
		this.dataSource = dataSource;
	}
	
	
	public void inzialize(){
		DataSource dataSource = this.getDataSource();
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			Statement statement = connection.createStatement();
			statement.executeQuery("create table users (username varchar(50) not null, password varchar(50) not null"
					+ ",nome varchar(50) not null,cognome varchar(50) not null,enabled int(1) not null,"
					+ "role varchar(20) not null, primary key (username))");
			
			statement.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try{
			 connection.close();
			}catch (SQLException e) {
				// TODO: handle exception
				 e.printStackTrace();
			}
		}
	}

}
