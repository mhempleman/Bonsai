package com.bonsai.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.bonsai.model.BonsaiModel;
import com.bonsai.view.BonsaiView;

/*
 * The controller 
 * 
 * @param _view a ref to the view
 * @param _model a ref to the model
 * 
 */
public class BonsaiController {
	
	private BonsaiModel _model;
	
	
	// controller used to manipulate tree data
	private TreeController _treeController;
	
	// controller used to manipulate documentw
	private DocumentController _documentController;
	
	public BonsaiController()
	{
		_model = load();
		
		
		set_treeController(new TreeController(_model));
		set_documentController(new DocumentController(_model));
		
	}

	public TreeController get_treeController() {
		return _treeController;
	}

	private void set_treeController(TreeController _treeController) {
		this._treeController = _treeController;
	}
	
	public DocumentController get_documentController() {
		return _documentController;
	}

	private void set_documentController(DocumentController _documentController) {
		this._documentController = _documentController;
	}
	
	public static void main(String[] args)
	{
		BonsaiController bc = new BonsaiController();
		BonsaiView view = new BonsaiView(bc._model, bc);
	}
	
	// temp save/load *****************************************************************************
	
	public void save()
	{
		try
	      {
	         FileOutputStream fileOut =
	         new FileOutputStream(System.getProperty("user.home") + "\\BonsaiManager\\model.ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(_model);
	         out.close();
	         fileOut.close();
	        
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	}
	
	private BonsaiModel load()
	{
		 BonsaiModel model = null;
	      try
	      {
	         FileInputStream fileIn = new FileInputStream(System.getProperty("user.home") + "\\BonsaiManager\\model.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         model = (BonsaiModel) in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException i)
	      {
	         model = new BonsaiModel();
	         
	      }catch(ClassNotFoundException c)
	      {
	        
	    	  model = new BonsaiModel();
	        
	      }
	      
	      return model;
	}

	

	

}
