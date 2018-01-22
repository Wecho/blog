package com.wecho.core.test.poi;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrintTextLocations extends PDFTextStripper {

    private final String PLACEHOLDER = "#$#";

    private List<TextPosition> textPositionList = new ArrayList<>();

    private PDPage pdPage;

    private PDDocument pdDocument;

    public PrintTextLocations() throws IOException {
    }

    public List<TextPosition> getTextPositionList() {
        return textPositionList;
    }

    public void setPdPage(PDPage pdPage) {
        this.pdPage = pdPage;
    }

    public void setPdDocument(PDDocument pdDocument) {
        this.pdDocument = pdDocument;
    }

    /**
     * Override the default functionality of PDFTextStripper.
     *
     */
    @Override
    protected void writeString(String string, List<TextPosition> textPositions) throws IOException {
        int index = 0;
        for (; index < textPositions.size(); index++) {
            System.out.println(textPositions.get(index).getY());
            //可能匹配
            if (textPositions.get(index).getUnicode().equals("#")) {
                //查看后面是否匹配 ToDo:可能存在匹配字符出现在下一页的情况
                if (index + 2 < textPositions.size()) {
                    if ((textPositions.get(index+1).getUnicode() + textPositions.get(index+2).getUnicode()).equals("$#")) {
                        //TextPosition
                        textPositionList.add(textPositions.get(index));
                        //TextPosition textPosition = textPositions.get(index);
                   /*     float x=textPosition.getX();
                        float y=textPosition.getY();
                        float h=textPosition.getHeight();
                        float w=textPosition.getWidth();
                        addSimpleForm(pdPage, pdDocument,x,y,h,w);*/
                    }
                    index = index + 3;
                }
            }
        }
    }

    /**
     * 清除list
     */
    public void clearPrintTextLocationList(){
        textPositionList.clear();
    }

}