package com.fuzzypg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * The Inference Engine evaluates rules and defuzzifies the answer using the 
 * Center of Gravity (CoG) method.
 * 
 * @author Clayton
 */
public class InferenceEngine {
    
    /**
     * Initial user input rules.
     */
    private final HashMap<Integer, FuzzyRule> firstRules;
    
    /**
     * Middle rules that lead to a final answer.
     */
    private final HashMap<Integer, FuzzyRule> secondRules;
    
    /**
     * Rules parsed from JSON file.
     */
    private static final HashMap<Integer, FuzzyRule> rules = new HashMap<>();
    
    /**
     * Linguistic Variables parsed from JSON file.
     */
    private static final HashMap<String, LinguisticVariable> variables = new HashMap<>();
    
    /**
     * Fuzzy sets parsed from JSON file.
     */
    private static final HashMap<String, FuzzySet> sets = new HashMap<>();
    
    public InferenceEngine(String setsFile ,String variablesFile, String rulesFile) {
        firstRules = new HashMap<>();
        secondRules = new HashMap<>();

        // Clear the maps and start fresh for the new engine.
        rules.clear();
        variables.clear();
        sets.clear();
        
        parseJsonSets(setsFile);
        parseJsonVariables(variablesFile);
        parseJsonRules(rulesFile);
    }
    
    private void parseJsonSets(String file) {
        parseJsonSets(new JSONArray(jsonFileToString(file)));        
    }
    
    private void parseJsonVariables(String file) {
        parseJsonVariables(new JSONArray(jsonFileToString(file)));        
    }
    
    private void parseJsonRules(String file) {
        parseJsonRules(new JSONArray(jsonFileToString(file)));
    }
    
    /**
     * Load the file and store its contents in a string.
     * 
     * @param file file to load
     * @return     String of file contents
     */
    private String jsonFileToString(String file) {
        String result = null;
        try {
            FileReader input = new FileReader(file);
            BufferedReader reader = new BufferedReader(input);
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }
            result = builder.toString();
        } catch (FileNotFoundException e) {
            
        } catch (IOException e) {
            
        }
        return result;
    }
    
    /**
     * Parse the FuzzySets from a JSONArray and place them in a map.
     * 
     * @param array JSONArray of sets
     */
    private void parseJsonSets(JSONArray array) {
        for (int i = 0; i < array.length(); ++i) {
            FuzzySet set = new FuzzySet(array.getJSONObject(i));
            sets.put(set.getName(), set);
        }
    }
    
    /**
     * Parse the Linguistic Variables from a JSONArray and place them in a map.
     * 
     * @param array JSONArray of variables
     */
    private void parseJsonVariables(JSONArray array) {
        for (int i = 0; i < array.length(); ++i) {
            LinguisticVariable variable = new LinguisticVariable(array.getJSONObject(i));
            variables.put(variable.getName(), variable);
        }
    }
    
    /**
     * Parse the Rules from a JSONArray and place them in a map.
     * 
     * @param array JSONArray of rules
     */
    private void parseJsonRules(JSONArray array) {
        for (int i = 0; i < array.length(); ++i) {
            FuzzyRule rule = new FuzzyRule(array.getJSONObject(i));     
            // If this rule leads to an answer, place it in the set of rules 
            // that are evaluated second.
            if (array.getJSONObject(i).getString("answer").equals("true")) {
                secondRules.put(rule.hashCode(), rule);
                rule.setAnswer(true);
            // Otherwise it's a rule based on user's input.
            } else {
                firstRules.put(rule.hashCode(), rule);
            }
            
            rules.put(rule.hashCode(), rule);
        }
    }
    
    /**
     * Gets a fuzzy set from the inference engine.
     * 
     * @param name name of the fuzzy set
     * @return     fuzzy set
     */
    public static FuzzySet getSet(String name) {
        return sets.get(name);
    }
    
    /**
     * Gets a linguistic variable from the inference engine.
     * 
     * @param name name of the linguistic variable
     * @return     linguistic variable
     */
    public static LinguisticVariable getVariable(String name) {
        return variables.get(name);
    }
    
    /**
     * Gets a rule from the inference engine.
     * 
     * @param name  name of the rule
     * @param value result of the rule
     * @return      rule
     */
    public static FuzzyRule getRule(String name, String value) {
        return rules.get(FuzzyRule.getHashCode(name, value));
    }
    
    /**
     * Removes a rule from the inference engine's mapping.
     * 
     * @param rule rule to remove
     */
    public static void removeRule(FuzzyRule rule) {
        rules.remove(rule.hashCode());
    }
    
    /**
     * Adds a rule to the inference engine's mapping.
     * 
     * @param rule rule to add
     */
    public static void addRule(FuzzyRule rule) {
        rules.put(rule.hashCode(), rule);
    }
    
    /**
     * Save rules to JSON file, "Rules.json".
     */
    public void saveRules() {
        ArrayList<JSONObject> list = new ArrayList<>();
        for (FuzzyRule rule : rules.values()) {
            list.add(rule.toJsonObject());
        }
        JSONArray arr = new JSONArray(list);
        File file = new File("Rules.json");
        try (PrintWriter out = new PrintWriter(file)) {
            out.print(arr);
            out.close();
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
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
            LinguisticVariable variable = getVariable(rule.getName());
            // Defuzzify result
            double thing = variable.defuzzify();
            // Set the CoG as the input for this linguistic variable
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
                alternates.addAll(answerVariable.getSets());
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
}
