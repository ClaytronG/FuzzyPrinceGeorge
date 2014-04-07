package com.fuzzypg;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

/**
 * The union of two fuzzy sets.
 * 
 *  X is x OR Y is y
 * 
 * where X is a linguistic variable and x is a fuzzy set in X 
 *   and Y is a linguistic variable and y is a fuzzy set in Y.
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
        
        return operation.toString();
    }

    /*
        { "name" : "Price", "value" : [ "High", "Very High" ] }
    */
    @Override
    public JSONObject toJsonObject() {
        
        FuzzyRuleTerm leftTerm = (FuzzyRuleTerm) left;
        FuzzyRuleTerm rightTerm = (FuzzyRuleTerm) right;
        
        String object = new JSONStringer()
                .object()
                    .key("name")
                    .value(leftTerm.getVariable().getName())
                    .key("value")
                    .array()
                        .value(leftTerm.getValue())
                        .value(rightTerm.getValue())
                    .endArray()
                .endObject()
                .toString();
        
        return new JSONObject(object);
    }

    @Override
    public JSONArray toJsonArray() {        
        return null;
    }
    
    public String getName() {
        if (left instanceof FuzzyRuleTerm) {
            return ((FuzzyRuleTerm) left).getVariable().getName();
        } else if (right instanceof FuzzyRuleTerm) {
            return ((FuzzyRuleTerm) right).getVariable().getName();            
        } else {
            return null;
        }
    }
}
