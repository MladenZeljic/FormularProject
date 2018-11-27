// The function that is executed when user clicks on one of the page tabs.

function showSelectedView(element){
	
	/* It is presumed that, at the time of activation of this function, the page
	 * consists of n tabs and n views. One view corresponds to one tab only, and
	 * it can only be displayed when user clicks on appropriate tab. */
	
	// Get the lists of page views and tabs and lengths of those lists
	var views = document.getElementById("views").children;
	var tabs = document.getElementById("tabs").getElementsByTagName('a');
	var numberOfTabs = tabs.length;
	var numberOfViews = views.length;
	
	/* If we have the same number of tabs and lists on our page, we can proceed with
	 * this function */
	
	if(numberOfTabs === numberOfViews){
		
		/* Iterate through views and if one of the views is visible, hide it and 
		 * end this loop. */
		
		for(var i = 0; i < numberOfViews; i++){
			if(views[i].classList.contains('tab-view')){
				views[i].classList.remove('tab-view');
				views[i].classList.add('tab-view-hide');
				break;
			}
		}
		
		/* Iterate through tabs and if one of the tabs is highlighted, 
		 * unhighlight it and end this loop */
		
		for(var i = 0; i < numberOfTabs; i++){
			if(tabs[i].classList.contains('tab-selected')){
				tabs[i].classList.remove('tab-selected');
				break;
			}
		}
		
		/* Iterate through tabs. If the id of clicked tab is the same as the id of
		 * one of the tabs in tabs list, show clicked tab (highlight it really), 
		 * and also show its appropriate view. */
		
		for(var i = 0; i < numberOfTabs; i++){
			if(element.id === tabs[i].id){
				element.classList.add('tab-selected');
				views[i].classList.remove('tab-view-hide');
				views[i].classList.add('tab-view');
				break;
			}
		}
	}
	else{
		
		/* Else we will alert user that some of the tabs or views on his/hers page 
		 * are missing. */
		
		alert("Bad HTML: Tab or tab view missing!");
	}
}

function searchForFormulars(){
	
	/* Using this method, user will send ajax request to server which will be
	 * processed and sent back as a response. Then the current page will be 
	 * updated without reload. */
	
	/* By using this object (xhttp) we will make ajax request. */
	
	var xhttp;
	if (window.XMLHttpRequest) {
		xhttp = new XMLHttpRequest();
	} 
	else {
		xhttp = new ActiveXObject('Microsoft.XMLHTTP');
	}
	
	/* In this section we will get a search form, which is the first form on 
	 * the current page. Then we will get its input field which is where we
	 * type the name of the form which we intend to search form, and then 
	 * we wil obtain its value. */
	
	var documentForms = document.getElementsByTagName("form");
	var searchForm = documentForms[0];
	
	var searchInput = searchForm.getElementsByTagName("input")[0];
	var searchValue = searchInput.value;
	/****************************************************/
	
	/* url variable will contain url string which when used in ajax will 
	 * point to index servlet which will handle that ajax request. params
	 * variable will contain search value, and it will be set to index 
	 * servlet. */
	
	var url = "http://localhost:8080/FormularProject/index";
	var params = 'searchValue='+searchValue;
	
	/***************************************************/
	
	/* When server response is ready and if user has typed something (except for
	 * empty string), show him/her formular form. If formular has been found,
	 * handle the response data. In it each element row will be separated with 
	 * <> symbol, and row data will be separated by | symbol). */
	
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var formularForm = documentForms[1];
			
			if(searchValue.trim() !== ""){
				
				formularForm.classList.remove("form-hidden");
			}
			
			if(this.responseText !== "Formular has not been found!"){
				createForm(this.responseText);
			}
			else{
				var formularRows = formularForm.getElementsByClassName("row");
				for(var i = formularRows.length-2; i >= 0; i--){
					formularForm.removeChild(formularRows[i]);
				}
				var formularRowElementLabel = formularRows[formularRows.length-1].getElementsByTagName("label")[0];
				formularRowElementLabel. innerHTML = "Element 1:"
			}
		}
	};
	
	xhttp.open('POST', url, true);
	xhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	xhttp.send(params); 
	
}

