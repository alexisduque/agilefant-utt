/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.hut.soberit.agilefant.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alex
 */
@XStreamAlias("Sequence")
public class Sequence {
 
    private int id;
    
    @XStreamAlias("attendee")
   	@XStreamAsAttribute
	private String attendee;
    
    @XStreamAlias("story")
   	@XStreamAsAttribute
	private int story;
    
    @XStreamAlias("Comment")
   	@XStreamAsAttribute
    private String comment;
    
    @XStreamAlias("finish")
   	@XStreamAsAttribute
    private float finish;
    
    @XStreamAlias("question")
   	@XStreamAsAttribute
    private String question;
    
    @XStreamImplicit(itemFieldName="Item")
	private ArrayList<String> item;
    
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
     * @return the attendee
     */
    public String getAttendee() {
        return attendee;
    }

    /**
     * @param attendee the attendee to set
     */
    public void setAttendee(String attendee) {
        this.attendee = attendee;
    }

    /**
     * @return the story
     */
    public int getStory() {
        return story;
    }

    /**
     * @param story the story to set
     */
    public void setStory(int story) {
        this.story = story;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the finish
     */
    public float getFinish() {
        return finish;
    }

    /**
     * @param finish the finish to set
     */
    public void setFinish(float finish) {
        this.finish = finish;
    }

    /**
     * @return the question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * @param question the question to set
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * @return the item
     */
    public ArrayList<String> getItem() {
        return item;
    }

    /**
     * @param item the item to set
     */
    public void setItem(ArrayList<String> item) {
        this.item = item;
    }
 
}
