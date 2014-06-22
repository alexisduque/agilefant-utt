/*.

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.hut.soberit.agilefant.business.impl;

import fi.hut.soberit.agilefant.exportimport.ListToStringXStreamConverter;
import com.google.common.base.Throwables;
import fi.hut.soberit.agilefant.business.MMImportExportBusiness;
import fi.hut.soberit.agilefant.db.StoryDAO;
import fi.hut.soberit.agilefant.exportimport.XmlSequenceImportExport;
import fi.hut.soberit.agilefant.model.AudioSequences;
import fi.hut.soberit.agilefant.model.Backlog;
import fi.hut.soberit.agilefant.model.Iteration;
import fi.hut.soberit.agilefant.model.Sequence;
import fi.hut.soberit.agilefant.model.Story;
import fi.hut.soberit.agilefant.model.StoryItem;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author alex
 */
@Service("mmImportBusiness")
@Transactional
public class MMImportExportBusinessImpl implements MMImportExportBusiness {

    @Autowired
    XmlSequenceImportExport converter;
    @Autowired
    private StoryDAO storyDAO;
    private String errorStacktrace;
    
    public void importStory(FileInputStream xml) {
        try {
            AudioSequences audioSeq = (AudioSequences) converter.convertFromXMLToAudioSequence(xml);
            ArrayList<Sequence> sequenceList = audioSeq.getSequence();
            Iterator<Sequence> seqItr = sequenceList.iterator();
            while (seqItr.hasNext()) {
                Sequence sequence = seqItr.next();
                String comment = sequence.getComment();
                ArrayList<String> itemList = sequence.getItem();
                if (itemList != null) {
                    Iterator<String> itemItr = itemList.iterator();
                    while (itemItr.hasNext()) {
                        String item = itemItr.next();
                        int id = parseItem(item);
                        if (id > 0) {
                            if (storyDAO.exists(id)) {
                                Story story = storyDAO.get(id);
                                if (story != null) {
                                    String storyDesc = story.getDescription();
                                    if (storyDesc != null) {
                                        story.setDescription(storyDesc + "<br>" + comment);
                                    } else {
                                        story.setDescription(comment);
                                    }
                                    storyDAO.store(story);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            errorStacktrace = Throwables.getStackTraceAsString(e);
        }
    }
    
    public String exportStory(Backlog backlogId) {
        try {
            ListToStringXStreamConverter convert = new ListToStringXStreamConverter("Item");
            converter.getXstream().processAnnotations(StoryItem.class);
            converter.getXstream().registerLocalConverter(StoryItem.class, "ItemList", convert);
            StoryItem stories = new StoryItem();
            ArrayList<String> items = new ArrayList<String>();
            List<Story> storyList = storyDAO.retrieveStoriesInBacklog(backlogId);
            stories.setNameList("Stories  "+backlogId.getName());
            stories.setColorList(-655105);
            Iterator<Story> it = storyList.iterator();
            while (it.hasNext()) {
                Story story = it.next();
                String item = "Story #"+story.getId();
                items.add(item);
            }
            stories.setItemList(items);   
            return converter.getXstream().toXML(stories);
        } catch (Exception e) {
            errorStacktrace = Throwables.getStackTraceAsString(e);
            return null;
        }
    }
    
    public String exportStory(Iteration iterationId) {
        try {
            ListToStringXStreamConverter convert = new ListToStringXStreamConverter("Item");
            converter.getXstream().processAnnotations(StoryItem.class);
            converter.getXstream().registerLocalConverter(StoryItem.class, "ItemList", convert);
            StoryItem stories = new StoryItem();
            ArrayList<String> items = new ArrayList<String>();
            List<Story> storyList = storyDAO.retrieveStoriesInIteration(iterationId);
            stories.setNameList("Stories "+iterationId.getName());
            stories.setColorList(-655105);
            Iterator<Story> it = storyList.iterator();
            while (it.hasNext()) {
                Story story = it.next();
                String item = "Story #"+story.getId();
                items.add(item);
            }
            stories.setItemList(items);   
            return converter.getXstream().toXML(stories);
        } catch (Exception e) {
            errorStacktrace = Throwables.getStackTraceAsString(e);
            return null;
        }
    }
        
    public int parseItem(String item) {
        String trim = item.trim().toLowerCase();
        if (trim.matches("story #\\d*")) {
            String idString = trim.split("#")[1];
            if (StringUtils.isNumeric(idString)) {
                return Integer.parseInt(idString);
            }
        }
        if (trim.matches("story \\d*")) {
            String idString = trim.split(" ")[1];
            if (StringUtils.isNumeric(idString)) {
                return Integer.parseInt(idString);
            }
        }
        return 0;
    }
}
