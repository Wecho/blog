package com.wecho.core.servlet.filter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;

public class MyResponseWrapper extends HttpServletResponseWrapper {

    private PrintWriter printWriter;
    private ByteArrayOutputStream byteArrayOutputStream;
    private CharArrayWriter bufferedWriter;

    public MyResponseWrapper(HttpServletResponse response) {
        super(response);
        bufferedWriter = new CharArrayWriter();
        printWriter = new PrintWriter(bufferedWriter);
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return printWriter;
    }

    @Override
    public void flushBuffer() throws IOException {
        if (printWriter!=null){
            printWriter.flush();
        }
    }

    public String getResult() throws IOException {
        String res="";
        try {
            flushBuffer();
            res = bufferedWriter.toString();
            System.out.println(res);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return res;
    }
    /**
     * 自定义类
     */
    private static class MyPrintWriter extends PrintWriter{

        ByteArrayOutputStream byteArrayOutputStream ;

        public MyPrintWriter(ByteArrayOutputStream out) {
            super(out);
            byteArrayOutputStream = out;
        }

        public ByteArrayOutputStream getByteStreamOutputStream(){
            return byteArrayOutputStream;
        }
    }
}