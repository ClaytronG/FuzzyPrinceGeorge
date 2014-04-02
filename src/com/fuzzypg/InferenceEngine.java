package com.fuzzypg;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * The Inference Engine evaluates rules and deffuzifies the answer using the 
 * Center of Gravity (CoG) method.
 * 
 * @author Clayton
 */
public class InferenceEngine {
    
    private final HashMap<Integer, FuzzyRule> firstRules;
    private final HashMap<Integer, FuzzyRule> secondRules;
    private final HashMap<Integer, FuzzyRule> rules;
    private final HashMap<String, LinguisticVariable> variables;
    
    /**
     * Create an empty inference engine. 
     */
    public InferenceEngine() {
        firstRules = new HashMap<>();
        secondRules = new HashMap<>();
        rules = new HashMap<>();
        variables = new HashMap<>();
    }
    
    /**
     * Create an inference engine with rules defined by a .rules file.
     * 
     * @param file name of a .rules file 
     */
    public InferenceEngine(String file) {
        firstRules = new HashMap<>();
        secondRules = new HashMap<>();
        rules = new HashMap<>();
        variables = new HashMap<>();
        parseJson(file);
    }
    
    /**
     * 
     * 
     * @param file 
     */
    private void parseJson(String file) {
        try {
            FileReader input = new FileReader(file);
            BufferedReader reader = new BufferedReader(input);
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }
            parseJson(new JSONArray(builder.toString()));
        } catch (FileNotFoundException e) {
            
        } catch (IOException e) {
            
        }
    }
    
    /**
     * 
     * 
     * @param array 
     */
    private void parseJson(JSONArray array) {
        for (int i = 0; i < array.length(); ++i) {
            FuzzyRule rule = new FuzzyRule(array.getJSONObject(i));            
            if (array.getJSONObject(i).getString("answer").equals("true")) {
                secondRules.put(rule.toString().hashCode(), rule);
                rule.setAnswer(true);
            } else {
                firstRules.put(rule.toString().hashCode(), rule);
            }
            
            rules.put(rule.toString().hashCode(), rule);            
        }
    }
    
    /**
     * Clears the list of linguistic variables associated with this inference 
     * engine and adds new ones.
     * 
     * @param variables list of variables to add
     */
    public void setVariables(Collection<LinguisticVariable> variables) {
        this.variables.clear();
        for (LinguisticVariable variable : variables) {
            this.variables.put(variable.getName(), variable);
        }
    }
    
    /**
     * Add a single variable to this inference engine without removing others.
     * 
     * @param variable variable to add
     */
    public void addVariable(LinguisticVariable variable) {
        variables.put(variable.getName(), variable);
    }
    
    /**
     * How many alternative places to show.
     */
    private static final int TOP_N = 3;
    
    /**
     * Evaluates the rules and defuzzifies the answer.
     * 
     * @return deffuzified linguistic variable
     */
    public LinguisticVariable answer() {
        // Evaluate the initial rules
        for (FuzzyRule rule : firstRules.values()) {
            rule.evaluate();
        }
        // Defuzzify the intermediate variables
        for (FuzzyRule rule : firstRules.values()) {
            LinguisticVariable variable = HousingSets.getVariable(rule.getName());
            double thing = variable.defuzzify();
            variable.setInput(thing);
        }
        // Evaluate the final rules
        for (FuzzyRule rule : secondRules.values()) {
            rule.evaluate();
        }
        
        // Deffuzify the answer
        double answerValue = -1;
        LinguisticVariable answerVariable = null;
        for (LinguisticVariable variable : variables.values()) {
            if (variable.isAnswer()) {
                answerValue = variable.defuzzify();
                answerVariable = variable;
            }
        }
        
        // Do something with the answer
        if (answerVariable != null) {
            ArrayList<FuzzySet> terms = new ArrayList<>();
            terms.addAll(answerVariable.getTermFromInput(answerValue));
            
            if (!terms.isEmpty()) {
                // Get the answer using CoG
                System.out.println("You should live around: ");
                for (FuzzySet term : terms) {
                    System.out.println("\t" + term.getName());
                }
                // Get a list of alternatives
                ArrayList<FuzzySet> alternates = new ArrayList<>();
                alternates.addAll(answerVariable.getTerms());
                Collections.sort(alternates, new Comparator<FuzzySet>() {
                    @Override
                    public int compare(FuzzySet o1, FuzzySet o2) {
                        return Double.compare(o2.getValue(o2.getPointValue()), o1.getValue(o1.getPointValue()));
                    }
                });
                // Since the list is sorted, check the first one. If it is 0
                // then there are no alternatives
                if (alternates.get(0).getValue(alternates.get(0).getPointValue()) != 0) {
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < TOP_N; ++i) {                        
                        FuzzySet term = alternates.get(i);
                        if (term.getValue(term.getPointValue()) > 0) {
                            boolean contains = false;
                            for (FuzzySet answer : terms) {
                                if (answer.getName().equals(term.getName())) {
                                    contains = true;
                                }
                            }
                            if (!contains) {
                                builder.append("\t")
                                        .append(i+1)
                                        .append(". ")
                                        .append(term.getName())
                                        .append("\n");
                            }
                        }
                    }
                    if (builder.length() != 0) {
                        System.out.println("Top alternatives:");
                        System.out.println(builder.toString());
                    }
                }
            } else {
                System.out.println("You shouldn't live in Prince George");                
            }
        } else {
            System.out.println("You shouldn't live in Prince George");
            
        }
        
        return answerVariable;
        
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
    public void saveRules() {
        ArrayList<JSONObject> ruleObjects = new ArrayList<>();
        for (FuzzyRule rule : firstRules.values()) {
            rule.toJsonObject();
        }
        
        for (FuzzyRule rule : secondRules.values()) {
            rule.toJsonObject();
        }
    }
}
