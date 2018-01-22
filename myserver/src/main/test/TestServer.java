import java.io.*;
import java.net.*;

public class TestServer {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket();
        SocketAddress remoteAddr=new InetSocketAddress("127.0.0.1",8090);
        socket.connect(remoteAddr);
        InputStream inputStream =null;
        inputStream = socket.getInputStream();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        byte []b= new byte[1024];
        int i =0;
        String temp ="";
        bufferedInputStream.read(b);
        temp = new String(b);
        bufferedInputStream.close();
        //OutputStream os = socket.getOutputStream();
        //PrintWriter writer = new PrintWriter(os);
        //byte []b= new byte[1024];
        //os.write(b);
        //writer.println("123");
        System.out.println(temp);
        socket.close();
    }
}