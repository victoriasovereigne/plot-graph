/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure;

import edu.stanford.nlp.util.IntPair;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author V Anugrah
 */
public class PlotGraph implements Serializable{
    
    private static final long serialVersionUID = 1880443643555724942L;
    
    public String originalStory;

    public ArrayList<ActionVertex> actionVertexList;
    public ArrayList<ActionVertexChild> actionVertexChildList;
    public ArrayList<ActionEdge> actionEdgeList;
    public ArrayList<ActionChildEdge> actionChildEdgeList;
    public ArrayList<CorefVertex> corefVertexList;
//    public ArrayList<CorefEdge> corefEdgeList;

    public HashMap<Integer, ArrayList<ActionVertex>> actionVertexHash;
    public HashMap<Integer, CorefVertex> corefVertexHash;
    public HashMap<Integer, ArrayList<CorefEdge>> corefEdgeHash;

    public int numOfSentence;
    public int numOfTotalChildren;

    public PlotGraph() {
        actionVertexList = new ArrayList<ActionVertex>();
        actionVertexChildList = new ArrayList<ActionVertexChild>();
        actionEdgeList = new ArrayList<ActionEdge>();
        actionChildEdgeList = new ArrayList<ActionChildEdge>();
        corefVertexList = new ArrayList<CorefVertex>();
//        corefEdgeList = new ArrayList<CorefEdge>();

        actionVertexHash = new HashMap<Integer, ArrayList<ActionVertex>>();
        corefVertexHash = new HashMap<Integer, CorefVertex>();
        corefEdgeHash = new HashMap<Integer, ArrayList<CorefEdge>>();
        
        numOfSentence = 0;
        numOfTotalChildren = 0;
    }

    // =========================================================================
    public void addActionVertexToHashMap(ActionVertex a) {
        int key = a.sentenceNumber;
        if (!actionVertexHash.containsKey(key)) {
            actionVertexHash.put(key, new ArrayList<ActionVertex>());
        }
        actionVertexHash.get(key).add(a);
        Collections.sort(actionVertexHash.get(key));
    }

    public void addCorefVertexToHashMap(CorefVertex c) {
        int key = c.chainID;
        if (!corefVertexHash.containsKey(key)) {
            corefVertexHash.put(key, c);
        }
    }

    public void printAVHashMap() {
        Set<Integer> set = actionVertexHash.keySet();
        Iterator iter = set.iterator();

        while (iter.hasNext()) {
            int key = (Integer) iter.next();
            ArrayList<ActionVertex> avlist = actionVertexHash.get(key);
            String actions = key + " --> ";

            for (ActionVertex av : avlist) {
                actions += av.word + " ";
            }
            System.out.println(actions);
        }
    }

    public void printCVHashMap() {
        Set<Integer> set = corefVertexHash.keySet();
        Iterator iter = set.iterator();

        while (iter.hasNext()) {
            int key = (Integer) iter.next();
            System.out.println(key + " --> " + corefVertexHash.get(key).toString());
        }
    }

    public String printCEHashMap() {
        ArrayList<Integer> keys = new ArrayList<Integer>(corefEdgeHash.keySet());
        Collections.sort(keys);
        String str = "Coref Edge: \n";
        for (int key : keys) {
            ArrayList<CorefEdge> celist = corefEdgeHash.get(key);
            str += key + " --> ";

            for (CorefEdge ce : celist) {
                str += ce.toString() + " ";
            }
            str += "\n";
        }
        System.out.println(str);
        return str;
    }

    public void linkCorefChainHash() {
        for (ActionVertexChild avc : actionVertexChildList) {
            for (CorefVertex c : corefVertexList) {
                for (IntPair ip : c.intpairs) {
                    if (avc.index == ip.getSource() && avc.sentenceNumber == ip.getTarget()) {
                        CorefEdge ce = new CorefEdge(c, avc, c.chainID);

                        if (!corefEdgeHash.containsKey(c.chainID)) {
                            corefEdgeHash.put(c.chainID, new ArrayList<CorefEdge>());
                        }
                        corefEdgeHash.get(c.chainID).add(ce);
                        break;
                    }
                }
            }
        }
    }

    // =========================================================================
    public void addActionVertex(ActionVertex a) {
        if (!contains(a)) {
            actionVertexList.add(a);
        }
    }

    public void addActionVertexChild(ActionVertexChild a) {
        if (!contains(a)) {
            actionVertexChildList.add(a);
        }
    }

    public void addActionEdge(ActionEdge e) {
        if (!contains(e)) {
            actionEdgeList.add(e);
        }
    }

    public void addActionChildEdge(ActionChildEdge e) {
        if (!contains(e)) {
            actionChildEdgeList.add(e);
        }
    }

    public boolean contains(ActionVertex a) {
        for (ActionVertex av : actionVertexList) {
            if (a.equals(av)) {
                return true;
            }
        }
        return false;
    }

    public boolean contains(ActionVertexChild a) {
        for (ActionVertexChild av : actionVertexChildList) {
            if (a.equals(av)) {
                return true;
            }
        }
        return false;
    }

    public boolean contains(ActionEdge e) {
        for (ActionEdge ae : actionEdgeList) {
            if (e.equals(ae)) {
                return true;
            }
        }
        return false;
    }

