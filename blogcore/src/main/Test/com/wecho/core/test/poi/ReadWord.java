package com.wecho.core.test.poi;

import org.apache.pdfbox.cos.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceCharacteristicsDictionary;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.apache.pdfbox.text.TextPosition;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.poifs.filesystem.FileMagic;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.*;
import org.docx4j.Docx4J;
import org.docx4j.fonts.IdentityPlusMapper;
import org.docx4j.fonts.Mapper;
import org.docx4j.fonts.PhysicalFonts;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.junit.Test;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReadWord {

    static String read(InputStream is) throws Exception {

        System.out.println(FileMagic.valueOf(is));
        String text = "";

        if (FileMagic.valueOf(is) == FileMagic.OLE2) {
            WordExtractor ex = new WordExtractor(is);
            text = ex.getText();
            ex.close();
        } else if (FileMagic.valueOf(is) == FileMagic.OOXML) {
            XWPFDocument doc = new XWPFDocument(is);
            XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
            text = extractor.getText();
            extractor.close();
        }
        return text;

    }

    @Test
    public void readLocalWord() throws IOException {
        InputStream inputStream = new BufferedInputStream(new FileInputStream("D:\\tools\\123.docx"));
        XWPFDocument doc = new XWPFDocument(inputStream);
        XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
        String range = extractor.getText();
        String text = new String(range.getBytes());
        //得到整个文档里面的所有纯文字，包含回车换行。一段是一行
        //System.out.println(text.replaceAll("\n",""));
        String[] s = getKeyWords(text.replaceAll("\n", ""));

        Iterator<XWPFParagraph> itPara = doc.getParagraphsIterator();
        while (itPara.hasNext()) {
            XWPFParagraph paragraph = itPara.next();
            String paragraphText = paragraph.getText();

            if (paragraphText.indexOf("${") == -1 || paragraphText.indexOf("}$") == -1) {
                continue;
            }
            int size = paragraph.getRuns().size();
            for (int i = size - 1; i >= 0; i--) {
                paragraph.removeRun(i);
            }
            System.out.println(paragraph.getRuns().size());

            System.out.println("\n");
            //遍历excel数据
            for (String key : s) {
                TextSegement found = paragraph.searchText(key, new PositionInParagraph());
                System.out.println("length" + paragraph.getCTP().getRArray().length);
                if (found == null) {
                    //System.out.println(found.getBeginRun()+"null"+found.getBeginRun());
                } else {
                    System.out.println(found.getBeginRun() + "not null" + found.getBeginRun());
                }
                if (paragraphText.indexOf("${" + key + "}$") != -1) {
                    //int size= paragraph.getRuns().size();

                    String temptext = paragraphText.replace("${" + key + "}$", "测试文本");
                    // Add new run with updated text
                    XWPFRun run = paragraph.createRun();
                    run.setText(temptext);
                    paragraph.addRun(run);
                    break;
                }

                /*List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs) {
                    String runText = run.getText(0);
                    if(runText!=null){
                        if(runText.startsWith("${")){

                        }
                        boolean isSetText = false;
                        if (runText.indexOf("${"+key+"}$") != -1) {
                            isSetText = true;
                            runText = runText.replace(runText,key+"123");
                        }
                        if (isSetText) {
                            //参数0表示生成的文字是要从哪一个地方开始放置,设置文字从位置0开始,就可以把原来的文字全部替换掉了
                            run.setText(runText, 0);
                        }
                    }
                }*/

            }
        }

        OutputStream outputStream = new FileOutputStream("D:\\tools\\test.docx");
        doc.write(outputStream);
        /*for (String s1 : s) {
            System.out.println(s1);
        }*/
    }

    private String[] getKeyWords(String text) {
        String tempStr = text;
        String[] temp = new String[4];
        int index = 0;
        int startIndex = text.indexOf("${");
        int endIndex = text.indexOf("}$");
        while (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
            String subStr = tempStr.substring(startIndex + 2, endIndex);
            //System.out.println(index);
            temp[index++] = subStr;
            tempStr = tempStr.substring(endIndex + 1);
            startIndex = tempStr.indexOf("${");
            endIndex = tempStr.indexOf("}$");
        }
        return temp;
    }

    @Test
    public void testXWPFWriter() throws IOException {
        File file = new File("D:\\tools\\123.docx");
        DocumentTemplateWriter documentTemplateWriter = DocumentTemplateWriterFactory.createDocumentTemplateWriter(new BufferedInputStream(new FileInputStream(file)));
        List<String> keys = new ArrayList<>();
        keys.add("甲方");
        keys.add("乙方");
        keys.add("乙方");
        documentTemplateWriter.replaceFirstPlaceHolder(keys, "测试");
        documentTemplateWriter.saveDocument();
    }

    /**
     * 测试将docx文件转换为PDF
     */
    @Test
    public void testWordToPdf() {
        String inputWordPath = "D:\\tools\\123.docx";
        String outputPDFPath = "D:\\tools\\1235.pdf";
        try {
            System.err.println("Word Document to PDF Convert Begins!");
            InputStream is = new FileInputStream(new File(inputWordPath));
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(is);
            List sections = wordMLPackage.getDocumentModel().getSections();
            for (int i = 0; i < sections.size(); i++) {
                //sections.
                wordMLPackage.getDocumentModel().getSections().get(i).getPageDimensions().setHeaderExtent(3000);
            }
            //Mapper fontMapper = new IdentityPlusMapper();
            // For font specific, enable the below lines
            // PhysicalFont font = PhysicalFonts.getPhysicalFonts().get("Comic
            // Sans MS");
            // fontMapper.getFontMappings().put("Algerian", font);
            Mapper fontMapper = new IdentityPlusMapper();

            fontMapper.getFontMappings().put("隶书", PhysicalFonts.getPhysicalFonts().get("LiSu"));
            fontMapper.getFontMappings().put("宋体", PhysicalFonts.getPhysicalFonts().get("SimSun"));
            fontMapper.getFontMappings().put("微软雅黑", PhysicalFonts.getPhysicalFonts().get("Microsoft Yahei"));
            fontMapper.getFontMappings().put("黑体", PhysicalFonts.getPhysicalFonts().get("SimHei"));
            fontMapper.getFontMappings().put("楷体", PhysicalFonts.getPhysicalFonts().get("KaiTi"));
            fontMapper.getFontMappings().put("新宋体", PhysicalFonts.getPhysicalFonts().get("NSimSun"));
            fontMapper.getFontMappings().put("华文行楷", PhysicalFonts.getPhysicalFonts().get("STXingkai"));
            fontMapper.getFontMappings().put("华文仿宋", PhysicalFonts.getPhysicalFonts().get("STFangsong"));
            fontMapper.getFontMappings().put("宋体扩展", PhysicalFonts.getPhysicalFonts().get("simsun-extB"));
            fontMapper.getFontMappings().put("仿宋", PhysicalFonts.getPhysicalFonts().get("FangSong"));
            fontMapper.getFontMappings().put("仿宋_GB2312", PhysicalFonts.getPhysicalFonts().get("FangSong_GB2312"));
            fontMapper.getFontMappings().put("幼圆", PhysicalFonts.getPhysicalFonts().get("YouYuan"));
            fontMapper.getFontMappings().put("华文宋体", PhysicalFonts.getPhysicalFonts().get("STSong"));
            fontMapper.getFontMappings().put("华文中宋", PhysicalFonts.getPhysicalFonts().get("STZhongsong"));
            wordMLPackage.setFontMapper(fontMapper);
            Docx4J.toPDF(wordMLPackage, new FileOutputStream(outputPDFPath));
            System.err.println("Your Word Document Converted to PDF Successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddFormToPdf() {
        // the document
        File file = new File("D:\\tools\\123.pdf");

        try (PDDocument doc = PDDocument.load(file)) {
            int pages = doc.getNumberOfPages();
            for (int i = 0; i < pages; i++) {
                PDPage pdPage = doc.getPage(i);
                // 获取内容信息
                PrintTextLocations pts = new PrintTextLocations();
                pts.setSortByPosition(true);
                pts.setStartPage(i+1);
                pts.setEndPage(i+1);

                Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
                pts.writeText(doc, dummy);
                dummy.close();
                List<TextPosition> textPositions = (pts).getTextPositionList();

                //遍历本页需要替换的特殊字符进行替换
                if (textPositions == null) {
                    continue;
                }
                for (TextPosition textPosition : textPositions) {
                    float x=textPosition.getX();
                    float y=textPosition.getEndY()-textPosition.getHeight()/2.0f;
                    float h=textPosition.getHeight();
                    float w=textPosition.getWidth();
                    System.out.println(x+y+h+w);
                    addSimpleForm(pdPage, doc,x,y,h,w);
                }
                System.out.println((pts).getTextPositionList().size());
                pts.clearPrintTextLocationList();
                System.out.println((pts).getTextPositionList().size());
            }
            doc.save("D:\\tools\\123test.pdf");

            //doc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addSimpleForm(PDPage pdPage, PDDocument document,float x,float y,float height,float width) throws IOException {
        // Adobe Acrobat uses Helvetica as a default font and
        // stores that under the name '/Helv' in the resources dictionary
        /*在添加表单之前*/
        PDFont font = PDType1Font.HELVETICA;
        PDResources resources = new PDResources();
        resources.put(COSName.getPDFName("Helv"), font);

        // Add a new AcroForm and add that to the document
        COSDictionary form = new COSDictionary();

        PDAcroForm acroForm = new PDAcroForm(document);
        document.getDocumentCatalog().setAcroForm(acroForm);

        // Add and set the resources and default appearance at the form level
        acroForm.setDefaultResources(resources);

        //COSArray border = (COSArray) acroForm.getCOSObject().getDictionaryObject(COSName.BORDER).;
        COSArray border = new COSArray();

        border.add(COSInteger.ONE);
        border.add(COSInteger.ONE);
        border.add(COSInteger.ONE);
        border.add(COSInteger.ONE);

        acroForm.getCOSObject().setItem(COSName.BORDER,border);
        // Acrobat sets the font size on the form level to be
        // auto sized as default. This is done by setting the font size to '0'
        String defaultAppearanceString = "/Helv 1 Tf 1 g";
        acroForm.setDefaultAppearance(defaultAppearanceString);

        // Add a form field to the form.
        PDTextField textBox = new PDTextField(acroForm);
        textBox.setPartialName("中文测试");

        // Acrobat sets the font size to 12 as default
        // This is done by setting the font size to '12' on the
        // field level.
        // The text color is set to blue in this example.
        // To use black, replace "0 0 1 rg" with "0 0 0 rg" or "0 g".
        defaultAppearanceString = "/Helv 12 Tf 0 0 1 rg";
        textBox.setDefaultAppearance(defaultAppearanceString);

        // add the field to the acroform
        acroForm.getFields().add(textBox);

        // Specify the widget annotation associated with the field
        PDAnnotationWidget widget = textBox.getWidgets().get(0);
        //PDRectangle rect = new PDRectangle(x, y, width*6, height);
        PDRectangle rect = new PDRectangle(x, y, width*6, height*2);

        widget.setRectangle(rect);
        widget.setPage(pdPage);

        // set green border and yellow background
        // if you prefer defaults, just delete this code block
        PDAppearanceCharacteristicsDictionary fieldAppearance
                = new PDAppearanceCharacteristicsDictionary(new COSDictionary());
        fieldAppearance.setBorderColour(new PDColor(new float[]{0, 0, 0}, PDDeviceRGB.INSTANCE));
        fieldAppearance.setBackground(new PDColor(new float[]{255, 255, 255}, PDDeviceRGB.INSTANCE));
        widget.setAppearanceCharacteristics(fieldAppearance);

        // make sure the widget annotation is visible on screen and paper
        widget.setPrinted(true);

        // Add the widget annotation to the page
        pdPage.getAnnotations().add(widget);

        // set the field value
        textBox.setValue("Sample field");

        //document.save("target/SimpleForm.pdf");

        PDPageContentStream contentStream = new PDPageContentStream(document, pdPage, PDPageContentStream.AppendMode.APPEND, true, false);
        contentStream.setNonStrokingColor(Color.white);
        contentStream.addRect(x, y, width*6, height*2);
        contentStream.fill();
        contentStream.close();
    }

}