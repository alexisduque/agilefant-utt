/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.hut.soberit.agilefant.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import java.util.ArrayList;

/**
 *
 * @author alex
 */
@XStreamAlias("AudioSequences")
public class AudioSequences {
 
    private int id;
    
    @XStreamImplicit(itemFieldName="Sequence")
	private ArrayList<Sequence> sequence;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the sequence
     */
    public ArrayList<Sequence> getSequence() {
        return sequence;
    }

    /**
     * @param sequence the sequences to set
     */
    public void setSequence(ArrayList sequence) {
        this.sequence = sequence;
    }
 
}
