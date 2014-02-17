package com.bonsai.datatypes;

public class BasicTreeData extends Datatype implements Comparable<BasicTreeData> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6483677868965013986L;
	
	private int _itemNumber;
	private String _name;
	private String _group;
	
	public BasicTreeData(int itemNumber, String name, String group)
	{
		this.set_itemNumber(itemNumber);
		this.set_name(name);
		this.set_group(group);
	}

	public int get_itemNumber() {
		return _itemNumber;
	}

	public void set_itemNumber(int _itemNumber) {
		this._itemNumber = _itemNumber;
	}

	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}

	public String get_group() {
		return _group;
	}

	public void set_group(String _group) {
		this._group = _group;
	}

	@Override
	public int compareTo(BasicTreeData data) {
		
		if(this._group.equals(data._group))
			return this._name.compareTo(data._name);
		else if(this._group.compareTo(data._group) > 0)
			return 1;
		else
			return -1;
			
		
	}
	
	@Override
	public String toString()
	{
		return this._name;
	}

}
