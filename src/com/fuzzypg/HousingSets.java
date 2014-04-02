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
    // Linguistic Variables
    public static LinguisticVariable price;
    public static LinguisticVariable safety;
    public static LinguisticVariable people;
    public static LinguisticVariable style;
    public static LinguisticVariable drugs;
    public static LinguisticVariable proximity;
    // ANSWER
    public static LinguisticVariable area;
    
    // Linguistic Terms
    public static FuzzySet strongDisagree;
    public static FuzzySet disagree;
    public static FuzzySet neutral;
    public static FuzzySet agree;
    public static FuzzySet strongAgree;
    // ANSWERS
    public static FuzzySet collegeHeights;
    public static FuzzySet ridgeview;
    public static FuzzySet hart;
    public static FuzzySet foothills;
    public static FuzzySet ospika;
    public static FuzzySet fortGeorge;
    public static FuzzySet westwood;
    public static FuzzySet crescents;
    public static FuzzySet downtown;
    public static FuzzySet pedenHill;
    public static FuzzySet outOfTown;    
    
    private static HashMap<String, LinguisticVariable> variables;
    private static HashMap<String, FuzzySet> terms;
    
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
        variables.put(price.getName(), price);
        variables.put(safety.getName(), safety);
        variables.put(people.getName(), people);
        variables.put(style.getName(), style);
        variables.put(drugs.getName(), drugs);
        variables.put(proximity.getName(), proximity);
        // ANSWER
        area = new LinguisticVariable("Area", true);
        variables.put(area.getName(), area);
        
        // Terms
        strongDisagree = new FuzzySet("Strongly Disagree");
        strongDisagree.addValue(new Pair(0,1), new Pair(1,1), new Pair(2,0));
        disagree = new FuzzySet("Disagree");
        disagree.addValue(new Pair(0,0), new Pair(1,1), new Pair(2,1), new Pair(3,0));
        neutral = new FuzzySet("Neutral");
        neutral.addValue(new Pair(1,0.5), new Pair(2,1), new Pair(3,1), new Pair(4,0.5));
        agree = new FuzzySet("Agree");
        agree.addValue(new Pair(2,0), new Pair(3,1), new Pair(4,1), new Pair(5,0));
        strongAgree = new FuzzySet("Strongly Agree");
        strongAgree.addValue(new Pair(3,0), new Pair(4,1), new Pair(5,1));
        terms.put(strongDisagree.getName(), strongDisagree);
        terms.put(disagree.getName(), disagree);
        terms.put(neutral.getName(), neutral);
        terms.put(agree.getName(), agree);
        terms.put(strongAgree.getName(), strongAgree);
        // ANSWERS
        collegeHeights = new FuzzySet("College Heights");
        collegeHeights.addValue(new Pair(0,0), new Pair(5,1), new Pair(10,0));
        ridgeview = new FuzzySet("Ridgeview");
        ridgeview.addValue(new Pair(10,0), new Pair(15,1), new Pair(20,0));
        hart = new FuzzySet("Hart");
        hart.addValue(new Pair(20,0), new Pair(25,1), new Pair(30,0));
        foothills = new FuzzySet("Foothills");
        foothills.addValue(new Pair(30,0), new Pair(35,1), new Pair(40,0));
        ospika = new FuzzySet("North Ospika");
        ospika.addValue(new Pair(40,0), new Pair(45,1), new Pair(50,0));
        fortGeorge = new FuzzySet("Fort George");
        fortGeorge.addValue(new Pair(50,0), new Pair(55,1), new Pair(60,0));
        westwood = new FuzzySet("Westwood and Pinewood");
        westwood.addValue(new Pair(60,0), new Pair(65,1), new Pair(70,0));
        crescents = new FuzzySet("Crescents");
        crescents.addValue(new Pair(70,0), new Pair(75,1), new Pair(80,0));
        downtown = new FuzzySet("Downtown");
        downtown.addValue(new Pair(80,0), new Pair(85,1), new Pair(90,0));
        pedenHill = new FuzzySet("Peden Hill");
        pedenHill.addValue(new Pair(90,0), new Pair(95,1), new Pair(100,0));
        outOfTown = new FuzzySet("Out of Town");
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
        
        ArrayList<FuzzySet> fuzzyTerms = new ArrayList<>();
        fuzzyTerms.add(strongDisagree);
        fuzzyTerms.add(disagree);
        fuzzyTerms.add(neutral);
        fuzzyTerms.add(agree);
        fuzzyTerms.add(strongAgree);
        
        ArrayList<FuzzySet> answerTerms = new ArrayList<>();
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
    
    public static FuzzySet getTerm(String name) {
        return terms.get(name);
    }
    
    public static Collection<FuzzySet> getTerms() {
        return terms.values();
    }

}
