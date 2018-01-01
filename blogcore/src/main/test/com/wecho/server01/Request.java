package com.wecho.server01;

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

    public void parse(){
        String request=null;
        BufferedInputStream bufferedIns = new BufferedInputStream(ins);
        byte[]b = new byte[2048];
        try {
            int i = bufferedIns.read(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
        request = new String(b);
        System.out.println(request);
        //return request;
    }

    private String parseUri(String uriStr){
        return "";
    }
}