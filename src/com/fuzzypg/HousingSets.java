package com.fuzzypg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Static class that holds our Linguistic Variables, Fuzzy Sets, and Rules.
 * 
 * @author Clayton
 */
public class HousingSets {
    
    //input variables
    public static LinguisticVariable canAfford;
    public static LinguisticVariable safetyImportant;
    public static LinguisticVariable enjoyPeople;
    public static LinguisticVariable plaidWins;
    public static LinguisticVariable likesDrugs;
    public static LinguisticVariable likesClose;
    
    // Linguistic Variables
    public static LinguisticVariable price;
    public static LinguisticVariable safety;
    public static LinguisticVariable people;
    public static LinguisticVariable style;
    public static LinguisticVariable drugs;
    public static LinguisticVariable proximity;
    // ANSWER
    public static LinguisticVariable area;
    
    
    public static LinguisticTerm veryHigh;
    public static LinguisticTerm high;
    public static LinguisticTerm middle;
    public static LinguisticTerm low;
    public static LinguisticTerm veryLow;
    // Linguistic Terms
    public static LinguisticTerm strongDisagree;
    public static LinguisticTerm disagree;
    public static LinguisticTerm neutral;
    public static LinguisticTerm agree;
    public static LinguisticTerm strongAgree;
    // ANSWERS
    public static LinguisticTerm collegeHeights;
    public static LinguisticTerm ridgeview;
    public static LinguisticTerm hart;
    public static LinguisticTerm foothills;
    public static LinguisticTerm ospika;
    public static LinguisticTerm fortGeorge;
    public static LinguisticTerm westwood;
    public static LinguisticTerm crescents;
    public static LinguisticTerm downtown;
    public static LinguisticTerm pedenHill;
    public static LinguisticTerm outOfTown;    
    
    private static HashMap<String, LinguisticVariable> variables;
    private static HashMap<String, LinguisticTerm> terms;
    
