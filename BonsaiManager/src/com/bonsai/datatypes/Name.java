package com.bonsai.datatypes;



/*The name class stores the tree name
 * 
 * @param _name the tree name
 */
public class Name extends Datatype {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String _name;
	
	public Name(String name)
	{
		this._name = name;
	}
	
	public void set_name(String name)
	{	
		this._name = name;
	}
	
	@Override
	public String toString()
	{
		return this._name;
	}

}

