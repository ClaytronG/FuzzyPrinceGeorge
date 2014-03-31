package com.fuzzypg;


import com.fuzzypg.ui.UI;
import javax.swing.JPanel;

/**
 *
 * @author Clayton
 */
public class Main extends JPanel {
    
    public static InferenceEngine engine;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {        
        HousingSets.init();
        
        engine = new InferenceEngine("Area.rules");
        engine.setVariables(HousingSets.getVariables());
               
        UI myUI = new UI();
        myUI.startUI();
    }    
}
