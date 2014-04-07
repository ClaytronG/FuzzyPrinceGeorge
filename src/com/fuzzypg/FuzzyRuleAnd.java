package com.fuzzypg;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * The intersection of two fuzzy sets.
 * 
 *  X is x AND y IS y
 * 
 * where X is a linguistic variable and x is a fuzzy set in X 
 *   and Y is a linguistic variable and y is a fuzzy set in Y.
 * 
 * @author Clayton
 */
public class FuzzyRuleAnd extends FuzzyRuleOperation {
    
    public FuzzyRuleAnd(FuzzyRuleObject left, FuzzyRuleObject right) {
        super(left, right);
    }
    
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
    
    /*
        { "name" : "Safety", "value" : [ "Very High" ] },
        { "name" : "Safety", "value" : [ "Very High" ] }
    */
    @Override
    public JSONObject toJsonObject() {
        
        return null;
    }

    /*
        
    */
    @Override
    public JSONArray toJsonArray() {
        JSONArray arr = new JSONArray(getList());
        return arr;
    }
    
    public ArrayList<JSONObject> getList() {
        ArrayList<JSONObject> list = new ArrayList<>();
        list.add(right.toJsonObject());
        // Base case
        if (left instanceof FuzzyRuleTerm) {
            list.add(left.toJsonObject());
            return list;
        } else if (left instanceof FuzzyRuleOr) {
            list.add(left.toJsonObject());
            return list;
        } else if (left instanceof FuzzyRuleAnd) {
            list.addAll(((FuzzyRuleAnd) left).getList());
            return list;
        } else {
            System.out.println("OH SHIT");
            return null;
        }
    }
}
