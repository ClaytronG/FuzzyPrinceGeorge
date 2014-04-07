package com.fuzzypg;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

/**
 * A FuzzyRuleTerm is the portion of the rule that looks like
 *  variable IS value
 * 
 * ex: Cost IS high
 * 
 * @author Clayton
 */
public class FuzzyRuleTerm extends FuzzyRuleObject {
    
    private final LinguisticVariable variable;    
    private final String value;
    private final boolean complement;
    
    public FuzzyRuleTerm(LinguisticVariable variable, String value, boolean complement) {
        this.variable = variable;
        this.value = value;
        this.complement = complement;
    }
    
    public FuzzyRuleTerm(String name, String value) {
        variable = InferenceEngine.getVariable(name);
        this.value = value;
        complement = false;
    }        
    
    @Override
    public double getResult() {
        double result;
        result = variable.getMembershipValueOf(value);
        if (complement) result = 1 - result;        
        return result;
    }
    
    public LinguisticVariable getVariable() {
        return variable;
    }
    
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        StringBuilder term = new StringBuilder();
        
        term.append(variable.getName());
        term.append(" IS ");
        term.append(value);
        
        return term.toString();
    }
    
    /*
        "then" : { 
                "name" : "Area", 
                "value" : [ "College Heights" ] 
        }
    */
    @Override
    public JSONObject toJsonObject() {
        String object = new JSONStringer()
                .object()
                    .key("name")
                    .value(variable.getName())
                    .key("value")
                    .array()
                        .value(value)
                    .endArray()
                .endObject()
                .toString();
        
        return new JSONObject(object);
    }

    @Override
    public JSONArray toJsonArray() {
        
        return null;
    }
    
}
