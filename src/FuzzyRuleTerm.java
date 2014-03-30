/**
 *
 * A FuzzyRuleTerm is the portion of the rule that looks like
 *  variable IS value
 * 
 * @author Clayton
 */
public class FuzzyRuleTerm extends FuzzyRuleObject {
    
    private final LinguisticVariable variable;    
    private final String value;
    private final boolean complement;
    
    public FuzzyRuleTerm(LinguisticVariable variable, String value, boolean complement) {
        this.variable = variable;
        this.value = value;
        this.complement = complement;
    }
    
    @Override
    public double getResult() {
        double result = variable.getMembershipValueOf(value);
        if (complement) result = 1 - result;
        return result;
    }
    
    public LinguisticVariable getVariable() {
        return variable;
    }
    
    public String getValue() {
        return value;
    }

}
