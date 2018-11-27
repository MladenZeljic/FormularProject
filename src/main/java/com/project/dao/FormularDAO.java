package com.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.project.models.*;

public class FormularDAO extends AbstractDAO implements BasicQueryInterface {
	
	public List<Object> select() {
		
		/* This is the method which we use to select all formular records from 
		 *  Formular table which we created in our database, and which may or may 
		 *  not be filled with some records. If the connection with our database
		 *  is established we will query this table, and convert those selected 
		 *  records to list of formular objects. Else the method will return 
		 *  an empty list. */ 
		
		Connection conn = createConnection();
		List<Object> formulars = new ArrayList<Object>();
		
		if (conn != null) {
			PreparedStatement selectEXP = null;
        	ResultSet rs = null;
        	
        	try {
        		String selectSQL = "SELECT * FROM Formular";
			
        		selectEXP = conn.prepareStatement(selectSQL);
        		
        		rs = selectEXP.executeQuery();
        		while (rs.next()) {
        			Formular formular = new Formular(rs.getString("FORMULARNAME"),rs.getString("FORMULARVERSION"));
        			formular.setFormularId(rs.getInt("FORMULARID"));
        			formulars.add(formular);
        		}
        	}         	
        	catch(SQLException e) {
			    e.printStackTrace();
			} finally {
			    closeConnection(rs, selectEXP);
			}
		}
		return formulars;
	}

	
	public Formular selectById(int id){
		
		/* With this method we will select only one record from our Formular table.
		 * It works similarly as select() method. It doesn't return the list of
		 * objects as select() method, but it returns only one single object with
		 * id that is provided as parameter.*/
		
		Connection conn = createConnection();
		Formular formular = new Formular(null,null);
		
		if (conn != null) {
			PreparedStatement selectEXP = null;
        	ResultSet rs = null;
        	
        	try {
        		String selectSQL = "SELECT * FROM Formular where FormularID = ?";
			
        		selectEXP = conn.prepareStatement(selectSQL);
        		selectEXP.setInt (1, id);
        		rs = selectEXP.executeQuery();
        		while (rs.next()) {
        			formular.setFormularName(rs.getString("FORMULARNAME"));
        			formular.setFormularVersion(rs.getString("FORMULARVERSION"));
        			formular.setFormularId(rs.getInt("FORMULARID"));
        			break;
        		}
        	}         	
        	catch(SQLException e) {
			    e.printStackTrace();
			} finally {
			    closeConnection(rs, selectEXP);
			}
		}
		return formular;
	}
	
	public Formular selectByName(String name){
		
		/* With this method we will select only one record from our Formular table.
		 * It works similarly as select() method. It doesn't return the list of
		 * objects as select() method, but it returns only one single object with
		 * name that is provided as parameter. */
		
		Connection conn = createConnection();
		Formular formular = new Formular(null,null);
		if (conn != null) {
			PreparedStatement selectEXP = null;
        	ResultSet rs = null;
        	
        	try {
        		String selectSQL = "SELECT * FROM Formular where FormularName = ?";
			
        		selectEXP = conn.prepareStatement(selectSQL);
        		selectEXP.setString (1, name);
        		rs = selectEXP.executeQuery();
        		while (rs.next()) {
        			formular.setFormularName(rs.getString("FORMULARNAME"));
        			formular.setFormularVersion(rs.getString("FORMULARVERSION"));
        			formular.setFormularId(rs.getInt("FORMULARID"));
        			break;
        		}
        	}         	
        	catch(SQLException e) {
			    e.printStackTrace();
			} finally {
			    closeConnection(rs, selectEXP);
			}
		}
		return formular;
	}

