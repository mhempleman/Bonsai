package com.bonsai.datatypes;



/*The Width class stores the tree width as a String
 * 
 * @param _width the tree width 
 */
public class Width  extends Datatype {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String _width;
	
	public Width(String width)
	{
		set_width(width);
	}
	
	public String get_width()
	{
		return this._width;
	}
	
	public void set_width(String width)
	{
		
			this._width = width;
		
	}
	
	@Override
	public String toString()
	{
		return "" + _width;
	}

}
