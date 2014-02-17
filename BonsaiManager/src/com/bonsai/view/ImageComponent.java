package com.bonsai.view;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;
import javax.swing.*;
 
/**
 * This class demonstrates how to load an Image from an external file
 */
public class ImageComponent extends Component {
           
    BufferedImage _img;
    Image _newImg;
    Dimension _parentDimension;
 
    public void paint(Graphics g) {
    	
    	int x = (_parentDimension.width - _newImg.getWidth(null))/2;
    	int y = (_parentDimension.height - _newImg.getHeight(null))/2;
    	
        g.drawImage(_newImg, x, y, null);
    }
 
    public ImageComponent(Dimension d, String picPath) {
    	this._parentDimension = d;
    	
       try {
           _img = ImageIO.read(new File(picPath));
       } catch (IOException e) {
       }
 
      scaleImage(_img);
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(_newImg.getWidth(null), _newImg.getHeight(null));
    }
 
    
      private void scaleImage(BufferedImage img)
	  {
		  if(img.getWidth(null) > img.getHeight(null))
		  {
			    int width = _parentDimension.width - (int)(_parentDimension.width*.1);
			    int height = (int) (((double) img.getHeight(null) / img.getWidth(null)) * (width));
			    this._newImg = _img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		  }
		  else
		  {
			  	int height = _parentDimension.height - (int)(_parentDimension.height*.1);
			  	int width = (int)(((double)img.getWidth(null) / img.getHeight(null)) * height);
			  	this._newImg = _img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		  }
	  }
 
    
}
