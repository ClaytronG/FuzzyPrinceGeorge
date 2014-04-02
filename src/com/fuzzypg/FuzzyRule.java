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
    
    private String name;
    
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
    
    public FuzzyRule(JSONObject rule) {
        // Construct the IF
        JSONArray ifThings = rule.getJSONArray("if");
        ArrayList<FuzzyRuleObject> andThings = new ArrayList<>();
        for (int i = 0; i < ifThings.length(); ++i) {
            JSONObject ifThing = ifThings.getJSONObject(i);
            String name = ifThing.getString("name");
            JSONArray value = ifThing.getJSONArray("value");
            if (value.length() == 1) {
                FuzzyRuleTerm term = new FuzzyRuleTerm(name, value.getString(0));
                andThings.add(term);
            } else if (value.length() == 2) {
                FuzzyRuleTerm left = new FuzzyRuleTerm(name, value.getString(0));
                FuzzyRuleTerm right = new FuzzyRuleTerm(name, value.getString(1));
                FuzzyRuleOr orOp = new FuzzyRuleOr(left, right);
                andThings.add(orOp);            
            } else {                
                System.err.println("Nah dog!");
            }            
        }
        // And all of those premises together
        if (andThings.size() == 1) {
            premise = andThings.get(0);
        } else {            
            FuzzyRuleAnd and = new FuzzyRuleAnd(andThings.get(0), andThings.get(1));
            for (int i = 2; i < andThings.size(); ++i) {
                and = new FuzzyRuleAnd(and, andThings.get(i));
            }
            premise = and;
        }
        // Construct the THEN
        JSONObject then = rule.getJSONObject("then");
        String thenName = then.getString("name");
        JSONArray value = then.getJSONArray("value");
        if (value.length() == 1) {
            result = new FuzzyRuleTerm(thenName, value.getString(0));
        } else {
            System.err.println("Nah dog!");
            result = null;
        }
    }
      
    /**
     * Evaluates the rule and updates the corresponding answer fuzzy set.
     */
    
    public boolean evaluate() {
        boolean updated=false;
        LinguisticVariable variable = result.getVariable();
        FuzzySet term = variable.getTerm(result.getValue());
        double limit = premise.getResult();
        if(term.getFuzzyLimit() != limit)
            updated=true;
        term.setFuzzyLimit(limit);
        
        return updated;
    }
    
    public String getName() {
        return name;
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
