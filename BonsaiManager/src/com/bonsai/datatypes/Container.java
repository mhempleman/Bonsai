package com.bonsai.datatypes;




/*The Container class stores the container type of the parent tree in a string.
 * 
 * @param _container the container type (string)
 */
public class Container extends Datatype {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String _container;
	
	public Container(String container)
	{
		this._container = container;
	}
	
	public void set_name(String container)
	{	
		this._container = container;
	}
	
	@Override
	public String toString()
	{
		return _container;
	}

}