function changeValidations(typesSelectElement){
	
	/* This function will be called when user changes his/hers selected input type 
	 * option. It that option is anything but textbox, validation options will be
	 * modified, to contain only None and Mandatory validation options. If the user
	 * changes back to textbox seletion, validation options will be modified again
	 * by adding Numeric, Email and Date options. */
	
	var typesSelectElementWrapper = typesSelectElement.parentElement;
	var validationsSelectElementWrapper = typesSelectElementWrapper.nextElementSibling.nextElementSibling;
	var validationsSelectElement = validationsSelectElementWrapper.firstElementChild;
	var validationOptions = validationsSelectElement.options;
	for(var i = validationOptions.length - 1; i >= 0; i--){
		if(validationOptions[i].value != "None" && validationOptions[i].value != "Mandatory"){
			validationsSelectElement.remove(i);
		}
	}
	if(typesSelectElement.options[typesSelectElement.selectedIndex].value == "Textbox"){
	
		var numericOption = document.createElement("option");
		numericOption.innerHTML = "Numeric";
		numericOption.value = "Numeric";
		var emailOption = document.createElement("option");
		emailOption.innerHTML = "Email";
		emailOption.value = "Email";
		var dateOption = document.createElement("option");
		dateOption.innerHTML = "Date";
		dateOption.value = "Date";
		validationsSelectElement.appendChild(numericOption);
		validationsSelectElement.appendChild(emailOption);
		validationsSelectElement.appendChild(dateOption);
		
	}
	validationsSelectElement.selectedIndex = 0;
}

