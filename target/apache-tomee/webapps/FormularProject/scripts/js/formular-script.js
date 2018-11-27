// This function will be called when the user searches for formular. It uses
// ajax to get all of the data belonging to the searched formular, which will 
// be needed for its creation on the page.
function searchFormular(){
	var documentForms = document.getElementsByTagName("form");
	var formularSearchForm = documentForms[2];
	var formularSelect = formularSearchForm.getElementsByTagName("select")[0];
	
	var formularID = parseInt(formularSelect.options[formularSelect.selectedIndex].value);
		
	var xhttp;
	if (window.XMLHttpRequest) {
		xhttp = new XMLHttpRequest();
	} 
	else {
		xhttp = new ActiveXObject('Microsoft.XMLHTTP');
	}
	
	var url = "http://localhost:8080/FormularProject/formular";
	var params = "formularID="+formularID;
	
	// If the formular has been found create it
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			showFormular(this.responseText);
		}
	};
	
	xhttp.open('POST', url, true);
	xhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	xhttp.send(params); 
}

/* With this function we will create formular and populate it with data from 
 * response string*/
function showFormular(responseString){
	
	var documentForms = document.getElementsByTagName("form");
	var formularSearchForm = documentForms[2];
	var formularForm = documentForms[3];
	formularForm.classList.remove("form-hidden");
	var responseArray = responseString.split("~");
	var dataArray = responseArray[1].split("<>");
	var formularVersionInput = formularSearchForm.getElementsByTagName("input")[0];
	
	formularVersionInput.value = responseArray[0];
	formularVersionInput.required = true;
	
	var formularRowsNumber = formularForm.getElementsByClassName("row").length;
	
	// We will remove all other rows that might have been in this form
	for(var i = 0; i < formularRowsNumber; i++){
		formularForm.removeChild(formularForm.firstElementChild);
	}
	// dataArray will contain all elements from the current formular
	// so we will need to iterate through it to get each element
	for(var i = 0; i < dataArray.length; i++){
		// Create the row element and set its css class
		var newFormularRow = document.createElement("div");
		newFormularRow.classList.add("row");
		var elementLabelText = "";
		
		//Now we will get each element propperty in data variable
		var data = dataArray[i].split("|");
		// if this element is textbox create textbox row elements and 
		//append the to the current row
		if(data[2] == "Textbox"){
			var newFormGroup = document.createElement("div");
			newFormGroup.classList.add("formular-label-group");
			elementLabelText = data[1];
			var formularRowLabel = document.createElement("label");
			formularRowLabel.classList.add("control-label");
			formularRowLabel.classList.add("formular-element-label");
			
			if(data[3] == "Mandatory"){
				elementLabelText+="*";
			}
			elementLabelText+=":";
			formularRowLabel.innerHTML = elementLabelText;
			formularRowLabel.htmlFor = data[0];
			
			newFormGroup.classList.add("form-group");
			newFormGroup.classList.add("formular-checkbox-group");
			newFormGroup.appendChild(formularRowLabel);
			
			var inputFieldWrapper = document.createElement("div");
			inputFieldWrapper.classList.add("input-group");
			inputFieldWrapper.classList.add("formular-input-group");
			
			var textboxInputGroup = document.createElement("div");
			textboxInputGroup.classList.add("input-group");
			var textboxInput = document.createElement("input");
			textboxInput.type = "text";
			textboxInput.classList.add("form-control");
			textboxInput.classList.add("width100");
			textboxInput.id = data[0];
			textboxInput.name = data[0];
			textboxInput.value = data[4];
			textboxInput.required = false;
			if(data[3] == "Mandatory"){
				textboxInput.required = true;
			}
			else if(data[3]=="Date"){
				textboxInput.type = "date";
			}
			else if(data[3]=="Numeric"){
				textboxInput.type = "number";
			}
			else{
				if(data[3]=="Email"){
					textboxInput.type = "email";
				}
			}
			inputFieldWrapper.appendChild(textboxInput);
			
			newFormGroup.appendChild(formularRowLabel);
			newFormularRow.appendChild(newFormGroup);
			newFormularRow.appendChild(inputFieldWrapper);
		}
		// If the current element is a checkbox then create checkbox row elements and
		// append them to the current row
		else if(data[2] == "Checkbox"){

			var newFormGroup = document.createElement("div");
			newFormGroup.classList.add("form-check");
			newFormGroup.classList.add("formular-label-group");
			elementLabelText = data[1];
			var formularRowLabel = document.createElement("label");
			formularRowLabel.classList.add("control-label");
			formularRowLabel.classList.add("formular-element-label");
			
			if(data[3] == "Mandatory"){
				elementLabelText+="*";
			}
			elementLabelText+=":";
			formularRowLabel.innerHTML = elementLabelText;
			formularRowLabel.id = data[0];
			
			newFormGroup.classList.add("form-group");
			newFormGroup.classList.add("formular-checkbox-group");
			newFormGroup.appendChild(formularRowLabel);
			
			var checkboxButtonWrapper = document.createElement("div");

			checkboxButtonWrapper.classList.add("btn-group");
			checkboxButtonWrapper.classList.add("formular-checkbox");
			var checkboxButtonInput = document.createElement("input");
			checkboxButtonInput.type = "checkbox";
			checkboxButtonInput.id = data[0];
			checkboxButtonInput.name = data[0];
			checkboxButtonInput.checked = true;
			if(data[4]=="unchecked"){
				checkboxButtonInput.checked = false;
			}

			checkboxButtonInput.required = false;
			if(data[3]=="Mandatory"){
				checkboxButtonInput.required = true;
			}
			
			checkboxButtonWrapper.appendChild(checkboxButtonInput);
			
			newFormularRow.appendChild(newFormGroup);
			newFormularRow.appendChild(checkboxButtonWrapper)
			
		}
		// If the current element is Radio buttons element create it
		else{
			if(data[2] == "Radio buttons"){

				var newFormGroup = document.createElement("div");
				newFormGroup.classList.add("form-group");
				newFormGroup.classList.add("formular-row-label-group");
				
				elementLabelText = data[1];
				var formularRowLabel = document.createElement("label");
				formularRowLabel.classList.add("control-label");
				formularRowLabel.classList.add("formular-element-label");
				
				if(data[3] == "Mandatory"){
					elementLabelText+="*";
				}
				elementLabelText+=":";
				formularRowLabel.innerHTML = elementLabelText;
				formularRowLabel.id = data[0];
				newFormGroup.appendChild(formularRowLabel);
				newFormularRow.appendChild(newFormGroup);
				
				/* then search through the dataArray again for any Radio element which 
				 * might be child of Radio buttons element, then group them and append 
				 * this Radio buttons element together with these Radio elements to 
				 * the current row*/
				var radioButtonGroup = document.createElement("div");
				radioButtonGroup.classList.add("btn-group");
				for(var j = i+1; j < dataArray.length; j++){
					var subdata = dataArray[j].split("|");
					if(data[0] == subdata[5]){
						var radioButtonWrapper = document.createElement("div");
						radioButtonWrapper.classList.add("radio");
						radioButtonWrapper.classList.add("formular-radio");
						var radioButtonLabel = document.createElement("label");
						var radioButtonInput = document.createElement("input");
						radioButtonInput.type = "radio";
						
						if(subdata[4]=="checked"){
							radioButtonInput.setAttribute("checked", true);
						}
						if(data[3] == "Mandatory"){
							radioButtonInput.required = true;
						}
						radioButtonInput.name = data[1];
						radioButtonInput.value = subdata[1];
						radioButtonInput.id = subdata[0];
						//innerHTML, Name, checked,validation and id
						radioButtonLabel.appendChild(radioButtonInput);
						radioButtonLabel.innerHTML += subdata[1];
						
						radioButtonWrapper.appendChild(radioButtonLabel);
						radioButtonGroup.appendChild(radioButtonWrapper);
						newFormularRow.appendChild(radioButtonGroup);
						
					}
				}
				
			}
			
		}
		// Each row containing Radio buttons, checkbox or textbox should be added 
		// to the current formular. Rows containing only Radio elements will not 
		// be added.  
		if(data[2] != "Radio"){
			
			elementLabelText = "";
			formularForm.insertBefore(newFormularRow,formularForm.lastElementChild.previousElementSibling);
		}
		
	}
	
}

