package com.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.project.models.Element;
import com.project.models.Formular;

public class ElementDAO extends AbstractDAO implements BasicQueryInterface  {

	public List<Object> select() {
		
		/* This is the method which we use to select all element records from 
		 *  Element table which we created in our database, and which may or may 
		 *  not be filled with some records. If the connection with our database
		 *  is established we will query this table, and convert those selected 
		 *  records to list of element objects. Else the method will return 
		 *  an empty list. */ 
		
		Connection conn = createConnection();
		List<Object> elements = new ArrayList<Object>();
		
		if (conn != null) {
			PreparedStatement selectEXP = null;
        	ResultSet rs = null;
        	
        	try {
        		
        		/* This query will get all rows from Element table. Some elements
        		 * will be parent elements to other elements. For example on 
        		 * our current formular we should, if necessary, have a section where 
        		 * we should be able to add for example three radio buttons. 
        		 * That selection section will be considered as an element of current
        		 * formular as it will contain its own label. Because we coose
        		 * to create 3 new radio button elements in that section, they
        		 * will become child elements of that section. The one way we could
        		 * do this is in our database is by using reflexive relationship in 
        		 * Element table. By using this query we will get all elements with
        		 * their parent elements. */
        		
        		String selectSQL = "SELECT ChildElement.ElementID AS ChildElementID, "
        				+ "ChildElement.ElementLabel AS ChildElementLabel, "
        				+ "ChildElement.ElementType AS ChildElementType, "
        				+ "ChildElement.ElementValidation AS ChildElementValidation, "
        				+ "ChildElement.ElementValue AS ChildElementValue, "
        				+ "ParentElement.ElementID AS ParentElementID, "
        				+ "ParentElement.ElementLabel AS ParentElementLabel, "
        				+ "ParentElement.ElementType AS ParentElementType, "
        				+ "ParentElement.ElementValidation AS ParentElementValidation, "
        				+ "ParentElement.ElementValue AS ParentElementValue, "
        				+ "ChildElement.FormularID AS FormularID "
        				+ "FROM Element AS ChildElement "
        				+ "LEFT JOIN Element AS ParentElement "
        				+ "ON ChildElement.ParentElementID=ParentElement.ElementID ";
			
        		selectEXP = conn.prepareStatement(selectSQL);
        		
        		rs = selectEXP.executeQuery();
        		while (rs.next()) {
        			
        			FormularDAO formularDao = new FormularDAO();
        			Formular formular = formularDao.selectById(rs.getInt("FORMULARID"));
        			
        			Element element = new Element(rs.getString("CHILDELEMENTLABEL"),rs.getString("CHILDELEMENTTYPE"),rs.getString("CHILDELEMENTVALIDATION"),rs.getString("CHILDELEMENTVALUE"),formular);
        			int parentElementID = rs.getInt("PARENTELEMENTID");
        			Element parentElement = null;
        		    if (!rs.wasNull()) {
        		        parentElement = new Element(rs.getString("PARENTELEMENTLABEL"),rs.getString("PARENTELEMENTTYPE"),rs.getString("PARENTELEMENTVALIDATION"),rs.getString("PARENTELEMENTVALUE"),formular);
        		        parentElement.setElementID(parentElementID);
        		    }
        		    element.setParentElement(parentElement);
        			element.setElementID(rs.getInt("CHILDELEMENTID"));
        			elements.add(element);
        		}
        	}         	
        	catch(SQLException e) {
			    e.printStackTrace();
			} finally {
			    closeConnection(rs, selectEXP);
			}
		}
		return elements;
	}

	
	public Element selectById(int id){
		
		/* With this method we will select only one record from our Element table.
		 * It works similarly as select() method. It doesn't return the list of
		 * objects as select() method, but it returns only one single object with
		 * id that is provided as parameter.*/
		
		Connection conn = createConnection();
		Element element = new Element(null,null,null,null,null);
		
		if (conn != null) {
			PreparedStatement selectEXP = null;
        	ResultSet rs = null;
        	
        	try {
        		
        		/* This query will get all rows from Element table. Some elements
        		 * will be parent elements to other elements. For example on 
        		 * our current formular we should, if necessary, have a section where 
        		 * we should be able to add for example three radio buttons. 
        		 * That selection section will be considered as an element of current
        		 * formular as it will contain its own label. Because we coose
        		 * to create 3 new radio button elements in that section, they
        		 * will become child elements of that section. The one way we could
        		 * do this is in our database is by using reflexive relationship in 
        		 * Element table. By using this query we will get all elements with
        		 * their parent elements. */
        		
        		String selectSQL = "SELECT ChildElement.ElementID AS ChildElementID, "
        				+ "ChildElement.ElementLabel AS ChildElementLabel, "
        				+ "ChildElement.ElementType AS ChildElementType, "
        				+ "ChildElement.ElementValidation AS ChildElementValidation, "
        				+ "ChildElement.ElementValue AS ChildElementValue, "
        				+ "ParentElement.ElementID AS ParentElementID, "
        				+ "ParentElement.ElementLabel AS ParentElementLabel, "
        				+ "ParentElement.ElementType AS ParentElementType, "
        				+ "ParentElement.ElementValidation AS ParentElementValidation, "
        				+ "ParentElement.ElementValue AS ParentElementValue, "
        				+ "ChildElement.FormularID AS FormularID "
        				+ "FROM Element AS ChildElement "
        				+ "LEFT JOIN Element AS ParentElement "
        				+ "ON ChildElement.ParentElementID=ParentElement.ElementID "
        				+ "WHERE ChildElement.ElementID = ? ";
			
        		selectEXP = conn.prepareStatement(selectSQL);
        		selectEXP.setInt (1, id);
        		rs = selectEXP.executeQuery();
        		while (rs.next()) {
        			
        			FormularDAO formularDao = new FormularDAO();
        			Formular formular = formularDao.selectById(rs.getInt("FORMULARID"));
        			
        			
        			element.setElementLabel(rs.getString("CHILDELEMENTLABEL"));
        			element.setElementType(rs.getString("CHILDELEMENTTYPE"));
        			element.setElementValidation(rs.getString("CHILDELEMENTVALIDATION"));
        			element.setElementValue(rs.getString("CHILDELEMENTVALUE"));
        			element.setFormular(formular);
        			
        			int parentElementID = rs.getInt("PARENTELEMENTID");
        			Element parentElement = null;
        		    if (!rs.wasNull()) {
        		        parentElement = new Element(rs.getString("PARENTELEMENTLABEL"),rs.getString("PARENTELEMENTTYPE"),rs.getString("PARENTELEMENTVALIDATION"),rs.getString("PARENTELEMENTVALUE"),formular);
        		        parentElement.setElementID(parentElementID);
        		    }
        		    element.setParentElement(parentElement);
        			element.setElementID(rs.getInt("CHILDELEMENTID"));
        		
        			
        			element.setElementID(rs.getInt("ELEMENTID"));
   
        			break;
        		}
        	}         	
        	catch(SQLException e) {
			    e.printStackTrace();
			} finally {
			    closeConnection(rs, selectEXP);
			}
		}
		return element;
	}
	
