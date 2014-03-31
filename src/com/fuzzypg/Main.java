package com.fuzzypg;


import com.fuzzypg.ui.UI;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Clayton
 */
public class Main extends JPanel {
    
    public static InferenceEngine engine;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {        
        HousingSets.init();
        
        engine = new InferenceEngine("Area.rules");
        engine.setVariables(HousingSets.getVariables());
               
        UI myUI = new UI();
        myUI.startUI();
        /**
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
        * */
    }
    
    public Main() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        housingTest();
    }
    
    private static JFrame frame;
    
    private static void createAndShowGUI() {
        //Create and set up the window.
        frame = new JFrame("SliderDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Main main = new Main();
        
        frame.add(main);
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
    private static final int FACTOR = 5;
    
    private void housingTest() {
        // Some variables yo
        final LinguisticVariable price = new LinguisticVariable("Price", false),
                 safety = new LinguisticVariable("Safety", false),
                 people = new LinguisticVariable("People", false),
                 style = new LinguisticVariable("Style", false),
                 drugs = new LinguisticVariable("Drugs", false),
                 proximity = new LinguisticVariable("Proximity", false);
        
        
        LinguisticTerm strongDisagree = new LinguisticTerm("Strongly Disagree");
        strongDisagree.addValue(new Pair(0,1), new Pair(1,1), new Pair(2,0));
        LinguisticTerm disagree = new LinguisticTerm("Disagree");
        disagree.addValue(new Pair(0,0), new Pair(1,1), new Pair(2,1), new Pair(3,0));
        LinguisticTerm neutral = new LinguisticTerm("Neutral");
        neutral.addValue(new Pair(1,0), new Pair(2,1), new Pair(3,1), new Pair(4,0));
        LinguisticTerm agree = new LinguisticTerm("Agree");
        agree.addValue(new Pair(2,0), new Pair(3,1), new Pair(4,1), new Pair(5,0));
        LinguisticTerm strongAgree = new LinguisticTerm("Strongly Agree");
        strongAgree.addValue(new Pair(3,0), new Pair(4,1), new Pair(5,1));
        ArrayList<LinguisticTerm> terms = new ArrayList<>();
        terms.add(strongDisagree);
        terms.add(disagree);
        terms.add(neutral);
        terms.add(agree);
        terms.add(strongAgree);
        
        price.addTerms(terms);
        safety.addTerms(terms);
        people.addTerms(terms);
        style.addTerms(terms);
        drugs.addTerms(terms);
        proximity.addTerms(terms);
        
        LinguisticVariable area = new LinguisticVariable("Area", true);
        LinguisticTerm collegeHeights = new LinguisticTerm("College Heights");
        collegeHeights.addValue(new Pair(0,0), new Pair(5,1), new Pair(10,0));
        LinguisticTerm ridgeview = new LinguisticTerm("Ridgeview");
        ridgeview.addValue(new Pair(10,0), new Pair(15,1), new Pair(20,0));
        LinguisticTerm hart = new LinguisticTerm("Hart");
        hart.addValue(new Pair(20,0), new Pair(25,1), new Pair(30,0));
        LinguisticTerm foothills = new LinguisticTerm("Foothills");
        foothills.addValue(new Pair(30,0), new Pair(35,1), new Pair(40,0));
        LinguisticTerm ospika = new LinguisticTerm("North Ospika");
        ospika.addValue(new Pair(40,0), new Pair(45,1), new Pair(50,0));
        LinguisticTerm fortGeorge = new LinguisticTerm("Fort George");
        fortGeorge.addValue(new Pair(50,0), new Pair(55,1), new Pair(60,0));
        LinguisticTerm westwood = new LinguisticTerm("Westwood and Pinewood");
        westwood.addValue(new Pair(60,0), new Pair(65,1), new Pair(70,0));
        LinguisticTerm crescents = new LinguisticTerm("Crescents");
        crescents.addValue(new Pair(70,0), new Pair(75,1), new Pair(80,0));
        LinguisticTerm downtown = new LinguisticTerm("Downtown");
        downtown.addValue(new Pair(80,0), new Pair(85,1), new Pair(90,0));
        LinguisticTerm pedenHill = new LinguisticTerm("Peden Hill");
        pedenHill.addValue(new Pair(90,0), new Pair(95,1), new Pair(100,0));
        LinguisticTerm outOfTown = new LinguisticTerm("Out of Town");
        outOfTown.addValue(new Pair(100,0), new Pair(105,1), new Pair(110,0));
        ArrayList<LinguisticTerm> resultTerms = new ArrayList<>();
        resultTerms.add(collegeHeights);
        resultTerms.add(ridgeview);
        resultTerms.add(hart);
        resultTerms.add(foothills);
        resultTerms.add(ospika);
        resultTerms.add(fortGeorge);
        resultTerms.add(westwood);
        resultTerms.add(crescents);
        resultTerms.add(downtown);
        resultTerms.add(pedenHill);
        resultTerms.add(outOfTown);
        area.addTerms(resultTerms);
        
        ArrayList<LinguisticVariable> variables = new ArrayList<>();
        variables.add(price);
        variables.add(safety);
        variables.add(people);
        variables.add(style);
        variables.add(drugs);
        variables.add(proximity);
        variables.add(area);
        
        // Some rules yo
        // IF safety IS high AND cost IS very high THEN area IS collegeheights
        FuzzyRuleTerm first = new FuzzyRuleTerm(safety, strongDisagree.getName(), false);
        FuzzyRuleTerm second = new FuzzyRuleTerm(price, strongAgree.getName(), false);
        FuzzyRuleTerm result = new FuzzyRuleTerm(area, collegeHeights.getName(), false);
        FuzzyRuleAnd and = new FuzzyRuleAnd(first, second);
        FuzzyRule rule = new FuzzyRule(and, result);
        ArrayList<FuzzyRule> rules = new ArrayList<>();
        rules.add(rule);
        
        final InferenceEngine engine = new InferenceEngine();
        engine.setRules(rules);
        engine.setVariables(variables);        
        // COST
        JLabel costLabel = new JLabel("Cost");
        JSlider costSlider = new JSlider(JSlider.HORIZONTAL, price.getMinValue()*FACTOR, price.getMaxValue()*FACTOR, price.getMaxValue());
        final JLabel costSliderValueLabel = new JLabel(Double.toString((double)costSlider.getValue() / FACTOR));
        JPanel costPanel = new JPanel();
        JPanel costSliderPanel = new JPanel();
        costSliderPanel.setLayout(new BoxLayout(costSliderPanel, BoxLayout.LINE_AXIS));
        costSliderPanel.add(costSlider);
        costSliderPanel.add(costSliderValueLabel);
        costPanel.setLayout(new BoxLayout(costPanel, BoxLayout.PAGE_AXIS));
        costPanel.add(costLabel);
        costPanel.add(costSliderPanel);
        
        // SAFETY
        JLabel safetyLabel = new JLabel("Safety");
        JSlider safetySlider = new JSlider(JSlider.HORIZONTAL, safety.getMinValue()*FACTOR, safety.getMaxValue()*FACTOR, safety.getMaxValue());
        final JLabel safetySliderValueLabel = new JLabel(Double.toString((double)safetySlider.getValue() / FACTOR));
        JPanel safetyPanel = new JPanel();
        JPanel safetySliderPanel = new JPanel();
        safetySliderPanel.setLayout(new BoxLayout(safetySliderPanel, BoxLayout.LINE_AXIS));
        safetySliderPanel.add(safetySlider);
        safetySliderPanel.add(safetySliderValueLabel);
        safetyPanel.setLayout(new BoxLayout(safetyPanel, BoxLayout.PAGE_AXIS));
        safetyPanel.add(safetyLabel);
        safetyPanel.add(safetySliderPanel);
        
        add(costPanel);
        add(safetyPanel);
        
        costSlider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                if (!source.getValueIsAdjusting()) {
                    double value = (double) source.getValue()/FACTOR;
                    costSliderValueLabel.setText(Double.toString(value));
                    price.setInput(value);
                    engine.answer();
                }
            }
        });
        
        safetySlider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                if (!source.getValueIsAdjusting()) {
                    double value = (double)source.getValue()/FACTOR;
                    safetySliderValueLabel.setText(Double.toString(value));
                    safety.setInput(value);
                    engine.answer();
                }
            }
        });
        
    }   
    
    /**
     * Test out fuzzy stuff
     * Using this as a test:
     * http://jfuzzylogic.sourceforge.net/html/manual.html#fcl
     */
    private static void testFuzzy() {
        LinguisticVariable service = new LinguisticVariable("service", false);
        LinguisticTerm poor = new LinguisticTerm("poor");
        poor.addValue(new Pair(0, 1), new Pair(4,0));
        LinguisticTerm good = new LinguisticTerm("good");
        good.addValue(new Pair(1, 0), new Pair(4, 1), new Pair(6, 1), new Pair(9, 0));
        LinguisticTerm excellent = new LinguisticTerm("excellent");
        excellent.addValue(new Pair(6, 0), new Pair(9, 1));
        service.addTerms(poor, good, excellent);

        System.out.println(service.getName());
        for (int i = 0; i <= service.getMaxValue(); ++i) {
            service.input(i);
        }

        LinguisticVariable food = new LinguisticVariable("food", false);
        LinguisticTerm rancid = new LinguisticTerm("rancid");
        rancid.addValue(new Pair(0,1), new Pair(1,1), new Pair(3,0));
        LinguisticTerm delicious = new LinguisticTerm("delicious");
        delicious.addValue(new Pair(7,0), new Pair(9,1));
        food.addTerms(rancid, delicious);

        System.out.println("\n" + food.getName());
        for (int i = 0; i <= food.getMaxValue(); ++i) {
            food.input(i);
        }
    }
    
}
