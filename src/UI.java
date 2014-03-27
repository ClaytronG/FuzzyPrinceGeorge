/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;

/**
 *
 * @author claire
 */
public class UI {
    
    private JFrame mf;
    public void startUI()
    {
        openWindow();
    }
    
    private void openWindow()
    {
        mf = new JFrame("Which PG Neighborhood is for you!?");
        mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        mf.setMaximumSize(new Dimension(900, 530));
        mf.setMinimumSize(new Dimension(500, 400));
        mf.setPreferredSize(new Dimension(900, 530));
        mf.setSize(990, 530);
        showMainPage();
        mf.pack();
        mf.setVisible(true);
    }
    
    private void showMainPage()
    {
        IMGPanel ip = new IMGPanel("TBCCWDisplay.jpg");
  
        
        JButton b = new JButton("Start");
        b.addActionListener(new ActionListener() {
 
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                showQuestionPage();
                
            }
        });
 

        b.setAlignmentX(Component.CENTER_ALIGNMENT); 
        ip.setLayout(new BoxLayout(ip, BoxLayout.PAGE_AXIS)); 
        ip.add(Box.createVerticalGlue()); 
        ip.add(b); 
        ip.add(Box.createVerticalGlue());
        
        mf.add(ip);
        
        //img.getScaledInstance(newWidth, -1, Image. SCALE_SMOOTH)
        
    }
    
    private void showQuestionPage()
    {
        mf.getContentPane().removeAll();
        JPanel empty = new JPanel();
        empty.setOpaque(false);
        
        JPanel Main = new JPanel(new BorderLayout());
        Main.setBackground(new Color(224,230,172));
        
        JPanel Questions = new JPanel(new GridBagLayout());
        Questions.setOpaque(false);
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx=1;
        
        JLabel Q1 = new JLabel("How much income does your family earn?");
        
        JLabel Q2 = new JLabel("How important to you is safety?");
        JLabel Q3 = new JLabel("How much do you like people?");
        
        
        Questions.add(Q1,c);
        Questions.add(Q2,c);
        Questions.add(Q3,c);
        
        Main.add(empty,BorderLayout.NORTH);
        Main.add(empty,BorderLayout.SOUTH);
        Main.add(empty,BorderLayout.EAST);
        Main.add(empty,BorderLayout.WEST);
        
        Main.add(Questions,BorderLayout.CENTER);
        
        mf.add(Main);
        mf.revalidate();
        mf.repaint();

        
    }
    
    private void showAnswerPage()
    {
        
    }
}
