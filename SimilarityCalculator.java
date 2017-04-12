
import datastructure.ActionVertex;
import datastructure.ActionVertexChild;
import datastructure.PlotGraph;
import edu.cmu.lti.lexical_db.ILexicalDatabase;
import edu.cmu.lti.lexical_db.NictWordNet;
import edu.cmu.lti.ws4j.RelatednessCalculator;
import edu.cmu.lti.ws4j.impl.WuPalmer;
import edu.cmu.lti.ws4j.util.WS4JConfiguration;
import edu.stanford.nlp.util.IntPair;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Calculates the similarity of two plot graphs
 *
 * @author V Anugrah
 */
public class SimilarityCalculator {

    public PlotGraph p1;
    public PlotGraph p2;

    private ILexicalDatabase db = new NictWordNet();
    private RelatednessCalculator[] rcs = {new WuPalmer(db)};

    private double actionScore;
    private double childScore;
    private IntPair[] pair;
    private RelatednessCalculator rc;
    
    private double alpha;
    private double beta;
    private double gap;

    public SimilarityCalculator(PlotGraph p1, PlotGraph p2, double alpha, double beta, double gap) {
        this.p1 = p1;
        this.p2 = p2;
        WS4JConfiguration.getInstance().setMFS(true);
        rc = rcs[0];
        this.alpha = alpha;
        this.beta = beta;
        this.gap = gap;
    }

    /**
     * Calculates the similarity of the action vertex sequence of two plot
     * graphs using WordNet similarity calculation and align the sequences with
     * Needleman-Wunsch algorithm
     *
     * @return
     */
    public double actionSimilarity() {
        ArrayList<ActionVertex> action1 = p1.actionVertexList;
        ArrayList<ActionVertex> action2 = p2.actionVertexList;

        int size1 = action1.size();
        int size2 = action2.size();

        pair = new IntPair[size1 > size2 ? size1 : size2];

        String[] sequence1 = new String[size1];
        String[] sequence2 = new String[size2];

        // Store action words into arrays of String
        for (int i = 0; i < size1; i++) {
            sequence1[i] = action1.get(i).word;
//            System.out.print(sequence1[i] + " ");
        }
//        System.out.println();
        for (int i = 0; i < size2; i++) {
            sequence2[i] = action2.get(i).word;
//            System.out.print(sequence2[i] + " ");
        }
//        System.out.println();

//        String[] sequence1 = {"live", "sleep", "play", "disturb"};
//        String[] sequence2 = {"eat", "live", "rest"};
        
        // use WuPalmer scoring system        
        double[][] similarityMatrix = rc.getNormalizedSimilarityMatrix(sequence1, sequence2);

        // print similarity matrix
        for (int i = 0; i < size1; i++) {
            for (int j = 0; j < size2; j++) {
                System.out.printf("%.2f ", similarityMatrix[i][j]);
            }
            System.out.println();
        }

        // Initialize Needleman-Wunsch matrix
        double[][] nwMatrix = new double[size1 + 1][size2 + 1];

        // the map for backtracking
        char[][] nwMap = new char[size1 + 1][size2 + 1];

        // Fill with -1 penalty gap
        for (int i = 0; i < nwMatrix.length; i++) {
            nwMatrix[i][0] = gap * i;
            nwMap[i][0] = 'U';
        }
        for (int j = 0; j < nwMatrix[0].length; j++) {
            nwMatrix[0][j] = gap * j;
            nwMap[0][j] = 'L';
        }
        nwMap[0][0] = 'D';

        pair = new IntPair[4];
        // Start filling Needleman-Wunsch matrix with scoring system
        for (int i = 1; i < nwMatrix.length; i++) { // start from (1,1)
            for (int j = 1; j < nwMatrix[0].length; j++) {
                double a = nwMatrix[i - 1][j - 1] + similarityMatrix[i - 1][j - 1]; // D
                double b = nwMatrix[i - 1][j] + gap; // U
                double c = nwMatrix[i][j - 1] + gap; // L

                if (a >= b) {
                    if (a >= c) {
                        nwMatrix[i][j] = a;
                        nwMap[i][j] = 'D';
                    } else {
                        nwMatrix[i][j] = c;
                        nwMap[i][j] = 'L';
                    }
                } else {
                    if (b >= c) {
                        nwMatrix[i][j] = b;
                        nwMap[i][j] = 'U';
                    } else {
                        nwMatrix[i][j] = c;
                        nwMap[i][j] = 'L';
                    }
                }
            }
        }
        // for printing purposes
        System.out.println("==================================================");
        for (int i = 0; i < nwMatrix.length; i++) {
            for (int j = 0; j < nwMatrix[0].length; j++) {
                System.out.printf("%.2f ", nwMatrix[i][j]);
            }
            System.out.println();
        }

        System.out.println("==================================================");
        for (int i = 0; i < nwMatrix.length; i++) {
            for (int j = 0; j < nwMatrix[0].length; j++) {
                System.out.print(nwMap[i][j] + " ");
            }
            System.out.println();
        }

        String result1 = "";
        String result2 = "";
        
        StringBuilder r1 = new StringBuilder();
        StringBuilder r2 = new StringBuilder();

//         backtracking
        for (int i = nwMap.length - 1, j = nwMap[0].length - 1, k = pair.length - 1;
                i >= 0 && j >= 0 && k >= 0; k--) {
//            System.out.println("(" + i + ", " + j + ") --> " + nwMap[i][j]);
            r1.insert(0, " ");
            r2.insert(0, " ");
            if (nwMap[i][j] == 'D') {
                if (i > 0 && j > 0) {
//                    result1 = sequence1[i - 1] + " " + result1;
//                    result2 = sequence2[j - 1] + " " + result2;
                    
                    r1.insert(0, sequence1[i-1]);
                    r2.insert(0, sequence2[j-1]);
                    
                    pair[k] = new IntPair(i - 1, j - 1);
                }
                i--;
                j--;
            } else if (nwMap[i][j] == 'L') {
                if (j > 0) {
//                    result2 = sequence2[j - 1] + " " + result2;                    
                    r2.insert(0, sequence2[j - 1]);
                    pair[k] = new IntPair(-1, j - 1);
                }
                r1.insert(0, "---");
//                result1 = " - " + result1;
                j--;
            } else {
                if (i > 0) {
//                    result1 = sequence1[i - 1] + " " + result1;
                    r1.insert(0, sequence1[i - 1]);
                    pair[k] = new IntPair(i - 1, -1);
                }
                r2.insert(0, "---");
//                result2 = " - " + result2;
                i--;
            }
        }
//        System.out.println("==================================================");
//
        System.out.println(r1.toString());
        System.out.println(r2.toString());
//
        for (IntPair i : pair) {
            System.out.println(i.toString());
        }

        // return normalized similarity score between two plot graphs
        double last = nwMatrix[nwMatrix.length - 1][nwMatrix[0].length - 1];
        
        double max = size1 > size2 ? size1 : size2; // best case is equal plot graph with length max
        double min = gap * max; // worst case is comparing plot graph of length max to 0
        
        actionScore = normalize(last, max, min);
        
        return actionScore;
    }

