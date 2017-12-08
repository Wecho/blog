package com.wecho.core.test.poi;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

/**
 * 解析docx格式文件
 */
public class XWPFDocumentTemplateWriter implements DocumentTemplateWriter {

    private XWPFDocument document;

    public XWPFDocumentTemplateWriter(InputStream ins) throws IOException {
        document = new XWPFDocument(ins);
    }

    @Override
    public void replaceFirstPlaceHolder(List<String> keys,String  value) throws IOException {
        Iterator<XWPFParagraph> paragraphsIterator = document.getParagraphsIterator();
        //遍历所有段落
        while (paragraphsIterator.hasNext()) {
            XWPFParagraph xwpfParagraph = paragraphsIterator.next();
            String paragraphText = xwpfParagraph.getParagraphText();
            //遍历需要替换的
            for (String key : keys) {
                if (containPlaceHolder(paragraphText, key)) {//进行替换
                    //定位
                    List<XWPFRun> xwpfRuns = xwpfParagraph.getRuns();
                    for(int i =0;i<xwpfRuns.size();i++){
                        XWPFRun Prefix_Run = xwpfRuns.get(i);
                        String text = Prefix_Run.text();
                        if(text.equals(PLACEHOLDER_PREFIX)){

                            if(xwpfRuns.get(i+1).text().equals(key) && xwpfRuns.get(i+2).text().equals(PLACEHOLDER_SUFFIX) ){
                                Prefix_Run.setText("",0);
                                xwpfRuns.get(i+1).setText(value,0);
                                xwpfRuns.get(i+2).setText("",0);
                            }
                        }
                    }

                }
            }

        }
    }


    @Override
    public boolean replaceAllPlaceHolder(String key, String value) {
        return false;
    }

    @Override
    public boolean saveDocument() throws IOException {
        OutputStream outputStream = new FileOutputStream("D:\\tools\\test1.docx");
        document.write(outputStream);
        return true;
    }

    /**
     * 判断文本段落是否包含特殊字符
     *
     * @param paragraphText
     * @param key
     * @return
     */
    private boolean containPlaceHolder(String paragraphText, String key) {
        if (paragraphText.indexOf(key) != -1) {
            return true;
        } else {
            return false;
        }
    }
}