	public List<Object> selectByFormular(Formular formularObject) {
		
		/* This is the method which we use to select all element records from 
		 *  Element table associated with some formular, with their foreign key.
		 *  If the connection with our database is established we will query 
		 *  this table, and convert those selected records to list of element 
		 *  objects. Else the method will return an empty list. */ 
		
		Connection conn = createConnection();
		List<Object> elements = new ArrayList<Object>();
		
		if (conn != null) {
			PreparedStatement selectEXP = null;
        	ResultSet rs = null;
        	
        	try {
        		
        		/* This query will get all rows from Element table. Some elements
        		 * will be parent elements to other elements. For example on 
        		 * our current formular we should, if necessary, have a section where 
        		 * we should be able to add for example three radio buttons. 
        		 * That selection section will be considered as an element of current
        		 * formular as it will contain its own label. Because we coose
        		 * to create 3 new radio button elements in that section, they
        		 * will become child elements of that section. The one way we could
        		 * do this is in our database is by using reflexive relationship in 
        		 * Element table. By using this query we will get all elements with
        		 * their parent elements. */
        		
        		String selectSQL = "SELECT ChildElement.ElementID AS ChildElementID, "
        				+ "ChildElement.ElementLabel AS ChildElementLabel, "
        				+ "ChildElement.ElementType AS ChildElementType, "
        				+ "ChildElement.ElementValidation AS ChildElementValidation, "
        				+ "ChildElement.ElementValue AS ChildElementValue, "
        				+ "ParentElement.ElementID AS ParentElementID, "
        				+ "ParentElement.ElementLabel AS ParentElementLabel, "
        				+ "ParentElement.ElementType AS ParentElementType, "
        				+ "ParentElement.ElementValidation AS ParentElementValidation, "
        				+ "ParentElement.ElementValue AS ParentElementValue, "
        				+ "ChildElement.FormularID AS FormularID "
        				+ "FROM Element AS ChildElement "
        				+ "LEFT JOIN Element AS ParentElement "
        				+ "ON ChildElement.ParentElementID=ParentElement.ElementID "
        				+ "WHERE FormularID = ? ORDER BY ChildElement.ElementID, "
        				+ "ParentElement.ElementID ASC";
			
        		selectEXP = conn.prepareStatement(selectSQL);
        		selectEXP.setInt(1, formularObject.getFormularId());
        		
        		rs = selectEXP.executeQuery();
        		while (rs.next()) {
        			
        			FormularDAO formularDao = new FormularDAO();
        			Formular formular = formularDao.selectById(rs.getInt("FORMULARID"));
        			
        			Element element = new Element(rs.getString("CHILDELEMENTLABEL"),rs.getString("CHILDELEMENTTYPE"),rs.getString("CHILDELEMENTVALIDATION"),rs.getString("CHILDELEMENTVALUE"),formular);
        			int parentElementID = rs.getInt("PARENTELEMENTID");
        			Element parentElement = null;
        		    if (!rs.wasNull()) {
        		        parentElement = new Element(rs.getString("PARENTELEMENTLABEL"),rs.getString("PARENTELEMENTTYPE"),rs.getString("PARENTELEMENTVALIDATION"),rs.getString("PARENTELEMENTVALUE"),formular);
        		        parentElement.setElementID(parentElementID);
        		    }
        		    element.setParentElement(parentElement);
        			element.setElementID(rs.getInt("CHILDELEMENTID"));
        			elements.add(element);
        		}
        	}         	
        	catch(SQLException e) {
			    e.printStackTrace();
			} finally {
			    closeConnection(rs, selectEXP);
			}
		}
		return elements;
	}

