/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package datastructure;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author V Anugrah
 */
public class ActionVertex implements Comparable, Serializable{    
    private static final long serialVersionUID = -2825102470978495913L;
    public String word;     // the action word
    public int index;       // the word index in the sentence
    public int sentenceNumber;  // the sentence number in the paragraph
    public ArrayList<ActionVertexChild> children; // list of children (based on IndexedWord)

    public ActionVertex(String word, int index, int sentenceNumber) {
        this.word = word;
        this.index = index;
        this.sentenceNumber = sentenceNumber;
        this.children = new ArrayList<ActionVertexChild>();
    }
    
    public void addChild(ActionVertexChild child){
        children.add(child);
    }
    
    @Override
    public String toString(){
        String childStr = "";
        
        for (ActionVertexChild child : children)
        {
            childStr = childStr + " " + child.word;
        }
        
        return "[" + word + ", " + index + ", " + sentenceNumber + ", " + childStr + "]";
    }
    
    public boolean equals(ActionVertex other){
        return this.word.equals(other.word) && 
                this.index == other.index && 
                this.sentenceNumber == other.sentenceNumber;
    }

    @Override
    public int compareTo(Object o) {
        ActionVertex other = (ActionVertex) o;
        int result = 0;
        
        if (this.sentenceNumber == other.sentenceNumber){
            result = this.index - other.index;
        }
        else{
            result = this.sentenceNumber - other.sentenceNumber;
        }
        return result;
    }
}
