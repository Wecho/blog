package com.wecho.core.test.poi;

import java.io.IOException;
import java.util.List;

/**
 * 根据数据替换占位符,生成文档模板
 */
public interface DocumentTemplateWriter {

    final String PLACEHOLDER_PREFIX = "${";

    final String PLACEHOLDER_SUFFIX = "}$";

    /**
     * 替换文档中第一个匹配的占位符
     */
    void replaceFirstPlaceHolder(List<String> keys, String value) throws IOException;

    /**
     * 替换文档中所有匹配的占位符
     */
    boolean replaceAllPlaceHolder(String key, String value);

    boolean saveDocument() throws IOException;
}