	public long insert(Object insertObject) {
		
		/* Check the formular which we need to insert. If the Formular table does 
		 * not contain the one with the same id as the one which we intend to insert, 
		 * and if the connection to our database is established perform this operation, 
		 * else do nothing. */
		
		Formular formularToInsert = (Formular)insertObject;
		Formular formularFromDB = this.selectById(formularToInsert.getFormularId());
		long id = 0;
        if(formularFromDB.getFormularId() == 0){
			Connection conn = createConnection();
			if (conn != null) {
	        	PreparedStatement insertEXP = null;
				try {
					
					/* FormularID will be incremented automatically when inserted in 
					 * database. */
					String generatedColumns[] = {"FORMULARID"};
					String insertSQL = "INSERT INTO Formular (FormularName, FormularVersion)"
									+ " VALUES (?,?)";

					insertEXP = conn.prepareStatement(insertSQL, generatedColumns);
					insertEXP.setString (1, formularToInsert.getFormularName());
					insertEXP.setString (2, formularToInsert.getFormularVersion());
		      
					insertEXP.execute();
					ResultSet rs = insertEXP.getGeneratedKeys();

					if (rs.next()) {
					    id = rs.getLong(1);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
				    closeConnection(null, insertEXP);
				}
	        }
		}

		return id;
	}

	public void update(Object oldObject, Object newObject) {
		
		/* Using this method we will update one record in Formular table. If this
		 * particular formular exists in formular table and if we are connected 
		 * with our database we will perform this operation. The first parameter
		 * represents the formular which we need to update, and the second one 
		 * represents the updated formular. */
		
		Formular formularToUpdate = (Formular)oldObject;
		Formular updatedFormular = (Formular)newObject;
		Formular formularFromDB = this.selectById(formularToUpdate.getFormularId());
		if(formularFromDB.getFormularId() != 0){
			Connection conn = createConnection();
	        if (conn != null) {
	        	PreparedStatement updateEXP = null;
				try {
					String updateSQL = "UPDATE Formular SET FormularName = ?, FormularVersion = ?  "
									+ "WHERE FormularID = ?";
					
					updateEXP = conn.prepareStatement(updateSQL);
					updateEXP.setString(1, updatedFormular.getFormularName());
					updateEXP.setString(2, updatedFormular.getFormularVersion());
					updateEXP.setInt(3, formularFromDB.getFormularId());
					updateEXP.executeUpdate();
					
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
				    closeConnection(null, updateEXP);
				} 
	        }
		}
	}

	public void delete(Object deleteObject) {
	
		/* This method is used to delete one particular record from Formular table.
		 * If the connection with our database is active, we will perform this 
		 * operation. Also all records from Element table associated with given
		 * formular will be deleted in the process. */
		
		Formular formularToDelete = (Formular)deleteObject;
		Formular formularFromDB = this.selectById(formularToDelete.getFormularId());

		if(formularFromDB.getFormularId() != 0){
			
			Connection conn = createConnection();
	        if (conn != null) {
	        	PreparedStatement deleteElementsEXP = null;
	        	PreparedStatement deleteFormularEXP = null;
				try {
					
					/* The code bellow will simulate ON DELETE CASCADE for any element 
					 * from Element table associated with given formular by their 
					 * FormularID foreign key. */
					
					String deleteElementsSQL = "DELETE FROM Element where FormularID = ?";
					
					deleteElementsEXP = conn.prepareStatement(deleteElementsSQL);
					deleteElementsEXP.setInt(1,formularFromDB.getFormularId());
					deleteElementsEXP.executeUpdate();
					
					/**************************************************/
					
					/* The code bellow will perform deletion of given formular record
					 * from Formular table. */
					
					String deleteFormularSQL = "DELETE FROM Formular where FormularID = ?";
					
					deleteFormularEXP = conn.prepareStatement(deleteFormularSQL);
					deleteFormularEXP.setInt(1,formularFromDB.getFormularId());
					deleteFormularEXP.executeUpdate();
					
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					
					/* In this section we will release all the resources occupied by 
					 * deleteElementsEXP, deleteFormularEXP and connection */
					
					try{
						if(deleteElementsEXP != null) {
							deleteElementsEXP.close();
						}
					}catch(SQLException e) {}
					
				    closeConnection(null, deleteFormularEXP);
				} 
	        }
		}

	}
}
