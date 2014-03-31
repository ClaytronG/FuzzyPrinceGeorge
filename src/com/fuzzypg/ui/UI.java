package com.fuzzypg.ui;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.fuzzypg.HousingSets;
import com.fuzzypg.Main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 *
 * @author claire
 */
public class UI {
    
    static class Input {
        public static final int STRONGLY_DISAGREE = 0;
        public static final int DISAGREE = 1;
        public static final int NEUTRAL = 2;
        public static final int AGREE = 3;
        public static final int STRONGLY_AGREE = 4;
    }
    
    private JFrame mf;
    
    //answers to questions;
    //LinguisticVariable[] inputs = new LinguisticVariable[6];
    double[] inputs = new double[6];
    
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
        
        
        JPanel Main = new JPanel(new BorderLayout());
        Main.setBackground(new Color(224,230,172));
        
        JPanel Questions = new JPanel(new GridBagLayout());
        Questions.setBackground(new Color (241,247,186));
        Questions.setOpaque(true);
        
        JPanel Buttons = new JPanel();
        Buttons.setLayout(new BoxLayout(Buttons,BoxLayout.PAGE_AXIS));
        Buttons.setOpaque(false);
        
        addQuestionContent(Questions);
        addEmptySpaceQuestionPage(Main);
        
        Main.add(Questions,BorderLayout.CENTER);
        
