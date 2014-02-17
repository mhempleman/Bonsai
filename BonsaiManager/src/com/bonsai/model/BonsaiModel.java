package com.bonsai.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import com.bonsai.datatypes.BasicTreeData;
import com.bonsai.datatypes.SpeciesInfo;
import com.bonsai.view.Observer;
import com.bonsai.DBF.DbfEngine;
import com.bonsai.DBF.DbfIterator;
import com.bonsai.DBF.DbfRecord;
import com.bonsai.controller.BonsaiController;

/*
 * The model 
 * 
 * @param _observers the list of views observing the model
 * @param _trees the list of trees in the model
 * @param _speciesInformation the list of species information objects from the database 
 * @param _basicTreeData a list of basic tree data objects (name, itemNum, group) for updating view
 * @param _currentPictures a list of paths to pictures coorisponding to current tree
 * @param _currentTree the currently selected tree
 * @param _fileManager a reference to the fileManager 
 * 
 */
public class BonsaiModel  implements Serializable, Observable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private transient ArrayList<Observer> _observers;
	private ArrayList<Tree> _trees;
	private ArrayList<SpeciesInfo> _speciesInformation;
	private ArrayList<BasicTreeData> _basicTreeData;
	private transient ArrayList<String> _currentPictures;
	
	private transient Tree _currentTree;
	
	private FileManager _fileManager;
	
	
	// constructor
	public BonsaiModel()
	{
		
		
		this._observers = new ArrayList<Observer>();
		this._trees = new ArrayList<Tree>();
		this._speciesInformation = new ArrayList<SpeciesInfo>();
		this._basicTreeData = new ArrayList<BasicTreeData>();
		
		
		this._fileManager = new FileManager(this);
		
		this._currentTree = null;
		this._currentPictures = null;
		
		readDBF();
		Collections.sort(_speciesInformation);
		updateSpeciesInformation();
	}
	
	public FileManager get_fileManager()
	{
		return this._fileManager;
	}
	
	protected void set_currentPictures(ArrayList<String> list)
	{
		this._currentPictures = list;
	}
	
	
	
// Trees **************************************************************************************
	
	/*
	 * Adds a tree to _trees list
	 * Updates basic tree data with added tree
	 * Calls updateTreeInformation to update observers
	 * Calls addTreeDir in fileManager if not editing
	 */
	public void addTree(Tree tree, boolean editing)
	{
		
		
		
		_trees.add(tree);
		_basicTreeData.add(new BasicTreeData(tree.get_itemNumber().get_itemNumber(), tree.get_name().toString(), tree.get_speciesInfo().get_group()));
		Collections.sort(_basicTreeData);
		updateTreeInformation();
		
		// if called from editTree a new directory is not necessary
		if(!editing)
			_fileManager.addTreeDir(tree.get_name().toString());
	}
	
	/*
	 * Removes current tree from _trees list
	 * Updates basic tree data 
	 * Calls updateTreeInformation to update observers
	 * Calls removeTreeDir in fileManager if not editing
	 */
	public void removeCurrentTree(boolean editing)
	{
			
		
			String name = _currentTree.get_name().toString();
			_trees.remove(_currentTree);
			
			for(int i=0; i<_basicTreeData.size(); i++)
			{
				if(name.equals(_basicTreeData.get(i).get_name()))
					_basicTreeData.remove(i);
			}
			
			this._currentTree = null;
			this._currentPictures = null;
			updateTreeInformation();
			
			// if called from editTree, removing directory is not necessary 
			if(!editing)
				_fileManager.removeTreeDir(name);
		
	}
	
	/*
	 * Edits existing tree in _trees list
	 * Removes existing tree and adds edited tree with editing flag = true on add and remove
	 * Calls changeTreeDir in fileManager if tree name is changed
	 */
	public void editCurrentTree(Tree tree)
	{
		
		
		String currentTreeName = _currentTree.get_name().toString();
		removeCurrentTree(true);
		addTree(tree, true);
		set_CurrentTree(tree.get_name().toString());
		
		// if user changed tree name, change tree directory name 
		if(!tree.get_name().toString().equalsIgnoreCase(currentTreeName))
			_fileManager.changeDirectoryName(currentTreeName, tree.get_name().toString());
		
		
		
	}
	
	/*
	 * updates _currentTree
	 * loads _currentPictures with _currentTree's pics
	 * calls updateCurrentTreeInformation to update observers
	 */
	public void set_CurrentTree(String name)
	{
		
		for(int i=0; i<_trees.size(); i++)
		{
			if(_trees.get(i).get_name().toString().equalsIgnoreCase(name.toString()))
			{
				
				_currentTree = _trees.get(i);
				_currentPictures = _fileManager.loadCurrentPictures(name);
				updateCurrentTreeInformation();
				
			
				return;
			}
		}
		
		_currentTree = null;
		_currentPictures = null;
		updateCurrentTreeInformation();
	}
	
	
	

// Observers **********************************************************************************

	// register observers
	@Override
	public void registerObserver(Observer observer) 
	{
		
		
		if(_observers == null)
			_observers = new ArrayList<Observer>();
		
		_observers.add(observer);
		observer.receiveSpeciesUpdate(_speciesInformation);
		observer.receiveTreeInformationUpdate(_basicTreeData);
		observer.receiveCurrentTreeUpdate(_currentTree, _currentPictures);
	}




	// unregister observers
	@Override
	public void unregisterObserver(Observer observer) 
	{
		
		
		_observers.remove(observer);
		
	}




	// update observer's speciesInformation 
	@Override
	public void updateSpeciesInformation() 
	{
		
		
		for(Observer observer : _observers)
		{
			observer.receiveSpeciesUpdate(_speciesInformation);
		}
		
		
	}
	
	// update observer's basic tree data  
	@Override
	public void updateTreeInformation() 
	{
		
		for(Observer observer : _observers)
		{
			observer.receiveTreeInformationUpdate(_basicTreeData);
		}
		
		
	}
	
	// update observer's current tree info
	@Override
	public void updateCurrentTreeInformation() 
	{
	
		
		for(Observer observer : _observers)
		{
			observer.receiveCurrentTreeUpdate(_currentTree, _currentPictures);
		}
		
		
	}
	
	
	
	// Database ***************************************************************************************
	
	public void readDBF()
	{
		DbfIterator dbfIterator = DbfEngine.getReader(

        BonsaiController.class.getClassLoader().getResourceAsStream("SPECIES.DBF"), null);

		SpeciesInfo s;

    while (dbfIterator.hasMoreRecords()) {

          DbfRecord dbfRecord = dbfIterator.nextRecord();

          String name = dbfRecord.getString("COM_NAME");
          String group = dbfRecord.getString("COM_GROUP");
          String type = dbfRecord.getString("TYPE");
          String family = dbfRecord.getString("FAMILY");
          String genus = dbfRecord.getString("GENUS");
          String species = dbfRecord.getString("SPECIES");
          String species2 = dbfRecord.getString("SPECIES2");
          String cultivar = dbfRecord.getString("CULTIVAR");
          String otherNames = dbfRecord.getString("OTHER_NAME");
          
         s = new SpeciesInfo();
         
         if(name != null)
          s.set_commonName(name);
         else
          continue;
         
          s.set_cultivar(cultivar);
          s.set_family(family);
          s.set_genus(genus);
          s.set_group(group);
          s.set_otherNames(otherNames);
          s.set_species(species);
          s.set_type(type);
          
          _speciesInformation.add(s);
        
          
          

    }
   
	}

	
	

	

	
}
