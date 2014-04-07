package com.fuzzypg;

import com.fuzzypg.ui.UI;

public class Main {
    
    private static InferenceEngine engine;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {        
        initEngine();
        //engine.saveRules();
        UI myUI = new UI();
        myUI.startUI();
    }  
    
    public static InferenceEngine getEngine(boolean create) {
        if (create || engine == null) {
            initEngine();
        }
        return engine;
    }
    
    private static void initEngine() {        
        engine = new InferenceEngine("Sets.json", "Variables.json", "ruletest.json");
    }
    
}
