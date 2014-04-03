package com.fuzzypg;

import com.fuzzypg.ui.UI;

public class Main {
    
    public static InferenceEngine engine;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {        
        initEngine();
        //engine.saveRules();
           
        UI myUI = new UI();
        myUI.startUI();
    }  
    
    public static void initEngine() {        
        engine = new InferenceEngine("Sets.json", "Variables.json", "Rules.json");
    }
}
