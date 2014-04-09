package com.fuzzypg;

import java.util.ArrayList;
import java.util.Collections;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A linguistic term or fuzzy set belongs to a linguistic variable, and has a 
 * member function located within the universe of discourse.
 * 
 * @author Clayton
 */
public class FuzzySet {
    
    /**
     * The name of this Fuzzy Set.
     */
    private final String name;
    
    /**
     * List containing all Pairs (x and y points) that make up the function of 
     * this term.
     */
    private final ArrayList<Pair> values;

    /**
     * Left most (minimum) x-value that this term covers.
     */
    private double minValue = Integer.MAX_VALUE;
    
    /**
     * Right most (maximum) x-value that this term covers.
     */
    private double maxValue = Integer.MIN_VALUE;
    
    /**
     * When defuzzified this is the maximum value of the membership.
     */
    private double fuzzyLimit = Double.MAX_VALUE;
    
    /**
     * First point (value closest to 1) of this set.
     */
    private double pointValue = 0;
    
    /**
     * Creates a Fuzzy Set by parsing a JSON object.<p>
     *   
     *  {
     *      "name" : String,
     *      "value" : [
     *          [ Real, Real ], // X,Y
     *          ...
     *      ]
     *  }<p>
     * 
     * Where name is the name of the Fuzzy Set, and value is a list
     * of points that make up the set.
     * 
     * @param object JSON object describing a fuzzy set
     */
    public FuzzySet(JSONObject object) {
        name = object.getString("name");
        values = new ArrayList<>();
        JSONArray valuesArray = object.getJSONArray("value");
        for (int i = 0; i < valuesArray.length(); ++i) {
            JSONArray point = valuesArray.getJSONArray(i);
            Pair pair = new Pair(point.getDouble(0), point.getDouble(1));
            addValue(pair);
        }
    }

    /**
     * Adds a list of Pairs (x and y points) to this term. Updates the min and 
     * max values of this term if needed.
     * 
     * @param pairs list of points to add to this set
     */
    private void addValue(Pair... pairs) {
        for (Pair pair : pairs) {
            values.add(pair);
            // Update the minimum value that this term covers
            if (pair.getFirst() < minValue) minValue = pair.getFirst();
            // Update the maximum value that this term covers.
            if (pair.getFirst() > maxValue) maxValue = pair.getFirst();
            // If this point is 1 then its point value is this
            if (pair.getSecond() == 1) pointValue = pair.getFirst();
        }
        // Sort the points in increasing order with respect to the X value,
        // for them membership algorithm to work.
        sortValues();
    }
    
    /**
     * Sorts the values in increasing order based on the x value, so they are
     * evaluated correctly.
     */
    private void sortValues() {
        // LAMBDAS! Java 8!
        Collections.sort(values, (Pair p1, Pair p2) -> 
                Double.compare(p1.getFirst(), p2.getFirst()));
    }

    /**
     * Returns true if this term covers the value given by input.
     * 
     * @param input value to check
     * @return      true if this set contains input
     */
    public boolean contains(double input) {
        return (input >= minValue) && (input <= maxValue);
    }

    /**
     * Returns the truth value for this term at the given input.
     * 
     * @param input value in the universe to check membership
     * @return      the truth value at input 
     */
    public double getValue(double input) {
        // Return the endpoint values if input is outside of this set
        if (input <= minValue) return values.get(0).getSecond();
        if (input > maxValue) return values.get(values.size()-1).getSecond();
        // Otherwise input falls inside of this set
        for (int i = 1; i < values.size(); ++i) {
            if (input <= values.get(i).getFirst()) {
                double x1 = values.get(i-1).getFirst();
                double y1 = values.get(i-1).getSecond();
                double x2 = values.get(i).getFirst();
                double y2 = values.get(i).getSecond();
                double answer = y1 + (y2 - y1) * ((input - x1) / (x2 - x1));
                double result = Math.min(answer, fuzzyLimit);
                return result; 
            }
        }
        return 0;
    }
    
    /**
     * Returns the name of this Fuzzy Set.
     * 
     * @return name
     */
    public String getName() { 
        return name; 
    }
    
    /**
     * Returns the smallest x-value that this fuzzy set covers.
     * 
     * @return minimum input value of this set
     */
    public double getMinValue() { 
        return minValue; 
    }
    
    /**
     * Returns the largest x-value that this fuzzy set covers.
     * 
     * @return maximum input value of this set
     */
    public double getMaxValue() { 
        return maxValue; 
    }
    
    /**
     * Set the maximum value of this fuzzy set, this is used in the
     * defuzzification process. This value is clamped in between 0 and 1.
     * 
     * @param limit max value of this set
     */
    public void setFuzzyLimit(double limit) {
        if (limit > 1) fuzzyLimit = 1;
        else if (limit < 0) fuzzyLimit = 0;
        else fuzzyLimit = limit;
    }
    
    /**
     * Returns the x-coordinate of this set where the function first reaches
     * a value of 1.
     * 
     * @return first point of this set
     */
    public double getPointValue() {
        return pointValue;
    }
    
    /**
     * Returns the maximum value that this set can return. This is used in 
     * defuzzification.
     * 
     * @return maximum value
     */
    public double getFuzzyLimit(){
        return fuzzyLimit;
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31)
                .append(name)
                .append(values)
                .toHashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof FuzzySet)) return false;
        
        FuzzySet rhs = (FuzzySet) obj;
        return new EqualsBuilder()
                .append(name, rhs.name)
                .append(values, rhs.values)
                .isEquals();
    }
    
}
