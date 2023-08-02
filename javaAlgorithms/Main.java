package javaAlgorithms;


import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Create an instance of the Viterbi class
        Viterbi viterbi = new Viterbi();
        
        // Define your states
        List<String> states = new ArrayList<>(Arrays.asList("Rainy", "Sunny"));

        // Define the observed sequence
        List<String> observedSequence = new ArrayList<>(Arrays.asList("walk", "shop", "clean"));

        // Define the start probabilities
        Map<String, Double> startProb = new HashMap<>();
        startProb.put("Rainy", 0.6);
        startProb.put("Sunny", 0.4);

        // Define the transition probabilities
        Map<String, Map<String, Double>> transProb = new HashMap<>();
        Map<String, Double> rainyTrans = new HashMap<>();
        rainyTrans.put("Rainy", 0.7);
        rainyTrans.put("Sunny", 0.3);
        transProb.put("Rainy", rainyTrans);
        Map<String, Double> sunnyTrans = new HashMap<>();
        sunnyTrans.put("Rainy", 0.4);
        sunnyTrans.put("Sunny", 0.6);
        transProb.put("Sunny", sunnyTrans);

        // Define the emission probabilities
        Map<String, Map<String, Double>> emitProb = new HashMap<>();
        Map<String, Double> rainyEmit = new HashMap<>();
        rainyEmit.put("walk", 0.1);
        rainyEmit.put("shop", 0.4);
        rainyEmit.put("clean", 0.5);
        emitProb.put("Rainy", rainyEmit);
        Map<String, Double> sunnyEmit = new HashMap<>();
        sunnyEmit.put("walk", 0.6);
        sunnyEmit.put("shop", 0.3);
        sunnyEmit.put("clean", 0.1);
        emitProb.put("Sunny", sunnyEmit);

        // Execute the Viterbi algorithm
        Pair<Double, List<String>> result = viterbi.viterbi(observedSequence, states, startProb, transProb, emitProb);

        // Print the result
        System.out.println("The highest probability is " + result.getKey());
        System.out.println("The most likely sequence of states is " + result.getValue());
    }
}
