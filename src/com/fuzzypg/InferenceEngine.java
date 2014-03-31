package com.fuzzypg;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


public class InferenceEngine {
    
    private final ArrayList<FuzzyRule> rules;
    private final HashMap<String, LinguisticVariable> variables;
    
    public InferenceEngine() {
        rules = new ArrayList<>();
        variables = new HashMap<>();
    }
    
    public InferenceEngine(String file) {
        rules = new ArrayList<>();
        variables = new HashMap<>();
        parseRules(file);
    }
    
    private void parseRules(String file) {
        try {
            FileReader input = new FileReader(file);
            BufferedReader buffer = new BufferedReader(input);
            String line;
            
            while ((line = buffer.readLine()) != null) {
                String[] things = line.split(",");
                //System.out.println("Reading: " + line);
                FuzzyRuleTerm result = new FuzzyRuleTerm(HousingSets.area, HousingSets.getTerm(things[0]).getName(), false);
                // Cost
                FuzzyRuleObject cost = createRule(HousingSets.price, Double.parseDouble(things[1]));
                // Safety
                FuzzyRuleObject safety = createRule(HousingSets.safety, Double.parseDouble(things[2]));
                // People
                FuzzyRuleObject people = createRule(HousingSets.people, Double.parseDouble(things[3]));
                // Hick
                FuzzyRuleObject hick = createRule(HousingSets.style, Double.parseDouble(things[4]));
                // Drugs
                FuzzyRuleObject drugs = createRule(HousingSets.drugs, Double.parseDouble(things[5]));
                // Proximity
                FuzzyRuleObject proximity = createRule(HousingSets.proximity, Double.parseDouble(things[6]));
                
                FuzzyRuleAnd first = new FuzzyRuleAnd(cost, safety);
                FuzzyRuleAnd second = new FuzzyRuleAnd(first, people);
                FuzzyRuleAnd third = new FuzzyRuleAnd(second, hick);
                FuzzyRuleAnd fourth = new FuzzyRuleAnd(third, drugs);
                FuzzyRuleAnd fifth = new FuzzyRuleAnd(fourth, proximity);
                
                FuzzyRule rule = new FuzzyRule(fifth, result);
                //System.out.println(rule);
                rules.add(rule);
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    private FuzzyRuleObject createRule(LinguisticVariable variable, double value) {
        int thing =(int) (value * 10); // Because Swicth dn'like d'ble
        switch (thing) {
            case 10:    // 1
                return new FuzzyRuleTerm(variable, HousingSets.strongDisagree.getName(), false);
            case 15:    // 1.5
                FuzzyRuleTerm left = new FuzzyRuleTerm(variable, HousingSets.strongDisagree.getName(), false);
                FuzzyRuleTerm right = new FuzzyRuleTerm(variable, HousingSets.disagree.getName(), false);
                return new FuzzyRuleOr(left, right);
            case 20:    // 2
                return new FuzzyRuleTerm(variable, HousingSets.disagree.getName(), false);                
            case 25:    // 2.5
                left = new FuzzyRuleTerm(variable, HousingSets.disagree.getName(), false);
                right = new FuzzyRuleTerm(variable, HousingSets.neutral.getName(), false);
                return new FuzzyRuleOr(left, right);
            case 30:    // 3
                return new FuzzyRuleTerm(variable, HousingSets.neutral.getName(), false);
            case 35:    // 3.5
                left = new FuzzyRuleTerm(variable, HousingSets.neutral.getName(), false);
                right = new FuzzyRuleTerm(variable, HousingSets.agree.getName(), false);
                return new FuzzyRuleOr(left, right);
            case 40:    // 4
                return new FuzzyRuleTerm(variable, HousingSets.agree.getName(), false);
            case 45:    // 4.5
                left = new FuzzyRuleTerm(variable, HousingSets.agree.getName(), false);
                right = new FuzzyRuleTerm(variable, HousingSets.strongAgree.getName(), false);
                return new FuzzyRuleOr(left, right);
            case 50:    // 5
                return new FuzzyRuleTerm(variable, HousingSets.strongAgree.getName(), false);
        }
        
        return null;
    }
        
    
    public void setRules(Collection<FuzzyRule> rules) {
        this.rules.clear();
        this.rules.addAll(rules);
    }
    
    public void addRule(FuzzyRule rule) {
        rules.add(rule);
    }
    
    public void setVariables(Collection<LinguisticVariable> variables) {
        this.variables.clear();
        for (LinguisticVariable variable : variables) {
            this.variables.put(variable.getName(), variable);
        }
    }
    
    public void addVariables(LinguisticVariable variable) {
        variables.put(variable.getName(), variable);
    }
    
    public void answer() {
        System.out.println("InferenceEngine.answer()");
        // Evaluate the rules
        for (FuzzyRule rule : rules) {
            System.out.println(rule);
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
            System.out.println("Suggested areas to live: ");
            for (LinguisticTerm term : answerVariable.getTermFromInput(answerValue)) {
                System.out.println("\t" + term.getName());
            }
        } else {
            System.out.println("You shouldn't live in Prince George");
        }
    }
}
