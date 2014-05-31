package fi.hut.soberit.agilefant.web;

import com.google.common.base.Throwables;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Workbook;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.Action;
import static com.opensymphony.xwork2.Action.NONE;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.typesafe.config.Config;
import fi.hut.soberit.agilefant.business.ExportImportBusiness;

import fi.hut.soberit.agilefant.business.ExportIterationBusiness;
import fi.hut.soberit.agilefant.business.IterationBusiness;
import fi.hut.soberit.agilefant.business.MMImportBusiness;
import fi.hut.soberit.agilefant.business.ProductBusiness;
import fi.hut.soberit.agilefant.business.UserBusiness;
import fi.hut.soberit.agilefant.exportimport.ExportImport;
import fi.hut.soberit.agilefant.exportimport.XmlBackupper;
import java.io.File;
import java.io.FileInputStream;
import java.util.zip.ZipInputStream;

@Scope("prototype")
@Component("storyImportAction")
public class ImportStoryAction {

    private String errorStacktrace;

    private File fileUpload;
    
    @Autowired
    private MMImportBusiness mmImportBusiness;
    
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
            mmImportBusiness.importStory(inputStream);
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

}