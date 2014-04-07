package com.fuzzypg;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.input.KeyCode;
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
    
    @Override
    public JSONArray toJsonArray() {
        JSONArray arr = new JSONArray(getHashMap().values());
        return arr;
    }
    
    public HashMap<String, JSONObject> getHashMap() {
        HashMap<String, JSONObject> map = new HashMap<>();
        
        if (right instanceof FuzzyRuleTerm) {
            FuzzyRuleTerm rightTerm = (FuzzyRuleTerm) right;
            //System.out.println("Right TERM: " + rightTerm.getVariable().getName());
            map.put(rightTerm.getVariable().getName(), right.toJsonObject());
        } else if (right instanceof FuzzyRuleOr) {
            FuzzyRuleOr rightTerm = (FuzzyRuleOr) right;
            //System.out.println("Right OR: " + rightTerm.getName());
            map.put(rightTerm.getName(), right.toJsonObject());
        } else {
            System.out.println("SHIT");
            return null;
        }
        // Base case
        if (left instanceof FuzzyRuleTerm) {
            FuzzyRuleTerm leftTerm = (FuzzyRuleTerm) left;
            //System.out.println("Term: " + leftTerm.getVariable().getName());
            map.put(leftTerm.getVariable().getName(), leftTerm.toJsonObject());
        } else if (left instanceof FuzzyRuleOr) {
            FuzzyRuleOr leftTerm = (FuzzyRuleOr) left;
            //System.out.println("OR: " + leftTerm.getName());
            map.put(leftTerm.getName(), leftTerm.toJsonObject());
        } else if (left instanceof FuzzyRuleAnd) {
            FuzzyRuleAnd leftTerm = (FuzzyRuleAnd) left;
            //System.out.println("AND: " + leftTerm);
            map.putAll(leftTerm.getHashMap());
        }
        //System.out.println(map.size());
        return map;
    }
}
