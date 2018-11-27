package com.project.controllers;

import java.io.IOException;
import java.io.PrintWriter;
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
 * Servlet implementation class FormularController
 */
public class FormularController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FormularController() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		//For debugging 
		//try {
		// If this section of the code is executed it means that the user has selected 
		// one of the formulars from the selection field on the second tab 
		if(request.getParameter("formularID") != null) {
				
			String searchValue = request.getParameter("formularID").toString();
			int formularID = Integer.parseInt(searchValue);
			FormularDAO formularDao = new FormularDAO();
			Formular searchedFormular = formularDao.selectById(formularID);
			String responseString = "Formular has not been found!";
			PrintWriter out = response.getWriter();
			
			// Get all of the data and then put it in the responseString
			// The formular will be delimited by tilda, each element by <>
			// and each elements property with pipe symbol. By doing this
			// we will know where each formular, element and elements property
			// begins in this string when we process it in javascript.
			if(searchedFormular.getFormularId() != 0) {
				ElementDAO elementDao = new ElementDAO();
				List<Object>formularElements = elementDao.selectByFormular(searchedFormular);
				responseString = searchedFormular.getFormularVersion()+"~";
				for(Object o : formularElements) {
					Element e = (Element)o;
					responseString+= e.getElementID()+"|"+e.getElementLabel()+"|"+e.getElementType()+"|"+e.getElementValidation()+"|";
					if(e.getElementValue() != null) {
						responseString +=e.getElementValue()+"|";
					}
					else {
						responseString += "|";
					}
					if(e.getParentElement() != null) {
						responseString+= e.getParentElement().getElementID()+"|"+e.getParentElement().getElementLabel()+"|"+e.getParentElement().getElementType()+"|"+e.getParentElement().getElementValue();
					}
					responseString +="<>";
					
				}
				
			}
			
			out.write(responseString);
			out.close();
		
		
		}
		else if(request.getParameter("formularId") != null) {
			String formularIDString = request.getParameter("formularId").toString();
			int formularID = Integer.parseInt(formularIDString);
			FormularDAO formularDao = new FormularDAO();
			Formular formularToDelete = formularDao.selectById(formularID);
			if(formularToDelete.getFormularId() != 0) {
				formularDao.delete(formularToDelete);
			}
			
		}
		else {
			// If this section of the code is executed it means that the user wants
			// to update one of the selected formulars and update its elements.
			List<String> parameterNames = Collections.list(request.getParameterNames());
			ElementDAO elementDao = new ElementDAO();
			FormularDAO formularDao = new FormularDAO();
			
				int formularID = Integer.parseInt(request.getParameter("formularId").toString());
				String formularVersion = request.getParameter("formularVersion").toString();
				
				// If the selected formular exists in the database update it
				Formular currentFormular = formularDao.selectById(formularID);
				if(currentFormular.getFormularId() != 0) {
					Formular newFormular = currentFormular;
					newFormular.setFormularVersion(formularVersion);
					
					formularDao.update(currentFormular, newFormular);
				}
				// Now go through each element of the formular. We will get
				// elements id and value and because of that we need to increment
				// parameters array by two
				for(int i = 2; i < parameterNames.size(); i+=2) {
					String idParameter = parameterNames.get(i);
					
					int elementID = Integer.parseInt(request.getParameter(idParameter).toString());
					String valueParameter = parameterNames.get(i+1);
					String elementValue = request.getParameter(valueParameter).toString();
					Element element = elementDao.selectById(elementID);
					
					// if the element exists in the database, update it
					if(element.getElementID() != 0) {
						Element newElement = element;
						newElement.setElementValue(elementValue);
						elementDao.update(element, newElement);
							
					}
					
				}
				
		}
		/*}
		catch(Exception e) {
			e.printStackTrace();
		}*/
		
		doGet(request, response);
	}

}
