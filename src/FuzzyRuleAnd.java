/**
 * left AND right
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

}
