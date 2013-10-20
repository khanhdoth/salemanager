package com.hatde.salemanager.web;

import com.hatde.salemanager.entities.PaymentTransaction;
import com.hatde.salemanager.entities.Sale;
import com.hatde.salemanager.entities.SaleItem;
import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import org.docx4j.XmlUtils;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.ContentAccessor;
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
            // create hastable for single variables to fill
            HashMap<String, String> variableFill = new HashMap<>();
            variableFill.put("=SID", Integer.toString(sale.getProductTransactionId()));
            variableFill.put("=DATE", customFormatDate(sale.getDate()));
            variableFill.put("=Contact Name", sale.getContact().getName());
            variableFill.put("=Subtotal", customFormatNumber(amountFormat, sale.getSubTotal()));
            variableFill.put("=ADiscountrate", customFormatNumber(percentFormat, sale.getDiscount() / 100));
            variableFill.put("=ADiscount", customFormatNumber(amountFormat, sale.getAmountDiscount()));
            variableFill.put("=TAXRATE", customFormatNumber(percentFormat, sale.getVAT() / 100));
            variableFill.put("=Tax", customFormatNumber(amountFormat, sale.getAmountVAT()));
            variableFill.put("=Total", customFormatNumber(amountFormat, sale.getAmount()));
            variableFill.put("=Paid", (sale.getPayment() instanceof PaymentTransaction) ? ("-" + customFormatNumber(amountFormat, sale.getPayment().getAmount())) : "");
            variableFill.put("=BalanceDue", customFormatNumber(amountFormat, sale.getAmountAfterPayment()));
            variableFill.put("", "");

            //open the docx template file
            System.out.println("modifyDoc");
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new File(bundleBean.getBundle().getString("PathSalesOrderTemplate")));
            System.out.println("load ok!");
            MainDocumentPart mdp = wordMLPackage.getMainDocumentPart();
            String xpath = "";
            List<Object> list;

            //Get table
            xpath = "//w:tbl";
            list = mdp.getJAXBNodesViaXPath(xpath, false);
            int tableIndex = Integer.parseInt(bundleBean.getBundle().getString("SalesOrderTableIndex"));
            Tbl dataTable = (Tbl) ((JAXBElement) list.get(tableIndex)).getValue();
            List rows = dataTable.getContent();
            Tr row1 = (Tr) rows.get(1);

            //insert additional rows in form table if the ListOfSaleItems is larger than the table form rows
            int rowNumber = Integer.parseInt(bundleBean.getBundle().getString("SalesOrderRows"));
            int rowNumnerToFill = sale.getListOfSaleItem().size();
            if (rowNumber < rowNumnerToFill) {
                for (int i = 0; i < rowNumnerToFill - rowNumber; i++) {
                    Tr tableRow = (Tr) XmlUtils.deepCopy(row1);
                    rows.add(1, tableRow);
                }
            }

            /*
             * fill table data
             */
            int rowIndex = 1;
            for (SaleItem si : sale.getListOfSaleItem()) {
                Tr cRow = (Tr) rows.get(rowIndex);
                List cols = cRow.getContent();

                HashMap<String, String> tableFill = new HashMap<>();
                tableFill.put("Quantity", customFormatNumber(quantityFormat, si.getQuantity()));
                tableFill.put("ItemName", si.getProduct().getName());
                tableFill.put("Price", customFormatNumber(amountFormat, si.getPrice()));
                tableFill.put("Discount", customFormatNumber(percentFormat, si.getDiscount() / 100));
                tableFill.put("LineTotal", customFormatNumber(amountFormat, si.getAmount()));
                
                fillRowContent(wordMLPackage, cols, tableFill);
                rowIndex++;
            }
            ((JAXBElement) list.get(tableIndex)).setValue(dataTable);

            /*
             * replace single variables
             */
            xpath = "//w:t[contains(text(),'=')]";
            list = mdp.getJAXBNodesViaXPath(xpath, false);

            for (Object e : list) {
                Text t = (Text) ((JAXBElement) e).getValue();
                String targetString = variableFill.get(t.getValue());
                t.setValue(targetString != null ? targetString : "---");
            }

            //Save to temporary file
            String outFileName = "temp" + (new Random()).nextInt(100000000) + ".docx";
            System.out.println("*************** outFile=" + outFileName);
            File outFile = new File(outFileName);
            wordMLPackage.save(outFile);

            //Prepare file to download
            InputStream stream = new FileInputStream(outFile);
            stockOutFile = new DefaultStreamedContent(stream, "application/docx", bundleBean.getBundle().getString("SalesOrderDownloadName") + sale.getProductTransactionId() + ".docx");
            outFile.deleteOnExit();

            /*PdfConversion c = new Conversion(wordMLPackage);
             OutputStream out = new FileOutputStream(bundleBean.getBundle().getString("PathSalesOrderResult") + ".pdf");
             c.output(out, new PdfSettings() );*/
        } catch (Exception ex) {
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

    public void fillCellContent(WordprocessingMLPackage wordMLPackage, List cols, int colIndex, String content) {
        Tc col = (Tc) ((JAXBElement) cols.get(colIndex)).getValue();
        Tc colnew = (Tc) XmlUtils.deepCopy(col);
        colnew.getContent().add(wordMLPackage.getMainDocumentPart().createParagraphOfText(content));
        ((JAXBElement) cols.get(colIndex)).setValue(colnew);
    }

    public void fillRowContent(WordprocessingMLPackage wordMLPackage, List cols, HashMap<String, String> tableFill) {
        for (Object cell : cols) {
            List<Text> texts = getAllElementFromObject(cell, Text.class);
            for (Text cellText : texts) {                
                String targetString = tableFill.get(cellText.getValue());
                cellText.setValue(targetString != null ? targetString : "---");
            }            
        }
    }

    public <T> List<T> getAllElementFromObject(Object obj, Class<T> toSearch) {
        List<T> result = new ArrayList<>();
        if (obj instanceof JAXBElement) {
            obj = ((JAXBElement<?>) obj).getValue();
        }

        if (obj.getClass().equals(toSearch)) {
            result.add((T) obj);
        } else if (obj instanceof ContentAccessor) {
            List children = ((ContentAccessor) obj).getContent();
            for (Object child : children) {
                result.addAll(getAllElementFromObject(child, toSearch));
            }

        }
        return result;
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
        return stockOutFile;
    }
}
