package com.bonsai.model;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

public class FileManager implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String _mainFolderPath;
	private BonsaiModel _model;
	private final String _picFolder = "Pictures";
	private final String _docFolder = "Documents";
	
	public FileManager(BonsaiModel model)
	{
		this._model = model;
		this._mainFolderPath = System.getProperty("user.home") + "/BonsaiManager";
		buildDir();
	}
	
	/*
	 * Creates a directory for bonsai files if it does not exist
	 * Also creates document and picture directories within the main directory
	 * 
	 */
	public void buildDir(){
		boolean b = new File(_mainFolderPath).mkdir();
		
	}
	
	/*
	 * Creates a directory for a new tree 
	 * Also creates document and picture directories within the main tree directory
	 */
	public void addTreeDir(String treeName)
	{
		
		
		String path = _mainFolderPath + "/" + treeName + "/" + _docFolder +"/" ;
		boolean b = new File(path).mkdirs();
		path = _mainFolderPath + "/" + treeName + "/" + _picFolder +"/"  ;
		b = new File(path).mkdirs();
	}
	
	/*
	 * removes a tree directory and all contents 
	 */
	public void removeTreeDir(String treeName){
		String path = _mainFolderPath + "/" + treeName ;
		
		File directory = new File(path);
		
		if(directory.exists())
		{
			try 
			{
				delete(directory);
			}
			catch(IOException e) 
			{
				
			}
		}
	}
	
	protected void changeDirectoryName(String oldName, String newName)
	{
	
		
		String path = _mainFolderPath + "/" + oldName ;
		String newPath = _mainFolderPath + "/" + newName ;
		File directory = new File(path);
		
		if(directory.exists())
			directory.renameTo(new File(newPath));
	}
	
	private static void delete(File file) throws IOException
	{
	 
	    	if(file.isDirectory())
	    	{
	    		if(file.list().length==0)
	    			file.delete();
	    		else
	    		{
	        	   String files[] = file.list();
	 
	        	   for(String temp : files) 
	        	   {
	        	
	        	      File fileDelete = new File(file, temp);
	        	      delete(fileDelete);
	        	   }
	 
	        	   if(file.list().length==0)
	           	     file.delete();
	        	   
	    		}
	 
	    	}else
	    		file.delete();

	    	
	    }
	
	/*
	 * copies file(s) to directory
	 * 
	 * @param folder either Pictures or Documents
	 * @param requires content pane for file chooser
	 * 
	 * @return true if the file is copied
	 */
	public boolean copyFile(JPanel contentPane, String folder, String treeName)
	{
		
		
		String path = _mainFolderPath + "/" + treeName + "/" + folder + "/";
		JFileChooser fc = new JFileChooser();
		fc.setMultiSelectionEnabled(true);
		int returnValue = fc.showDialog(contentPane, "Add");
		
		Path source, target;
		
		
		if(returnValue == JFileChooser.APPROVE_OPTION)
		{
			File[] files = fc.getSelectedFiles();
			
			
			
			for(File file : files)
			{
				
				source = FileSystems.getDefault().getPath(file.getAbsolutePath());
				target = FileSystems.getDefault().getPath(path + file.getName());
				
				try
				{
					if(folder.equals(_picFolder))
					{
						String[] ara = file.getAbsolutePath().split(".");
						
						if(ara[ara.length-1].equals("jpg") || ara[ara.length-1].equals("jpeg") || ara[ara.length-1].equals("png") || ara[ara.length-1].equals("gif"))
							Files.copy(source, target, REPLACE_EXISTING);
					}
					else
					{
						Files.copy(source, target, REPLACE_EXISTING);
					}
					
					if(folder.equals(_picFolder))
					{
						_model.set_currentPictures(loadCurrentPictures(treeName));
						_model.updateCurrentTreeInformation();
					}
				}
				catch (IOException e)
				{
					e.printStackTrace();
					
				}
			}
			return true;
		}
		return false;
	}
	
	/*
	 * deletes file(s) from the bonsai manager directory 
	 * 
	 * @return true if the file(s) are deleted
	 */
	public void removeFile(JPanel contentPane, String folder, String treeName)
	{
		
		
		String path = _mainFolderPath + "/" + treeName + "/" + folder + "/";
		JFileChooser fc = new JFileChooser();
		fc.setMultiSelectionEnabled(true);
		fc.setCurrentDirectory(new File(path));
		int returnValue = fc.showDialog(contentPane, "Remove");
		
		Path target;
		
		
		if(returnValue == JFileChooser.APPROVE_OPTION)
		{
			File[] files = fc.getSelectedFiles();
			
			
			
			for(File file : files)
			{
				
				target = FileSystems.getDefault().getPath(file.getAbsolutePath());
				
				try
				{
					Files.delete(target);
					
					if(folder.equals(_picFolder))
					{
						_model.set_currentPictures(loadCurrentPictures(treeName));
						_model.updateCurrentTreeInformation();
					}
					
				}
				catch (IOException e)
				{
					e.printStackTrace();
					
				}
			}
			
			
				
			
		}
		
	}
	
	public boolean editDocument(JPanel contentPane, String treeName)
	{
		
		
		String path = _mainFolderPath + "/" + treeName + "/" + _docFolder + "/";
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File(path));
		int returnValue = fc.showDialog(contentPane, "Open");
		
		Path target;
		
		
		if(returnValue == JFileChooser.APPROVE_OPTION)
		{
			File file = fc.getSelectedFile();
			
			if (!Desktop.isDesktopSupported()) {
			    return false;
			  }

			  Desktop desktop = Desktop.getDesktop();
			  if (!desktop.isSupported(Desktop.Action.EDIT)) {
			    return false;
			  }

			  try {
			    desktop.open(file);
			  } catch (IOException e) {
			    e.printStackTrace();
			    return false;
			  }

			  return true;
		}
		return false;
		
	}
	
	protected ArrayList<String> loadCurrentPictures(String treeName)
	{
		
		
		String path = _mainFolderPath + "/" + treeName + "/" + _picFolder + "/";
		ArrayList<String> a = new ArrayList<String>();
		
		File dir = new File(path);
		
		
		if(dir.exists())
		{
		
			String[] list = dir.list();
			
			for(int i=0; i<list.length; i++)
			{
				a.add(path + list[i]);
			}
		}
	
		
		return a;
	}
		
		
}
