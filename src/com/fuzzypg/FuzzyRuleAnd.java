package com.fuzzypg;

import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * The intersection of two fuzzy sets (AND).
 * <pre>
 *  X is x AND y IS y
 * 
 *  where X is a linguistic variable and x is a fuzzy set in X 
 *    and Y is a linguistic variable and y is a fuzzy set in Y.
 * </pre>
 * 
 * @author Clayton
 */
public class FuzzyRuleAnd extends FuzzyRuleOperation {
    
    /**
     * Connect two FuzzyRule objects by an AND.
     * 
     * @param left  left side of and
     * @param right right side of and
     */
    public FuzzyRuleAnd(FuzzyRuleObject left, FuzzyRuleObject right) {
        super(left, right);
    }
    
    /**
     * And the left and right sides by taking the minimum value of both.
     * 
     * @return left side and'd with right side
     */
    @Override
    public double getResult() {
        return Math.min(left.getResult(), right.getResult());
    }

    @Override
    public String toString() {
        StringBuilder operation = new StringBuilder();
        
        operation.append(left);
        operation.append(" AND ");
        operation.append(right);
        
        return operation.toString();
    }    
   
    /**
     * Not used. All ANDs are described in JSONArrays.
     * @return null
     */
    @Override
    public JSONObject toJsonObject() {        
        return null;
    }

    /**
     * Creates a JSONArray representation of this AND and its connected terms
     * so it can be saved back to file.
     * 
     * { "name" : "Safety", "value" : [ "Very High" ] },
     * { "name" : "Safety", "value" : [ "Very High" ] }
     * 
     * @return JSONArray representation
     */
    @Override
    public JSONArray toJsonArray() {
        JSONArray arr = new JSONArray(getHashMap().values());
        return arr;
    }
    
    /**
     * Generate a map of all connected FuzzyRuleObjects in JSON form.
     * 
     * @return map of and'd rules
     */
    public HashMap<String, JSONObject> getHashMap() {
        HashMap<String, JSONObject> map = new HashMap<>();
        
        // If the right side is a term, then it can get added directly to the
        // map.
        if (right instanceof FuzzyRuleTerm) {
            FuzzyRuleTerm rightTerm = (FuzzyRuleTerm) right;
            map.put(rightTerm.getVariable().getName(), right.toJsonObject());
        // If the right side is an or term, then it can get added directly to 
        // the map.
        } else if (right instanceof FuzzyRuleOr) {
            FuzzyRuleOr rightTerm = (FuzzyRuleOr) right;
            map.put(rightTerm.getName(), right.toJsonObject());
        // Otherwise it's something we don't want.
        } else {
            return null;
        }
        // If the left side is a term, then this is our base case for recursion
        // and we can add it to the map and return.
        if (left instanceof FuzzyRuleTerm) {
            FuzzyRuleTerm leftTerm = (FuzzyRuleTerm) left;
            map.put(leftTerm.getVariable().getName(), leftTerm.toJsonObject());
        // If the left side is an or term, then this is another base case for
        // recursion and we can add it to the map and return.
        } else if (left instanceof FuzzyRuleOr) {
            FuzzyRuleOr leftTerm = (FuzzyRuleOr) left;
            map.put(leftTerm.getName(), leftTerm.toJsonObject());
        // Otherwise, there is another and and'd together and we need its 
        // children. So add its mapping to this map.
        } else if (left instanceof FuzzyRuleAnd) {
            FuzzyRuleAnd leftTerm = (FuzzyRuleAnd) left;
            map.putAll(leftTerm.getHashMap());
        }
        return map;
    }
}
