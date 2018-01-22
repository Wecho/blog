import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 测试Socket/ServerSocket
 */
public class TestServerSocket {
    public static void main(String[] args) throws IOException {
        int port = 8090;
        ServerSocket serverSocket = new ServerSocket(port,1, InetAddress.getByName("127.0.0.1"));
        Socket socket = null;
        InputStream inputStream =null;
        OutputStream outputStream =null;
        while(socket==null){
            socket = serverSocket.accept();
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream);
            printWriter.print("<!DOCTYPE html><html>123</html>");
            printWriter.flush();
            System.out.println(socket.toString());
        }
    }
}