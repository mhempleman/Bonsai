package com.bonsai.view;

import java.util.ArrayList;

import com.bonsai.controller.BonsaiController;
import com.bonsai.datatypes.BasicTreeData;
import com.bonsai.datatypes.SpeciesInfo;
import com.bonsai.model.Observable;
import com.bonsai.model.Tree;

public class BonsaiView implements Observer {
	
	private ArrayList<SpeciesInfo> _speciesInformation;
	private ArrayList<BasicTreeData> _basicTreeData;
	private ArrayList<String> _currentPictures;
	
	private Observable _observable;
	
	private MainWindow _mainWindow;
	
	private BonsaiController _controller;
	
	private Tree _currentTree;
	
	public BonsaiView(Observable o, BonsaiController controller)
	{
		this._observable = o;
		_mainWindow = new MainWindow(this, controller);
		this._controller = controller;
		this.set_currentTree(null);
		this.set_currentPictures(null);
		initialize();
		
	}
	
	private void initialize()
	{
		_observable.registerObserver(this);
		
		
	}
	
	public void loadEditDialog(ArrayList<SpeciesInfo> speciesInfo, BonsaiView view, Tree tree)
	{
		EditDialog treeDialog;
		
		if(tree == null)
			treeDialog = new EditDialog(speciesInfo, _controller);
		else
			treeDialog = new EditDialog(speciesInfo, _controller, tree);
			
		treeDialog.setVisible(true);
		treeDialog.setResizable(false);
	}
	
	protected void getPic(boolean forward, int currentIndex)
	{
		int newIndex;
		
		if(_currentPictures != null)
		{
			if(forward)
			{
				newIndex = currentIndex+1;
				
				if(newIndex >= _currentPictures.size())
					newIndex = 0;
			}
			else
			{
				newIndex = currentIndex-1;
				
				if(newIndex < 0)
					newIndex = _currentPictures.size()-1;
			}
			
			_mainWindow.updatePictureData(_currentPictures, newIndex);
		}
		
		
	}

	// Observer ****************************************************************************************
	
	@Override
	public void receiveSpeciesUpdate(ArrayList<SpeciesInfo> speciesInfo) {
		this.set_speciesInformation(speciesInfo);
		
	}
	
	@Override
	public void receiveTreeInformationUpdate(ArrayList<BasicTreeData> treeData) {
		this.set_basicTreeData(treeData);
		_mainWindow.updateJTree(treeData);
		
	}
	
	@Override
	public void receiveCurrentTreeUpdate(Tree tree, ArrayList<String> currentPictures) {
		this.set_currentTree(tree);
		_mainWindow.updateCurrentTreeData(_currentTree);
		
		this.set_currentPictures(currentPictures);
		_mainWindow.updatePictureData(currentPictures, 0);
		
	}
	
	// *****************************************************************************************

	public ArrayList<SpeciesInfo> get_speciesInformation() {
		return _speciesInformation;
	}

	private void set_speciesInformation(ArrayList<SpeciesInfo> _speciesInformation) {
		this._speciesInformation = _speciesInformation;
	}

	public ArrayList<BasicTreeData> get_basicTreeData() {
		return _basicTreeData;
	}

	private void set_basicTreeData(ArrayList<BasicTreeData> _basicTreeData) {
		this._basicTreeData = _basicTreeData;
	}

	public Tree get_currentTree() {
		return _currentTree;
	}

	private void set_currentTree(Tree _currentTree) {
		this._currentTree = _currentTree;
	}

	public ArrayList<String> get_currentPictures() {
		return _currentPictures;
	}

	public void set_currentPictures(ArrayList<String> _currentPictures) {
		this._currentPictures = _currentPictures;
	}

	
	

}
