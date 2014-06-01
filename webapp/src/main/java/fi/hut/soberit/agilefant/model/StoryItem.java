/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.hut.soberit.agilefant.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import java.util.ArrayList;

/**
 *
 * @author alex
 */
@XStreamAlias("XML")
public class StoryItem {
 
    @XStreamAlias("NameList")
    private String NameList;
    @XStreamAlias("ColorList")
    private long ColorList;
    
	private ArrayList<String> ItemList;

    /**
     * @return the NameList
     */
    public String getNameList() {
        return NameList;
    }

    /**
     * @param NameList the NameList to set
     */
    public void setNameList(String NameList) {
        this.NameList = NameList;
    }

    /**
     * @return the ColorList
     */
    public long getColorList() {
        return ColorList;
    }

    /**
     * @param ColorList the ColorList to set
     */
    public void setColorList(long ColorList) {
        this.ColorList = ColorList;
    }

    /**
     * @return the ItemList
     */
    public ArrayList<String> getItemList() {
        return ItemList;
    }

    /**
     * @param ItemList the ItemList to set
     */
    public void setItemList(ArrayList<String> ItemList) {
        this.ItemList = ItemList;
    }
 
}
