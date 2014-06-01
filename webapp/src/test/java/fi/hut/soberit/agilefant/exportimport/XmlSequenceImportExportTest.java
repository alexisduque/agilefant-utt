/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.hut.soberit.agilefant.exportimport;

import fi.hut.soberit.agilefant.model.AudioSequences;
import fi.hut.soberit.agilefant.model.Sequence;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author alex
 */
@RunWith(SpringJUnit4ClassRunner.class)
// ApplicationContext will be loaded from "/applicationContext.xml" and "/applicationContext-test.xml"
// in the root of the classpath
@ContextConfiguration
public class XmlSequenceImportExportTest {

    public XmlSequenceImportExportTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    @Autowired
    private XmlSequenceImportExport instance;

    /**
     * Test of convertFromXMLToObject method, of class XmlSequenceImport.
     */
    @Test
    public void testConvertFromXMLToObject() throws Exception {
        String resource = Thread.currentThread().getContextClassLoader().getResource("MMproject.xml").getPath();
        System.out.println("convertFromXMLToObject");
        System.out.println(resource);
        FileInputStream os = null;
        try {
            os = new FileInputStream(new File(resource));

            ArrayList item = new ArrayList<String>();
            ArrayList seqList = new ArrayList<Sequence>();
            AudioSequences expResult = new AudioSequences();

            Sequence seq = new Sequence();
            seq.setAttendee("alex");
            seq.setComment("comment");
            seq.setQuestion("question");
            seq.setFinish((float) 8249.0);
            item.add("Competences");
            item.add("2");
            seq.setItem(item);
            seqList.add(seq);

            expResult.setSequence(seqList);
            AudioSequences result = (AudioSequences) instance.convertFromXMLToAudioSequence(os);
            Sequence seqResult = result.getSequence().get(0);

            String itemR = seqResult.getItem().get(1);

            assertEquals(itemR, "2");
            assertEquals(seqResult.getAttendee(), "alex");

        } finally {
            if (os != null) {
                os.close();
            }
        }

    }

    @Test
    public void testParseItem() throws Exception {
        int test1 = parseItem("story 21");
        int test2 = parseItem("Story 2");
        int test3 = parseItem("Story #2");
        int test4 = parseItem("story #29");
        int test5 = parseItem("storyD2");
        assertEquals(21, test1);
        assertEquals(2, test2);
        assertEquals(2, test3);
        assertEquals(29, test4);
        assertEquals(0, test5);
    }

    @Test
    public void testReadingXML() throws Exception {
        int id = 0;
        String comment = "";
        String resource = Thread.currentThread().getContextClassLoader().getResource("MMproject.xml").getPath();
        System.out.println("convertFromXMLToObject");
        System.out.println(resource);
        FileInputStream os = null;
        try {
            os = new FileInputStream(new File(resource));
            AudioSequences audioSeq = (AudioSequences) instance.convertFromXMLToAudioSequence(os);
            ArrayList<Sequence> sequenceList = audioSeq.getSequence();
            Iterator<Sequence> seqItr = sequenceList.iterator();
            while (seqItr.hasNext()) {
                Sequence sequence = seqItr.next();
                comment = sequence.getComment();
                ArrayList<String> itemList = sequence.getItem();
                Iterator<String> itemItr = itemList.iterator();
                while (itemItr.hasNext()) {
                    String item = itemItr.next();
                    id = parseItem(item);
                }
            }
            assertEquals(3, id);
            assertEquals(comment, "Bonjour Bob");

        } finally {
            if (os != null) {
                os.close();
            }
        }
    }

    public int parseItem(String item) {
        String trim = item.trim().toLowerCase();
        if (trim.matches("story #\\d*")) {
            String idString = trim.split("#")[1];
            System.out.println(idString);
            if (StringUtils.isNumeric(idString)) {
                return Integer.parseInt(idString);
            }
        }
        if (trim.matches("story \\d*")) {
            String idString = trim.split(" ")[1];
            System.out.println(idString);
            if (StringUtils.isNumeric(idString)) {
                return Integer.parseInt(idString);
            }
        }
        System.out.println("Pattern not matched");
        return 0;
    }

}
