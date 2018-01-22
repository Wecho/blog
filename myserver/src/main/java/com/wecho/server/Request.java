package com.wecho.server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 构造request对象，能够解析请求的参数，如请求的url等
 */
public class Request {
    private String uri;
    private InputStream ins;

    public Request(InputStream ins) {
        this.ins = ins;
    }

    public String getUri() {
        return uri;
    }

    public void parse() throws IOException {
        BufferedInputStream bufferedIns = new BufferedInputStream(ins);
        byte[] bytes = new byte[2048];
        try {
            int i = bufferedIns.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String request = new String(bytes);
        //ins.close();
        System.out.println(request);
        //uri = parseUri(request);
        System.out.println("---uri="+uri+"---");
    }

    private String parseUri(String requestStr){
        int index1 = requestStr.indexOf(" ");
        int index2 = requestStr.indexOf(" ",index1+1);
        System.out.println(index1+" "+index2);
        return requestStr.substring(index1+1,index2);
    }
}