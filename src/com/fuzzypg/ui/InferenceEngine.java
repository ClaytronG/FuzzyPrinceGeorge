package com.fuzzypg.ui;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author claire
 */
public class InferenceEngine {
    
    private int[] ClearInput;
    private LinguisticVariable[] FuzzyInput;
    
    public InferenceEngine(LinguisticVariable[] inputs)
    {
        FuzzyInput = inputs;
        ClearInput = new int[inputs.length];
    }
    
    public LinguisticVariable Infer()
    {
        DeFuzzify();
        
        return LinguisticVariable.AGREE; //for now, change when real answer exists
    }
    
    public void Fuzzify()
    {
        
    }
    
    public void DeFuzzify()
    {
       
         
    }
    
}