	public long insert(Object insertObject) {
		
		/* Check the element which we need to insert. If the Element table does 
		 * not contain the one with the same id as the one which we intend to insert, 
		 * and if the connection to our database is established perform this operation, 
		 * else do nothing. */
		
		Element elementToInsert = (Element)insertObject;
		Element elementFromDB = this.selectById(elementToInsert.getElementID());
		long id = 0;
        if(elementFromDB.getElementID() == 0){
			Connection conn = createConnection();
	        if (conn != null) {
	        	PreparedStatement insertEXP = null;
				try {
					
					/* ElementID will be incremented automatically when inserted in 
					 * database. */
					
					String generatedColumns[] = {"ELEMENTID"};
					String insertSQL = "INSERT INTO Element (ElementLabel, ElementType, ElementValidation, ElementValue, FormularId, ParentElementId)"
									+ " VALUES (?,?,?,?,?,?)";

					insertEXP = conn.prepareStatement(insertSQL,generatedColumns);
					insertEXP.setString (1, elementToInsert.getElementLabel());
					insertEXP.setString (2, elementToInsert.getElementType());
					insertEXP.setString (3, elementToInsert.getElementValidation());
					insertEXP.setString(4, elementToInsert.getElementValue());
					insertEXP.setInt (5, elementToInsert.getFormular().getFormularId());
					insertEXP.setNull(6, java.sql.Types.NULL);
					if(elementToInsert.getParentElement() != null) {
						insertEXP.setInt(6, elementToInsert.getParentElement().getElementID());
					}
					
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
		
		/* Using this method we will update one record in Element table. If this
		 * particular element exists in Element table and if we are connected 
		 * with our database we will perform this operation. The first parameter
		 * represents the element which we need to update, and the second one 
		 * represents the updated element. */
		
		Element elementToUpdate = (Element)oldObject;
		Element updatedElement = (Element)newObject;
		Element elementFromDB = this.selectById(elementToUpdate.getElementID());
		if(elementFromDB.getElementID() != 0){
			Connection conn = createConnection();
	        if (conn != null) {
	        	PreparedStatement updateEXP = null;
				try {
					String updateSQL = "UPDATE Element SET ElementLabel = ?, ElementType = ?, ElementValidation = ?, ElementValue = ?, FormularID = ?, parentElementID = ? "
									+ "WHERE ElementID = ?";
					
					updateEXP = conn.prepareStatement(updateSQL);
					updateEXP.setString(1, updatedElement.getElementLabel());
					updateEXP.setString(2, updatedElement.getElementType());
					updateEXP.setString(3, updatedElement.getElementValidation());
					updateEXP.setString(4, updatedElement.getElementValue());
					updateEXP.setInt(5, updatedElement.getFormular().getFormularId());
					updateEXP.setNull(6, java.sql.Types.NULL);
					if(updatedElement.getParentElement() != null) {
						updateEXP.setInt(6, updatedElement.getParentElement().getElementID());
					}
					updateEXP.setInt(7, elementFromDB.getElementID());
					updateEXP.executeUpdate();
					
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
				    closeConnection(null, updateEXP);
				} 
	        }
		}
	}
	
	public void updateWithoutValue(Object oldObject, Object newObject) {
		
		/* Using this method we will update one record in Element table. If this
		 * particular element exists in Element table and if we are connected 
		 * with our database we will perform this operation. The first parameter
		 * represents the element which we need to update, and the second one 
		 * represents the updated element. It will update the entire element
		 * except for its value. */
		
		Element elementToUpdate = (Element)oldObject;
		Element updatedElement = (Element)newObject;
		Element elementFromDB = this.selectById(elementToUpdate.getElementID());
		if(elementFromDB.getElementID() != 0){
			Connection conn = createConnection();
	        if (conn != null) {
	        	PreparedStatement updateEXP = null;
				try {
					String updateSQL = "UPDATE Element SET ElementLabel = ?, ElementType = ?, ElementValidation = ?, FormularID = ?, parentElementID = ? "
									+ "WHERE ElementID = ?";
					
					updateEXP = conn.prepareStatement(updateSQL);
					updateEXP.setString(1, updatedElement.getElementLabel());
					updateEXP.setString(2, updatedElement.getElementType());
					updateEXP.setString(3, updatedElement.getElementValidation());
					updateEXP.setInt(4, updatedElement.getFormular().getFormularId());
					updateEXP.setNull(5, java.sql.Types.NULL);
					if(updatedElement.getParentElement() != null) {
						updateEXP.setInt(5, updatedElement.getParentElement().getElementID());
					}
					updateEXP.setInt(6, elementFromDB.getElementID());
					updateEXP.executeUpdate();
					
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
				    closeConnection(null, updateEXP);
				} 
	        }
		}
	}
	
	public void deleteUnaffectedFromFormular(List<Element> elements, Formular formular) {
		
		if(!elements.isEmpty()) {
			String deleteSQL = "DELETE FROM Element";
			String whereClausule = " WHERE";
			String whereParent = "";
			String whereChild = "";

			String formularClausule = " AND FormularID = ?";
			for(int i = 0; i < elements.size(); i++) {
					whereChild+=" ElementID <> ?";
					whereParent+=" ParentElementID <> ?";
					if(i != elements.size() - 1){
						whereChild+=" AND";
						whereParent+=" AND";
				}
			}
			
			String deleteChildElementsSQL = deleteSQL + whereClausule + whereChild + formularClausule;
			String deleteParentElementSQL = deleteSQL + whereClausule + whereParent + formularClausule;
			Connection conn = createConnection();
			if (conn != null) {
				PreparedStatement deleteParentElementEXP = null;
				PreparedStatement deleteChildElementsEXP = null;
		
				try {
					
					deleteChildElementsEXP = conn.prepareStatement(deleteChildElementsSQL);
					deleteParentElementEXP = conn.prepareStatement(deleteParentElementSQL);
					
					int expIndex = 1;
					for(Element elementToDelete : elements) {
						Element elementFromDB = this.selectById(elementToDelete.getElementID());

						
							deleteChildElementsEXP.setInt(expIndex,elementFromDB.getElementID());
							deleteParentElementEXP.setInt(expIndex,elementFromDB.getElementID());
						
							expIndex++;
							
						
					}
					
					deleteChildElementsEXP.setInt(expIndex, formular.getFormularId());
					deleteParentElementEXP.setInt(expIndex, formular.getFormularId());
					
					
					deleteParentElementEXP.executeUpdate();
					deleteChildElementsEXP.executeUpdate();
					
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					
					
					try{
						if(deleteChildElementsEXP != null) {
							deleteChildElementsEXP.close();
						}
					}catch(SQLException e) {}
					
				    closeConnection(null, deleteParentElementEXP);
				}
		}

	}	
		
		
	}
	
	public void delete(Object deleteObject) {
	
		/* This method is used to delete one particular record from Element table,
		 * and its child records. If the connection with our database is active, 
		 * we will perform this operation. */
		
		Element elementToDelete = (Element)deleteObject;
		Element elementFromDB = this.selectById(elementToDelete.getElementID());

		if(elementFromDB.getElementID() != 0){
			
			Connection conn = createConnection();
	        if (conn != null) {
	        	PreparedStatement deleteParentElementEXP = null;
	        	PreparedStatement deleteChildElementsEXP = null;
				try {
					
					/* The code bellow will perform deletion of child elements of 
					 * given element record from Element table. */
					
					String deleteChildElementsSQL = "DELETE FROM Element WHERE ParentElementID = ?";
					
					deleteChildElementsEXP = conn.prepareStatement(deleteChildElementsSQL);
					deleteChildElementsEXP.setInt(1,elementFromDB.getElementID());
					deleteChildElementsEXP.executeUpdate();
					
					/* The code bellow will perform deletion of given element record
					 * from Element table. */
					
					String deleteParentElementSQL = "DELETE FROM Element where ElementID = ?";
					
					deleteParentElementEXP = conn.prepareStatement(deleteParentElementSQL);
					deleteParentElementEXP.setInt(1,elementFromDB.getElementID());
					deleteParentElementEXP.executeUpdate();
					
					
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					
					/* In this section we will release all the resources occupied by 
					 * deleteParentElementEXP, deleteChildElementsEXP and connection */
					
					try{
						if(deleteChildElementsEXP != null) {
							deleteChildElementsEXP.close();
						}
					}catch(SQLException e) {}
					
				    closeConnection(null, deleteParentElementEXP);
				} 
	        }
		}

	}

}
