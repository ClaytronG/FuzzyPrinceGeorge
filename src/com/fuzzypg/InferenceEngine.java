package com.fuzzypg;

import com.fuzzypg.ui.UI;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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
    
    private final HashMap<String, FuzzyRule> rules;
    private final HashMap<String, LinguisticVariable> variables;
    
    /**
     * Create an empty inference engine. 
     */
    public InferenceEngine() {
        rules = new HashMap<>();
        variables = new HashMap<>();
    }
    
    /**
     * Create an inference engine with rules defined by a .rules file.
     * 
     * @param file name of a .rules file 
     */
    public InferenceEngine(String file) {
        rules = new HashMap<>();
        variables = new HashMap<>();
        //parseRules(file);
        parseJson(file);
    }
    
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
    
    private void parseJson(JSONArray array) {
        for (int i = 0; i < array.length(); ++i) {
            FuzzyRule rule = new FuzzyRule(array.getJSONObject(i));
            rules.put(rule.getName(), rule);
        }
    }    
    
    /**
     * Parses a .rules file into FuzzyRules and adds them to the inference
     * engine.
     * 
     * @param file name of a .rules file
     */
    private void parseRules(String file) {
        try {
            FileReader input = new FileReader(file);
            BufferedReader buffer = new BufferedReader(input);
            String line;
            
            while ((line = buffer.readLine()) != null) {
                FuzzyRule rule = createRule(line);
                rules.put(rule.getName(), rule);
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    /**
     * Creates a FuzzyRule from a line from a .rules file.
     * 
     * @param line text string formatted from a .rules file
     * @return     fuzzy rule
     */
    private FuzzyRule createRule(String line) {
        String[] things = line.split(",");
        double[] values = new double[6];
        String name;
        FuzzyRuleTerm result = 
                new FuzzyRuleTerm(HousingSets.area, 
                                  HousingSets.getTerm(things[0]).getName(), 
                                  false);
        name = things[0];
        // Cost
        FuzzyRuleObject cost = 
                createRule(HousingSets.price, Double.parseDouble(things[1]));
        values[0] = Double.parseDouble(things[1]);
        // Safety
        FuzzyRuleObject safety = 
                createRule(HousingSets.safety, Double.parseDouble(things[2]));
        values[1] = Double.parseDouble(things[2]);
        // People
        FuzzyRuleObject people = 
                createRule(HousingSets.people, Double.parseDouble(things[3]));
        values[2] = Double.parseDouble(things[3]);
        // Hick
        FuzzyRuleObject hick = 
                createRule(HousingSets.style, Double.parseDouble(things[4]));
        values[3] = Double.parseDouble(things[4]);
        // Drugs
        FuzzyRuleObject drugs = 
                createRule(HousingSets.drugs, Double.parseDouble(things[5]));
        values[4] = Double.parseDouble(things[5]);
        // Proximity
        FuzzyRuleObject proximity = 
                createRule(HousingSets.proximity, Double.parseDouble(things[6]));
        values[5] = Double.parseDouble(things[6]);

        FuzzyRuleAnd first = new FuzzyRuleAnd(cost, safety);
        FuzzyRuleAnd second = new FuzzyRuleAnd(first, people);
        FuzzyRuleAnd third = new FuzzyRuleAnd(second, hick);
        FuzzyRuleAnd fourth = new FuzzyRuleAnd(third, drugs);
        FuzzyRuleAnd fifth = new FuzzyRuleAnd(fourth, proximity);

        return new FuzzyRule(fifth, result, name, values);
    }
    
    /**
     * Create an intermediate rule object like a term or an operation.
     * 
     * @param variable fuzzy variable that is being evaluated
     * @param value    membership to check
     * @return         rule term or operation
     */
    private FuzzyRuleObject createRule(LinguisticVariable variable, double value) {
        int thing =(int) (value*10); // Because Swicth dn'like d'ble
        switch (thing) {
            case 10:    // 1
                return new FuzzyRuleTerm(variable, 
                                         HousingSets.strongDisagree.getName(), 
                                         false);
            case 15:    // 1.5
                FuzzyRuleTerm left = 
                        new FuzzyRuleTerm(variable, 
                                          HousingSets.strongDisagree.getName(), 
                                          false);
                FuzzyRuleTerm right = 
                        new FuzzyRuleTerm(variable, 
                                          HousingSets.disagree.getName(), 
                                          false);
                return new FuzzyRuleOr(left, right);
            case 20:    // 2
                return new FuzzyRuleTerm(variable, 
                                         HousingSets.disagree.getName(), 
                                         false);                
            case 25:    // 2.5
                left = new FuzzyRuleTerm(variable, 
                                         HousingSets.disagree.getName(), 
                                         false);
                right = new FuzzyRuleTerm(variable, 
                                          HousingSets.neutral.getName(), 
                                          false);
                return new FuzzyRuleOr(left, right);
            case 30:    // 3
                return new FuzzyRuleTerm(variable, 
                                         HousingSets.neutral.getName(), 
                                         false);
            case 35:    // 3.5
                left = new FuzzyRuleTerm(variable, 
                                         HousingSets.neutral.getName(), 
                                         false);
                right = new FuzzyRuleTerm(variable, 
                                          HousingSets.agree.getName(), 
                                          false);
                return new FuzzyRuleOr(left, right);
            case 40:    // 4
                return new FuzzyRuleTerm(variable, 
                                         HousingSets.agree.getName(), 
                                         false);
            case 45:    // 4.5
                left = new FuzzyRuleTerm(variable, 
                                         HousingSets.agree.getName(), 
                                         false);
                right = new FuzzyRuleTerm(variable, 
                                          HousingSets.strongAgree.getName(), 
                                          false);
                return new FuzzyRuleOr(left, right);
            case 50:    // 5
                return new FuzzyRuleTerm(variable, 
                                         HousingSets.strongAgree.getName(), 
                                         false);
            default:
                return new FuzzyRuleTerm(variable, 
                                         HousingSets.neutral.getName(), 
                                         false);
        }
    }
    
    /**
     * Update the values of a rule.
     * 
     * @param name   rule to update
     * @param values values to update
     */
    public void updateRules(String name, int[] values) {
        FuzzyRule originalRule = rules.get(name);
        StringBuilder ruleString = new StringBuilder();
        ruleString.append(name);
        ruleString.append(",");
        ruleString.append(Double.toString(updateValue(originalRule, values[0], 0)));
        ruleString.append(",");
        ruleString.append(Double.toString(updateValue(originalRule, values[1], 1)));
        ruleString.append(",");
        ruleString.append(Double.toString(updateValue(originalRule, values[2], 2)));
        ruleString.append(",");
        ruleString.append(Double.toString(updateValue(originalRule, values[3], 3)));
        ruleString.append(",");
        ruleString.append(Double.toString(updateValue(originalRule, values[4], 4)));
        ruleString.append(",");
        ruleString.append(Double.toString(updateValue(originalRule, values[5], 5)));
        FuzzyRule rule = createRule(ruleString.toString());
        rules.remove(name);
        rules.put(name, rule);
        saveRules();
    }
    
    /**
     * Update the rules value for the (i'th) fuzzy set.
     * 
     * @param rule   rule to change
     * @param change raise or lower value
     * @param i      what fuzzy set to update
     * @return       the updated value
     */
    private double updateValue(FuzzyRule rule, int change, int i) {
        double value = rule.getValues()[i];
        // If the rule is to strong, then lower it
        if (change == UI.Feedback.LESS) {
            value -= 0.5;
            if (value < 1) {
                value = 1;
            }
        // If the rule is to weak, the raise it
        } else if (change == UI.Feedback.MORE) {
            value += 0.5;
            if (value > 5) {
                value = 5;
            }
        }
        return value;
    }
       
    /**
     * Save the list of rules to Area.rules.
     */
    private void saveRules() {
        BufferedWriter writer = null;
        try {
            File file = new File("Area.rules");
            StringBuilder fileString = new StringBuilder();
            for (FuzzyRule rule : rules.values()) {
                fileString.append(rule.getRuleString());
                fileString.append("\n");
            }
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(fileString.toString());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }
    
    /**
     * Clears the list of rules associated with this inference engine and adds
     * new ones.
     * 
     * @param rules list of rules to add
     */
    public void setRules(Collection<FuzzyRule> rules) {
        this.rules.clear();
        for (FuzzyRule rule : rules) {
            this.rules.put(rule.getName(), rule);
        }
    }
    
    /**
     * Add a single rule to this inference engine without removing others.
     * 
     * @param rule rule to add
     */
    public void addRule(FuzzyRule rule) {
        rules.put(rule.getName(), rule);
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
        System.out.println("InferenceEngine.answer()");
        // Evaluate the rules
        for (FuzzyRule rule : rules.values()) {
            rule.evaluate();
        }
        
        // Deffuzify the answer
        double answerValue = -1;
        LinguisticVariable answerVariable = null;
        for (LinguisticVariable variable : variables.values()) {
            if (variable.isAnswer()) {
                answerValue = variable.defuzzify();
                answerVariable = variable;
                System.out.println("v:"+answerValue);
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
}