    public boolean contains(ActionChildEdge e) {
        for (ActionChildEdge ace : actionChildEdgeList) {
            if (e.equals(ace)) {
                return true;
            }
        }
        return false;
    }

    public String printActionVertex() {
        String s = "Action Vertex: \n";
        for (ActionVertex a : actionVertexList) {
            s = s + a.toString() + "\n";
        }
        System.out.println(s);
        return s;
    }

    public String printActionEdge() {
        String s = "Action Edge: \n";
        for (ActionEdge a : actionEdgeList) {
            s = s + a.toString() + "\n";
        }
        System.out.println(s);
        return s;
    }

    public String printCorefVertex() {
        String s = "Coref Vertex: \n";
        for (CorefVertex c : corefVertexList) {
            s = s + c.toString() + "\n";
        }
        System.out.println(s);
        return s;
    }

    public String printActionChildEdge() {
        String s = "Action Child Edge: \n";
        for (ActionChildEdge a : actionChildEdgeList) {
            s = s + a.toString() + "\n";
        }
        System.out.println(s);
        return s;
    }

    public String printActionVertexChild() {
        String s = "Action Vertex Child: \n";
        for (ActionVertexChild avc : actionVertexChildList) {
            s = s + avc.word + " " + avc.index + " " + avc.sentenceNumber + "\n";
        }
        System.out.println(s);
        return s;
    }

//    public String printCorefEdge() {
//        String s = "Coref Edge: \n";
//        for (CorefEdge ce : corefEdgeList) {
//            s = s + ce.toString() + "\n";
//        }
//        System.out.println(s);
//        return s;
//    }
//    public void linkCorefChain() {
//        Collections.sort(corefVertexList);
//
//        for (ActionVertexChild avc : actionVertexChildList) {
//            for (CorefVertex c : corefVertexList) {
//                for (IntPair ip : c.intpairs) {
//                    if (avc.index == ip.getSource() && avc.sentenceNumber == ip.getTarget()) {
//                        CorefEdge ce = new CorefEdge(c, avc, c.chainID);
//                        corefEdgeList.add(ce);
//                        break;
//                    }
//                }
//            }
//        }
//        Collections.sort(corefEdgeList);
//    }
    public void linkAction() {
        Collections.sort(actionVertexList);

        for (int i = 0; i < actionVertexList.size() - 1; i++) {
            ActionEdge ae = new ActionEdge(actionVertexList.get(i), actionVertexList.get(i + 1));
            actionEdgeList.add(ae);
        }
    }

    public CorefEdge getChain(CorefVertex cv, ActionVertexChild avc) {
        if (corefEdgeHash.containsKey(cv.chainID)) {
            ArrayList<CorefEdge> ce = corefEdgeHash.get(cv.chainID);

            for (CorefEdge edge : ce) {
                if (edge.end.equals(avc)) {
                    return edge;
                }
            }
        }
        return null;
    }

    public int getNumOfChildren(CorefVertex cv) {
        int num = 0;
        if (corefEdgeHash.containsKey(cv.chainID)) {
            num = corefEdgeHash.get(cv.chainID).size();
        }
        return num;
    }
    
    public void replaceCorefWords(){
        ArrayList<Integer> keys = new ArrayList<Integer>(corefEdgeHash.keySet());
        Collections.sort(keys);
        
        for (int key : keys){
            ArrayList<CorefEdge> celist = corefEdgeHash.get(key);
            
            for (CorefEdge ce : celist){
                ce.end.word = ce.start.word;
            }
        }   
    }
    
    public ActionVertexChild getActionVertexChild(int index, int sentNum){
        for (ActionVertexChild avc : actionVertexChildList){
            if (avc.index == index && avc.sentenceNumber == sentNum){
                return avc;
            }
        }
        return null;
    }
    
    public CorefVertex getCorefVertex(int chainID){
        return corefVertexHash.get(chainID);
    }
    
    public void removeCorefEdge(int corefID, int index, int sentNum, String startWord, String endWord){
        ArrayList<CorefEdge> alce = corefEdgeHash.get(corefID);
        for (int i = 0; i < alce.size(); i++){
            CorefEdge ce = alce.get(i);
            if (ce.end.index == index && ce.end.sentenceNumber == sentNum){
                alce.remove(i);
            }
        }
    }
    
    public void changeRelation(String startWord, int idxStart, int sentStart, 
            String endWord, int idxEnd, int sentEnd, String newReln){
        
        for (int i = 0; i < actionChildEdgeList.size(); i++){
            ActionChildEdge ace = actionChildEdgeList.get(i);
            
            ActionVertex parent = ace.parent;
            ActionVertexChild child = ace.child;
            
            if (parent.word.equals(startWord) && parent.index == idxStart && parent.sentenceNumber == sentStart
                    && child.word.equals(endWord) && child.index == idxEnd && child.sentenceNumber == sentEnd){
//                System.out.println("enter here: " + newReln);
//                parent.word = "halo";
                ace.relation = newReln;
                child.relation = newReln;
                break;
            }
        }
    }
    
    public ActionVertex getActionVertex(String startWord, int idx, int sentNum){
        for (int i = 0; i < actionVertexList.size(); i++){
            ActionVertex av = actionVertexList.get(i);
            if (av.word.equals(startWord) && av.index == idx && av.sentenceNumber == sentNum){
                return av;
            }
        }
        return null;
    }
}
