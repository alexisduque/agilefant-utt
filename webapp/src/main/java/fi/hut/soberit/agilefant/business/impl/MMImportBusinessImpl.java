/*.

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.hut.soberit.agilefant.business.impl;

import com.google.common.base.Throwables;
import fi.hut.soberit.agilefant.business.MMImportBusiness;
import fi.hut.soberit.agilefant.db.StoryDAO;
import fi.hut.soberit.agilefant.exportimport.XmlSequenceImport;
import fi.hut.soberit.agilefant.model.AudioSequences;
import fi.hut.soberit.agilefant.model.Sequence;
import fi.hut.soberit.agilefant.model.Story;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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
public class MMImportBusinessImpl implements MMImportBusiness {

    @Autowired
    XmlSequenceImport converter;
    @Autowired
    private StoryDAO storyDAO;
    private String errorStacktrace;
    
    public void importStory(FileInputStream xml) {
        try {
            AudioSequences audioSeq = (AudioSequences) converter.convertFromXMLToObject(xml);
            ArrayList<Sequence> sequenceList = audioSeq.getSequence();
            Iterator<Sequence> seqItr = sequenceList.iterator();
            while (seqItr.hasNext()) {
                Sequence sequence = seqItr.next();
                String comment = sequence.getComment();
                ArrayList<String> itemList = sequence.getItem();
                Iterator<String> itemItr = itemList.iterator();
                while (itemItr.hasNext()) {
                    String item = itemItr.next();
                    if (StringUtils.isNumeric(item)) {
                        try {
                            int id = Integer.parseInt(item);
                            if (storyDAO.exists(id)) {
                                Story story = storyDAO.get(id);
                                if (story != null) {
                                    String storyDesc = story.getDescription();
                                    if (storyDesc.length() > 1) {
                                        story.setDescription(storyDesc+"<br>"+comment);
                                    } else {
                                        story.setDescription(comment);
                                    }
                                    storyDAO.store(story);
                                }
                            }
                        } catch (NumberFormatException e) {
                        }
                    }
                }
            }
        } catch (Exception e) {
             errorStacktrace = Throwables.getStackTraceAsString(e);
        }
    }
}
