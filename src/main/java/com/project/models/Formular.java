package com.project.models;

// Model class for Formular table in database
public class Formular {

	private int _formularID;
	private String _formularName;
	private String _formularVersion;
	
	//Formular constructor with parameters
	public Formular(String formularName, String formularVersion) {
		this._formularName = formularName;
		this._formularVersion = formularVersion;
	}
	
	//Getters and setters for Formular properties
	public int getFormularId() {
		return this._formularID;
	}
	public void setFormularId(int formularID) {
		this._formularID = formularID;
	}
	public String getFormularName() {
		return this._formularName;
	}
	public void setFormularName(String formularName) {
		this._formularName = formularName;
	}
	public String getFormularVersion() {
		return this._formularVersion;
	}
	public void setFormularVersion(String formularVersion) {
		this._formularVersion = formularVersion;
	}
}
