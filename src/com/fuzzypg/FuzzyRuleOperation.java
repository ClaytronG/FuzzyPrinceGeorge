package com.fuzzypg;

/**
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
    public abstract double getResult();
    
}
