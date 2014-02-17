package com.bonsai.datatypes;


import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;


/*The Acquired class stores acquisition date of the tree
 * 
 * @param _day the day of acquisition 
 * @param _month the month of acquisition 
 * @param _year the year of acquisition 
 * 
 */

public class Acquired extends Datatype {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int _day;
	private int _month;
	private int _year;
	
	public Acquired(int day, int month, int year)
	{
		set_day(day);
		set_month(month);
		set_year(year);
		
	}
	
	public int get_day()
	{
		return this._day;
	}
	
	public void set_day(int day)
	{	
		// TODO test for day count of appropriate month
		if(day > 0 && day < 32) 
			this._day = day;
		else
			this._day = 1;
				
			
		
		
	}
	
	public int get_month()
	{
		return this._month;
	}
	
	public void set_month(int month)
	{	
		// test for real month
		if(month > 0 && month < 13) 
			this._month = month;
		else
			this._month = 1;
		
	}
	
	public int get_year()
	{
		return this._year;
	}
	
	public void set_year(int year)
	{	
		// Test year against current year
		GregorianCalendar gc = new GregorianCalendar(TimeZone.getTimeZone("America/Seattle"));
		int yearCur = gc.get(Calendar.YEAR);
		
		// year must be between 1901 and current year
		if(year > 1900 && year <= yearCur) 
			this._year = year;
		else
			this._year = 1900;
		
		
	}
	
	@Override
	public String toString()
	{
		return _month + "/" + _day + "/" + _year;
	}

}
