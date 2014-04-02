package com.fuzzypg;

/**
 * The intersection of two fuzzy sets.
 * 
 *  X is x AND y IS y
 * 
 * where X is a linguistic variable and x is a fuzzy set in X 
 *   and Y is a linguistic variable and y is a fuzzy set in Y.
 * 
 * @author Clayton
 */
public class FuzzyRuleAnd extends FuzzyRuleOperation {
    
    public FuzzyRuleAnd(FuzzyRuleObject left, FuzzyRuleObject right) {
        super(left, right);
    }
    
    @Override
    public double getResult() {
        return Math.min(left.getResult(), right.getResult());
    }

    @Override
    public String toString() {
        StringBuilder operation = new StringBuilder();
        
        operation.append(left);
        operation.append(" AND ");
        operation.append(right);
        
        return operation.toString();
    }
}
