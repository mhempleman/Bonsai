package com.bonsai.controller;

import javax.swing.JPanel;

import com.bonsai.model.BonsaiModel;

public class DocumentController {
	
private BonsaiModel _model;
	
	public DocumentController(BonsaiModel bm)
	{
		this._model = bm;
	}
	
	public void addPictures(String treeName, JPanel contentPane)
	{
		_model.get_fileManager().copyFile(contentPane, "Pictures", treeName);
	}
	
	public void addDocuments(String treeName, JPanel contentPane)
	{
		_model.get_fileManager().copyFile(contentPane, "Documents", treeName);
	}
	
	public void removePictures(String treeName, JPanel contentPane)
	{
		_model.get_fileManager().removeFile(contentPane, "Pictures", treeName);
	}
	
	public void removeDocuments(String treeName, JPanel contentPane)
	{
		_model.get_fileManager().removeFile(contentPane, "Documents", treeName);
	}
	
	public boolean editDocument(String treeName, JPanel contentPane)
	{
		return _model.get_fileManager().editDocument(contentPane, treeName);
	}

}
