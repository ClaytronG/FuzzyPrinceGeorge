/**
 *
 * @author Clayton
 */
public class DefaultFuzzy {

    // INPUT
    double input;
    
    double strongDisagree;
    double disagree;
    double neutral;
    double agree;
    double strongAgree;
    
    public DefaultFuzzy() {
        
    }
    
    public void calc() {
        
    }
    
    private void fuzzify() {
        strongDisagree = fuzzifyStrongDisagree(input);
        disagree = fuzzifyDisagree(input);
        neutral = fuzzifyNeutral(input);
        agree = fuzzifyAgree(input);
        strongAgree = fuzzifyStrongAgree(input);
    }
    
    public double fuzzifyStrongDisagree(double x) {
        if (x <= 0) return 1; // Left of member
        else if (x > 1) return 0; // Right of member
        else return 1; // Inside member
    }
    
    public double fuzzifyDisagree(double x) {
        if (x <= 0) return 0;
        else if (x > 2) return 0;
        else return 1;
    }
    
    public double fuzzifyNeutral(double x) {
        if (x <= 1) return 0;
        else if (x > 3) return 0;
        else return 1;
    }
    
    public double fuzzifyAgree(double x) {
        if (x <= 2) return 0;
        else if (x > 4) return 0;
        else return 1;
    }
    
    public double fuzzifyStrongAgree(double x) {
        if (x <= 3) return 0;
        else if (x > 4) return 1;
        else return 1;
    }
}