/* With this function we will validate input fields and send input data to server via 
 * ajax */
function saveFormular(){

	var documentForms = document.getElementsByTagName("form");
	var formularSearchForm = documentForms[2];
	var formularForm = documentForms[3];
	var formularSearchButton = formularSearchForm.getElementsByTagName("button")[0];
	var formularSaveButton = formularForm.lastElementChild.getElementsByTagName("button")[0];
	
	
	var formularSearchSelect = formularSearchForm.getElementsByTagName("select")[0];
	var formularID = parseInt(formularSearchSelect.options[formularSearchSelect.selectedIndex].value);
	
	var formularVersionInput = formularSearchForm.getElementsByTagName("input")[0];
	var formularVersion = formularVersionInput.value;
	
	var validationOK = true;

	formularVersionInput.setAttribute("required", "");
	formularVersionInput.parentElement.classList.remove("has-error");
	
	if(formularVersion.trim() == ""){
		validationOK &= false;
		formularVersionInput.parentElement.classList.add("has-error");
		formularSearchForm.checkValidity();
	}

	var formularRows = formularForm.getElementsByClassName("row");
	for(var i = 0; i < formularRows.length-1; i++){
		var formularLabel = formularRows[i].getElementsByTagName("label")[0];
		var formularInput = formularRows[i].getElementsByTagName("input")[0];

		formularVersionInput.parentElement.classList.remove("has-error");
		if(formularInput.type == "text" || formularInput.type == "number" || formularInput.type == "date" || formularInput.type == "email"){
			formularInput.parentElement.classList.remove("has-error");
			if(formularInput.required){
				if(formularInput.value.trim() == ""){
					formularInput.parentElement.classList.add("has-error");
					validationOK &= false;
				}
				
			}
			else{
				// In this section we will test if the user has entered an email in email 
				// field if there is one, because in that case this input field  
				// should only contain an email, and nothing else
				if(formularInput.type == "email"){
					var regexEmail = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
					
					if (!regexEmail.test(formularInput.value)) {
						formularInput.parentElement.classList.add("has-error");
						validationOK &= false;
					}
				}
			}
		}
		else if(formularInput.type == "checkbox"){
			formularInput.parentElement.classList.remove("has-error");
			if(formularInput.required){

				if(!formularInput.checked){
					validationOK &= false;
					formularInput.parentElement.classList.add("has-error");
				}
			}
		}
		else{
			var formularInputs = formularRows[i].getElementsByTagName("input");
			if(formularInputs[0].required){
				var radioValidation = false;
				for(var j = 0; j < formularInputs.length; j++){
					formularInputs[j].parentElement.classList.remove("has-error");
					if(formularInputs[j].checked){
						formularInputs[j].parentElement.classList.add("has-error");
						radioValidation |= true;
					}
				}
				validationOK &= radioValidation;
			}
		}
	}
	// If validation is ok then we will send an ajax request with all necessary data
	// to server
	if(validationOK){
		if(confirm("Are you sure that you want to save this formular?")){
			formularSaveButton.disabled = true;
			formularSearchButton.disabled = true;
			
			var xhttp;
			if (window.XMLHttpRequest) {
				xhttp = new XMLHttpRequest();
			} 
			else {
				xhttp = new ActiveXObject('Microsoft.XMLHTTP');
			}
			
			var url = "http://localhost:8080/FormularProject/formular";
			
			// In the section below  we will get the data from the form
			var params = "formularId="+formularID+"&formularVersion="+formularVersion;
			var paramElementID = 0;
			var formularRows = formularForm.getElementsByClassName("row");
			for(var i = 0; i < formularRows.length-1; i++){
				var formularLabel = formularRows[i].getElementsByTagName("label")[0];
				var formularInput = formularRows[i].getElementsByTagName("input")[0];
				
				if(formularInput.type == "text" || formularInput.type == "number" || formularInput.type == "email" || formularInput.type == "date"){
					params += "&element"+paramElementID+"ID="+formularInput.id+"&element"+paramElementID+"Value="+formularInput.value;
	
					paramElementID++;
				}
				else if(formularInput.type == "checkbox"){
					params += "&element"+paramElementID+"ID="+formularInput.id+"&element"+paramElementID+"Value=";
					if(formularInput.checked){
						params += "checked";
					}
					else{
						params += "unchecked";
					}
	
					paramElementID++;
				}
				else{
					var formularInputs = formularRows[i].getElementsByTagName("input");
					for(var j = 0; j < formularInputs.length; j++){
						params += "&element"+paramElementID+"ID="+formularInputs[j].id+"&element"+paramElementID+"Value=";
						
						if(formularInputs[j].checked){
							params += "checked";
						}
						else{
							params += "unchecked";
						}
	
						paramElementID++;
					}
				}
			}
			
			//Now we will send ajax request and display the appropriate alert message
			xhttp.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					alert("Formular is saved!");
					formularSaveButton.disabled = false;
					formularSearchButton.disabled = false;
				}
			};
			
			xhttp.open('POST', url, true);
			xhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
			xhttp.send(params);
		}
	}
	else{
		alert("Please check your inputs before submitting!");
	}
}

function deleteFormular(){
	if(confirm("Are you sure that you want to delete this formular?")){
		var documentForms = document.getElementsByTagName("form");
		var formularSearchForm = documentForms[2];
		var formularForm = documentForms[3];
		
		var formularSearchSelect = formularSearchForm.getElementsByTagName("select")[0];
		var formularID = parseInt(formularSearchSelect.options[formularSearchSelect.selectedIndex].value);
		var formularRows = formularForm.getElementsByClassName("row");
		var xhttp;
		if (window.XMLHttpRequest) {
			xhttp = new XMLHttpRequest();
		} 
		else {
			xhttp = new ActiveXObject('Microsoft.XMLHTTP');
		}
		
		var url = "http://localhost:8080/FormularProject/formular";
		
		// In the section below  we will get the data from the form
		var params = "action=delete&formularId="+formularID;
		xhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				alert("Formular is deleted!");
				formularSearchSelect.removeChild(formularSearchSelect.options[formularSearchSelect.selectedIndex]);
				
				for(var i = 0; i < formularRows.length; i++){
					formularForm.removeChild(formularForm.firstElementChild);
				}
			}
		};
		
		xhttp.open('POST', url, true);
		xhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
		xhttp.send(params);
	}
}