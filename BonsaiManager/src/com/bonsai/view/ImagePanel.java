package com.bonsai.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{

    private BufferedImage _img;
    private Image _newImg;
    private Dimension _parentDimension;

    public ImagePanel(Dimension d, String picPath) {
    	
    	this._parentDimension = d;
    	
       try {                
          _img = ImageIO.read(new File(picPath));
       } catch (IOException ex) {
            // handle exception...
       }
       
       scaleImage(_img);
       
     
      
    }
    
    private void scaleImage(BufferedImage img)
	{
		  if(img.getWidth(null) > img.getHeight(null))
		  {
			    int width = _parentDimension.width - (int)(_parentDimension.width*.2);
			    int height = (int) (((double) img.getHeight(null) / img.getWidth(null)) * (width));
			    this._newImg = _img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		  }
		  else
		  {
			  	int height = _parentDimension.height - (int)(_parentDimension.height*.2);
			  	int width = (int)(((double)img.getWidth(null) / img.getHeight(null)) * height);
			  	this._newImg = _img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		  }
	}

    @Override
    protected void paintComponent(Graphics g) {
    	
    	int x = (_parentDimension.width - _newImg.getWidth(null))/2;
    	int y = (_parentDimension.height - _newImg.getHeight(null))/2;
    	
        super.paintComponent(g);
        g.drawImage(_newImg, x, y, null);           
    }

}
