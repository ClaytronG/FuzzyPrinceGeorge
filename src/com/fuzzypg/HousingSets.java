package com.fuzzypg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Static class that holds our Linguistic Variables.
 * 
 * @author Clayton
 */
public class HousingSets {
    
    // input variables
    public static LinguisticVariable canAfford;
    public static LinguisticVariable safetyImportant;
    public static LinguisticVariable enjoyPeople;
    public static LinguisticVariable plaidWins;
    public static LinguisticVariable likesDrugs;
    public static LinguisticVariable likesClose;
    // middle variables
    public static LinguisticVariable price;
    public static LinguisticVariable safety;
    public static LinguisticVariable people;
    public static LinguisticVariable style;
    public static LinguisticVariable drugs;
    public static LinguisticVariable proximity;
    // answer variables
    public static LinguisticVariable area;  
    
    private static HashMap<String, LinguisticVariable> variables;
    
    /**
     * Call this when the program starts.
     */
    public static void init() {
        variables = new HashMap<>();
        
        initLinguisticVariables();
    }
    
    private static void initLinguisticVariables() {
        initInputLinguisticVariables();
        initMiddleLinguisticVariables();
        initAnswerLinguisticVariables();
    }
    
    /**
     * Creates the linguistic variables that take input from the user.
     */
    private static void initInputLinguisticVariables() { 
        canAfford = new LinguisticVariable("Can Afford", false);
        canAfford.addTerms(initInputFuzzySets());
        safetyImportant = new LinguisticVariable("Safety Important", false);
        safetyImportant.addTerms(initInputFuzzySets());
        enjoyPeople = new LinguisticVariable("Enjoys People", false);
        enjoyPeople.addTerms(initInputFuzzySets());
        plaidWins = new LinguisticVariable("Plaid Wins", false);
        plaidWins.addTerms(initInputFuzzySets());
        likesDrugs = new LinguisticVariable("Likes Drugs", false);
        likesDrugs.addTerms(initInputFuzzySets());
        likesClose = new LinguisticVariable("Likes Close", false);
        likesClose.addTerms(initInputFuzzySets());
        
        variables.put(canAfford.getName(), canAfford);
        variables.put(safetyImportant.getName(), safetyImportant);
        variables.put(enjoyPeople.getName(), enjoyPeople);
        variables.put(plaidWins.getName(), plaidWins);
        variables.put(likesDrugs.getName(), likesDrugs);
        variables.put(likesClose.getName(), likesClose);
    }
    
    /**
     * Creates the linguistic variables used in intermediate decision making.
     */
    private static void initMiddleLinguisticVariables() {        
        price = new LinguisticVariable("Price", false);
        price.addTerms(initMiddleFuzzySets());
        safety = new LinguisticVariable("Safety", false);
        safety.addTerms(initMiddleFuzzySets());
        people = new LinguisticVariable("People", false);
        people.addTerms(initMiddleFuzzySets());
        style = new LinguisticVariable("Style", false);
        style.addTerms(initMiddleFuzzySets());
        drugs = new LinguisticVariable("Drugs", false);
        drugs.addTerms(initMiddleFuzzySets());
        proximity = new LinguisticVariable("Proximity", false); 
        proximity.addTerms(initMiddleFuzzySets());
        
        variables.put(price.getName(), price);
        variables.put(safety.getName(), safety);
        variables.put(people.getName(), people);
        variables.put(style.getName(), style);
        variables.put(drugs.getName(), drugs);
        variables.put(proximity.getName(), proximity);        
    }
    
    /**
     * Creates the answer linguistic variables.
     */
    private static void initAnswerLinguisticVariables() {
        area = new LinguisticVariable("Area", true);
        area.addTerms(initAnswerFuzzySets());
        variables.put(area.getName(), area);        
    }
    
    /**
     * Creates a list containing fuzzy sets for linguistic variables used for
     * input.
     * 
     * @return input fuzzy sets
     */
    private static ArrayList<FuzzySet> initInputFuzzySets() {
        ArrayList<FuzzySet> inputSetList = new ArrayList<>();
        
        FuzzySet strongDisagree = new FuzzySet("Strongly Disagree");
        strongDisagree.addValue(new Pair(0,1), new Pair(1,1), new Pair(2,0));
        inputSetList.add(strongDisagree);
        
        FuzzySet disagree = new FuzzySet("Disagree");
        disagree.addValue(new Pair(0,0), new Pair(1,1), new Pair(2,1), new Pair(3,0));
        inputSetList.add(disagree);
        
        FuzzySet neutral = new FuzzySet("Neutral");
        neutral.addValue(new Pair(1,0), new Pair(2,1), new Pair(3,1), new Pair(4,0));
        inputSetList.add(neutral);
        
        FuzzySet agree = new FuzzySet("Agree");
        agree.addValue(new Pair(2,0), new Pair(3,1), new Pair(4,1), new Pair(5,0));
        inputSetList.add(agree);
        
        FuzzySet strongAgree = new FuzzySet("Strongly Agree");
        strongAgree.addValue(new Pair(3,0), new Pair(4,1), new Pair(5,1));
        inputSetList.add(strongAgree);
        
        return inputSetList;
    }
    
