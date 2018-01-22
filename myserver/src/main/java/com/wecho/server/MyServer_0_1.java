package com.wecho.server;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class MyServer_0_1 {

    private boolean isStop = false;

    public static void main(String[] args) throws IOException {//D:\gitRepository\blog\myserver\src\main\webapp
        //System.out.println(System.getProperty("user.dir"));
        new MyServer_0_1().await();
    }

    /**
     * 开启serversocket
     */
    public void await() throws IOException {
        ServerSocket serverSocket = null;
        Socket socket = null;
        String netAddr = "127.0.0.1";
        serverSocket = new ServerSocket(8090,1, InetAddress.getByName(netAddr));
        while(!isStop){
            socket = serverSocket.accept();

            InputStream ins =socket.getInputStream();
            OutputStream ops = socket.getOutputStream();

            Request request = new Request(ins);
            request.parse();

            Response response = new Response(ops);
            response.setRequest(request);
            response.sendStaticResource();
            socket.close();
        }
    }
}