package com.fuzzypg;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

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
    
    /**
     * Used in .rules files
     */
    private String name;    
    private double[] values;
    
    /**
     * The IFs
     */
    private final FuzzyRuleObject premise;
    
    /**
     * The THEN
     */
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
    
    public FuzzyRule(JSONObject rule) {
        // Construct the IF
        JSONArray ifThings = rule.getJSONArray("if");
        ArrayList<FuzzyRuleObject> andThings = new ArrayList<>();
        for (int i = 0; i < ifThings.length(); ++i) {
            JSONObject ifThing = ifThings.getJSONObject(i);
            String name = ifThing.getString("name");
            JSONArray value = ifThing.getJSONArray("value");
            if (value.length() == 1) {
                System.out.println(name + " - " + value.getString(0));
                FuzzyRuleTerm term = new FuzzyRuleTerm(name, value.getString(0));
                System.out.println(term);
                andThings.add(term);
            } else if (value.length() == 2) {
                FuzzyRuleTerm left = new FuzzyRuleTerm(name, value.getString(0));
                FuzzyRuleTerm right = new FuzzyRuleTerm(name, value.getString(1));
                FuzzyRuleOr orOp = new FuzzyRuleOr(left, right);
                System.out.println(orOp);
                andThings.add(orOp);            
            } else {                
                System.err.println("Nah dog!");
            }
            
        }
        // Construct the THEN
        JSONObject then = rule.getJSONObject("then");
        String thenName = then.getString("name");
        JSONArray value = then.getJSONArray("value");
        if (value.length() == 1) {
            premise = new FuzzyRuleTerm(thenName, value.getString(0));
        } else {
            System.err.println("Nah dog!");
            premise = null;
        } 
        
        result = null;
    }
    
    /**
     * Evaluates the rule and updates the corresponding answer fuzzy set.
     */
    public void evaluate() {
        LinguisticVariable variable = result.getVariable();
        FuzzySet term = variable.getTerm(result.getValue());
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
    
    /**
     * Returns a formated string for saving to a .rules file.
     * 
     * @return .rules format string
     */
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
