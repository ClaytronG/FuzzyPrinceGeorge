package com.fuzzypg;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
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
    private final HashMap<String, LinguisticTerm> values;

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
        values = new HashMap<>();
        this.answer = answer;
    }
    
    /**
     * Adds a list of terms to this fuzzy set.
     * 
     * @param terms list of terms to add
     */
    public void addTerms(List<LinguisticTerm> terms) {
        for (LinguisticTerm term : terms) {
            addTerms(term);
        }
    }

    /**
     * Adds terms to this fuzzy set. Updates the minimum and maximum values
     * that this fuzzy set covers.
     * 
     * @param terms list of terms to add to this fuzzy set
     */
    public void addTerms(LinguisticTerm... terms) {
        for (LinguisticTerm term : terms) {
            if (answer) {
                term.setFuzzyLimit(0);
            }
            values.put(term.getName(), term);
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
    public Collection<LinguisticTerm> getTermFromInput(double x) {
        Collection<LinguisticTerm> terms = new ArrayList<>();
        for (LinguisticTerm term : values.values()) {
            if (term.contains(x)) {
                terms.add(term);
            }
        }
        return terms;
    }
    
    public double getMembershipValueOf(String name) {
        return values.get(name).getValue(input);
    }
    
    /**
     * Returns a list of all terms associated with this Fuzzy set.
     * 
     * @return list of terms
     */
    public Collection<LinguisticTerm> getTerms() {
        return values.values();
    }
    
    public LinguisticTerm getTerm(String name) {
        return values.get(name);
    }
    
    /**
     * Deffuzify this set using the Center of Gravity (CoG) technique. 
     * 
     * @return the defuzzified value
     */
    public double defuzzify() {
        System.out.println("Defuzzifing...");
        double[] results = new double[(int) ((maxValue - minValue) / STEP_SIZE)];
        for (LinguisticTerm term : values.values()) {
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