function addElement(){
	/* In this function we will handle situations when user clicks on + button. */
	
	var documentForms = document.getElementsByTagName("form");
	var formularForm = documentForms[1];
	var formularButtons = formularForm.getElementsByTagName("button");
	var saveFormularButton = formularButtons[formularButtons.length-1];
	var saveFormularButtonWrapper = saveFormularButton.parentElement;
	var formularRow = saveFormularButtonWrapper.previousElementSibling.previousElementSibling;
	var elementLabel = formularRow.getElementsByTagName("label")[0];
	var elementLabelNumber = parseInt(elementLabel.innerHTML.replace(/\D/g, ""));
	
	/* Since we will create new element, elementLabelNumber should be incremented. */
	elementLabelNumber++;
	/* Now we will clone current formularRow.  */
	var newFormularRow = formularRow.cloneNode(true);
	
	/* In these two lines, we will set old row buttons onclick atribute to call
	 * removeElement function and then we will set its symbol from + to -.  */
	formularRow.getElementsByTagName("button")[0].setAttribute("onclick","removeElement(this)");
	formularRow.getElementsByTagName("button")[0].innerHTML = "&#8722;";
	
	/*Because we'll get exactly the same row as the current one, we should tidy 
	 * it up.*/
	
	/* Now we will set label text for this new elements label, and we will clean up
	 * its input field by setting its value to empty string. */
	
	var newElementLabelText = "Element "+elementLabelNumber+":";
	newFormularRow.getElementsByTagName("label")[0].innerHTML = newElementLabelText;
	newFormularRow.getElementsByTagName("label")[0].htmlFor = "element"+elementLabelNumber;
	newFormularRow.getElementsByTagName("input")[0].value = "";
	newFormularRow.getElementsByTagName("input")[0].id = "element"+elementLabelNumber;
	newFormularRow.getElementsByTagName("input")[0].name = "element"+elementLabelNumber;

	newFormularRow.getElementsByTagName("select")[0].name = "element"+elementLabelNumber+"-selectType";
	newFormularRow.getElementsByTagName("select")[1].name = "element"+elementLabelNumber+"-selectRadioNumber";
	newFormularRow.getElementsByTagName("select")[2].name = "element"+elementLabelNumber+"-selectValidation";
	
	/* The next thing that we should do is to set new elements select fields selected 
	 * option to first option. We will also hide second select field, because it should 
	 * not be shown unless user selects radio buttons option on first select. */

	newFormularRow.getElementsByTagName("select")[0].selectedIndex = 0;
	newFormularRow.getElementsByTagName("select")[1].parentElement.classList.add("radio-number-hide");
	newFormularRow.getElementsByTagName("select")[1].selectedIndex = 0;
	newFormularRow.getElementsByTagName("select")[2].selectedIndex = 0;
	
	 /* If the user previously selected anything but textfield in the first 
	  * select, the third select will contain only None and Mandatory validation. Now,
	  * because the new elements first select fields selected option will be textfield,
	  * we should add three other validation options, like Numeric, Email and Date in
	  * the third select field. */
	
	if(newFormularRow.getElementsByTagName("select")[2].options.length == 2){
		var numericOption = document.createElement("option");
		numericOption.innerHTML = "Numeric";
		numericOption.value = "Numeric";
		var emailOption = document.createElement("option");
		emailOption.innerHTML = "Email";
		emailOption.value = "Email";
		var dateOption = document.createElement("option");
		dateOption.innerHTML = "Date";
		dateOption.value = "Date";
		newFormularRow.getElementsByTagName("select")[2].appendChild(numericOption);
		newFormularRow.getElementsByTagName("select")[2].appendChild(emailOption);
		newFormularRow.getElementsByTagName("select")[2].appendChild(dateOption);
	}
	
	
	/* Now we should remove new elements radio button input fields which might have been
	 * shown in the old element, except for the first and the second one, which will be 
	 * hidden. */
	
	while(newFormularRow.getElementsByClassName("radio-input-group").length > 2){
		newFormularRow.removeChild(newFormularRow.lastChild);
	}
	
	/* We will also update id and the name of the first radio button label input */
	newFormularRow.getElementsByTagName("input")[1].parentElement.parentElement.parentElement.classList.add("radio-input-hidden");
	newFormularRow.getElementsByTagName("input")[1].id = "radioelement"+elementLabelNumber+"-labelinput1";
	newFormularRow.getElementsByTagName("input")[1].name = "radioelement"+elementLabelNumber+"-labelinput1";
	newFormularRow.getElementsByTagName("input")[2].parentElement.parentElement.parentElement.classList.add("radio-input-hidden");
	newFormularRow.getElementsByTagName("input")[2].id = "radioelement"+elementLabelNumber+"-labelinput2";
	newFormularRow.getElementsByTagName("input")[2].name = "radioelement"+elementLabelNumber+"-labelinput2";
	
	// Finally we will add the new row in our form right before save button.
	formularForm.insertBefore(newFormularRow, saveFormularButtonWrapper.previousElementSibling);
	
}

// With this function we will remove one row from formular form. 
//This function activates when user clicks on button
function removeElement(button){
	var row = button.parentElement.parentElement;
	var documentForms = document.getElementsByTagName("form");
	var formularForm = documentForms[1];
	// With this line we will remove selected row
	formularForm.removeChild(row);
	
	// When this row is removed we will set up all other rows labels, so that 
	//there are no "gaps" on the form
	var formularLabels = formularForm.getElementsByTagName("label");
	for(var i = 0; i < formularLabels.length; i++){
		formularLabels[i].innerHTML = "Element "+(i+1)+":";
	}
}


function showRadioLabels(typesSelectElement){
	var typesElementWrapper = typesSelectElement.parentElement;
	var radioNumberSelect = typesElementWrapper.nextElementSibling;
	var firstRadioLabelInputGroup = typesElementWrapper.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling;
	var secondRadioLabelInputGroup = firstRadioLabelInputGroup.nextElementSibling;
	firstRadioLabelInputGroup.classList.add("radio-input-hidden");
	secondRadioLabelInputGroup.classList.add("radio-input-hidden");
	radioNumberSelect.classList.add("radio-number-hide");
	if(typesSelectElement.options[typesSelectElement.selectedIndex].value == "Radio buttons"){
		firstRadioLabelInputGroup.classList.remove("radio-input-hidden");
		secondRadioLabelInputGroup.classList.remove("radio-input-hidden");
		radioNumberSelect.classList.remove("radio-number-hide");
	}
	else{
		var formularRow = secondRadioLabelInputGroup.parentElement;
		while(secondRadioLabelInputGroup.nextElementSibling != null){
			formularRow.removeChild(formularRow.lastChild);
		}
	}
}