    /**
     * Call this when the program starts.
     */
    public static void init() {
        variables = new HashMap<>();
        terms = new HashMap<>();
        
        // Variables
        price = new LinguisticVariable("Price", false);
        safety = new LinguisticVariable("Safety", false);
        people = new LinguisticVariable("People", false);
        style = new LinguisticVariable("Style", false);
        drugs = new LinguisticVariable("Drugs", false);
        proximity = new LinguisticVariable("Proximity", false);
        
        
        canAfford = new LinguisticVariable("Price", false);
        safetyImportant = new LinguisticVariable("Safety", false);
        enjoyPeople = new LinguisticVariable("People", false);
        plaidWins = new LinguisticVariable("Style", false);
        likesDrugs = new LinguisticVariable("Drugs", false);
        likesClose = new LinguisticVariable("Proximity", false);
        
        variables.put(price.getName(), price);
        variables.put(safety.getName(), safety);
        variables.put(people.getName(), people);
        variables.put(style.getName(), style);
        variables.put(drugs.getName(), drugs);
        variables.put(proximity.getName(), proximity);
        
        variables.put(canAfford.getName(), canAfford);
        variables.put(safetyImportant.getName(), safetyImportant);
        variables.put(enjoyPeople.getName(), enjoyPeople);
        variables.put(plaidWins.getName(), plaidWins);
        variables.put(likesDrugs.getName(), likesDrugs);
        variables.put(likesClose.getName(), likesClose);
        
        // ANSWER
        area = new LinguisticVariable("Area", true);
        variables.put(area.getName(), area);
        
        // Terms
        strongDisagree = new LinguisticTerm("Strongly Disagree");
        strongDisagree.addValue(new Pair(0,1), new Pair(1,1), new Pair(2,0));
        disagree = new LinguisticTerm("Disagree");
        disagree.addValue(new Pair(0,0), new Pair(1,1), new Pair(2,1), new Pair(3,0));
        neutral = new LinguisticTerm("Neutral");
        neutral.addValue(new Pair(1,0.5), new Pair(2,1), new Pair(3,1), new Pair(4,0.5));
        agree = new LinguisticTerm("Agree");
        agree.addValue(new Pair(2,0), new Pair(3,1), new Pair(4,1), new Pair(5,0));
        strongAgree = new LinguisticTerm("Strongly Agree");
        strongAgree.addValue(new Pair(3,0), new Pair(4,1), new Pair(5,1));
        terms.put(strongDisagree.getName(), strongDisagree);
        terms.put(disagree.getName(), disagree);
        terms.put(neutral.getName(), neutral);
        terms.put(agree.getName(), agree);
        terms.put(strongAgree.getName(), strongAgree);
        
        veryLow= new LinguisticTerm("Very Low");
        veryLow.addValue(new Pair(0,1), new Pair(1,1), new Pair(2,0));
        low = new LinguisticTerm("Low");
        low.addValue(new Pair(0,0), new Pair(1,1), new Pair(2,1), new Pair(3,0));
        middle = new LinguisticTerm("Middle");
        middle.addValue(new Pair(1,0.5), new Pair(2,1), new Pair(3,1), new Pair(4,0.5));
        high = new LinguisticTerm("High");
        high.addValue(new Pair(2,0), new Pair(3,1), new Pair(4,1), new Pair(5,0));
        veryHigh = new LinguisticTerm("Very High");
        veryHigh.addValue(new Pair(3,0), new Pair(4,1), new Pair(5,1));
        terms.put(veryLow.getName(), veryLow);
        terms.put(low.getName(), low);
        terms.put(middle.getName(), middle);
        terms.put(high.getName(), high);
        terms.put(veryHigh.getName(), veryHigh);
       
        
        // ANSWERS
        collegeHeights = new LinguisticTerm("College Heights");
        collegeHeights.addValue(new Pair(0,0), new Pair(5,1), new Pair(10,0));
        ridgeview = new LinguisticTerm("Ridgeview");
        ridgeview.addValue(new Pair(10,0), new Pair(15,1), new Pair(20,0));
        hart = new LinguisticTerm("Hart");
        hart.addValue(new Pair(20,0), new Pair(25,1), new Pair(30,0));
        foothills = new LinguisticTerm("Foothills");
        foothills.addValue(new Pair(30,0), new Pair(35,1), new Pair(40,0));
        ospika = new LinguisticTerm("North Ospika");
        ospika.addValue(new Pair(40,0), new Pair(45,1), new Pair(50,0));
        fortGeorge = new LinguisticTerm("Fort George");
        fortGeorge.addValue(new Pair(50,0), new Pair(55,1), new Pair(60,0));
        westwood = new LinguisticTerm("Westwood and Pinewood");
        westwood.addValue(new Pair(60,0), new Pair(65,1), new Pair(70,0));
        crescents = new LinguisticTerm("Crescents");
        crescents.addValue(new Pair(70,0), new Pair(75,1), new Pair(80,0));
        downtown = new LinguisticTerm("Downtown");
        downtown.addValue(new Pair(80,0), new Pair(85,1), new Pair(90,0));
        pedenHill = new LinguisticTerm("Peden Hill");
        pedenHill.addValue(new Pair(90,0), new Pair(95,1), new Pair(100,0));
        outOfTown = new LinguisticTerm("Out of Town");
        outOfTown.addValue(new Pair(100,0), new Pair(105,1), new Pair(110,0));
        terms.put(collegeHeights.getName(), collegeHeights);
        terms.put(ridgeview.getName(), ridgeview);
        terms.put(hart.getName(), hart);
        terms.put(foothills.getName(), foothills);
        terms.put(ospika.getName(), ospika);
        terms.put(fortGeorge.getName(), fortGeorge);
        terms.put(westwood.getName(), westwood);
        terms.put(crescents.getName(), crescents);
        terms.put(downtown.getName(), downtown);
        terms.put(pedenHill.getName(), pedenHill);
        terms.put(outOfTown.getName(), outOfTown);
        
        
        ArrayList<LinguisticTerm> inputTerms = new ArrayList<>();
        inputTerms.add(strongDisagree);
        inputTerms.add(disagree);
        inputTerms.add(neutral);
        inputTerms.add(agree);
        inputTerms.add(strongAgree);
        
        ArrayList<LinguisticTerm> fuzzyTerms = new ArrayList<>();
        fuzzyTerms.add(veryLow);
        fuzzyTerms.add(low);
        fuzzyTerms.add(middle);
        fuzzyTerms.add(high);
        fuzzyTerms.add(veryHigh);
        
        
        ArrayList<LinguisticTerm> answerTerms = new ArrayList<>();
        answerTerms.add(collegeHeights);
        answerTerms.add(ridgeview);
        answerTerms.add(hart);
        answerTerms.add(foothills);
        answerTerms.add(ospika);
        answerTerms.add(fortGeorge);
        answerTerms.add(westwood);
        answerTerms.add(crescents);
        answerTerms.add(downtown);
        answerTerms.add(pedenHill);
        answerTerms.add(outOfTown);
        
        //add terms
        canAfford.addTerms(inputTerms);
        safetyImportant.addTerms(inputTerms);
        enjoyPeople.addTerms(inputTerms);
        plaidWins.addTerms(inputTerms);
        likesDrugs.addTerms(inputTerms);
        likesClose.addTerms(inputTerms);
        
        // Add the terms to the variables
        price.addTerms(fuzzyTerms);
        safety.addTerms(fuzzyTerms);
        people.addTerms(fuzzyTerms);
        style.addTerms(fuzzyTerms);
        drugs.addTerms(fuzzyTerms);
        proximity.addTerms(fuzzyTerms);
        // ANSWER
        area.addTerms(answerTerms);
    }
    
    public static LinguisticVariable getVariable(String name) {
        return variables.get(name);
    }
    
    public static Collection<LinguisticVariable> getVariables() {
        return variables.values();
    }
    
    public static LinguisticTerm getTerm(String name) {
        return terms.get(name);
    }
    
    public static Collection<LinguisticTerm> getTerms() {
        return terms.values();
    }

}
