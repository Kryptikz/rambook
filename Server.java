import java.net.*;
import java.io.*;
public class Server implements Runnable {
    ServerSocket ss;
    Socket s;
    BufferedReader din;
    DataOutputStream dout;
    public static void main(String[] args) throws Exception {
        new Server();
    }
    public Server() {
        try {
            ss = new ServerSocket(8888);
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }       
    }
    public void run() {
        while(true) {
            try {
                s = ss.accept();
                (new Thread(new serverRunnable(s))).start();                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
