package com.bonsai.model;

import java.util.ArrayList;

import com.bonsai.datatypes.*;
import com.bonsai.view.Observer;

public interface Observable {
	
	public void registerObserver(Observer observer);
	
	public void unregisterObserver(Observer observer);
	
	public void updateSpeciesInformation();
	
	public void updateTreeInformation();
	
	public void updateCurrentTreeInformation();

}
