package com.project.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractDAO {
	
	protected Connection connection=null;
	
	public Connection getConnection(String database,String username,String password){
		try{
			
			// Register hsqldb driver so that it could be used by DriverManager.
			
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			
			/* Get our current directory, and replace all backslash delimiters with java
			 * compatible slash delimiters (this is the solution for windows directory 
			 * paths). Because we use maven with tomee plugin and after the conversion of
			 * current directory path, we will get this path:
			 * C:/Users/myuser/workspace/FormularProject/target/apache-tomee
			 * At the runtime we will be located at tomee server.
			 * Finally we need to move up by two levels. Because of that we will add ../../
			 * to our current directory, and then we will get correct project path */
			
			String cwd = System.getProperty("user.dir");
			String currentProjectPath = cwd;
			if (File.separatorChar=='\\') {
				currentProjectPath = cwd.replace("\\","/");
			}
		    currentProjectPath += "/../../";
		    
		    // open a connection and return the 'connection' object which represents it
			connection = DriverManager.getConnection("jdbc:hsqldb:file:"+currentProjectPath+"hsqldb/"+database, username,password);
	        return connection;
		}
		catch(SQLException e){
			// Handle errors for JDBC
			e.printStackTrace();
		}
		catch (Exception ex) {
			// Handle errors raised by Class.forName
			ex.printStackTrace();
		}
		return null;
	}
	public void closeConnection(ResultSet rs,PreparedStatement preparedStatement){
		
		/* This method will close ResultSet,PreparedStatement and connection if
		 * any of them is active. */
		
		try { if (rs != null) rs.close(); } catch (SQLException e) {};
	    try { if (preparedStatement != null) preparedStatement.close(); } catch (SQLException e) {};
	    try { if (this.connection != null) connection.close(); } catch (SQLException e) {};
	}
	
	public Connection createConnection(){
		
		/* Method which will open a connection between formularDB database, by using
		 * previously defined getConnection method, and by providing default username 
		 * and password for that database. */
		
		return this.getConnection("formularDB", "sa", "");
	}
	

	
}