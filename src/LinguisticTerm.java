import java.util.ArrayList;

/**
 * 
 * 
 * @author Clayton
 */
public class LinguisticTerm {
    
    private final String name;
    
    /**
     * List containing all Pairs (x and y points) that make up the function of 
     * this term.
     */
    private final ArrayList<Pair> values;

    /**
     * Left most (minimum) x-value that this term covers.
     */
    private int minValue = Integer.MAX_VALUE;
    
    /**
     * Right most (maximum) x-value that this term covers.
     */
    private int maxValue = Integer.MIN_VALUE;
    
    /**
     * 
     */
    private double fuzzyLimit = Double.MAX_VALUE;

    /**
     * Creates a new term.
     * 
     * @param name name of the term
     */
    public LinguisticTerm(String name) {
        this.name = name;
        values = new ArrayList<>();
    }

    /**
     * Adds a list of Pairs (x and y points) to this term. Updates the min and 
     * max values of this term if needed.
     * 
     * @param pairs 
     */
    public void addValue(Pair... pairs) {
        for (Pair pair : pairs) {
            values.add(pair);
            if (pair.getFirst() < minValue) minValue = pair.getFirst();
            if (pair.getFirst() > maxValue) maxValue = pair.getFirst();
        }
    }

    /**
     * Returns true if this term covers the value given by input.
     * 
     * @param input 
     * @return      
     */
    public boolean contains(double input) {
        return (input >= minValue) && (input <= maxValue);
    }

    /**
     * Returns the truth value for this term at the given input.
     * 
     * @param input 
     * 
     * @return      the truth value at input 
     */
    public double getValue(double input) {
        for (int i = 0; i < values.size() - 1; ++i) {
            Pair point1 = values.get(i),
                 point2 = values.get(i+1);
            if (input < minValue) {
                return function(point1, point2, minValue);
            }
            else if (input > maxValue) {
                return function(point1, point2, maxValue);
            }
            else if ((input >= point1.getFirst()) && (input <= point2.getFirst())) {
                return function(point1, point2, input);
            }
        }
        return 0;
    }

    /**
     * Constructs a straight line (y = mx + b) from point1 to point2 and 
     * returns the value of x on that line.
     * 
     * @param point1 first point of the line
     * @param point2 second point of the line
     * @param x      
     * 
     * @return       value at x of the line from point1 to point2
     */
    private double function(Pair point1, Pair point2, double x) {
        double x1 = point1.getFirst(),
               y1 = point1.getSecond(),
               x2 = point2.getFirst(),
               y2 = point2.getSecond();
        double m = (y2 - y1) / (x2 - x1);
        double b = y1 - m * x1;
        double answer = m*x + b;
        return Math.min(answer, fuzzyLimit);
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
    
    public void setFuzzyLimit(double limit) {
        fuzzyLimit = limit;
    }
    
}
