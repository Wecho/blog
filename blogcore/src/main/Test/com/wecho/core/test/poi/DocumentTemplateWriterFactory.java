package com.wecho.core.test.poi;

import org.apache.poi.poifs.filesystem.FileMagic;

import java.io.IOException;
import java.io.InputStream;

public class DocumentTemplateWriterFactory {

    /**
     * 根据文件头标记返回具体的操作类
     * @return
     */
    public static DocumentTemplateWriter createDocumentTemplateWriter(InputStream ins) throws IOException {
        if (FileMagic.valueOf(ins) == FileMagic.OLE2) {//文件为doc格式
            return new HWPFDocumentTemplateWriter(ins);
        } else if (FileMagic.valueOf(ins) == FileMagic.OOXML) {//文件为docx格式
            return new XWPFDocumentTemplateWriter(ins);
        }
        return null;
    }
}