    /**
     * Creates a list containing fuzzy sets for linguistic variables used in
     * intermediate rules.
     * 
     * @return middle fuzzy sets
     */
    private static ArrayList<FuzzySet> initMiddleFuzzySets() {
        ArrayList<FuzzySet> middleSetList = new ArrayList<>();
        
        FuzzySet veryLow = new FuzzySet("Very Low");
        veryLow.addValue(new Pair(0,1), new Pair(1,1), new Pair(2,0));
        middleSetList.add(veryLow);
        
        FuzzySet low = new FuzzySet("Low");
        low.addValue(new Pair(0,0), new Pair(1,1), new Pair(2,1), new Pair(3,0));
        middleSetList.add(low);
        
        FuzzySet middle = new FuzzySet("Middle");
        middle.addValue(new Pair(1,0), new Pair(2,1), new Pair(3,1), new Pair(4,0));
        middleSetList.add(middle);
        
        FuzzySet high = new FuzzySet("High");
        high.addValue(new Pair(2,0), new Pair(3,1), new Pair(4,1), new Pair(5,0));
        middleSetList.add(high);
        
        FuzzySet veryHigh = new FuzzySet("Very High");
        veryHigh.addValue(new Pair(3,0), new Pair(4,1), new Pair(5,1));
        middleSetList.add(veryHigh);
        
        return middleSetList;
    }
    
    /**
     * Creates the list of fuzzy sets belonging to the answer linguistic 
     * variable.
     * 
     * @return answer fuzzy sets
     */
    private static ArrayList<FuzzySet> initAnswerFuzzySets() {
        ArrayList<FuzzySet> answerSetList = new ArrayList<>();
        
        FuzzySet collegeHeights = new FuzzySet("College Heights");
        collegeHeights.addValue(new Pair(0,0), new Pair(5,1), new Pair(10,0));
        answerSetList.add(collegeHeights);
        
        FuzzySet ridgeview = new FuzzySet("Ridgeview");
        ridgeview.addValue(new Pair(10,0), new Pair(15,1), new Pair(20,0));
        answerSetList.add(ridgeview);
        
        FuzzySet hart = new FuzzySet("Hart");
        hart.addValue(new Pair(20,0), new Pair(25,1), new Pair(30,0));
        answerSetList.add(hart);
        
        FuzzySet foothills = new FuzzySet("Foothills");
        foothills.addValue(new Pair(30,0), new Pair(35,1), new Pair(40,0));
        answerSetList.add(foothills);
        
        FuzzySet ospika = new FuzzySet("North Ospika");
        ospika.addValue(new Pair(40,0), new Pair(45,1), new Pair(50,0));
        answerSetList.add(ospika);
        
        FuzzySet fortGeorge = new FuzzySet("Fort George");
        fortGeorge.addValue(new Pair(50,0), new Pair(55,1), new Pair(60,0));
        answerSetList.add(fortGeorge);
        
        FuzzySet westwood = new FuzzySet("Westwood and Pinewood");
        westwood.addValue(new Pair(60,0), new Pair(65,1), new Pair(70,0));
        answerSetList.add(westwood);
        
        FuzzySet crescents = new FuzzySet("Crescents");
        crescents.addValue(new Pair(70,0), new Pair(75,1), new Pair(80,0));
        answerSetList.add(crescents);
        
        FuzzySet downtown = new FuzzySet("Downtown");
        downtown.addValue(new Pair(80,0), new Pair(85,1), new Pair(90,0));
        answerSetList.add(downtown);
        
        FuzzySet pedenHill = new FuzzySet("Peden Hill");
        pedenHill.addValue(new Pair(90,0), new Pair(95,1), new Pair(100,0));
        answerSetList.add(pedenHill);
        
        FuzzySet outOfTown = new FuzzySet("Out of Town");
        outOfTown.addValue(new Pair(100,0), new Pair(105,1), new Pair(110,0));
        answerSetList.add(outOfTown);
        
        return answerSetList;
    }
    
    public static LinguisticVariable getVariable(String name) {
        return variables.get(name);
    }
    
    public static Collection<LinguisticVariable> getVariables() {
        return variables.values();
    }
    
    public static void printVariables() {
        for (LinguisticVariable variable : variables.values()) {
            System.out.print(variable.getName() + " : { ");
            for (FuzzySet set : variable.getTerms()) {
                System.out.print(set.getName() + ", ");
            }
            System.out.println(" }");
        }
    }

}
