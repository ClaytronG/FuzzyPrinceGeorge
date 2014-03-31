package com.fuzzypg;

/**
 *
 * @author Clayton
 */
public class FuzzyRule {
    
    // The IFs
    private final FuzzyRuleOperation premise;   
    // The THEN
    private final FuzzyRuleTerm result;
    
    public FuzzyRule(FuzzyRuleOperation premise, FuzzyRuleTerm result) {
        this.premise = premise;
        this.result = result;
    }
    
    /**
     * Runs through the rule and updates the answer fuzzy set.
     */
    public void evaluate() {
        LinguisticVariable variable = result.getVariable();
        LinguisticTerm term = variable.getTerm(result.getValue());
        double limit = premise.getResult();
        term.setFuzzyLimit(limit);
    }
    
    @Override
    public String toString() {
        StringBuilder rule = new StringBuilder();
        
        rule.append("IF ");
        rule.append(premise);
        rule.append(" THEN ");
        rule.append(result);
        
        return rule.toString();
    }
    
}
