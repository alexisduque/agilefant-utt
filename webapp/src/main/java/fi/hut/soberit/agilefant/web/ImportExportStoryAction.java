package fi.hut.soberit.agilefant.web;

import com.google.common.base.Throwables;
import com.opensymphony.xwork2.Action;
import static com.opensymphony.xwork2.Action.NONE;
import static com.opensymphony.xwork2.Action.SUCCESS;

import fi.hut.soberit.agilefant.business.MMImportExportBusiness;
import fi.hut.soberit.agilefant.db.BacklogDAO;
import fi.hut.soberit.agilefant.db.IterationDAO;
import fi.hut.soberit.agilefant.exportimport.ExportImport;
import fi.hut.soberit.agilefant.model.Backlog;
import fi.hut.soberit.agilefant.model.Iteration;
import fi.hut.soberit.agilefant.model.StoryItem;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component("storyImportExportAction")
public class ImportExportStoryAction {

    private String errorStacktrace;

    private File fileUpload;
    
    private int id;
    
    private ByteArrayOutputStream xmlStream;
    
    @Autowired
    private MMImportExportBusiness mmImportExportBusiness;
    @Autowired
    private BacklogDAO backlogDAO;
    @Autowired
    private IterationDAO iterationDAO;

    public File getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(File fileUpload) {
        this.fileUpload = fileUpload;
    }

    public String storyImport() {
        try {
            if (fileUpload == null) {
                return Action.NONE;
            }
            FileInputStream inputStream = new FileInputStream(fileUpload);
            mmImportExportBusiness.importStory(inputStream);
            return Action.SUCCESS;
        } catch (Exception e) {
            if (e.getCause() instanceof ExportImport.VersionMismatchException) {
                errorStacktrace = e.getMessage();
            } else {
                errorStacktrace = Throwables.getStackTraceAsString(e);
            }
            return Action.ERROR;
        }
    }
    
    public String storyExport() {
        try {
            if (id == 0) {
                return Action.NONE;
            }
            Backlog backlog = backlogDAO.get(id);
            xmlStream = new ByteArrayOutputStream();
            String xmlList = mmImportExportBusiness.exportStory(backlog);
            String header = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n";
            xmlStream.write(header.getBytes());
            xmlStream.write(xmlList.getBytes());
            return Action.SUCCESS;
        } catch (Exception e) {
        	errorStacktrace = Throwables.getStackTraceAsString(e);
            return Action.ERROR;
        }
    }
    
    public String storyExportIteration() {
        try {
            if (id == 0) {
                return Action.NONE;
            }
            Iteration iteration = iterationDAO.get(id);
            xmlStream = new ByteArrayOutputStream();
            String xmlList = mmImportExportBusiness.exportStory(iteration);
            String header = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n";
            xmlStream.write(header.getBytes());
            xmlStream.write(xmlList.getBytes());
            return Action.SUCCESS;
        } catch (Exception e) {
        	errorStacktrace = Throwables.getStackTraceAsString(e);
            return Action.ERROR;
        }
    }

    /**
     * @return the xmlStream
     */
    public InputStream getXmlStream() {
        return new ByteArrayInputStream(this.xmlStream.toByteArray());
    }

    /**
     * @param xmlStream the xmlStream to set
     */
    public void setXmlStream(ByteArrayOutputStream xmlStream) {
        this.xmlStream = xmlStream;
    }

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

}