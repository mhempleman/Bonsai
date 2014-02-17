package com.bonsai.datatypes;




/*The Height class stores the tree height in a String
 * 
 * @param _height the tree height (String)
 */
public class Height extends Datatype {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String _height;
	
	public Height(String height)
	{
		set_height(height);
	}
	
	public String get_height()
	{
		return this._height;
	}
	
	public void set_height(String height)
	{
		
			this._height = height;
		
	}
	
	@Override
	public String toString()
	{
		return "" + _height;
	}

}
