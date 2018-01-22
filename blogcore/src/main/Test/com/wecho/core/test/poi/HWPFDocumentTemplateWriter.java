package com.wecho.core.test.poi;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
/**
 * 解析doc格式文件
 */
public class HWPFDocumentTemplateWriter implements DocumentTemplateWriter {

    private HWPFDocument document;

    public HWPFDocumentTemplateWriter(InputStream ins) throws IOException {
        document = new HWPFDocument(ins);
    }

    @Override
    public void replaceFirstPlaceHolder(List<String> key, String value) {
        Range range = document.getRange();
        int paragraphNum = range.numParagraphs();
        for (int i = 0; i < paragraphNum; i++) {
            Paragraph paragraph = range.getParagraph(i);
            for (String s : key) {
                //paragraph
                paragraph.replaceText(PLACEHOLDER_PREFIX+s+PLACEHOLDER_SUFFIX,"ceshi123");
            }
        }

        System.out.println(range.text());
    }

    @Override
    public boolean replaceAllPlaceHolder(String key, String value) {
        return false;
    }

    @Override
    public boolean saveDocument() throws IOException {
        OutputStream outputStream = new FileOutputStream("D:\\tools\\test2.doc");
        document.write(outputStream);
        outputStream.close();
        return true;
    }
}