/* This function will be called when user changes radio number option on dedicated
 * select field. This function will determine how many new input fields are to be 
 * created or how many old inputs fields are to be removed, depending on selected 
 * option. It will also update input fields ids names and other parameters accordingly. */
function changeRadioLabels(radioLabelsSelect){
	var radioLabelInputGroup = radioLabelsSelect.parentElement.nextElementSibling.nextElementSibling.nextElementSibling;
	var currentElementRow = radioLabelInputGroup.parentElement;
	
	var numberOfRadioLabelInputGroups = currentElementRow.getElementsByClassName("radio-input-group").length;
	var numberOfLabelInputs = parseInt(radioLabelsSelect.options[radioLabelsSelect.selectedIndex].value);
	
	/* If the number of current radio label input fields is greater than the number of 
	 * input fields selected on selection field, then we will need to remove excess
	 * radio label input fields, else we will need to add required radio label input
	 * fields.*/
	
	if(numberOfRadioLabelInputGroups > numberOfLabelInputs){
		var itemsToRemove = numberOfRadioLabelInputGroups - numberOfLabelInputs;
		while(itemsToRemove > 0){
			currentElementRow.removeChild(currentElementRow.lastChild);
			itemsToRemove--;
		}
	}
	else{
		var itemsToAdd = numberOfLabelInputs - numberOfRadioLabelInputGroups;
		var oldLabelInputName = radioLabelInputGroup.getElementsByTagName("input")[0].name;
		var oldLabelInputNumber = parseInt(oldLabelInputName.split("-")[1].replace(/\D/g, ""));
		while(itemsToAdd > 0){
			var newRadioLabelInputGroup = radioLabelInputGroup.cloneNode(true);
			oldLabelInputNumber++;
			
			// Change new radio label inputs value, id and name
			var newRadioLabelInput = newRadioLabelInputGroup.getElementsByTagName("input")[0];
			newRadioLabelInput.value = "";
			newRadioLabelInput.id = oldLabelInputName.split("-")[0]+"-labelinput"+oldLabelInputNumber;
			newRadioLabelInput.name = oldLabelInputName.split("-")[0]+"-labelinput"+oldLabelInputNumber;
			currentElementRow.appendChild(newRadioLabelInputGroup);
			itemsToAdd --;
			
		}
	}
}

