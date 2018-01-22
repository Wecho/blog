package com.wecho.servlet_2;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.*;
import java.util.Locale;

public class Response implements ServletResponse {
    private Request request;

    private OutputStream ops ;

    private boolean isStop = false;

    public Response(OutputStream ops) {
        this.ops = ops;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void sendStaticResource() throws IOException {
        String uri = request.getUri();
        File file = new File("D:\\gitRepository\\blog\\myserver\\src\\main\\webapp\\index.html");
        FileInputStream fileInputStream= null;
        if(file.exists()){
            fileInputStream = new FileInputStream(file);
            byte[]b = new byte[2048];
            int i =1;
            String errorMessage = "HTTP/1.1 200 \r\n" +
                    "Content-Type: text/html\r\n"+
                    "\r\n" ;
            ops.write(errorMessage.getBytes());
            while( (i = fileInputStream.read(b))!= -1 ){
                ops.write(b,0,i);
                System.out.println("read"+i);
            }
        }else{
             String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: 23\r\n" +
                "\r\n" +
                "<h1>File Not Found</h1>";
            ops.write(errorMessage.getBytes());

        }
        fileInputStream.close();
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return null;
    }

    @Override
    public void setCharacterEncoding(String charset) {

    }

    @Override
    public void setContentLength(int len) {

    }

    @Override
    public void setContentLengthLong(long len) {

    }

    @Override
    public void setContentType(String type) {

    }

    @Override
    public void setBufferSize(int size) {

    }

    @Override
    public int getBufferSize() {
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {

    }

    @Override
    public void resetBuffer() {

    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void setLocale(Locale loc) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }
}