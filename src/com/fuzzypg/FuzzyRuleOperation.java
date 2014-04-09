package com.fuzzypg;

/**
 * Operations are union (OR) and intersection (AND).
 * 
 * @author Clayton
 */
public abstract class FuzzyRuleOperation extends FuzzyRuleObject {
    
    /**
     * Left side of the operation.
     */
    protected final FuzzyRuleObject left;
    
    /**
     * Right side of the operation.
     */
    protected final FuzzyRuleObject right;
    
    public FuzzyRuleOperation(FuzzyRuleObject left, FuzzyRuleObject right) {
        this.left = left;
        this.right = right;
    }
    
    public FuzzyRuleObject getLeft() {
        return left;
    }
 
    public FuzzyRuleObject getRight() {
        return right;
    }
}
