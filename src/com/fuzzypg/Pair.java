package com.fuzzypg;

/**
 * A point (x,y) used for defining fuzzy sets.
 * 
 * @author Clayton
 */
public class Pair {
    
    /**
     * X value.
     */
    private final double first;
    
    /**
     * Y value.
     */
    private final double second;

    /**
     * Creates an x,y point.
     * 
     * @param first  x value
     * @param second y value 
     */
    public Pair(double first, double second) {
        this.first = first;
        this.second = second;
    }
    
    public double getFirst() {
        return first;
    }
    
    public double getSecond() {
        return second;
    }
}
