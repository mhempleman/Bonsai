package com.bonsai.controller;

import com.bonsai.model.BonsaiModel;
import com.bonsai.model.Tree;

public class TreeController {
	
	private BonsaiModel _model;
	
	public TreeController(BonsaiModel bm)
	{
		this._model = bm;
	}
	
	public void addTree(Tree tree)
	{
		_model.addTree(tree, false);
	}
	
	public void removeCurrentTree()
	{
		_model.removeCurrentTree(false);
	}
	
	public void updateCurrentTree(String treeName)
	{
		_model.set_CurrentTree(treeName);
	}
	
	public void editTree(Tree tree)
	{
		_model.editCurrentTree(tree);
	}

}
