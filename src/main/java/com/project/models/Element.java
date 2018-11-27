package com.project.models;

//Model class for Element table in database
public class Element {
	
	private int _elementID;
	private String _elementLabel;
	private String _elementType;
	private String _elementValidation;
	private String _elementValue;
	private Element _parentElement;
	//One formular can contain many elements, but one element may be in only one formular (Formular) 1-N (Element)
	private Formular _formular;
	
	//Element constructor with parameters
	public Element(String elementLabel, String elementType, String elementValidation, String elementValue, Formular formular) {
		this._elementLabel = elementLabel;
		this._elementType = elementType;
		this._elementValidation = elementValidation;
		this._elementValue = elementValue;
		this._parentElement = null;
		this._formular = formular;
	}
	
	// Getters and setters for Element properties
	public int getElementID() {
		return this._elementID;
	}
	public void setElementID(int elementID) {
		this._elementID = elementID;
	}
	public String getElementLabel() {
		return this._elementLabel;
	}
	public void setElementLabel(String elementLabel) {
		this._elementLabel = elementLabel;
	}
	public String getElementType() {
		return this._elementType;
	}
	public void setElementType(String elementType) {
		this._elementType = elementType;
	}
	public String getElementValidation() {
		return this._elementValidation;
	}
	public void setElementValidation(String elementValidation) {
		this._elementValidation = elementValidation;
	}
	public String getElementValue() {
		return this._elementValue;
	}
	public void setElementValue(String elementValue) {
		this._elementValue = elementValue;
	}
	public Element getParentElement() {
		return this._parentElement;
	}
	public void setParentElement(Element parentElement) {
		this._parentElement = parentElement;
	}
	public Formular getFormular() {
		return this._formular;
	}
	public void setFormular(Formular formular) {
		this._formular = formular;
	}
}
