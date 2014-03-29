
import java.util.ArrayList;

/**
 *
 * @author Clayton
 */
public class FuzzySet {
    private final String name;
    private final ArrayList<Term> things;

    private int minValue = Integer.MAX_VALUE;
    private int maxValue = Integer.MIN_VALUE;

    public FuzzySet(String name) {
        this.name = name;
        things = new ArrayList<>();
    }

    public void addTerms(Term... terms) {
        for (Term term : terms) {
            things.add(term);
            if (term.getMinValue() < minValue) minValue = term.getMinValue();
            if (term.getMaxValue() > maxValue) maxValue = term.getMaxValue();
        }
    }

    public void input(double input) {
        System.out.println("Input " + input);
        for (Term term : things) {
            if (term.contains(input)) {
                System.out.print("\t" + term.getName() + " = ");
                System.out.println("\t" + term.getValue(input));
            }
        }
    }
    
    public String getName() {
        return name;
    }
    
    public int getMinValue() {
        return minValue;
    }
    
    public int getMaxValue() {
        return maxValue;
    }
}
