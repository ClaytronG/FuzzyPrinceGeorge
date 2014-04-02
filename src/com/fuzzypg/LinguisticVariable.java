package com.fuzzypg;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Linguistic variables consist of a name and fuzzy sets. If it is defined as 
 * an answer, then this can be defuzzified.
 * 
 * @author Clayton
 */
public class LinguisticVariable {
    
    /**
     * How many discrete steps in the universe of discourse.
     */
    private final double STEP_SIZE = 0.1;
    
    private final String name;
    
    /**
     * Where to evaluate this linguistic variable.
     */
    private double input;
    
    /**
     * If this is an answer variable and can be defuzzified.
     */
    private final boolean answer;
    
    /**
     * Collection of sets that belong to this linguistic variable.
     */
    private final HashMap<String, FuzzySet> sets;

    /**
     * Minimum value that this fuzzy set covers.
     */
    private double minValue = Integer.MAX_VALUE;
    
    /**
     * Maximum value that this fuzzy set covers.
     */
    private double maxValue = Integer.MIN_VALUE;

    /**
     * Creates a new fuzzy set without any values.
     * 
     * @param name   name of the fuzzy set
     * @param answer 
     */
    public LinguisticVariable(String name, boolean answer) {
        this.name = name;
        sets = new HashMap<>();
        this.answer = answer;
    }
    
    /**
     * Adds a list of terms to this fuzzy set.
     * 
     * @param terms list of terms to add
     */
    public void addTerms(List<FuzzySet> terms) {
        for (FuzzySet term : terms) {
            addTerms(term);
        }
    }

    /**
     * Adds terms to this fuzzy set. Updates the minimum and maximum values
     * that this fuzzy set covers.
     * 
     * @param terms list of terms to add to this fuzzy set
     */
    public void addTerms(FuzzySet... terms) {
        for (FuzzySet term : terms) {
            if (answer) {
                term.setFuzzyLimit(0);
            }
            sets.put(term.getName(), term);
            if (term.getMinValue() < minValue) minValue = term.getMinValue();
            if (term.getMaxValue() > maxValue) maxValue = term.getMaxValue();
        }
    }
    
    /**
     * Returns the list of terms that cover the value x.
     * 
     * @param x
     * @return 
     */
    public Collection<FuzzySet> getTermFromInput(double x) {
        Collection<FuzzySet> terms = new ArrayList<>();
        for (FuzzySet term : sets.values()) {
            if (term.contains(x)) {
                terms.add(term);
            }
        }
        return terms;
    }
    
    public double getMembershipValueOf(String name) {
        return sets.get(name).getValue(input);
    }
    
    /**
     * Returns a list of all terms associated with this Fuzzy set.
     * 
     * @return list of terms
     */
    public Collection<FuzzySet> getTerms() {
        return sets.values();
    }
    
    /**
     * 
     * 
     * @param name 
     * @return     
     */
    public FuzzySet getTerm(String name) {
        return sets.get(name);
    }
    
    /**
     * Deffuzify this set using the Center of Gravity (CoG) technique. 
     * 
     * @return the defuzzified value
     */
    public double defuzzify() {
        System.out.println("Defuzzifing...");
        double[] results = new double[(int) ((maxValue - minValue) / STEP_SIZE)];
        for (FuzzySet term : sets.values()) {
            for (int i = 0; i < results.length; ++i) {
                double x = minValue + (i * STEP_SIZE);
                // Generate the resulting graph using max-min
                double value = term.getValue(x);
                double thing = results[i];
                if (value > thing) {
                    results[i] = value;
                }
            }
        }
        
        double numerator = 0;
        double denominator = 0;
        for (int i = 0; i < results.length; ++i) {
            double x = minValue + (i * STEP_SIZE);
            numerator += x * results[i];
            denominator += results[i];
        }
        return numerator / denominator;
    }
    
    public String getName() {
        return name;
    }
    
    public double getMinValue() {
        return minValue;
    }
    
    public double getMaxValue() {
        return maxValue;
    }
    
    public boolean isAnswer() {
        return answer;
    }
    
    public void setInput(double input) {
        this.input = input;
    }
}
