package com.project.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.dao.ElementDAO;
import com.project.dao.FormularDAO;
import com.project.models.Element;
import com.project.models.Formular;

/**
 * Servlet implementation class IndexController
 */
public class AdministrationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdministrationController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	/* In this method we will process all data sent to the server via ajax calls. */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		
		// If the user has sent searchValue parameter to the server it means that
		// he/she wants to search for the formulars
		if(request.getParameter("searchValue") != null) {
			String searchValue = request.getParameter("searchValue").toString();
		
			if(searchValue != null) {
				FormularDAO formularDao = new FormularDAO();
				Formular searchedFormular = formularDao.selectByName(searchValue);
				PrintWriter out = response.getWriter();
				String responseString = "Formular has not been found!";
				
				// If the formular has been found then send the response to the user
				if(searchedFormular.getFormularId() != 0) {
					ElementDAO elementDao = new ElementDAO();
					List<Object>formularElements = elementDao.selectByFormular(searchedFormular);
					responseString = "";
					
					// The response will contain each element from the searched formular
					// Each elements property will be divided from the other one with
					// pipe symbol. Each element will be divided with <> symbol. By 
					// doing this we will ensure that we can know where each element
					// and elements property will begin in the response string
					for(Object o : formularElements) {
						Element e = (Element)o;
						responseString+= e.getElementID()+"|"+e.getElementLabel()+"|"+e.getElementType()+"|"+e.getElementValidation()+"|";
						if(e.getParentElement() != null) {
							responseString+= e.getParentElement().getElementID()+"|"+e.getParentElement().getElementLabel()+"|"+e.getParentElement().getElementType()+"|"+e.getParentElement().getElementValidation();
						}
						responseString +="<>";
						
					}
				}
				
				out.write(responseString);
				out.close();
			}
			
		}
		else if(request.getParameter("action") != null) {
			// If this section is executed it means that we want to update formular 
			// selection element on the second tab. This section will be executed 
			// each time when the user adds new formulars or updates the old ones.
			// Like in the code above, and for similiar reasons we will insert pipe
			// and <> symbols in the response string
			if(request.getParameter("action").toString().equals("updateSelect")) {
				FormularDAO formularDao = new FormularDAO();
				List<Object> formulars = formularDao.select();
				PrintWriter out = response.getWriter();
				String responseString = "";
				
				int i = 0;
				for(Object objectFormular : formulars) {
					Formular formular = (Formular)objectFormular;
					responseString += formular.getFormularId()+"|"+formular.getFormularName();
					if(i != formulars.size()-1) {
						responseString += "<>";
					}
					i++;
					
				}
				//System.out.println(responseString);
				out.write(responseString);
				out.close();
			}
		}
		else {
			// if this section of the code is executed it means that the user
			// wants to insert or update formular / and or its elements. Because 
			// we might (and probably will) get variable number of parameters we 
			// will need to get through each of them and process them
			
			List<String> parameterNames = Collections.list(request.getParameterNames());
			List<Element>affectedElements = new ArrayList<Element>();
			ElementDAO elementDao = new ElementDAO();
			FormularDAO formularDao = new FormularDAO();
			Formular currentFormular = new Formular(null,null);
			boolean newFormular = false;
			String formularName = parameterNames.get(0);
			
			if(formularName.equals("formularName")) {
				currentFormular = formularDao.selectByName(request.getParameter(formularName).toString());

				// If the formular has not been found insert it
				if(currentFormular.getFormularId() == 0) {
					currentFormular.setFormularName(request.getParameter(formularName).toString());
					currentFormular.setFormularVersion("1.0");
					formularDao.insert(currentFormular);
					currentFormular = formularDao.selectByName(currentFormular.getFormularName());
					newFormular = true;
				}
			}
			// For debugging
			//try {
				// Get through all parameters and process them
				for (int i = 1; i < parameterNames.size(); i++) {
					String idParameter = parameterNames.get(i);
					
					// If the parameter name contains element string, any number and ID
					// string it means that we have stumbled upon the element of the formular
					if(idParameter.matches("element[0-9]+ID")) {
						int elementID = 0;
						String elementIDString = request.getParameter(idParameter).toString();
						// we will get an id of this element from the first parameter
						if(!elementIDString.isEmpty()) {
							try { 
								elementID = Integer.parseInt(elementIDString,10); 
						    } catch(NumberFormatException e) { 
						        elementID = 0; 
						    } catch(NullPointerException e) {
						        elementID = 0;
						    }
							
						}
						
						// In these several lines we will get other element properties
						String labelParameter = parameterNames.get(i+1);
						String typeParameter = parameterNames.get(i+2);
						String radioNumberParameter = parameterNames.get(i+3);
						String validationParameter = parameterNames.get(i+4);
						String elementLabel = request.getParameter(labelParameter).toString();
						String elementType = request.getParameter(typeParameter).toString();
						int radiosNumber = Integer.parseInt(request.getParameter(radioNumberParameter).toString());
						String elementValidation = request.getParameter(validationParameter);
						
						// Now we will create new element object and then we
						// will populate it
						Element newElement = new Element(null,null,null,null,null);
						
						newElement.setElementID(elementID);
						newElement.setElementLabel(elementLabel);
						newElement.setElementType(elementType);
						newElement.setElementValidation(elementValidation);
						newElement.setFormular(currentFormular);
						Element elementFromDB = elementDao.selectById(newElement.getElementID());
						
						newElement.setParentElement(null);
						
						// If the element with processed id is in the database
						// then update it, else insert it
						if(elementFromDB.getElementID() != 0) {
							elementDao.updateWithoutValue(elementFromDB, newElement);
						}
						else {
							long id = elementDao.insert(newElement);
							newElement.setElementID(Math.toIntExact(id));
						}
						// add this element to the affected elements array
						affectedElements.add(newElement);
						
						// If this element is Radio buttons element
						if(elementType != null && elementType.equals("Radio buttons")) {
							int j = 1;
							// Then we will process the request string further, to get each radio button
							while(j <= 2*radiosNumber) {
								Element newRadioElement = new Element(null,null,null,null,null);
								
								// Get an id of radio element and then convert it to integer
								int radioElementID = 0;
								String radioElementIDString = request.getParameter(parameterNames.get(i+j+4)).toString();
								if(!radioElementIDString.isEmpty()) {
									try { 
										radioElementID = Integer.parseInt(radioElementIDString);
									} catch(NumberFormatException e) { 
								        radioElementID = 0; 
								    } catch(NullPointerException e) {
								        radioElementID = 0;
								    }
								}
								// Create this element and populate it
								String radioElementLabel = request.getParameter(parameterNames.get(i+j+5)).toString();
								newRadioElement.setElementLabel(radioElementLabel);
								newRadioElement.setElementType("Radio");
								newRadioElement.setElementValidation(elementValidation);
								newRadioElement.setElementID(radioElementID);
								newRadioElement.setParentElement(newElement);
								newRadioElement.setFormular(currentFormular);
								elementFromDB = elementDao.selectById(newRadioElement.getElementID());
								
								// If this element is already in the database then just update it
								// but do not update its value
								
								if(elementFromDB.getElementID() != 0) {
									elementDao.updateWithoutValue(elementFromDB, newRadioElement);
								}
								// else insert it
								else {
									long id = elementDao.insert(newRadioElement);
									newRadioElement.setElementID(Math.toIntExact(id));
								}
								// Then add this element to affected elements list
								affectedElements.add(newRadioElement);
								// We will increment parameters by 2 because each radio element contains 
								// an id and label
								j = j+2;
							}
							
							
						}
						
					}
				}
				// And then we will delete every unaffected element belonging to the 
				// current formular from the database
				if(!newFormular) {
					elementDao.deleteUnaffectedFromFormular(affectedElements,currentFormular);
				}
			/*}catch(Exception e) {
				e.printStackTrace();
			}*/
			
		}
		doGet(request, response);
	}

}
