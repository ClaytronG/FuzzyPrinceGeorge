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
    
    private final double STEP_SIZE = 0.1;
    
    private final String name;
    
    private double input;
    
    private final boolean answer;
    
    /**
     * 
     */
    private final HashMap<String, LinguisticTerm> values;

    /**
     * Minimum value that this fuzzy set covers.
     */
    private int minValue = Integer.MAX_VALUE;
    
    /**
     * Maximum value that this fuzzy set covers.
     */
    private int maxValue = Integer.MIN_VALUE;

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

    /**
     * 
     * 
     * @param input 
     */
    public void input(double input) {
        System.out.println("Input " + input);
        for (LinguisticTerm term : values.values()) {
            if (term.contains(input)) {
                System.out.print("\t" + term.getName() + " = ");
                System.out.println("\t" + term.getValue(input));
            }
        }
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
        double[] results = new double[(int) ((maxValue - minValue) / STEP_SIZE)];
        for (LinguisticTerm term : values.values()) {
            System.out.println(term.getName());
            for (int i = 0; i < results.length; ++i) {
                double x = minValue + (i * STEP_SIZE);
                // Generate the resulting graph using max-min
                double value = term.getValue(x);
                double thing = results[i];
                if (value > thing) {
                    System.out.println("\tReplacing " + thing + " @ " + i + " with " + value);
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
    
    public int getMinValue() {
        return minValue;
    }
    
    public int getMaxValue() {
        return maxValue;
    }
    
    public boolean isAnswer() {
        return answer;
    }
    
    public void setInput(double input) {
        this.input = input;
    }
}
