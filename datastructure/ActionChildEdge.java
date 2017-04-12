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
public class ActionChildEdge implements Serializable{
    private static final long serialVersionUID = -3643005641837584385L;
    public ActionVertex parent;
    public ActionVertexChild child;
    public String relation;

    public ActionChildEdge(ActionVertex parent, ActionVertexChild child, String relation) {
        this.parent = parent;
        this.child = child;
        this.relation = relation;
    }
    
    public boolean equals(ActionChildEdge other){
        return this.parent.equals(other.parent) && 
                this.child.equals(other.child) &&
                this.relation.equals(other.relation);
    }
    
    public void setRelation(String s){
        this.relation = s;
    }
    
    public String toString(){
        return relation + "(" + parent.word + ", " + child.word + ")";
    }
}
