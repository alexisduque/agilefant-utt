/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.hut.soberit.agilefant.exportimport;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import fi.hut.soberit.agilefant.model.AudioSequences;
import fi.hut.soberit.agilefant.model.Sequence;
import fi.hut.soberit.agilefant.model.StoryItem;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alex
 */
public class XmlSequenceImportExport {

    private XStream xstream = new XStream(new StaxDriver());
    
    public void init() {
        xstream.processAnnotations(AudioSequences.class);
        xstream.processAnnotations(Sequence.class);
        xstream.processAnnotations(StoryItem.class);
    }

    public Object convertFromXMLToAudioSequence(FileInputStream xmlfile) throws IOException {

        this.init();
        AudioSequences audioSequence = null;
        try {
            return (AudioSequences) getXstream().fromXML(xmlfile);
        } finally {
            if (xmlfile != null) {
                xmlfile.close();
            }
        }
    }

    public String convertFromObjectToStoryItem(Object object) throws IOException {
        this.init();
        getXstream().processAnnotations(StoryItem.class);
        return getXstream().toXML((StoryItem) object);
    }

    /**
     * @return the xstream
     */
    public XStream getXstream() {
        return xstream;
    }

    /**
     * @param xstream the xstream to set
     */
    public void setXstream(XStream xstream) {
        this.xstream = xstream;
    }

}
