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
public class ActionEdge implements Serializable {
    private static final long serialVersionUID = -4582215334970197047L;
    public ActionVertex start;
    public ActionVertex end;

    public ActionEdge(ActionVertex start, ActionVertex end) {
        this.start = start;
        this.end = end;
    }
    
    public boolean equals(ActionEdge other){
        return start.equals(other.start) && end.equals(other.end);
    }
    
    public String toString(){
        return "[" + start.word + ", " + end.word + "]";
    }
}