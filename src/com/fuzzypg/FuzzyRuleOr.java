package com.fuzzypg;

/**
 * The union of two fuzzy sets.
 * 
 *  X is x OR Y is y
 * 
 * where X is a linguistic variable and x is a fuzzy set in X 
 *   and Y is a linguistic variable and y is a fuzzy set in Y.
 * 
 * @author Clayton
 */
public class FuzzyRuleOr extends FuzzyRuleOperation {
    
    public FuzzyRuleOr(FuzzyRuleTerm left, FuzzyRuleTerm right) {
        super(left, right);
    }
    
    @Override
    public double getResult() {
        return Math.max(left.getResult(), right.getResult());
    }

    @Override
    public String toString() {
        StringBuilder operation = new StringBuilder();
        
        operation.append(left);
        operation.append(" OR ");
        operation.append(right);
        
        return operation.toString();
    }
}
