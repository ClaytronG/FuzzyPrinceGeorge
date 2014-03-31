package com.fuzzypg;

/**
 * left OR right
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
        operation.append(" ");
        
        return operation.toString();
    }
}
