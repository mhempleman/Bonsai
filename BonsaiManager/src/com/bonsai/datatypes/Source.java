package com.bonsai.datatypes;




/*The Source class stores the tree source in a string
 * 
 * @param _source the tree source (string)
 */
public class Source  extends Datatype {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String _source;
	
	public Source(String source)
	{
		this._source = source;
	}
	
	public void set_source(String source)
	{	
		this._source = source;
	}
	
	@Override
	public String toString()
	{
		return this._source;
	}

}
