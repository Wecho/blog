package com.wecho.server01;

import com.sun.org.apache.regexp.internal.RE;

import java.io.*;

/**
 * 构造response对象，能够通过request对象来拿到请求的各种参数
 */
public class Response {
    private Request request;

    private OutputStream ops;

    public Response(OutputStream ops) {
        this.ops = ops;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void sendStaticResource() throws FileNotFoundException {
        //String uri = request.getUri();
        FileInputStream FileInputStream = new FileInputStream(new File("D:\\blogparent\\blog\\blogcore\\src\\main\\webapp\\index.jsp"));
        byte[]b = new byte[2048];
        int i = 0;
        try {
            while((i=FileInputStream.read(b))!=-1){
                ops.write(b,0,i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {//D:\blogparent\blog\blogcore\src\main\webapp\index.jsp
            try {
                ops.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}