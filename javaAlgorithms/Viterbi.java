package javaAlgorithms;

import java.util.*;

public class Viterbi {
    public static Pair<Double, List<String>> viterbi(List<String> observedSequence, List<String> states, Map<String, Double> startProb, Map<String, Map<String, Double>> transProb, Map<String, Map<String, Double>> emitProb) {
        Map<String, Double> V = new HashMap<>();
        Map<String, List<String>> path = new HashMap<>();

        for (String y : states) {
            V.put(y, startProb.get(y) * emitProb.get(y).get(observedSequence.get(0)));
            path.put(y, new ArrayList<>(Arrays.asList(y)));
        }

        for (int t = 1; t < observedSequence.size(); t++) {
            Map<String, Double> Vt = new HashMap<>();
            Map<String, List<String>> newPath = new HashMap<>();

            for (String y : states) {
                Pair<Double, String> maxPair = new Pair<>(Double.NEGATIVE_INFINITY, "");

                for (String y0 : states) {
                    double nprob = V.get(y0) * transProb.get(y0).get(y) * emitProb.get(y).get(observedSequence.get(t));
                    if (nprob > maxPair.getKey()) {
                        maxPair = new Pair<>(nprob, y0);
                    }
                }

                Vt.put(y, maxPair.getKey());
                List<String> pathY0 = new ArrayList<>(path.get(maxPair.getValue()));
                pathY0.add(y);
                newPath.put(y, pathY0);
            }

            V = Vt;
            path = newPath;
        }

        Pair<Double, String> maxPair = new Pair<>(Double.NEGATIVE_INFINITY, "");
        for (String y : states) {
            double prob = V.get(y);
            if (prob > maxPair.getKey()) {
                maxPair = new Pair<>(prob, y);
            }
        }

        return new Pair<>(maxPair.getKey(), path.get(maxPair.getValue()));
    }
}