    public double childSimilarity() {
        ArrayList<ActionVertex> action1 = p1.actionVertexList;
        ArrayList<ActionVertex> action2 = p2.actionVertexList;
        
        int total1 = p1.numOfTotalChildren;
        int total2 = p2.numOfTotalChildren;

        double score = 0;

        for (IntPair p : pair) {
            // Ambil 2 action yang dianggap sejajar
            if (p.getSource() != -1 && p.getTarget() != -1) {
                ActionVertex av1 = action1.get(p.getSource());
                ActionVertex av2 = action2.get(p.getTarget());

                for (ActionVertexChild c1 : av1.children) {
                    for (ActionVertexChild c2 : av2.children) {
                        if (c1.relation.equals(c2.relation)) {
//                            System.out.println("Relation: " + c1.relation + " Word: " + c1.word + ", " + c2.word);
                            double tmp = rc.calcRelatednessOfWords(c1.word, c2.word);
                            if (tmp > 10000){
                                tmp = 1;
                            }
                            score += tmp ;
//                            System.out.println("Score: " + tmp);
                        }
                    }
                }
            }
        }
        
        double max = total1 > total2 ? total1 : total2;
        double min = max * gap;
        
        childScore = normalize(score, max, min);
        
        return childScore;
    }
    
    public double getTotalSimilarity(){
        double a = actionSimilarity();
        double c = childSimilarity();
        
        return alpha * a + beta * c;
    }
    
    public double normalize(double value, double max, double min){
        return (value - min)/(max - min);
    }
    public static void main(String[] args){
        SimilarityCalculator s = new SimilarityCalculator(null, null, 1, 1, -1);
        s.actionSimilarity();
    }
}
