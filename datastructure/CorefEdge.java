/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package datastructure;

import java.io.Serializable;

/**
 *
 * @author V Anugrah
 */
public class CorefEdge implements Comparable, Serializable{
    private static final long serialVersionUID = -8280053211607305782L;
    public CorefVertex start;
    public ActionVertexChild end;
    public int chainID;

    public CorefEdge(CorefVertex start, ActionVertexChild end, int chainID) {
        this.start = start;
        this.end = end;
        this.chainID = chainID;
    }
    
    @Override
    public String toString(){
        return "[" + start.word + " --> " + end.word + " (" + end.sentenceNumber + ")]";
    }

    @Override
    public int compareTo(Object o) {
        return this.chainID - ((CorefEdge)o).chainID;
    }
}
