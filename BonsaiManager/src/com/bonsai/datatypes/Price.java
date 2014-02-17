package com.bonsai.datatypes;





/*The Price class stores the tree price as a double
 * 
 * @param _price the tree price 
 */
public class Price  extends Datatype {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String _price;
	
	public Price(String price)
	{
		set_price(price);
	}
	
	public String get_price()
	{
		return this._price;
	}
	
	public void set_price(String price)
	{
		
			this._price = price;
	
	}
	
	@Override
	public String toString()
	{
		
		return this._price;
	}

}
