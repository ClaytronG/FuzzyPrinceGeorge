package com.fuzzypg;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 
 * 
 * @author Clayton
 */
public abstract class FuzzyRuleObject {
    
    /**
     * Evaluate the membership value of this rule.
     * 
     * @param defuzzy 
     * @return membership value
     */
    public abstract double getResult();
    
    public abstract JSONObject toJsonObject();
    public abstract JSONArray toJsonArray();
}
