package com.hatde.salemanager.web;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Text;

/**
 *
 * @author Khanh
 */
@Named(value = "reportBean")
@SessionScoped
public class ReportBean implements Serializable {
    @Inject
    private BundleBean bundleBean;    

    public ReportBean() {
        
    }

    public void createStockOutForm() {
        try {
            System.out.println("modifyDoc");
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new File(bundleBean.getBundle().getString("PathSalesOrderTemplate")));
            System.out.println("load ok!");
            MainDocumentPart mdp = wordMLPackage.getMainDocumentPart();

            String xpath = "//w:t[contains(text(),'[')]";
            List<Object> list = mdp.getJAXBNodesViaXPath(xpath, false);
            for (Object e : list) {
                Text t = (Text) ((JAXBElement) e).getValue();
                t.setValue("Hello Khanh Do");
            }
            wordMLPackage.save(new java.io.File(bundleBean.getBundle().getString("PathSalesOrderResult") + ".docx"));
            
            /*PdfConversion c = new Conversion(wordMLPackage);
            OutputStream out = new FileOutputStream(bundleBean.getBundle().getString("PathSalesOrderResult") + ".pdf");
            c.output(out, new PdfSettings() );*/
            

        } catch (InvalidFormatException ex) {
            Logger.getLogger(ReportBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Docx4JException ex) {
            Logger.getLogger(ReportBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JAXBException ex) {
            Logger.getLogger(ReportBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
