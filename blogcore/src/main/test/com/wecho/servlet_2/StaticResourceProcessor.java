package com.wecho.servlet_2;

import java.io.IOException;
import java.io.OutputStream;

public class StaticResourceProcessor {

    public void process(Request request,Response response) throws IOException {
        response.sendStaticResource();
    }
}