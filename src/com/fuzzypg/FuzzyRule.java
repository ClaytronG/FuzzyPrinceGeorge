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
    
    private String name;
    private double[] values;
    
    // The IFs
    private final FuzzyRuleObject premise;   
    // The THEN
    private final FuzzyRuleTerm result;
    
    public FuzzyRule(FuzzyRuleObject premise, FuzzyRuleTerm result) {
        this.premise = premise;
        this.result = result;
    }
    
    public FuzzyRule(FuzzyRuleObject premise, FuzzyRuleTerm result, String name, double[] values) {
        this(premise, result);
        this.name = name;
        this.values = values;
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
    
    public String getName() {
        return name;
    }
    
    public double[] getValues() {
        return values;
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
    
    public String getRuleString() {
        StringBuilder builder = new StringBuilder();
        builder.append(name);
        builder.append(",");
        builder.append(values[0]);
        builder.append(",");
        builder.append(values[1]);
        builder.append(",");
        builder.append(values[2]);
        builder.append(",");
        builder.append(values[3]);
        builder.append(",");
        builder.append(values[4]);
        builder.append(",");
        builder.append(values[5]);
        return builder.toString();
    }
    
}
