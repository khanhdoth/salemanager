package com.hatde.salemanager.reports;

import com.hatde.salemanager.web.ReportBean;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;
import org.docx4j.XmlUtils;
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
 * @author Do
 */
public abstract class AbstractReport<V, T> {

    protected String amountFormat = "#,##0";
    protected String quantityFormat = "#,##0.##";
    protected String percentFormat = "#.##%";

    private StreamedContent file;
    protected ObjectFactory factory;

    protected String PathSalesOrderTemplate;
    protected String SalesOrderTableIndex;
    protected String SalesOrderRows;
    protected String SalesOrderDownloadName;
    protected int id;

    public abstract HashMap<String, String> getVariableMap(V entity);

    public abstract HashMap<String, String> getTableMap(T entityDetail);

    public abstract V getEntity();

    public abstract Collection<T> getDetails();

    public abstract void init();

    public AbstractReport() {
    }

    public void createForm(V entity, Collection<T> details) {
        try {
            // create hastable for single variables to fill
            HashMap<String, String> variableFill = getVariableMap(entity);

            //open the docx template file            
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new File(PathSalesOrderTemplate));
            MainDocumentPart mdp = wordMLPackage.getMainDocumentPart();
            String xpath = "";
            List<Object> list;

            //Get table
            xpath = "//w:tbl";
            list = mdp.getJAXBNodesViaXPath(xpath, false);
            int tableIndex = Integer.parseInt(SalesOrderTableIndex);
            Tbl dataTable = (Tbl) ((JAXBElement) list.get(tableIndex)).getValue();
            List rows = dataTable.getContent();
            Tr row1 = (Tr) rows.get(1);

            //insert additional rows in form table if the detail list is larger than the table form rows
            int rowNumber = Integer.parseInt(SalesOrderRows);
            int rowNumnerToFill = details.size();
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
            for (T si : details) {
                Tr cRow = (Tr) rows.get(rowIndex);
                List cols = cRow.getContent();

                HashMap<String, String> tableFill = getTableMap(si);

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
            file = new DefaultStreamedContent(stream, "application/docx", SalesOrderDownloadName + id + ".docx");
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

    public StreamedContent getFile() {
        createForm(getEntity(), getDetails());
        return file;
    }
}
