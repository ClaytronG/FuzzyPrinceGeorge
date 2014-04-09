package com.fuzzypg;

import com.fuzzypg.ui.UI;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

/**
 * Fuzzy rules consist of a premise or multiple premises and a result. Premises
 * consist of Fuzzy Terms and, if necessary, operations like AND and OR.<p>
 * <pre>
 * Example:
 * 
 *      IF temp IS hot AND time IS late THEN fan IS high
 *         ----------------------------      -----------
 *                  Premise                     Result
 * </pre>
 */
public class FuzzyRule {
    
    /**
     * The name of the fuzzy rule corresponds to the name of the result
     * Linguistic Variable.
     */
    private final String name;
    
    /**
     * The IFs.
     */
    private final FuzzyRuleObject premise;
    
    /**
     * The THEN.
     */
    private final FuzzyRuleTerm result;
    
    /**
     * If this Rule leads to an answer, and not used as a value in another rule.
     */
    private boolean answer = false;
    
    /**
     * Creates a Fuzzy Rule by parsing a JSON object.
     * <pre>
     * {@code
     *  {
     *      "answer" : String, // "true" or "false"
     *      "then" : {
     *          "name" : String,
     *          "value" : [ String, ... ]
     *      },
     *      "if" : [
     *          { "name" : String, "value" : [ String, ... ] },
     *          ...
     *      ]
     *  }
     * }
     * </pre>
     * Where answer is a boolean value telling if this rule leads to an answer,
     * or if it is an intermediate rule. The then portion is the result of the 
     * rule with a name of a {@link LinguisticVariable} and an associated 
     * {@link FuzzySet} with that variable. The if portion contains an array of
     * {@link FuzzyRuleOr}s and {@link FuzzyRuleTerm}s that are to be and'd
     * together.
     * 
     * @param object JSON object to be parsed into a Fuzzy Rule
     */
    public FuzzyRule(JSONObject object) {
        // Construct the IF
        JSONArray ifThings = object.getJSONArray("if");
        ArrayList<FuzzyRuleObject> andThings = new ArrayList<>();
        for (int i = 0; i < ifThings.length(); ++i) {
            JSONObject ifThing = ifThings.getJSONObject(i);
            String ifName = ifThing.getString("name");
            JSONArray value = ifThing.getJSONArray("value");
            if (value.length() == 1) {
                FuzzyRuleTerm term = new FuzzyRuleTerm(ifName, value.getString(0));
                andThings.add(term);
            } else if (value.length() == 2) {
                FuzzyRuleTerm left = new FuzzyRuleTerm(ifName, value.getString(0));
                FuzzyRuleTerm right = new FuzzyRuleTerm(ifName, value.getString(1));
                FuzzyRuleOr orOp = new FuzzyRuleOr(left, right);
                andThings.add(orOp);            
            } else {                
                System.err.println("Nah dog!");
            }            
        }
        // And all of those premises together
        // If its length is 1 then it is just a single term and does not need
        // to be and'd together.
        if (andThings.size() == 1) {
            premise = andThings.get(0);
        // Otherwise each entry in the array needs to be and'd.
        } else {            
            FuzzyRuleAnd and = new FuzzyRuleAnd(andThings.get(0), andThings.get(1));
            for (int i = 2; i < andThings.size(); ++i) {
                and = new FuzzyRuleAnd(and, andThings.get(i));
            }
            premise = and;
        }
        // Construct the THEN
        JSONObject then = object.getJSONObject("then");
        String thenName = then.getString("name");
        JSONArray value = then.getJSONArray("value");
        if (value.length() == 1) {
            result = new FuzzyRuleTerm(thenName, value.getString(0));
        } else {
            System.err.println("Nah dog!");
            result = null;
        }
        this.name = thenName;
    }
    
