/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.hut.soberit.agilefant.exportimport;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import fi.hut.soberit.agilefant.model.AudioSequences;
import fi.hut.soberit.agilefant.model.Sequence;
import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 * @author alex
 */
public class XmlSequenceImport {

    private XStream xstream = new XStream(new StaxDriver());

    public Object convertFromXMLToObject(FileInputStream xmlfile) throws IOException {

        getXstream().processAnnotations(AudioSequences.class);
        getXstream().processAnnotations(Sequence.class);
        AudioSequences audioSequence = null;
        try {
            return (AudioSequences) getXstream().fromXML(xmlfile);
        } finally {
            if (xmlfile != null) {
                xmlfile.close();
            }
        }
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
