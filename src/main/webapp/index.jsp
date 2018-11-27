<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.project.models.Formular" %>
<%@ page import="com.project.dao.FormularDAO" %>
<%@ page import="com.project.models.Element" %>
<%@ page import="com.project.dao.ElementDAO" %>
<%@ page import="java.util.List" %>

<%	int maxNumberOfRadioInputs = 5;
	int minNumberOfRadioInputs = 2;
	FormularDAO formularDao = new FormularDAO();
	
	List<Object> existingFormulars = formularDao.select();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="shortcut icon" href="">
		<title>A Web Page</title>
	</head>
	<!-- Including bootstrap 3.3.7 css file -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" href="styles/index-style.css">
	
	<body>
		<div class="page">
		  	<div id="tabs" class="tabs"> <!-- Container for current page tabs -->
		      	<a id="tab1" href="javascript:void(0);" class="tab-selected" onclick="showSelectedView(this);">
		      		Administration
		      	</a><!--

				--><a id="tab2" href="javascript:void(0);" onclick="showSelectedView(this);">
		      		Formular
				</a>
		  	</div>
		  	<div id="views">
		  		<div id="tab1-view" class="tab-view">
		  			<form method="post" onsubmit="return false;" action="">
						<div class="form-group formular-form-group">
							<div class="input-bar">
        						<div class="input-bar-item width100">
        							<label class="control-label formular-name-label" for="formular-name-input">Formular name:</label>
          							<div class="input-group col-xs-3">
                						<input type="text" class="form-control width100" id="formular-name-input" name="formular-name-input" placeholder="Enter the name of formular">
                						<span class="input-group-btn">
                  							<button class="btn btn-info" onclick="searchForFormulars()">Search</button>
                						</span>
            						</div>
					        	</div>
      						</div>
						</div>
					</form>
					<form id="formular-form" method="post" class="formular-form form-hidden" onsubmit="return false;" action="">
						<div class="row">
							<div class="form-group col-xs-3">
								<label class="control-label formular-element-label" for="element1">Element 1:</label>
	          					<div class="input-group">
	                				<input type="text" class="form-control width100" id="element1" name="element1" placeholder="Enter a label">
	            				</div>
							</div>
							<div class="form-group col-xs-2">
                  				<select class="form-control width100" id="element1-selectType" name ="element1-selectType" onchange="changeValidations(this); showRadioLabels(this)">
                  					<option value="Textbox" selected>Textbox</option>
                  					<option value="Checkbox">Checkbox</option>
                  					<option value="Radio buttons">Radio buttons</option>
                  				</select>
                  			</div>
                  			<div class="form-group col-xs-1 radio-number-hide">
                  				<select class="form-control width100" id="element1-selectRadioNumber" name ="element1-selectRadioNumber" onchange="changeRadioLabels(this)">
                  					<% for (int i = 2; i <= maxNumberOfRadioInputs; i++){ %>
                  						<option value="<%=i %>" 
                  							<% if(i == 2){ %>
                  								selected 
                  							<%} %>
                  							><%=i %>
                  						</option>
                  					<% } %>
                  				</select>
                  			</div>
                  			<div class="form-group col-xs-2">
                  				<select id="element1-selectValidation" name ="element1-selectValidation" class="form-control width100">
                  					<option value="None" selected>None</option>
                  					<option value="Mandatory">Mandatory</option>
                  					<option value="Numeric">Numeric</option>
                  					<option value="Email">Email</option>
                  					<option value="Date">Date</option>
                  				</select>
                  			</div>
                  			<div class="form-group btn-group">
                  				<button type="button" class="btn btn-add-element" onclick="addElement()">&#43;</button>
                			</div>
                			<div class="radio-input-group radio-input-hidden">
	                			<div class="form-group col-xs-3">
								</div>
	                			<div class="form-group col-xs-4 radio-input">
									<div class="input-group">
		                				<input type="text" class="form-control width100" id="radioelement1-labelinput1" name="radioelement1-labelinput1" placeholder="Enter a label">
		            				</div>
								</div>
							</div>
                			<div class="radio-input-group radio-input-hidden">
	                			<div class="form-group col-xs-3">
								</div>
	                			<div class="form-group col-xs-4 radio-input">
									<div class="input-group">
		                				<input type="text" class="form-control width100" id="radioelement1-labelinput2" name="radioelement1-labelinput2" placeholder="Enter a label">
		            				</div>
								</div>
							</div>
                  		</div>
                  		<div class="flex"></div>
                  		<div class="form-group">
                  			<button class="btn btn-info btn-save-formular" onclick="submitFormular()">Save</button>			
                  		</div>
           
					</form>
				</div>
				<div id="tab2-view" class="tab-view-hide">
					<form method="post" onsubmit="return false;" action="">
						<div class="row formular-form-group">
							<div class="form-group col-xs-3">
    							<label for="sort" class="control-label formular-select-label">Formular name:</label>
    								<div class="input-group formular-select-group">
    									<select class="form-control" id="formular-name-select" name="formular-name-select">
                							<% for(Object objectFormular : existingFormulars){
                							Formular formular = (Formular)objectFormular;
                							%>
                							<option value="<%=formular.getFormularId()%>"><%=formular.getFormularName()%></option>
                							<%
                							}
                						
                						%>
                						</select>
        
     								</div>
							</div>
							<div class="form-group formular-version-group">
                  				<label class="control-label formular-version-label" for="formular-version-input">Version:</label>
	          					<div class="input-group formular-version-input-group">
	                				<input type="text" class="form-control width100" id="formular-version-input" name="formular-version-input" placeholder="Version">
	            				</div>
                  			</div>
                  			
                  			<div class="form-group btn-group">
                	  			<button class="btn btn-info" onclick="searchFormular()">Load</button>			
           		       		</div>
                  		</div>
      					
           			</form>
					<form method="post" class="formular-form form-hidden" onsubmit="return false;" action="">
					
                  		<div class="flex"></div>
                  		<div class="form-group">
                  			<div class="btn btn-group btn-delete-formular">                  			
                  				<button class="btn btn-danger" onclick="deleteFormular()">Delete</button>			
                  			</div>
                  			
                  			<div class="btn btn-group btn-save-formular">
                  				<button class="btn btn-info" onclick="saveFormular()">Save</button>			
                  			</div>
                  			
                  		</div>	
					</form>
				</div>
			</div>
		</div>
		<script src="scripts/js/administration-script.js"></script>
		<script src="scripts/js/formular-script.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	</body>
</html>
