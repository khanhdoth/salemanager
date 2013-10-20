package com.hatde.salemanager.web;

import com.hatde.salemanager.entities.PaymentTransaction;
import com.hatde.salemanager.entities.Sale;
import com.hatde.salemanager.entities.SaleItem;
import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.docx4j.wml.P;
import org.docx4j.wml.R;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.Tc;
import org.docx4j.wml.Text;
import org.docx4j.wml.Tr;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Khanh
 */
@Named(value = "reportBean")
@SessionScoped
public class ReportBean implements Serializable {

    @Inject
    private BundleBean bundleBean;

    @Inject
    private StockOutController stockOutController;

    private String amountFormat = "#,##0";
    private StreamedContent stockOutFile;

    public void init() {
        try {
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new File(bundleBean.getBundle().getString("PathSalesOrderTemplate")));
        } catch (Docx4JException ex) {
            Logger.getLogger(ReportBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ReportBean() {       
    }

    public void createStockOutForm(Sale sale) {
        try {
            System.out.println("modifyDoc");
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new File(bundleBean.getBundle().getString("PathSalesOrderTemplate")));
            System.out.println("load ok!");
            MainDocumentPart mdp = wordMLPackage.getMainDocumentPart();

            String xpath = "//w:t[contains(text(),'=')]";
            List<Object> list = mdp.getJAXBNodesViaXPath(xpath, false);
            for (Object e : list) {
                Text t = (Text) ((JAXBElement) e).getValue();

                String replacedText = "";
                switch (t.getValue()) {
                    case "=SID":
                        replacedText = sale.getProductTransactionId() + "";
                        break;

                    case "=DATE":
                        replacedText = customFormatDate(sale.getDate());
                        break;

                    case "=Contact Name":
                        replacedText = sale.getContact().getName();
                        break;

                    case "=Subtotal":
                        replacedText = customFormatNumber(amountFormat, sale.getSubTotal());
                        break;

                    case "=Tax":
                        replacedText = customFormatNumber(amountFormat, sale.getAmountVAT());
                        break;

                    case "=Total":
                        replacedText = customFormatNumber(amountFormat, sale.getAmount());
                        break;

                    case "=Paid":
                        if (sale.getPayment() instanceof PaymentTransaction) {
                            replacedText = "-" + customFormatNumber(amountFormat, sale.getPayment().getAmount());
                        }
                        break;

                    case "=BalanceDue":
                        replacedText = customFormatNumber(amountFormat, sale.getAmountAfterPayment());
                        break;

                    default:
                        replacedText = "----";
                }

                t.setValue(replacedText);
            }
            
            //Get table
            xpath = "//w:tbl";
            list = mdp.getJAXBNodesViaXPath(xpath, false);
            Tbl dataTable = (Tbl) ((JAXBElement) list.get(3)).getValue();
            
            List rows = dataTable.getContent();
            int rowIndex = 1;
            for(SaleItem si: sale.getListOfSaleItem()){
                Tr cRow = (Tr) rows.get(rowIndex);                
                List cols = cRow.getContent();
                Tc col0 = (Tc) ((JAXBElement)cols.get(0)).getValue();
                Text col0Text = (Text)((JAXBElement) ((R)(((P) col0.getContent().get(0)).getContent().get(0))).getContent().get(0)).getValue();
                col0Text.setValue(si.getQuantity()+"");
                rowIndex++;
            }
            
            
            String outFile = bundleBean.getBundle().getString("PathSalesOrderResult") + ".docx";
            wordMLPackage.save(new java.io.File(outFile));

            //InputStream stream = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream(outFile);
            InputStream stream = new FileInputStream(outFile);
            stockOutFile = new DefaultStreamedContent(stream, "application/docx", "stockOut_" + sale.getProductTransactionId() + ".docx");

            /*PdfConversion c = new Conversion(wordMLPackage);
             OutputStream out = new FileOutputStream(bundleBean.getBundle().getString("PathSalesOrderResult") + ".pdf");
             c.output(out, new PdfSettings() );*/
        } catch (InvalidFormatException ex) {
            Logger.getLogger(ReportBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Docx4JException ex) {
            Logger.getLogger(ReportBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JAXBException ex) {
            Logger.getLogger(ReportBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReportBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String customFormatNumber(String pattern, double value) {
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        return myFormatter.format(value);
    }

    public String customFormatDate(Date date) {
        return new SimpleDateFormat("dd.MM.yyyy").format(date);
    }

    public StreamedContent getStockOutFile() {
        createStockOutForm(stockOutController.getSelectedT());
        //InputStream stream = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream("/images/loading.gif");
        //stockOutFile = new DefaultStreamedContent(stream, "image/gif", "loading.gif");

        return stockOutFile;
    }
}
