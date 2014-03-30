/**
 *
 * @author Clayton
 */
public class FuzzyRule {
    
    // The IFs
    private final FuzzyRuleOperation premise;   
    // The THEN
    private final FuzzyRuleTerm result;
    
    public FuzzyRule(FuzzyRuleOperation premise, FuzzyRuleTerm result) {
        this.premise = premise;
        this.result = result;
    }
    
    public double getResult() {
        return 0;
    }
    
    /**
     * Runs through the rule and updates the answer fuzzy set.
     */
    public void evaluate() {        
        LinguisticVariable variable = result.getVariable();
        LinguisticTerm term = variable.getTerm(result.getValue());
        term.setFuzzyLimit(premise.getResult());
    }
    
}
