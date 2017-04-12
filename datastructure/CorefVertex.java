/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package datastructure;

import edu.stanford.nlp.util.IntPair;
import java.io.Serializable;

/**
 *
 * @author V Anugrah
 */
public class CorefVertex implements Comparable, Serializable{
    private static final long serialVersionUID = 6376055073281321901L;
    public String word;
    public int chainID;
    public IntPair[] intpairs;

    public CorefVertex(String word, int chainID, int arraySize) {
        this.word = word;
        this.chainID = chainID;
        intpairs = new IntPair[arraySize];
    }
    
    public boolean equals(CorefVertex other){
        return this.word.equals(other.word) && this.chainID == other.chainID;
    }
    
    @Override
    public String toString(){
        String childStr = "";
        
        for (IntPair i : intpairs)
        {
            childStr = childStr + "[" + i.getSource() + ", " + i.getTarget() + "] ";
        }
        
        return word + " " + chainID + " " + childStr;
    }

    @Override
    public int compareTo(Object o) {
        CorefVertex other = (CorefVertex) o;
        return this.chainID - other.chainID;
    }
}
