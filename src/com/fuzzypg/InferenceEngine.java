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
    
    private static final int TOP_N = 3;
    
    public void answer() {
        // Evaluate the rules
        for (FuzzyRule rule : rules) {
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
            ArrayList<LinguisticTerm> terms = new ArrayList<>();
            terms.addAll(answerVariable.getTermFromInput(answerValue));
            
            if (!terms.isEmpty()) {
                // Get the answer using CoG
                System.out.println("You should live around: ");
                for (LinguisticTerm term : terms) {
                    System.out.println("\t" + term.getName());
                }
                // Get a list of alternatives
                ArrayList<LinguisticTerm> alternates = new ArrayList<>();
                alternates.addAll(answerVariable.getTerms());
                Collections.sort(alternates, new Comparator<LinguisticTerm>() {
                    @Override
                    public int compare(LinguisticTerm o1, LinguisticTerm o2) {
                        return Double.compare(o2.getValue(o2.getPointValue()), o1.getValue(o1.getPointValue()));
                    }
                });
                // Since the list is sorted, check the first one. If it is 0
                // then there are no alternatives
                if (alternates.get(0).getValue(alternates.get(0).getPointValue()) != 0) {
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < TOP_N; ++i) {                        
                        LinguisticTerm term = alternates.get(i);
                        if (term.getValue(term.getPointValue()) > 0) {
                            boolean contains = false;
                            for (LinguisticTerm answer : terms) {
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
        }
    }
}
