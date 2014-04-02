package com.fuzzypg;

/**
 * Operations are union (OR) and intersection (AND).
 * 
 * @author Clayton
 */
public abstract class FuzzyRuleOperation extends FuzzyRuleObject {
    
    protected final FuzzyRuleObject left;
    protected final FuzzyRuleObject right;
    
    public FuzzyRuleOperation(FuzzyRuleObject left, FuzzyRuleObject right) {
        this.left = left;
        this.right = right;
    }
    
    @Override
    public abstract double getResult(boolean defuzzy);
    
}
