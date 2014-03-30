
import java.util.ArrayList;
import javax.security.sasl.Sasl;
import javax.swing.JSlider;

/**
 *
 * @author Clayton
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        UI myUI= new UI();
        myUI.startUI();
        
        
    }
    
    private static void housingTest() {
        // Some variables yo
        LinguisticVariable price = new LinguisticVariable("Price", false),
                 safety = new LinguisticVariable("Safety", false),
                 people = new LinguisticVariable("People", false),
                 style = new LinguisticVariable("Style", false),
                 drugs = new LinguisticVariable("Drugs", false),
                 proximity = new LinguisticVariable("Proximity", false);
        
        
        LinguisticTerm strongDisagree = new LinguisticTerm("Strongly Disagree");
        strongDisagree.addValue(new Pair(0,1), new Pair(1,0));
        LinguisticTerm disagree = new LinguisticTerm("Disagree");
        disagree.addValue(new Pair(0,0), new Pair(1,1), new Pair(2,0));
        LinguisticTerm neutral = new LinguisticTerm("Neutral");
        neutral.addValue(new Pair(1,0), new Pair(2,1), new Pair(3,0));
        LinguisticTerm agree = new LinguisticTerm("Agree");
        agree.addValue(new Pair(2,0), new Pair(3,1), new Pair(4,0));
        LinguisticTerm strongAgree = new LinguisticTerm("Strongly Agree");
        strongAgree.addValue(new Pair(3,0), new Pair(4,1));
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
        FuzzyRuleTerm first = new FuzzyRuleTerm(safety, "Agree", false);
        FuzzyRuleTerm second = new FuzzyRuleTerm(price, "Strongly Agree", false);
        FuzzyRuleTerm result = new FuzzyRuleTerm(area, "College Heights", false);
        FuzzyRuleAnd and = new FuzzyRuleAnd(first, second);
        FuzzyRule rule = new FuzzyRule(and, result);
        ArrayList<FuzzyRule> rules = new ArrayList<>();
        
        InferenceEngine engine = new InferenceEngine();
        engine.setRules(rules);
        engine.setVariables(variables);
        
        JSlider costSlider = new JSlider(JSlider.HORIZONTAL, price.getMinValue(), price.getMaxValue(), price.getMaxValue()/2);
        JSlider safetySlider = new JSlider(JSlider.HORIZONTAL, safety.getMinValue(), safety.getMaxValue(), safety.getMaxValue()/2);
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
