package com.wecho.servlet_2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer_0_2 {

    private static String SHUTDOWN = "SHUTDOWN";

    private boolean stop = false;

    public static void main(String[] args) throws IOException {
        new MyServer_0_2().await();
    }

    private void await() throws IOException {
        ServerSocket serverSocket = null;
        Socket socket = null;
        String netAddr = "127.0.0.1";
        serverSocket = new ServerSocket(8090,1, InetAddress.getByName(netAddr));
        while(stop){
            socket = serverSocket.accept();

            InputStream ins =socket.getInputStream();
            OutputStream ops = socket.getOutputStream();

            Request request = new Request(ins);
            request.parse();

            Response response = new Response(ops);
            //response.setRequest(request);
            String uri = request.getUri();
            if(uri.startsWith("/servlet")){
                //加载servlet处理
            }else{
                //返回静态数据处理
                StaticResourceProcessor processor = new StaticResourceProcessor();
                processor.process(request,response);
            }
            socket.close();


        }
    }
}