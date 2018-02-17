import java.io.*;
import java.net.*;

public class client {
    Socket sock;
    public static void main(String[] args) throws Exception {
        client client = new client();
        client.initialize();
        while(true)
        client.run();
    }
    public void run() throws Exception {
        PrintStream ps = new PrintStream(sock.getOutputStream());
        ps.println("Hello to SERVER from CLIENT");
        ps.println("Second message");
        
        InputStreamReader ir = new InputStreamReader(sock.getInputStream());
        BufferedReader br = new BufferedReader(ir);
        
        String message = br.readLine();
        System.out.println(message);
        
    }
    public void initialize() throws Exception {
        sock = new Socket("127.0.0.1", 4445);
    }
}
        