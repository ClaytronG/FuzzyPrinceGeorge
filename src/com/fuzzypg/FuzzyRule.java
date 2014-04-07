package com.fuzzypg;

import java.util.ArrayList;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

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
    
    private boolean answer = false;
    
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
            String ifName = ifThing.getString("name");
            JSONArray value = ifThing.getJSONArray("value");
            if (value.length() == 1) {
                FuzzyRuleTerm term = new FuzzyRuleTerm(ifName, value.getString(0));
                andThings.add(term);
            } else if (value.length() == 2) {
                FuzzyRuleTerm left = new FuzzyRuleTerm(ifName, value.getString(0));
                FuzzyRuleTerm right = new FuzzyRuleTerm(ifName, value.getString(1));
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
        this.name = thenName;
    }
    
    /**
     * 
     */
    public void evaluate() {
        LinguisticVariable variable = result.getVariable();
        FuzzySet term = variable.getTerm(result.getValue());
        double value = premise.getResult();
        term.setFuzzyLimit(value);
    }
    
    public String getName() {
        return name;
    }
    
    public boolean isAnswer() {
        return answer;
    }
    
    public void setAnswer(boolean answer) {
        this.answer = answer;
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
    
    /*
    
    {
		"answer" : "true",
		"if" : [
			{ "name" : "Price", "value" : [ "High", "Very High" ] },
			{ "name" : "Safety", "value" : [ "Very High" ] },
			{ "name" : "People", "value" : [ "Very High" ] },
			{ "name" : "Style", "value" : [ "Very Low" ] },
			{ "name" : "Drugs", "value" : [ "Very Low" ] },
			{ "name" : "Proximity", "value" : [ "Very Low", "Low" ] }			
		],
		"then" : { 
			"name" : "Area", 
			"value" : [ "College Heights" ] 
		}
	}    
    */
    public JSONObject toJsonObject() {
        String answerKey = isAnswer() ? "true" : "false";
        
        // Construct the premise JSON object/array
        String ifString = null;
        JSONArray ifValue = null;
        if (premise instanceof FuzzyRuleOr) {
            JSONObject obj = premise.toJsonObject();
            ifString = new JSONStringer()
                    .array()
                        .object()
                            .value(obj)
                        .endObject()
                    .endArray()
                    .toString();
            ifValue = new JSONArray(ifString);
        } else if (premise instanceof FuzzyRuleAnd) {
            ifValue = ((FuzzyRuleAnd) premise).toJsonArray();
        } else if (premise instanceof FuzzyRuleTerm) {
            JSONObject arr = premise.toJsonObject();
            ifString = new JSONStringer()
                    .array()
                        .value(arr)
                    .endArray()
                    .toString();
            ifValue = new JSONArray(ifString);
        }
        
        JSONObject thenValue = result.toJsonObject();
        
        String object = new JSONStringer()
                .object()
                    .key("answer")
                    .value(answerKey)
                    .key("if")
                    .value(ifValue)
                    .key("then")
                    .value(thenValue)
                .endObject()
                .toString();
        
        return new JSONObject(object);
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31)
                .append(name)
                .append(premise)
                .append(result)
                .toHashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FuzzyRule)) {
            return false;
        }
        
        FuzzyRule rhs = (FuzzyRule) obj;
        return new EqualsBuilder()
                .append(name, rhs.name)
                .append(premise, rhs.premise)
                .append(result, rhs.result)
                .isEquals();
    }
    
}
