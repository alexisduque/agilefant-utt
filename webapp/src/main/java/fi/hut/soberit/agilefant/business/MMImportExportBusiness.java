/*.

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.hut.soberit.agilefant.business;

import fi.hut.soberit.agilefant.model.Backlog;
import fi.hut.soberit.agilefant.model.Iteration;
import fi.hut.soberit.agilefant.model.StoryItem;
import java.io.FileInputStream;

/**
 *
 * @author alex
 */

public interface MMImportExportBusiness {
    
    public void importStory(FileInputStream xml);
    public String exportStory(Iteration iterationId);
    public String exportStory(Backlog iterationId);
    public int parseItem(String item);
}
