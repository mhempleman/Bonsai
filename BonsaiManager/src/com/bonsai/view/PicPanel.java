package com.bonsai.view;


import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * Extends JPanel to build an image panel object
 * 
 * @param _newImg used to hold image once it has been scaled to the panel size
 * @param _lbl the JLabel that holds the image
 * 
 */
public class PicPanel extends JPanel {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image _newImg;
	private JLabel _lbl;
	private Dimension _parentDimension;

	  

	  public void setImage(String img, Dimension d) {
		  
		  this._parentDimension = d;
		  scaleImage(new ImageIcon(img).getImage());
		  _lbl = new JLabel(new ImageIcon(_newImg));
		  this.add(_lbl);
		  this.repaint();
	  }
	  
	  @Override
	  public Dimension getPreferredSize() {
	        return new Dimension(_newImg.getWidth(null), _newImg.getHeight(null));
	  }
	  
	 
	  
	  private void scaleImage(Image img)
	  {
		  if(img.getWidth(null) > img.getHeight(null))
		  {
			    int width = _parentDimension.width - 10;
			    int height = (int) (((double) img.getHeight(null) / img.getWidth(null)) * (width));
			    this._newImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		  }
		  else
		  {
			  	int height = _parentDimension.height - 10;
			  	int width = (int)(((double)img.getWidth(null) / img.getHeight(null)) * height);
			  	this._newImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		  }
	  }

	 

}
