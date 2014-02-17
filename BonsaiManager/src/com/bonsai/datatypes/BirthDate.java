package com.bonsai.datatypes;


import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;


/*The BirthDate class stores the birth date of the tree
 * 
 * @param _day the day of birth
 * @param _month the month of birth 
 * @param _year the year of birth 
 * 
 */

public class BirthDate extends Datatype{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int _day;
	private int _month;
	private int _year;
	private int _age;
	
	public BirthDate(int day, int month, int year)
	{
		set_day(day);
		set_month(month);
		set_year(year);
		set_age();
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
	
	private void set_age()
	{
		GregorianCalendar gc = new GregorianCalendar(TimeZone.getTimeZone("America/Seattle"));
		int age = 0;
		
		if(this._month < gc.get(Calendar.MONTH))
		{
			age = gc.get(Calendar.YEAR) - this._year;
		}
		else
		{
			if(this._day <= gc.get(Calendar.DAY_OF_MONTH))
				age = gc.get(Calendar.YEAR) - this._year;
			else
				age = gc.get(Calendar.YEAR) - this._year - 1;
		}
		
		this._age = age;
	}
	
	public int get_age()
	{
		return this._age;
	}
	
	@Override
	public String toString()
	{
		return _month + "//" + _day + "//" + _year;
	}

}

