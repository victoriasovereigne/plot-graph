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
public class ActionVertexChild implements Serializable {
    private static final long serialVersionUID = -2943541458760674212L;
    public String word;
    public String tag;
    public String relation;
    
    public int index;
    public int sentenceNumber;
//    public int chainID;

    public ActionVertexChild(String word, String type, String relation, int index, int sentenceNumber) {
        this.word = word;
        this.tag = type;
        this.relation = relation;
        this.index = index;
        this.sentenceNumber = sentenceNumber;
    }
    public boolean equals(ActionVertexChild other){
        return this.word.equals(other.word) && 
                this.tag.equals(other.tag) && 
                this.relation.equals(other.relation) && 
                this.index == other.index && 
                this.sentenceNumber == other.sentenceNumber;
//                this.chainID == other.chainID;
    }
}
