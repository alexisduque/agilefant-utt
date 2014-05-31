/*.

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.hut.soberit.agilefant.business;

import java.io.FileInputStream;

/**
 *
 * @author alex
 */

public interface MMImportBusiness {
    
    public void importStory(FileInputStream xml);
}