        mf.add(Main);
        mf.revalidate();
        mf.repaint();

        
    }
    
    private void showAnswerPage(LinguisticVariable a)
    {
        
    }
    
    private void addLabel(String s, JPanel p, GridBagConstraints c, int fs)
    {
        JLabel l = new JLabel(s);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        //update font size
        Font f = l.getFont();
        f=f.deriveFont((float)fs);
        l.setFont(f);
        
        p.add(l,c);
    }
    
    private void addRadioButtonGroup(JPanel p, GridBagConstraints c, final int i)
    {
        //create the 5 buttons
        JRadioButton b1 = new JRadioButton();
        b1.setActionCommand("StrongDis");
        JRadioButton b2 = new JRadioButton();
        b2.setActionCommand("Disagree");
        JRadioButton b3 = new JRadioButton();
        b3.setActionCommand("Neutral");
        JRadioButton b4 = new JRadioButton();
        b4.setActionCommand("Agree");
        JRadioButton b5 = new JRadioButton();
        b5.setActionCommand("StrongAgr");
        
        //make them all have no background
        b1.setOpaque(false);
        b2.setOpaque(false);
        b3.setOpaque(false);
        b4.setOpaque(false);
        b5.setOpaque(false);
        
        //group together
        ButtonGroup g = new ButtonGroup();
        g.add(b1);
        g.add(b2);
        g.add(b3);
        g.add(b4);
        g.add(b5);
        
        //create action listener
        ActionListener al = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                switch(e.getActionCommand())
                {
                    case "StrongDis"://answ[i]=LinguisticVariable.STRONGDISAGREE;
                        inputs[i] = Input.STRONGLY_DISAGREE;
                        break;
                    case "Disagree"://answ[i]=LinguisticVariable.DISAGREE;
                        inputs[i] = Input.DISAGREE;
                        break;
                    case "Neutral"://answ[i]=LinguisticVariable.NEUTRAL;
                        inputs[i] = Input.NEUTRAL;
                        break;
                    case "Agree"://answ[i]=LinguisticVariable.AGREE;
                        inputs[i] = Input.AGREE;
                        break;
                    case "StrongAgr"://answ[i]=LinguisticVariable.STRONGAGREE;
                        inputs[i] = Input.STRONGLY_AGREE;
                        break;
                        
                }
            }
        };
        //add action listener to all buttons
        b1.addActionListener(al);
        b2.addActionListener(al);
        b3.addActionListener(al);
        b4.addActionListener(al);
        b5.addActionListener(al);
        
        JPanel rp = new JPanel(new GridLayout(0,5,40,0));
        rp.setOpaque(false);
        rp.add(b1);
        rp.add(b2);
        rp.add(b3);
        rp.add(b4);
        rp.add(b5);
        
        p.add(rp,c);
    }
    
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 4;
    private static final int FACTOR = 5;
    
    private void addSlider(JPanel p, GridBagConstraints c, final int i) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, MIN_VALUE * FACTOR, MAX_VALUE * FACTOR, MAX_VALUE * FACTOR / 2);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                if (!source.getValueIsAdjusting()) {
                    double value = (double) source.getValue()/FACTOR;
                    inputs[i] = value;
                }
            }
        });
        
        JPanel rp = new JPanel();
        rp.add(slider);
        
        p.add(rp, c);       
    }
    
    private void addQuestionContent(JPanel Questions)
    {
        GridBagConstraints a = new GridBagConstraints();
        GridBagConstraints b = new GridBagConstraints();
        a.fill = GridBagConstraints.VERTICAL;
        b.fill = GridBagConstraints.VERTICAL;
        
        a.anchor = GridBagConstraints.WEST;
        a.gridx=0;
        b.gridx=1;
        
        
        b.gridy=0;
        addLabel("Strongly Disagree :: Disagree :: Neutral :: Agree :: Strongly Agree",Questions,b,10);
       
        a.gridy=1;
        b.gridy=1;
        addLabel("I can afford anything I want!",Questions,a,15);
        //addRadioButtonGroup(Questions,b,0);
        addSlider(Questions, b, 0);
        
        a.gridy=2;
        b.gridy=2;
        addLabel("Safety is my number one priority.",Questions,a,15);
        //addRadioButtonGroup(Questions,b,1);
        addSlider(Questions, b, 1);
        
        a.gridy=3;
        b.gridy=3;
        addLabel("I enjoy the company of others.",Questions,a,15);
        //addRadioButtonGroup(Questions,b,2);
        addSlider(Questions, b, 2);
        
        a.gridy=4;
        b.gridy=4;
        addLabel("I like to plaid more than red carpet attire.",Questions,a,15);
        //addRadioButtonGroup(Questions,b,3);
        addSlider(Questions, b, 3);
        
        a.gridy=5;
        b.gridy=5;
        addLabel("I am comfortable with hardcore drugs.",Questions,a,15);
        //addRadioButtonGroup(Questions,b,4);
        addSlider(Questions, b, 4);
        
        a.gridy=6;
        b.gridy=6;
        addLabel("Town amenities should be close by.",Questions,a,15);
        //addRadioButtonGroup(Questions,b,5);
        addSlider(Questions, b, 5);
        
        
        JButton butt = new JButton("Submit");
        butt.addActionListener(new ActionListener() {
 
            @Override
            public void actionPerformed(ActionEvent e)
            {
                /*
                //Execute when button is pressed
                InferenceEngine IE = new InferenceEngine(inputs);
                LinguisticVariable a =IE.Infer();

                showAnswerPage(a);
                */
                HousingSets.price.setInput(inputs[0]);
                HousingSets.safety.setInput(inputs[1]);
                HousingSets.people.setInput(inputs[2]);
                HousingSets.style.setInput(inputs[3]);
                HousingSets.drugs.setInput(inputs[4]);
                HousingSets.proximity.setInput(inputs[5]);
                Main.engine.answer();
                
            }
        });
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_END;
        c.insets = new Insets(100,0,0,0);  
        c.gridx = 1;       
        c.gridy = 7;       
        
        Questions.add(butt, c);
    }
    
    private void addEmptySpaceQuestionPage(JPanel p)
    {
        JPanel emptyN = new JPanel();
        JPanel emptyE = new JPanel();
        JPanel emptyS = new JPanel();
        JPanel emptyW = new JPanel();
        
        emptyN.setOpaque(false);
        emptyE.setOpaque(false);
        emptyS.setOpaque(false);
        emptyW.setOpaque(false);
        
        emptyN.setLayout(new BoxLayout(emptyN, BoxLayout.PAGE_AXIS));
        emptyE.setLayout(new BoxLayout(emptyE, BoxLayout.PAGE_AXIS));
        emptyS.setLayout(new BoxLayout(emptyS, BoxLayout.PAGE_AXIS));
        emptyW.setLayout(new BoxLayout(emptyW, BoxLayout.PAGE_AXIS));
        
        emptyN.add(Box.createHorizontalGlue());
        emptyN.add(Box.createRigidArea(new Dimension(50,50)));
        emptyE.add(Box.createVerticalGlue());
        emptyE.add(Box.createRigidArea(new Dimension(50,50)));
        emptyS.add(Box.createHorizontalGlue());
        emptyS.add(Box.createRigidArea(new Dimension(50,50)));
        emptyW.add(Box.createVerticalGlue());
        emptyW.add(Box.createRigidArea(new Dimension(50,50)));
        
        p.add(emptyN,BorderLayout.NORTH);
        p.add(emptyS,BorderLayout.SOUTH);
        p.add(emptyE,BorderLayout.EAST);
        p.add(emptyW,BorderLayout.WEST);
    }
}