/* This function will create new formular, with all necessary DOM elements. */
function createForm(responseString){
	
	var documentForms = document.getElementsByTagName("form");
	var formularForm = documentForms[1];
	
	/* This function will get a response string as a parameter. Response string is made 
	 * in Administration servlet and each element is separated by <> sign. Each element
	 * property is separated by pipe | char.*/
	
	/* In this line we will get an array of elements, simply by splitting a response 
	 * string by <>. */
	var dataArray = responseString.split("<>");
	
	// Next, we will flush our formular to get rid of other possible formular rows.
	// Except for the last row which we will use to create new rows in this formular.
	var formularRowsNumber = formularForm.getElementsByClassName("row").length;
	for(var i = 0; i < formularRowsNumber - 1; i++){
		formularForm.removeChild(formularForm.firstElementChild);
	}
	
	// Next, we need to set remaining rows input, select and button field parameters 
	// like id, name and etc., so that it can be propperly used in the next steps. 
	var firstFormularRow = formularForm.firstElementChild;
	var firstElementLabelInput = firstFormularRow.getElementsByTagName("input")[0];
	var firstRowLabel = firstFormularRow.getElementsByTagName("label")[0];
	var firstRowTypeSelect = firstFormularRow.getElementsByTagName("select")[0];
	var firstRowRadioNumberSelect = firstFormularRow.getElementsByTagName("select")[1];
	var firstRowValidationSelect = firstFormularRow.getElementsByTagName("select")[2];
	var firstRowRadioLabelInput = firstFormularRow.getElementsByTagName("input")[1];
	firstElementLabelInput.id = "element1";
	firstElementLabelInput.name = "element1";
	firstRowLabel.innerHTML = "Element 1:";
	firstRowLabel.htmlFor = "element1";
	firstRowTypeSelect.id = "element1-selectType";
	firstRowTypeSelect.name = "element1-selectType";
	firstRowRadioNumberSelect.id = "element1-selectRadioNumber";
	firstRowRadioNumberSelect.name = "element1-selectRadioNumber";
	firstRowValidationSelect.id = "element1-selectValidation";
	firstRowValidationSelect.name = "element1-selectValidation";
	firstRowRadioLabelInput.id = "radioelement1-labelinput1";
	firstRowRadioLabelInput.name = "radioelement1-labelinput1";

	// Next we will iterate through our elements array
	for(var i = 0; i < dataArray.length-1; i++){
		// In this line we will split each formular element string into data array 
		// which will contain all data of current formular element.
		var data = dataArray[i].split("|");
		
		// If this current elements type is not Radio
		if(data[2] != "Radio"){
			
			// We will get the last row of this formular (this is the row which we)
			// previously cleared in one of the previous steps
			var formularRows = formularForm.getElementsByClassName("row");
			var formularRowsLength = formularRows.length;
			var lastFormularRow = formularRows[formularRowsLength-1];
			
			// We will get the label number of that row
			var rowLabel = lastFormularRow.getElementsByTagName("label")[0];
			var rowLabelNumber = parseInt(rowLabel.innerHTML.replace(/\D/g, ""));
			
			// We will clone that row
			var newFormularRow = lastFormularRow.cloneNode(true);
			
			// We will get all subelements of the newly cloned row which are needed now
			var newElementLabelInput = newFormularRow.getElementsByTagName("input")[0];
			var newRowLabel = newFormularRow.getElementsByTagName("label")[0];
			var newRowTypeSelect = newFormularRow.getElementsByTagName("select")[0];
			var newRowRadioNumberSelect = newFormularRow.getElementsByTagName("select")[1];
			var newRowValidationSelect = newFormularRow.getElementsByTagName("select")[2];
			var firstNewRowRadioLabelInput = newFormularRow.getElementsByTagName("input")[1];
			var secondNewRowRadioLabelInput = newFormularRow.getElementsByTagName("input")[2];
			
			// We will increase row number by one and we will set parameters
			// of all needed subelements of the new row.
			newElementLabelInput.id = "element"+(rowLabelNumber + 1);
			newElementLabelInput.name = "element"+(rowLabelNumber + 1);
			newRowLabel.innerHTML = "Element "+(rowLabelNumber+1)+":";
			newRowLabel.htmlFor = "element"+(rowLabelNumber + 1);
			newRowTypeSelect.id = "element"+(rowLabelNumber + 1)+"-selectType";
			newRowTypeSelect.name = "element"+(rowLabelNumber + 1)+"-selectType";
			newRowRadioNumberSelect.id = "element"+(rowLabelNumber + 1)+"-selectRadioNumber";
			newRowRadioNumberSelect.name = "element"+(rowLabelNumber + 1)+"-selectRadioNumber";
			newRowValidationSelect.id = "element"+(rowLabelNumber + 1)+"-selectValidation";
			newRowValidationSelect.name = "element"+(rowLabelNumber + 1)+"-selectValidation";
			firstNewRowRadioLabelInput.id = "radioelement"+(rowLabelNumber + 1)+"-labelinput1";
			firstNewRowRadioLabelInput.name = "radioelement"+(rowLabelNumber + 1)+"-labelinput1";
			secondNewRowRadioLabelInput.id = "radioelement"+(rowLabelNumber + 1)+"-labelinput2";
			secondNewRowRadioLabelInput.name = "radioelement"+(rowLabelNumber + 1)+"-labelinput2";
			
			// Now we will use the other row and we will set its subelements properties
			var elementLabelInput = lastFormularRow.getElementsByTagName("input")[0];
			var rowTypeSelect = lastFormularRow.getElementsByTagName("select")[0];
			var rowRadioNumberSelect = lastFormularRow.getElementsByTagName("select")[1];
			var rowValidationSelect = lastFormularRow.getElementsByTagName("select")[2];
			var rowButton = lastFormularRow.getElementsByTagName("button")[0];
			
			// Seting the other rows input field and label properties
			elementLabelInput.id = data[0];
			elementLabelInput.value = data[1];
			rowLabel.htmlFor = data[0];
		
			// Setting the other rows selection fields
			for(var j = 0; j < rowTypeSelect.options.length; j++){
				if(rowTypeSelect.options[j].innerHTML == data[2]){
					rowTypeSelect.selectedIndex = j;
					rowTypeSelect.onchange();
					break;
				}
			}
		
			for(var j = 0; j < rowValidationSelect.options.length; j++){
				if(rowValidationSelect.options[j].innerHTML == data[3]){
					rowValidationSelect.selectedIndex = j;
					break;
				}
			}
			
			// setting the other rows button. We will also change its onclick function
			rowButton.setAttribute("onclick","removeElement(this)");
			rowButton.innerHTML = "&#8722;";
			
		}
		// If the current element is Radio buttons element 
		if(data[2] == "Radio buttons"){
			/* Now we will iterate again through element array to get all Radio elements
			 * to whom this Radio buttons element is a parent. */
			lastFormularRow.removeChild(lastFormularRow.lastElementChild);
			for(var j = i+1; j < dataArray.length; j++){
				var subdata = dataArray[j].split("|");
				
				// If this is a radio element of the current Radio elements element
				if(data[0] == subdata[4]){
					// Create this element, set its properties, set the other rows radio number 
					// selection fields option and append it to the other row
					var currentElementLabel = lastFormularRow.getElementsByTagName("label");
					var currentElementLabelNumber = parseInt(currentElementLabel[0].innerHTML.replace(/\D/g, ""));
					var currentRowRadioLabelInputNumber = lastFormularRow.getElementsByClassName("radio-input-group").length;
					lastFormularRow.lastElementChild.getElementsByTagName("input")[0].name = "radioelement"+currentElementLabelNumber+"-labelinput"+currentRowRadioLabelInputNumber;
					
					var newRadioLabelInputGroup = lastFormularRow.lastElementChild.cloneNode(true);
					var radioLabelInput = lastFormularRow.lastElementChild.getElementsByTagName("input")[0];
					
					radioLabelInput.value = subdata[1];
					radioLabelInput.id= subdata[0];
					lastFormularRow.appendChild(newRadioLabelInputGroup);
					var radioInputsNumber = lastFormularRow.getElementsByClassName("radio-input-group").length;
					
					var selectedIndex = 0;
					for(k = 0; k < rowRadioNumberSelect.options.length ; k++){
						if(rowRadioNumberSelect.options[k].value == radioInputsNumber){
							selectedIndex = k;
							break;
						}
					}
					
						
					rowRadioNumberSelect.selectedIndex = selectedIndex;
					lastRadioIndex = j;
				}
			}
			// Next we will remove one excess radio label input field because it will be
			// created in the previous step. We will also decrement other rows radio 
			// number select field option
			var radioLabelInputNumber = lastFormularRow.getElementsByClassName("radio-input-group").length;
				if(radioLabelInputNumber > 2){
					var selectedIndex = rowRadioNumberSelect.selectedIndex;
					selectedIndex --;
					rowRadioNumberSelect.selectedIndex = selectedIndex;
					lastFormularRow.removeChild(lastFormularRow.lastElementChild);
			}
		}
		
		// Other row now contains all the data from this current element and it is
		// already appended to this formular (remember that we used it to make a 
		// new row). New row is not appended to this formular and in the next line
		// we will do that.
		if(data[2] != "Radio"){
			
			formularForm.insertBefore(newFormularRow, lastFormularRow.nextElementSibling);
		}
	}

}

