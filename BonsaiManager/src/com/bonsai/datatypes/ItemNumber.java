package com.bonsai.datatypes;



public class ItemNumber extends Datatype {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int _itemNumber;
	
	public ItemNumber(int itemNumber)
	{
		set_itemNumber(itemNumber);
	}

	public int get_itemNumber() {
		return _itemNumber;
	}

	public void set_itemNumber(int _itemNumber) {
		this._itemNumber = _itemNumber;
	}
	
	@Override
	public String toString()
	{
		return this._itemNumber + "";
	}

}
