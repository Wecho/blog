package com.wecho.server;

import com.sun.org.apache.regexp.internal.RE;

import java.io.*;

/**
 * 构造response对象，能够通过request对象来拿到请求的各种参数
 */
public class Response {
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
}