package com.fuzzypg;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Base class for FuzzyRules. This is needed to combine rules using AND and OR.
 * 
 * @author Clayton
 */
public abstract class FuzzyRuleObject {
    
    /**
     * Evaluate the membership value of this rule.
     * 
     * @return membership value
     */
    public abstract double getResult();
    
    public abstract JSONObject toJsonObject();
    public abstract JSONArray toJsonArray();
}
