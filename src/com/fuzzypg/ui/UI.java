package com.fuzzypg.ui;

import com.fuzzypg.FuzzyRule;
import com.fuzzypg.FuzzyRuleTerm;
import com.fuzzypg.FuzzySet;
import com.fuzzypg.InferenceEngine;
import com.fuzzypg.LinguisticVariable;
import com.fuzzypg.Main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.json.JSONObject;
import org.json.JSONStringer;

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
    
    public class Feedback {
        public static final int NONE = 0;
        public static final int MORE = 1;
        public static final int LESS = 2;
    }
    
    private JFrame mf;
    
    String answer;
    
    //answers to questions;
    private final double[] inputs = new double[6];
    // answers to feedback.
    private final HashMap<String, Integer> feedback = new HashMap<>();
    private final String[] names = {
        "Price",
        "Safety",
        "People",
        "Style",
        "Drugs",
        "Proximity"
    };
    // answers to adding a rule
    private final String[] ruleAnswers = {
        "Very High",
        "Very High",
        "Very High",
        "Very High",
        "Very High",
        "Very High",
        "College Heights"
    };
    
    public void startUI()
    {
        // Initialize the input values to neutral
        for (int i = 0; i < inputs.length; ++i) {
            inputs[i] = Input.NEUTRAL;
        }
        for (String name : names) {
            feedback.put(name, Feedback.NONE);
        }
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
        JMenuBar bar = new JMenuBar();
        JMenu m1 = new JMenu("Options");
        bar.add(m1);
        JMenuItem mi1 = new JMenuItem("Add Rule");
        JMenuItem mi2 = new JMenuItem("Add Fuzzy Set");
        
        mi1.addActionListener(new ActionListener() {
 
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                showAddRulePopUp();
                
            }

           
        });
        
        mi2.addActionListener(new ActionListener() {
 
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                showAddFuzzySetPopUp();
                
            }

            
        });
        
        m1.add(mi1);
        m1.add(mi2);

        mf.setJMenuBar(bar);
        
        
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
    
     private void showAddRulePopUp() {
         
         final JFrame pu = new JFrame();
        pu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        pu.setMaximumSize(new Dimension(700, 400));
        pu.setMinimumSize(new Dimension(700, 400));
        pu.setPreferredSize(new Dimension(700, 400));
        pu.setSize(700, 400);
        
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints a = new GridBagConstraints();
        a.insets = new Insets(4,4,4,4);
        a.gridx=2;
        a.gridy=2;
        addLabel("Answer", p, a, 14);
        a.gridy=3;
        addLabel("Price", p, a, 14);
        a.gridy=4;
        addLabel("Safety", p, a, 14);
        a.gridy=5;
        addLabel("Socialness", p, a, 14);
        a.gridy=6;
        addLabel("Redneck Area", p, a, 14);
        a.gridy=7;
        addLabel("Drug Usage", p, a, 14);
        a.gridy=8;
        addLabel("Close to Town", p, a, 14);
        
        a.gridx=3;
        a.gridy=2;
        addAnswerDropDown(p,a);
        a.gridy=3;
        addHighLowDropDown(p,a, 0);
        a.gridy=4;
        addHighLowDropDown(p,a,1);
        a.gridy=5;
        addHighLowDropDown(p,a,2);
        a.gridy=6;
        addHighLowDropDown(p,a,3);
        a.gridy=7;
        addHighLowDropDown(p,a,4);
        a.gridy=8;
        addHighLowDropDown(p,a,5);
 
        JButton butt = new JButton("Submit");
        butt.addActionListener(new ActionListener() {
 
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                //Main.engine.addRule(null);
                // Create the new rule using a JSONObject
                String rule = new JSONStringer()
                        .object()
                            .key("answer")
                            .value("true")
                            .key("then")
                            .object()
                                .key("name")
                                .value("Area")
                                .key("value")
                                .array()
                                    .value(ruleAnswers[6])
                                .endArray()
                            .endObject()
                            .key("if")
                            .array()
                                .object()
                                .key("name")
                                .value("Price")
                                .key("value")
                                .array()
                                .value(ruleAnswers[0])
                                .endArray()
                                .endObject()
                                .object()
                                .key("name")
                                .value("Safety")
                                .key("value")
                                .array()
                                .value(ruleAnswers[1])
                                .endArray()
                                .endObject()
                                .object()
                                .key("name")
                                .value("People")
                                .key("value")
                                .array()
                                .value(ruleAnswers[2])
                                .endArray()
                                .endObject()
                                .object()
                                .key("name")
                                .value("Style")
                                .key("value")
                                .array()
                                .value(ruleAnswers[3])
                                .endArray()
                                .endObject()
                                .object()
                                .key("name")
                                .value("Drugs")
                                .key("value")
                                .array()
                                .value(ruleAnswers[4])
                                .endArray()
                                .endObject()
                                .object()
                                .key("name")
                                .value("Proximity")
                                .key("value")
                                .array()
                                .value(ruleAnswers[5])
                                .endArray()
                                .endObject()
                            .endArray()
                        .endObject()
                        .toString();
                FuzzyRule newRule = new FuzzyRule(new JSONObject(rule));
                // Remove the conflicting rule.
                InferenceEngine.removeRule(InferenceEngine.getRule("Area", ruleAnswers[6]));
                // Add the new rule.
                InferenceEngine.addRule(newRule);
                // Close the window
                pu.dispose();
            }
        });
        
        p.add(butt);
        pu.add(p);
        
        pu.pack();
        pu.setVisible(true); //To change body of generated methods, choose Tools | Templates.
            }
    
     
     
     private void showAddFuzzySetPopUp() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
     
     
    private void showQuestionPage()
    {
        // Reset the inference engine
        Main.getEngine(true);
        
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
            ArrayList<FuzzySet> terms = new ArrayList<>();
            terms.addAll(a.getTermFromInput(a.defuzzify()));
            
            if (!terms.isEmpty()) {
                  answ= terms.get(0).getName();
                  c=addResultContent(answ);
                  
            }
        }
        
        answer = answ;
        
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
    
    /**
     * Minimum value for the input sliders.
     */
    private static final int MIN_VALUE = 0;
    /**
     * Maximum value for the input sliders.
     */
    private static final int MAX_VALUE = 4;
    /**
     * Step size for slider values.
     */
    private static final int FACTOR = 5;
    
    private void addSlider(JPanel p, GridBagConstraints c, final int i) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, MIN_VALUE, MAX_VALUE * FACTOR, MAX_VALUE * FACTOR / 2);
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
        JPanel r = new JPanel(new GridBagLayout());
        r.setBackground(primary);
        r.setOpaque(true);
        r.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
    
        
        String imgURL=answ.replace(" ","")+".PNG";
        //imgURL="CollegeHeights.PNG";
        JLabel picLabel = new JLabel();
         try
        {
            URL resource = Main.class.getResource("images/" + imgURL);
            BufferedImage i = ImageIO.read(resource);
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
                reset();
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
        addLabel("I like to wear plaid more than red carpet attire.",Questions,a,15);
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
                InferenceEngine.getVariable("Can Afford").setInput(inputs[0]);
                InferenceEngine.getVariable("Safety Important").setInput(inputs[1]);
                InferenceEngine.getVariable("Enjoys People").setInput(inputs[2]);
                InferenceEngine.getVariable("Plaid Wins").setInput(inputs[3]);
                InferenceEngine.getVariable("Likes Drugs").setInput(inputs[4]);
                InferenceEngine.getVariable("Likes Close").setInput(inputs[5]);
                LinguisticVariable a = Main.getEngine(false).answer();
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
                reset();
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
                // Create the new rule
                FuzzyRule rule = InferenceEngine.getRule("Area", answer);
                InferenceEngine.removeRule(rule);
                rule = rule.alterRule(feedback);
                InferenceEngine.addRule(rule);                
                Main.getEngine(false).saveRules();
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
           //Main.engine.updateRules(answer, feedback);
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
    private void addHighLowDropDown(JPanel p, GridBagConstraints c, int i)
    {
        
        String[] items = {"Very High", "High", "Average", "Low", "Very Low"};
        
        JComboBox jc = new JComboBox(items);
        
        jc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ruleAnswers[i] = (String) jc.getSelectedItem();
            }
        });
        
        p.add(jc, c);
    }
    
    private void addAnswerDropDown(JPanel p, GridBagConstraints c)
    {
        String[] items = {"College Heights","Ridgeview", "Hart",
        "Foothills", "North Opsika", "Fort George", "Westwood",
        "Crescents", "Downtown", "PedenHill", "OutofTown"};

        
        JComboBox jc = new JComboBox(items);
        
        jc.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ruleAnswers[6] = (String) jc.getSelectedItem();
            }
        });
        
        p.add(jc, c);
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
                feedback.put(names[i], Feedback.MORE);
            }
        });
        
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                feedback.put(names[i], Feedback.LESS);
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
    
    private void reset()
    {
        for(int i=0; i<inputs.length; i++)
        {
            inputs[i] = 3.0;
        }
    }
}