/* This function will be called when user submits formular */
function submitFormular(){
	var documentForms = document.getElementsByTagName("form");
	var formularSearchForm = documentForms[0];
	var formularForm = documentForms[1];
	var formularRows = formularForm.getElementsByClassName("row");
	var formularSearchInput = formularSearchForm.getElementsByTagName("input")[0];
	var formularSearchButton = formularSearchForm.getElementsByTagName("button")[0];
	var formularSaveButton = formularForm.lastElementChild.getElementsByTagName("button")[0];
	
	// We will now validate all fields from this formular
	var validationOK = true;
	for (var i = 0; i < formularRows.length-1; i++){
		var rowInputs = formularRows[i].getElementsByTagName("input");
		var rowTypeSelect = formularRows[i].getElementsByTagName("select")[0];
		var rowRadioNumberSelect = formularRows[i].getElementsByTagName("select")[1];
		var numberOfRadios = parseInt(rowRadioNumberSelect.options[rowRadioNumberSelect.selectedIndex].value);
		var elementLabelInput = rowInputs[0];
		formularSearchInput.parentElement.classList.remove("has-error");
		if(formularSearchInput.value.trim() == ""){
			validationOK &= false;
			formularSearchInput.parentElement.classList.add("has-error");
		}
		
		elementLabelInput.parentElement.classList.remove("has-error");
		if(elementLabelInput.value.trim() == ""){
			validationOK &= false;
			elementLabelInput.parentElement.classList.add("has-error");
		}
		
		if(rowTypeSelect.options[rowTypeSelect.selectedIndex].value == "Radio buttons"){
			for(var j = 1; j <= numberOfRadios; j++){
				var radioLabelInput = rowInputs[j];
				radioLabelInput.parentElement.classList.remove("has-error");
				if(radioLabelInput.value.trim()== ""){
					radioLabelInput.parentElement.classList.add("has-error");
					validationOK &= false;
				}
			}
		}
	}
	// If the validation is successful we will submit ajax request and send the 
	// data from this form
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
			
			var url = "http://localhost:8080/FormularProject/index";
			var params = "formularName="+formularSearchInput.value;
			
			for(var i = 0; i < formularRows.length-1; i++){
				var rowInputs = formularRows[i].getElementsByTagName("input");
				var rowSelects = formularRows[i].getElementsByTagName("select");
				params+="&"+rowInputs[0].name+"ID="+rowInputs[0].id;
				params+="&"+rowInputs[0].name+"="+rowInputs[0].value;
				params+="&"+rowSelects[0].name+"="+rowSelects[0].options[rowSelects[0].selectedIndex].value;
				params+="&"+rowSelects[1].name+"="+rowSelects[1].options[rowSelects[1].selectedIndex].value;
				params+="&"+rowSelects[2].name+"="+rowSelects[2].options[rowSelects[2].selectedIndex].value;
				for(j = 1; j < rowInputs.length; j++){
					params+="&"+rowInputs[j].name+"ID="+rowInputs[j].id;
					params+="&"+rowInputs[j].name+"="+rowInputs[j].value;
				}
			}
			
			xhttp.onreadystatechange = function() {
				if (this.readyState == 4) {
					updateFormularSelect();
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
	// Else alert user that something is not right.
	else{
		alert("Please check your inputs before submitting!");
	}
	
	
}

function updateFormularSelect(){
	var documentForms = document.getElementsByTagName("form");
	var formularForm = documentForms[2];
	var formularSelect = formularForm.getElementsByTagName("select")[0];
	
	var xhttp;
	if (window.XMLHttpRequest) {
		xhttp = new XMLHttpRequest();
	} 
	else {
		xhttp = new ActiveXObject('Microsoft.XMLHTTP');
	}
	
	var url = "http://localhost:8080/FormularProject/index";
	var params = "action=updateSelect";
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var dataArray = this.responseText.split("<>");
			
			while(formularSelect.firstElementChild != null){
				formularSelect.removeChild(formularSelect.firstElementChild);
			}
			for(var i = 0; i < dataArray.length; i++){
				var data = dataArray[i].split("|");
				var formularSelectOption = document.createElement("option");
				formularSelectOption.id = data[0];
				formularSelectOption.value = data[0];
				formularSelectOption.innerHTML = data[1];
				formularSelect.appendChild(formularSelectOption);
			}
		}
	};

	xhttp.open('POST', url, true);
	xhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	xhttp.send(params); 

}