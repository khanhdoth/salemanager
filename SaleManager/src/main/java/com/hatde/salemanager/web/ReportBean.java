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
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
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
    private String quantityFormat = "#,##0.##";
    private String percentFormat = "#.##%";
    private StreamedContent stockOutFile;
    private ObjectFactory factory;    

    public void init() {
        try {
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new File(bundleBean.getBundle().getString("PathSalesOrderTemplate")));
            factory = Context.getWmlObjectFactory();
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
            for (SaleItem si : sale.getListOfSaleItem()) {
                Tr cRow = (Tr) rows.get(rowIndex);
                List cols = cRow.getContent();
                
                Tc col0 = (Tc) ((JAXBElement) cols.get(0)).getValue();                
                Tc col0new = createCellContent(wordMLPackage, col0, customFormatNumber(quantityFormat, si.getQuantity()));
                ((JAXBElement) cols.get(0)).setValue(col0new);                
                
                Tc col1 = (Tc) ((JAXBElement) cols.get(1)).getValue();
                Tc col1new = createCellContent(wordMLPackage, col1, si.getProduct().getName());
                ((JAXBElement) cols.get(1)).setValue(col1new);                

                Tc col3 = (Tc) ((JAXBElement) cols.get(3)).getValue();
                Tc col3new = createCellContent(wordMLPackage, col3, customFormatNumber(amountFormat, si.getPrice()));
                ((JAXBElement) cols.get(3)).setValue(col3new);                

                Tc col4 = (Tc) ((JAXBElement) cols.get(4)).getValue();
                Tc col4new = createCellContent(wordMLPackage, col4, customFormatNumber(percentFormat, si.getDiscount()/100));
                ((JAXBElement) cols.get(4)).setValue(col4new);                

                Tc col5 = (Tc) ((JAXBElement) cols.get(5)).getValue();
                Tc col5new = createCellContent(wordMLPackage, col5, customFormatNumber(amountFormat, si.getAmount()));
                ((JAXBElement) cols.get(5)).setValue(col5new);                
                
                rowIndex++;
            }

            String outFile = bundleBean.getBundle().getString("PathSalesOrderResult") + ".docx";
            wordMLPackage.save(new java.io.File(outFile));

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

    public Tc createCellContent(WordprocessingMLPackage wordMLPackage, Tc colToReplace, String content) {
        Tc tableCell = factory.createTc();
        tableCell.getContent().add(
                wordMLPackage.getMainDocumentPart().createParagraphOfText(content));
        tableCell.setTcPr(colToReplace.getTcPr());   
        ((P) tableCell.getContent().get(0)).setPPr(((P) colToReplace.getContent().get(0)).getPPr());
        return tableCell;
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
