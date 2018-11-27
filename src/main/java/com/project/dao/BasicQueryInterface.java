package com.project.dao;

import java.util.List;

public interface BasicQueryInterface {
	
	/* Interface that contains declarations of methods for some of the most used 
	 * queries (SELECT, INSERT, UPDATE and DELETE) in this application. DAO classes 
	 * in this project must implement this methods, in order to be able to perform 
	 * these operations. */
	
	public abstract List<Object> select();
	public abstract Object selectById(int id);
	public abstract long insert(Object insertObject);
	public abstract void update(Object oldObject,Object newObject);
	public abstract void delete(Object deleteObject);
}