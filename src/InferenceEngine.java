
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
            System.out.println("Suggested areas to live: ");
            for (LinguisticTerm term : answerVariable.getTermFromInput(answerValue)) {
                System.out.println("\t" + term.getName());
            }
        } else {
            System.out.println("You shouldn't live in Prince George");
        }
    }
}