    /**
     * 
     * 
     * @param change
     * @return 
     */
    public FuzzyRule alterRule(HashMap<String, Integer> change) {
        ArrayList<JSONObject> newThings = new ArrayList<>();
        // JAVA 8!
        change.entrySet().stream().forEach((entry) -> {
            newThings.add(newThing(entry.getKey(), entry.getValue()));
        });
        JSONArray ifArray = new JSONArray(newThings);
        
        String ruleString = new JSONStringer()
                .object()
                    .key("answer")
                    .value("true")
                    .key("if")
                    .value(ifArray)
                    .key("then")
                    .object()
                        .key("name")
                        .value("Area")
                        .key("value")
                        .array()
                            .value(result.getValue())
                        .endArray()
                    .endObject()                        
                .endObject()
                .toString();
        
        return new FuzzyRule(new JSONObject(ruleString));
    }
    
    private JSONObject newThing(String thing, int change) {
        if (change == UI.Feedback.MORE) {
            return increaseRule(thing);
        } else if (change == UI.Feedback.LESS) {
            return reduceRule(thing);
        } else {
            return ((FuzzyRuleAnd) premise).getHashMap().get(thing);
        }
    }
    
    private JSONObject increaseRule(String thing) {
        JSONObject obj = ((FuzzyRuleAnd) premise).getHashMap().get(thing);
        JSONArray valueArray = obj.getJSONArray("value");
        if (valueArray.length() > 1) {
            // This is an inbetween (OR) rule, change it to a single term
            String termString = new JSONStringer()
                    .object()
                        .key("name")
                        .value(thing)
                        .key("value")
                        .array()
                            .value(valueArray.getString(1))
                        .endArray()
                    .endObject()
                    .toString();
            return new JSONObject(termString);
        } else if (valueArray.length() == 1) {
            // This is a single term, change it to an OR rule
            String termString = null;
            switch (valueArray.getString(0)) {
                case "Very Low" : // Very Low OR Low
                    termString = new JSONStringer()
                            .object()
                                .key("name")
                                .value(thing)
                                .key("value")
                                .array()
                                    .value("Very Low")
                                    .value("Low")
                                .endArray()
                            .endObject()
                            .toString();
                    break;
                case "Low" : // Low OR Middle
                    termString = new JSONStringer()
                            .object()
                                .key("name")
                                .value(thing)
                                .key("value")
                                .array()
                                    .value("Low")
                                    .value("Middle")
                                .endArray()
                            .endObject()
                            .toString();                    
                    break;
                case "Middle" : // Middle OR High
                    termString = new JSONStringer()
                            .object()
                                .key("name")
                                .value(thing)
                                .key("value")
                                .array()
                                    .value("Middle")
                                    .value("High")
                                .endArray()
                            .endObject()
                            .toString();
                    break;
                case "High" : // High OR Very High
                    termString = new JSONStringer()
                            .object()
                                .key("name")
                                .value(thing)
                                .key("value")
                                .array()
                                    .value("High")
                                    .value("Very High")
                                .endArray()
                            .endObject()
                            .toString();                    
                    break;
                case "Very High" : // Very High
                    termString = new JSONStringer()
                            .object()
                                .key("name")
                                .value(thing)
                                .key("value")
                                .array()
                                    .value("Very High")
                                .endArray()
                            .endObject()
                            .toString();
                    break;
            }
            return new JSONObject(termString);
        }
        return null;
    }
    
