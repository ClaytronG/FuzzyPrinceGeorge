/**
 *
 * @author Clayton
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        UI myUI= new UI();
        myUI.startUI();
        
        //testFuzzy();
    }
    
    /**
     * Test out fuzzy stuff
     * Using this as a test:
     * http://jfuzzylogic.sourceforge.net/html/manual.html#fcl
     */
    private static void testFuzzy() {
       FuzzySet service = new FuzzySet("service");
       Term poor = new Term("poor");
       poor.addValue(new Pair(0, 1), new Pair(4,0));
       Term good = new Term("good");
       good.addValue(new Pair(1, 0), new Pair(4, 1), new Pair(6, 1), new Pair(9, 0));
       Term excellent = new Term("excellent");
       excellent.addValue(new Pair(6, 0), new Pair(9, 1));
       service.addTerms(poor, good, excellent);
       
       System.out.println(service.getName());
       for (int i = 0; i <= service.getMaxValue(); ++i) {
           service.input(i);
       }
       
       FuzzySet food = new FuzzySet("food");
       Term rancid = new Term("rancid");
       rancid.addValue(new Pair(0,1), new Pair(1,1), new Pair(3,0));
       Term delicious = new Term("delicious");
       delicious.addValue(new Pair(7,0), new Pair(9,1));
       food.addTerms(rancid, delicious);
       
       System.out.println("\n" + food.getName());
       for (int i = 0; i <= food.getMaxValue(); ++i) {
           food.input(i);
       }
    }
    
}
