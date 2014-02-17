package com.bonsai.datatypes;





public class SpeciesInfo extends Datatype implements Comparable<SpeciesInfo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String _commonName;
	private String _species;
	private String _group;
	private String _type;
	private String _family;
	private String _genus;
	private String _cultivar;
	private String _otherNames;
	

	public String get_commonName() {
		return _commonName;
	}

	public void set_commonName(String _commonName) {
		this._commonName = _commonName;
	}

	public String get_species() {
		return _species;
	}

	public void set_species(String _species) {
		this._species = _species;
	}
	
	public void append_species(String species)
	{
		this._species += ", " + species;
	}

	public String get_group() {
		return _group;
	}

	public void set_group(String _group) {
		this._group = _group;
	}

	public String get_type() {
		return _type;
	}

	public void set_type(String _type) {
		this._type = _type;
	}

	public String get_family() {
		return _family;
	}

	public void set_family(String _family) {
		this._family = _family;
	}

	public String get_genus() {
		return _genus;
	}

	public void set_genus(String _genus) {
		this._genus = _genus;
	}

	public String get_cultivar() {
		return _cultivar;
	}

	public void set_cultivar(String _cultivar) {
		this._cultivar = _cultivar;
	}

	public String get_otherNames() {
		return _otherNames;
	}

	public void set_otherNames(String _otherNames) {
		this._otherNames = _otherNames;
	}
	
	@Override
	public String toString()
	{
		return this._commonName;
	}
	
	public int compareTo(SpeciesInfo info)
	{
		
		return this._commonName.compareTo(info._commonName);
		
			 
	}
	
}
