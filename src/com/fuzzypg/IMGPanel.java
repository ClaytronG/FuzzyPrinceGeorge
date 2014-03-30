package com.fuzzypg;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author claire
 */
public class IMGPanel extends JPanel {
    
    private String imgURL;
    Image img;
    
    public IMGPanel(String url)
    {
        imgURL = url;
        try{
            img = ImageIO.read(new File(imgURL));
        }
        catch(IOException e)
        {
            System.out.println("Image not read from file: Error is:");
            System.out.println(e.getMessage());
        }
        
        
    }
    
  @Override
  protected void paintComponent(Graphics g) {

    super.paintComponent(g);
        g.drawImage(img, 0, 0, null);
}
    
  
}
