package com.wecho.server_2;

import java.io.IOException;

public class StaticResourceProcessor {

    public void process(Request request,Response response) throws IOException {
        response.sendStaticResource();
    }
}