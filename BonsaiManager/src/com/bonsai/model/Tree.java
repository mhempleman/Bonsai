package com.bonsai.model;

import java.io.Serializable;

import com.bonsai.datatypes.*;



/*
 * The Tree class stores all tree data
 * 
 * @param _itemNumber a unique integer for each tree
 * @param _name a unique identifying name for the tree
 * @param _acquiredDate date of acquisition
 * @param _container the trees container
 * @param _source the trees source
 * @param _height the height of the tree
 * @param _width the diameter of the tree
 * 
 */
public class Tree implements Comparable<Tree>, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ItemNumber _itemNumber;
	private Name _name;
	private Acquired _acquired;
	private BirthDate _birthDate;
	private Container _container;
	private Source _source;
	private Height _height;
	private Width _width;
	private Price _price;
	private Potted _potted;
	private SpeciesInfo _speciesInfo;
	
	// constructors 
	public Tree(ItemNumber itemNumber, Name name)
	{
		this._itemNumber = itemNumber;
		this._name = name;
	}
	
	 
	
	public Name get_name() {
		return _name;
	}
	
	public void set_name(Name name) {
		this._name = name;
	}
	
	public BirthDate get_birthDate() {
		return _birthDate;
	}
	
	public void set_birthDate(BirthDate bd) {
		this._birthDate = bd;
	}
	
	public Acquired get_acquiredDate() {
		return _acquired;
	}
	
	public void set_acquiredDate(Acquired acquired) {
		this._acquired = acquired;
	}

	public Container get_container() {
		return _container;
	}

	public void set_container(Container _container) {
		this._container = _container;
	}

	public Source get_source() {
		return _source;
	}

	public void set_source(Source _source) {
		this._source = _source;
	}

	public Height get_height() {
		return _height;
	}

	public void set_height(Height _height) {
		this._height = _height;
	}

	public Width get_width() {
		return _width;
	}

	public void set_width(Width _width) {
		this._width = _width;
	}
	

	public ItemNumber get_itemNumber() {
		return _itemNumber;
	}

	public void set_itemNumber(ItemNumber _itemNumber) {
		this._itemNumber = _itemNumber;
	}

	
	
	public Price get_price() {
		return _price;
	}

	public void set_price(Price _price) {
		this._price = _price;
	}
	
	public Potted get_potted() {
		return _potted;
	}

	public void set_potted(Potted _potted) {
		this._potted = _potted;
	}



	public SpeciesInfo get_speciesInfo() {
		return _speciesInfo;
	}



	public void set_speciesInfo(SpeciesInfo _speciesInfo) {
		this._speciesInfo = _speciesInfo;
	}
	
	@Override
	public String toString()
	{
		return this._name.toString();
	}
	
	public int compareTo(Tree tree)
	{
		
			return this.toString().compareTo(tree.toString());
		
			 
	}


	


	
	

	

	

	
	

}
