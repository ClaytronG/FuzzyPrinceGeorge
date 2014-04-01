package com.fuzzypg.ui;

import com.fuzzypg.Main;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author claire
 */
public class IMGPanel extends JPanel {

    private Image img;

    public IMGPanel(String url)
    {
        URL resource = Main.class.getResource("images/" + url);
        try{
            //img = ImageIO.read(new File(imgURL));
            img = ImageIO.read(resource);
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
