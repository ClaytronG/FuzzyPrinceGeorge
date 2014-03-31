package com.fuzzypg;

/**
 * Fuzzy rules consist of a premise or multiple premises and a result. Premises
 * consist of Fuzzy Terms and, if necessary, operations like AND and OR.
 * 
 * Example:
 * 
 *      IF temp IS hot AND time IS late THEN fan IS high
 *         ----------------------------      -----------
 *                  Premise                     Result
 * 
 * @author Clayton
 */
public class FuzzyRule {
    
    // The IFs
    private final FuzzyRuleObject premise;   
    // The THEN
    private final FuzzyRuleTerm result;
    
    public FuzzyRule(FuzzyRuleObject premise, FuzzyRuleTerm result) {
        this.premise = premise;
        this.result = result;
    }
    
    /**
     * Evaluates the rule and updates the corresponding answer fuzzy set.
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
