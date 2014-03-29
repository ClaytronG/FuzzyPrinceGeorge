
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Clayton
 */
public class Term {
    
    private final String name;
    private final ArrayList<Pair> values;

    private int minValue = Integer.MAX_VALUE;
    private int maxValue = Integer.MIN_VALUE;

    public Term(String name) {
        this.name = name;
        values = new ArrayList<>();
    }

    public void setValues(List<Pair> values) {
        this.values.addAll(values);
    }

    public void addValue(Pair... pairs) {
        for (Pair pair : pairs) {
            values.add(pair);
            if (pair.getFirst() < minValue) minValue = pair.getFirst();
            if (pair.getFirst() > maxValue) maxValue = pair.getFirst();
        }
    }

    public boolean contains(double input) {
        return (input >= minValue) && (input <= maxValue);
    }

    public double getValue(double input) {
        for (int i = 0; i < values.size() - 1; ++i) {
            Pair point1 = values.get(i),
                 point2 = values.get(i+1);
            if ((input >= point1.getFirst()) && (input <= point2.getFirst())) {
                return function(point1, point2, input);
            }
        }
        return 0;
    }

    public double function(Pair point1, Pair point2, double x) {
        double x1 = point1.getFirst(),
               y1 = point1.getSecond(),
               x2 = point2.getFirst(),
               y2 = point2.getSecond();
        double m = (y2 - y1) / (x2 - x1);
        double b = y1 - m * x1;
        return m * x + b;
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
