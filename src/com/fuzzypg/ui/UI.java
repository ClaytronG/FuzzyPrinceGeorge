package com.fuzzypg.ui;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.fuzzypg.HousingSets;
import com.fuzzypg.LinguisticTerm;
import com.fuzzypg.LinguisticVariable;
import com.fuzzypg.Main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author claire
 */
public class UI {
    
    private final Color primary = new Color(242,245,245);
    private final Color secondary = new Color(217,222,222);
    private final Color tertiary = new Color(216,231,232);
    
    static class Input {
        public static final int STRONGLY_DISAGREE = 0;
        public static final int DISAGREE = 1;
        public static final int NEUTRAL = 2;
        public static final int AGREE = 3;
        public static final int STRONGLY_AGREE = 4;
    }    
    
    public class Feedback{
        public static final int NONE = 0;
        public static final int MORE = 1;
        public static final int LESS = 2;
    }
    
    private String answer;
    
    private JFrame mf;
    
    //answers to questions;
    double[] inputs = new double[6];
    int[] feedback = new int[6];
    
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
        Main.setBackground(secondary);
        
        JPanel Questions =getQuestionContent();
        addEmptySpaceQuestionPage(Main);
        
        Main.add(Questions,BorderLayout.CENTER);
        
        mf.add(Main);
        mf.revalidate();
        mf.repaint();

        
    }
    
    private void showAnswerPage(LinguisticVariable a)
    {
        String answ="";
        mf.getContentPane().removeAll();
        
        
        JPanel Main = new JPanel(new BorderLayout());
        Main.setBackground(secondary);
        
        addEmptySpaceQuestionPage(Main);
        JPanel c = null;
        
        if (a!= null) {
            ArrayList<LinguisticTerm> terms = new ArrayList<>();
            terms.addAll(a.getTermFromInput(a.defuzzify()));
            
            if (!terms.isEmpty()) {
                  answ= terms.get(0).getName();
                  c=addResultContent(answ);
                  
            }
        }
        
        if("".equals(answ))
        {
            c=noAnswerPage();
        }
        
        
        
        Main.add(c,BorderLayout.CENTER);
        
        mf.add(Main);
        mf.revalidate();
        mf.repaint();
        
    }
    
    private void showInputPage(boolean feebackRequired)
    {
       mf.getContentPane().removeAll();
       
       JPanel Main = new JPanel(new BorderLayout());
       Main.setBackground(secondary);
        
        addEmptySpaceQuestionPage(Main);
        
        JPanel p = getFeedbackPanel(feebackRequired);
        
        Main.add(p,BorderLayout.CENTER);
        
        mf.add(Main);
       
       mf.revalidate();
       mf.repaint();
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
                    case "StrongDis":
                        inputs[i] = Input.STRONGLY_DISAGREE;
                        break;
                    case "Disagree":
                        inputs[i] = Input.DISAGREE;
                        break;
                    case "Neutral":
                        inputs[i] = Input.NEUTRAL;
                        break;
                    case "Agree":
                        inputs[i] = Input.AGREE;
                        break;
                    case "StrongAgr":
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
        slider.setPreferredSize(new Dimension(400,20));
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
    
    private JPanel addResultContent(String answ)
    {
        answer = answ;
        JPanel r = new JPanel(new GridBagLayout());
        r.setBackground(primary);
        r.setOpaque(true);
        r.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
    
        
        String imgURL=answ.replace(" ","")+".PNG";
        //imgURL="CollegeHeights.PNG";
        JLabel picLabel = new JLabel();
         try
        {
            BufferedImage i = ImageIO.read(new File(imgURL));
            picLabel = new JLabel(new ImageIcon(i.getScaledInstance(300, 300,Image.SCALE_SMOOTH)));
            picLabel.setPreferredSize(new Dimension(300,300));
            picLabel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        }
        catch(IOException e){}
        
        GridBagConstraints a = new GridBagConstraints();
        GridBagConstraints b = new GridBagConstraints();
        a.fill = GridBagConstraints.VERTICAL;
        b.fill = GridBagConstraints.VERTICAL;
        
        a.anchor = GridBagConstraints.WEST;
        a.gridx=1;
        b.gridx=2;
        
        b.gridy=0;
        b.gridheight=4;
        b.insets = new Insets(0,40,0,0);
        r.add(picLabel, b);
        
        a.gridy=0;
        addLabel("You should live around:",r,a,15);
        
        a.gridy=1;
        addLabel(answ,r,a,15);
        
        //add back button
        JButton butt = new JButton("Back");
        butt.addActionListener(new ActionListener() {
 
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                showQuestionPage();
                
            }
        });
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_START;
        c.insets = new Insets(0,0,100,50);  
        c.gridx = 0;       
        c.gridy = 0; 
        r.add(butt,c);
        
        
        //add input buttons and text
        c.anchor = GridBagConstraints.PAGE_END;
        c.insets = new Insets(15,0,5,5);
        c.gridy =4;
        c.gridwidth=3;
        r.add(getIsUsefulButtonPanel(),c);
        
        
        return r;     
    }
    
    private JPanel getQuestionContent()
    {
        JPanel Questions = new JPanel(new GridBagLayout());
        Questions.setBackground(primary);
        Questions.setOpaque(true);
        Questions.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        
        GridBagConstraints a = new GridBagConstraints();
        GridBagConstraints b = new GridBagConstraints();
        a.fill = GridBagConstraints.VERTICAL;
        b.fill = GridBagConstraints.VERTICAL;
        a.ipady=5;
        b.ipady=5;
        
        a.anchor = GridBagConstraints.WEST;
        a.gridx=0;
        b.gridx=1;
        
        
        b.gridy=0;
        addLabel("Strongly Disagree :: Disagree :: Neutral :: Agree :: Strongly Agree",Questions,b,10);
       
        a.gridy=1;
        b.gridy=1;
        b.fill = GridBagConstraints.HORIZONTAL;
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
                LinguisticVariable a =Main.engine.answer();
                showAnswerPage(a);
                
            }
        });
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_END;
        c.insets = new Insets(100,0,0,0);  
        c.gridx = 1;       
        c.gridy = 7;       
        
        Questions.add(butt, c);
        
        return Questions;
    }
    
    private JPanel noAnswerPage()
    {
        JPanel na = new JPanel(new GridBagLayout());
        na.setBackground(primary);
        na.setOpaque(true);
        na.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        
        GridBagConstraints a = new GridBagConstraints();
        
        a.gridx=2;
        a.gridy=1;
        a.insets= new Insets(30,0,0,0);
        addLabel("We're Sorry",na, a, 18);
        
        a.gridx=2;
        a.gridy=2;
        a.gridwidth=3;
        a.gridheight=1;
        
        String text ="We're very sorry, but our system could not find any location"+
                " in Prince George which suited your criteria. Perhaps Prince George"+
                " is not the right town for you. Feel free to modify your search criteria"+
                " and try again.";
        JTextArea ta = new JTextArea(text);
        ta.setOpaque(false);
        ta.setFont(ta.getFont().deriveFont((float)15));
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setPreferredSize(new Dimension(300,300));
        
        na.add(ta,a);
        
        JButton butt = new JButton("Try Again");
        butt.addActionListener(new ActionListener() {
 
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                showQuestionPage();
                
            }
        });
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_END;
        c.insets = new Insets(0,10,10,10);  
        c.gridx = 2;       
        c.gridy = 3; 
        na.add(butt,c);
        
        
        return na;
    }
    
    private JPanel getFeedbackPanel(boolean feedbackRequired)
    {
        JPanel j = new JPanel(new GridBagLayout());
        j.setBackground(primary);
        j.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        
        if(feedbackRequired)
        {
           GridBagConstraints a = new GridBagConstraints();
           
           a.gridx=0;
           a.gridy=0;
           a.gridwidth=2;
            addLabel("What was wrong with our selection?", j,a,18 ); 
            
            GridBagConstraints b = new GridBagConstraints();
            GridBagConstraints c = new GridBagConstraints();
            b.gridx=0;
            c.gridx=1;
            
            a.gridy=1;
            a.insets=new Insets(0,0,25,0);
            addLabel("*Note: Please select only those that apply", j, a, 15);
            
            b.gridy=2;
            c.gridy=2;
            addLabel("Too Inexpensive",j,b,13);
            addLabel("Too Expensive",j,c,13);
            b.gridy =3;
            addFeedBackRadioButtons(j,b, 0);
            
            b.gridy=4;
            c.gridy=4;
            addLabel("Too Dangerous",j,b,13);
            addLabel("Not enough Excitement",j,c,13);
            b.gridy=5;
            addFeedBackRadioButtons(j,b, 1);
            
            
            b.gridy=6;
            c.gridy=6;
            addLabel("Too Few People",j,b,13);
            addLabel("Not Many People",j,c,13);
            b.gridy=7;
            addFeedBackRadioButtons(j,b, 2);
            
            b.gridy=8;
            c.gridy=8;
            addLabel("Too Many Drugs",j,b,13);
            addLabel("Too Anti-Drug",j,c,13);
            b.gridy=9;
            addFeedBackRadioButtons(j,b, 3);
            
            b.gridy=10;
            c.gridy=10;
            addLabel("Too Preppy",j,b,13);
            addLabel("Too Redneck",j,c,13);
            b.gridy=11;
            addFeedBackRadioButtons(j,b, 4);
            
            b.gridy=12;
            c.gridy=12;
            addLabel("Too Far from Town",j,b,13);
            addLabel("Too Close to Town",j,c,13);
            b.gridy=13;
            addFeedBackRadioButtons(j,b, 5);
            
            
            JButton butt = new JButton("Submit");
            butt.addActionListener(new ActionListener() {
 
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                showInputPage(false);
                
            }
            
            
        });
            a.fill = GridBagConstraints.HORIZONTAL;
        a.anchor = GridBagConstraints.PAGE_END;
        a.insets = new Insets(90,0,0,0); 
        a.gridy=14;
            j.add(butt,a);
            
        }
        else
        {
           GridBagConstraints a = new GridBagConstraints();
           addLabel("Awesome! Thanks for the feedback.", j,a,18 );
           
           // Update the rules
           Main.engine.updateRules(answer, feedback);
        }
        return j;
    }
    private JPanel getIsUsefulButtonPanel()
    {
        JPanel useful = new JPanel();
        useful.setLayout(new BoxLayout(useful, BoxLayout.PAGE_AXIS));
        useful.setBackground(tertiary);
        useful.setOpaque(true);
        
        JLabel l = new JLabel("Did you find our prediction helpful?");
        
        JButton yb = new JButton("Yes!");
        JButton nb = new JButton("No!");
        
        
        yb.addActionListener(new ActionListener() {
 
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                showInputPage(false);
                
            }
        });
        
        nb.addActionListener(new ActionListener() {
 
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                showInputPage(true);
                
            }
        });
        
        Box hb = Box.createHorizontalBox();
        hb.setBorder(BorderFactory.createEmptyBorder(15, 15,15, 15));
        hb.add(l);
        hb.add(Box.createHorizontalGlue());
       // hb.add(Box.createRigidArea(new Dimension(40,5)));
        hb.add(yb);
        hb.add(Box.createRigidArea(new Dimension(15,5)));
        hb.add(nb);
        
        useful.add(hb);
        
        
        return useful;
        
    }
    
    
    private void addFeedBackRadioButtons(JPanel j, GridBagConstraints c, final int i)
    {
        JRadioButton b1 = new JRadioButton();
        JRadioButton b2 = new JRadioButton();
        
        b1.setOpaque(false);
        b2.setOpaque(false);
        
        ButtonGroup g = new ButtonGroup();
        g.add(b1);
        g.add(b2);
        
        j.add(b1,c);
        c.gridx=1;
        j.add(b2,c);
        
        c.gridx=0;
        
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button1");
                feedback[i] = Feedback.MORE;
            }
        });
        
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button2");
                feedback[i] = Feedback.LESS;
            }
        });
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
        emptyN.add(Box.createRigidArea(new Dimension(20,20)));
        emptyE.add(Box.createVerticalGlue());
        emptyE.add(Box.createRigidArea(new Dimension(20,20)));
        emptyS.add(Box.createHorizontalGlue());
        emptyS.add(Box.createRigidArea(new Dimension(20,20)));
        emptyW.add(Box.createVerticalGlue());
        emptyW.add(Box.createRigidArea(new Dimension(20,20)));
       
        p.add(emptyN,BorderLayout.NORTH);
        p.add(emptyS,BorderLayout.SOUTH);
        p.add(emptyE,BorderLayout.EAST);
        p.add(emptyW,BorderLayout.WEST);
    }
}