    private JSONObject reduceRule(String thing) {
        JSONObject obj = ((FuzzyRuleAnd) premise).getHashMap().get(thing);
        JSONArray valueArray = obj.getJSONArray("value");
        if (valueArray.length() > 1) {
            // This is an inbetween (OR) rule, change it to a single term
            String termString = new JSONStringer()
                    .object()
                        .key("name")
                        .value(thing)
                        .key("value")
                        .array()
                            .value(valueArray.getString(0))
                        .endArray()
                    .endObject()
                    .toString();
            return new JSONObject(termString);
        } else if (valueArray.length() == 1) {
            // This is a single term, change it to an OR rule
            String termString = null;
            switch (valueArray.getString(0)) {
                case "Very Low" : // Very Low
                    termString = new JSONStringer()
                            .object()
                                .key("name")
                                .value(thing)
                                .key("value")
                                .array()
                                    .value("Very Low")
                                .endArray()
                            .endObject()
                            .toString();
                    break;
                case "Low" : // Very Low or Low
                    termString = new JSONStringer()
                            .object()
                                .key("name")
                                .value(thing)
                                .key("value")
                                .array()
                                    .value("Very Low")
                                    .value("Low")
                                .endArray()
                            .endObject()
                            .toString();                    
                    break;
                case "Middle" : // Low OR Middle
                    termString = new JSONStringer()
                            .object()
                                .key("name")
                                .value(thing)
                                .key("value")
                                .array()
                                    .value("Low")
                                    .value("Middle")
                                .endArray()
                            .endObject()
                            .toString();
                    break;
                case "High" : // Middle OR High
                    termString = new JSONStringer()
                            .object()
                                .key("name")
                                .value(thing)
                                .key("value")
                                .array()
                                    .value("Middle")
                                    .value("High")
                                .endArray()
                            .endObject()
                            .toString();                    
                    break;
                case "Very High" : // High OR Very High
                    termString = new JSONStringer()
                            .object()
                                .key("name")
                                .value(thing)
                                .key("value")
                                .array()
                                    .value("High")
                                    .value("Very High")
                                .endArray()
                            .endObject()
                            .toString();
                    break;
            }
            return new JSONObject(termString); 
        }
        return null;
    }
    
    /**
     * 
     */
    public void evaluate() {
        LinguisticVariable variable = result.getVariable();
        FuzzySet term = variable.getTerm(result.getValue());
        double value = premise.getResult();
        term.setFuzzyLimit(value);
    }
    
    public String getName() {
        return name;
    }
    
    public boolean isAnswer() {
        return answer;
    }
    
    public void setAnswer(boolean answer) {
        this.answer = answer;
    }
    
    @Override
    public String toString() {
        StringBuilder rule = new StringBuilder();
        
        rule.append("IF ");
        rule.append(premise);
        rule.append(" THEN ");
        rule.append(result);
        
        return rule.toString();
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
    public JSONObject toJsonObject() {
        String answerKey = isAnswer() ? "true" : "false";
        
        // Construct the premise JSON object/array
        String ifString;
        JSONArray ifValue = null;
        if (premise instanceof FuzzyRuleOr) {
            JSONObject obj = premise.toJsonObject();
            ifString = new JSONStringer()
                    .array()
                        .object()
                            .value(obj)
                        .endObject()
                    .endArray()
                    .toString();
            ifValue = new JSONArray(ifString);
        } else if (premise instanceof FuzzyRuleAnd) {
            ifValue = ((FuzzyRuleAnd) premise).toJsonArray();
        } else if (premise instanceof FuzzyRuleTerm) {
            JSONObject arr = premise.toJsonObject();
            ifString = new JSONStringer()
                    .array()
                        .value(arr)
                    .endArray()
                    .toString();
            ifValue = new JSONArray(ifString);
        }
        
        JSONObject thenValue = result.toJsonObject();
        
        String object = new JSONStringer()
                .object()
                    .key("answer")
                    .value(answerKey)
                    .key("if")
                    .value(ifValue)
                    .key("then")
                    .value(thenValue)
                .endObject()
                .toString();
        
        return new JSONObject(object);
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31)
                .append(name)
                .append(result.getValue())
                .toHashCode();
    }
    
    public static int getHashCode(String name, String value) {
        return new HashCodeBuilder(17, 31)
                .append(name)
                .append(value)
                .toHashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FuzzyRule)) {
            return false;
        }
        
        FuzzyRule rhs = (FuzzyRule) obj;
        return new EqualsBuilder()
                .append(name, rhs.name)
                .append(premise, rhs.premise)
                .append(result, rhs.result)
                .isEquals();
    }
    
}
