
import datastructure.ActionChildEdge;
import datastructure.CorefEdge;
import datastructure.PlotGraph;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author V Anugrah
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException {
//        String story1 = "Once upon a time there lived a lion in a forest. \n"
//                + "One day after a heavy meal, it was sleeping under a tree. \n"
//                + "After a while, there came a mouse and it started to play on the lion. ";
//        String story2 = "Once upon a time, a big lion lived in the jungle.\n"
//                + "Every day it liked to sleep under a tree. \n"
//                + "Suddenly a mouse came and began to play on the lion. ";
//
//        PlotGraph[] listP = new PlotGraph[2];
//        listP[0] = PlotGraphCreator.createPlotGraph(story1);
//        listP[1] = PlotGraphCreator.createPlotGraph(story2);
//
//        double[][] result = getSimilarityResult(listP, 0.3, 0.7, 0);
//
//        for (double[] dd : result) {
//            for (double d : dd) {
//                if (d != -10) {
//                    System.out.printf("%.2f ", d);
//                }
//            }
//            System.out.println();
//        }
//        
//        result = getSimilarityResult(listP, 0.3, 0.7, -1);
//
//        for (double[] dd : result) {
//            for (double d : dd) {
//                if (d != -10) {
//                    System.out.printf("%.2f ", d);
//                }
//            }
//            System.out.println();
//        }
//        serialize(listP[0], "file0.ser");
//        serialize(listP[1], "file1.ser");
//        listP[0] = deserialize("file0.ser");
//        listP[1] = deserialize("file1.ser");

//        Visualizer.visualize(listP[0]);
//        Visualizer.visualize(listP[1]);
//        listP[0].removeCorefEdge(5, 8, 2, "day", "day");
//        listP[0].corefEdgeHash.get(2).add(new CorefEdge(listP[0].getCorefVertex(2), listP[0].getActionVertexChild(8, 2), 2));
//
//        listP[0].removeCorefEdge(9, 10, 3, "while", "while");
//        listP[0].corefEdgeHash.get(10).add(new CorefEdge(listP[0].getCorefVertex(10), listP[0].getActionVertexChild(10, 3), 10));
//        listP[0].replaceCorefWords();
//        
//        listP[0].changeRelation("live", 6, 1, "there", 5, 1, "test");
//        listP[0].changeRelation("live", 6, 1, "lion", 8, 1, "nsubj");
//        listP[0].changeRelation("come", 6, 3, "mouse", 8, 3, "nsubj");
//
//        listP[1].removeCorefEdge(1, 3, 2, "time", "time");
//        listP[1].corefEdgeHash.get(2).add(new CorefEdge(listP[1].getCorefVertex(2), listP[1].getActionVertexChild(3, 2), 2));
//        listP[1].replaceCorefWords();

//        Visualizer.visualize(listP[0]);
//        Visualizer.visualize(listP[1]);

//        result = getSimilarityResult(listP, 0.3, 0.7, 0);
//
//        for (double[] dd : result) {
//            for (double d : dd) {
//                if (d != -10) {
//                    System.out.printf("%.2f ", d);
//                }
//            }
//            System.out.println();
//        }
//        
//        result = getSimilarityResult(listP, 0.3, 0.7, -1);
//
//        for (double[] dd : result) {
//            for (double d : dd) {
//                if (d != -10) {
//                    System.out.printf("%.2f ", d);
//                }
//            }
//            System.out.println();
//        }
//        File folder = new File("C:\\Users\\IR\\Documents\\Victoria TA [DO NOT DISTURB]\\TestTA");
        File folder = new File("C:\\Users\\V Anugrah\\Documents\\NetBeansProjects\\TA_1106087534");
        File[] files = folder.listFiles();
        System.out.println(files.length);
//        
//        String[] stories = new String[24];
//        int i = 0;
//        for (File f : files) {
//            if (f.getName().endsWith(".txt")) {
//                System.out.println(f.getName());
//                stories[i] = processFile(f.getName());
//                i++;
//            }
//        }
//        
//        CosineSimilarity c = new CosineSimilarity();
//        double[][] score = new double[24][24];
//        
//        for (i = 0; i < 24; i++){
//            for (int j = 0; j < 24; j++){
//                score[i][j] = c.Cosine_Similarity_Score(stories[i], stories[j]);
//            }
//        }
//        
//        for (double[] dd : score){
//            for (double d : dd){
//                System.out.printf("%.2f ", d);
//            }
//            System.out.println();
//        }
        
////        System.out.println("ting tong");
//        //Membaca file .ser dan memasukkan ke dalam list of plot graph       
        PlotGraph[] listP = new PlotGraph[files.length];
//
        System.out.println("........................... READING FILES ...........................");
        for (int k = 0, n = 1; k < files.length; k++) {
            String fileName = files[k].getName();            
            if (fileName.endsWith(".ser")) {
                System.out.println(fileName);
//                String title = fileName.substring(13, fileName.length()-8).replace("_", " ").trim().toLowerCase();
//                
//                String[] tmp = title.split(" ");
//                title = "";
//                for (String word : tmp){
//                    title += word.substring(0, 1).toUpperCase() + word.substring(1) + " ";
//                }
                
//                System.out.println("\\hline " + n + ". & " + title + " \\\\");
//                n++;
                listP[k] = deserialize(fileName);
                System.out.println(listP[k].actionVertexList.size());
                System.out.println(listP[k].corefVertexList.size());
                System.out.println(listP[k].actionVertexChildList.size());
            }
        }
//
//        // parameter
//        double[] alphaL = {0.7, 0.5, 0.3};
//        double[] betaL = {0.3, 0.5, 0.7};
//        double[] gapL = {-1, -0.5, 0};
////
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                System.out.println("........................... START EXPERIMENT ...........................");
//
//                double alpha = alphaL[i];
//                double beta = betaL[i];
//                double gap = gapL[j];
//
//                System.out.println("Parameter: alpha = " + alpha + ", beta = " + beta + ", gap = " + gap);
//
//                System.out.println(".............................. SIMILARITY RESULT ..............................");
//                double[][] res = getSimilarityResult(listP, alpha, beta, gap);
//                
////                int n = 1;
////                for (double[] dd : res) {
//////                    System.out.print("\\hline " + n + " ");
////                    for (double d : dd) {
////                        if (d != -10) {
////                            System.out.printf("%.2f ", d);
////                        }
////                    }
//////                    System.out.print(" \\\\");
////                    System.out.println();
////                }
//                
//                System.out.println("........................... FINISH EXPERIMENT ...........................");
//            }
//        }
    }

    /**
     *
     * @param p
     * @param fileName
     */
    public static void serialize(PlotGraph p, String fileName) {
        FileOutputStream fos = null;
        ObjectOutputStream out = null;

        try {
            fos = new FileOutputStream(fileName);
            out = new ObjectOutputStream(fos);
            out.writeObject(p);
            out.close();

        } catch (FileNotFoundException ex) {
            System.err.println("FILE OUTPUT STREAM: File not found exception");
        } catch (IOException ex) {
            System.err.println("OBJECT OUTPUT STREAM: IO exception");
        }
    }

    /**
     *
     * @param fileName
     * @return
     */
    public static PlotGraph deserialize(String fileName) {
        FileInputStream fis = null;
        ObjectInputStream in = null;
        PlotGraph p = null;

        try {
            System.out.println("Reading file: " + fileName + "...");
            fis = new FileInputStream(fileName);
            in = new ObjectInputStream(fis);
            p = (PlotGraph) in.readObject();
        } catch (FileNotFoundException ex) {
            System.err.println("FILE INPUT STREAM: File not found exception");
        } catch (IOException ex) {
            System.err.println("OBJECT INPUT STREAM: IO exception");
            System.out.println(ex);
        } catch (ClassNotFoundException ex) {
            System.err.println("Class cast exception");
        }

//        System.out.println("Reading object... DONE!");
        return p;
    }
    
    
    public static String processFile(String fileName) throws FileNotFoundException, IOException {
        Scanner reader = new Scanner(new File(fileName));
        String text = "";
        while (reader.hasNextLine()) {
            text += " " + reader.nextLine().toLowerCase();
        }

        reader.close();

//        PlotGraph plotGraph = PlotGraphCreator.createPlotGraph(text);
//        serialize(plotGraph, "serial_" + fileName + ".ser");

        return text;
//        String outputFileName = "output\\output_graph_" + fileName + ".txt";
//        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName));
//
//        writer.append(text + "\n");
//        writer.append("=====================================================================\n");
//        writer.append(plotGraph.printActionVertex() + "\n");
//        writer.append("=====================================================================\n");
//        writer.append(plotGraph.printCorefVertex() + "\n");
//        writer.append("=====================================================================\n");
//        writer.append(plotGraph.printActionChildEdge() + "\n");
//        writer.append("=====================================================================\n");
//        writer.append(plotGraph.printActionVertexChild() + "\n");
//        writer.append("=====================================================================\n");
//        writer.append(plotGraph.printCEHashMap() + "\n");
//        writer.append("=====================================================================\n");
//        writer.append(plotGraph.printActionEdge() + "\n");
//
//        writer.close();
    }

    public static double[][] getSimilarityResult(PlotGraph[] listPlotGraph, double alpha, double beta, double gap) {
        double[][] result = new double[listPlotGraph.length][listPlotGraph.length];

        for (int i = 0; i < listPlotGraph.length; i++) {
            for (int j = 0; j < i; j++) {
                if (listPlotGraph[i] != null && listPlotGraph[j] != null) {
                    System.out.print("     ");
                }
            }
            for (int j = i + 1; j < listPlotGraph.length; j++) {
                if (listPlotGraph[i] != null && listPlotGraph[j] != null) {
                    SimilarityCalculator s = new SimilarityCalculator(listPlotGraph[i], listPlotGraph[j], alpha, beta, gap);
                    result[i][j] = s.getTotalSimilarity();

                    System.out.printf("%.2f ", result[i][j]);
                } else {
                    result[i][j] = -10;
                }
            }
            System.out.println();
        }

        return result;
    }
}
