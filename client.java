import java.io.*;
import java.net.*;
import cs1.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import javax.swing.JFileChooser;
import java.awt.Component;
import javax.swing.JComponent;
import java.awt.event.*;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.*;
import java.util.*;
public class client {
    Socket sock;    
    PrintStream ps;
    BufferedReader din;    
    InputStream is;
    public static void main(String[] args) throws Exception {
        new client();
        //types:B:bio, F:#friends, S:status
        //
    }
    public client() {
        try {
            sock = new Socket("10.7.20.30", 12524);
            ps = new PrintStream(sock.getOutputStream());
            din = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            is = sock.getInputStream();
            //ps.println("HELLO");
            //run(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String getData(String name, String type, int number) {
        //IMPORTANT: type should be "c" when requesting schools, all others are common words
        //s:status, n:name, f:friends, a:age, b:bio, h:hometown, c:schools
        String t = type.toLowerCase().substring(0, 1);
        String ret = "";
        try {
            if (number==0) {
                ret = run("g:" + t + ":" + name);
                System.out.println("Data sent - g:" + t + ":" + name);
            } else {
                ret = run("g" + t + ":" + name + ":" + number);
                System.out.println("Data sent - g:" + t + ":" + name + ":" + number);         
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String get = "";
        try {
            get = run(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("data recieved: " + get);
        return get;
    }
    public String run(String input) throws Exception {
        String output = "";
        ps.println(input);
        String line = "";
        boolean temp = false;
        while(temp == false) {
            line = din.readLine();
            if (line != null) 
                temp = true;
        }
        //String line = din.readLine(); 
        return line;
    }
    public boolean checkPassword(String username, String password) throws Exception {
        String hash = login.hash(password);
        String ret = "";
        ret = "p:" + username + ":" + hash;
        String line = run(ret);
        if (line.equals("true")) {
            return true;
        } else 
        if (line.equals("false")) {
            return false;
        }
        return false;
    }
    public void addUser(String username, String name, int age, String bio, String password, String hometown, String schools) throws Exception {
        System.out.println("Adding new user");
        String ret;
        ret = "a:u:" + username + ":" + name + ":" + age + ":" + bio + ":" + login.hash(password) + ":" + hometown + ":" + schools;
        try {
            System.out.println("Sending request to create user " + username);
            ps.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean checkUserExist(String name) throws Exception {
        String ret;
        ret = "e:" + name;
        String line = run(ret);
        if (line.equals("true")) {
            return true;
        }
        if (line.equals("false")) {
            return false;
        }
        return false;
    }
    public BufferedImage getProfile(String name) throws Exception{
        String ret;
        ret = "i:p:" + name;
        ps.println(ret);
        System.out.println("Reading: " + System.currentTimeMillis());
        byte[] sizeAr = new byte[4];
        is.read(sizeAr);
        System.out.println("created sizeAR successfully with length " + sizeAr.length);
        int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
        byte[] imageAr = new byte[size];
        System.out.println("created imageAR successfully with length " + imageAr.length);
        is.read(imageAr);
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
        return image;
    }
    public void addImage(String name, String url) {
        String ret;
        ret = "i:a:p:" + name + ":" + url;
        ps.println(ret);
    }
    public void loadImage(String name) {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("png");
    }
    public void addPost(String name, String subject, String content) {
        String ret;
        ret = "u:a:" + name + ":" + subject + ":" + content;
        ps.println(ret);
    }
    public String getPost(String name, int number) throws Exception{
        String ret;
        ret = "u:g:" + name + ":" + number;
        String line = run(ret);
        return line;
    }
    public int getNumPosts(String name) throws Exception {
        String ret;
        ret  = "u:c:" + name + ":0";
        String line = run(ret);
        return Integer.parseInt(line);
    }
    public void setData(String name, String type, String content) throws Exception {
        String t = type.toLowerCase().substring(0, 1);
        ps.println("s:" + t + ":" + content + ":" + name);
        System.out.println("Data sent - s:" + t + ":" + content + ":" + name);
    }
    public void addFriend(String name, String other) {
        String s = "f:a:" + name + ":" + other;
        ps.println(s);
    }
    public boolean isFriendsWith(String name, String other) throws Exception{
        String s = "f:i:" + name + ":" + other;
        String ret = run(s);        
        if (ret.equals("true")) {
            return true;
        } else {
            return false;
        }
    }
    public ArrayList<String> getFriends(String name) throws Exception{
        String s = "f:g:" + name;
        String ret = run(s);
        ArrayList<String> ar = new ArrayList<String>();
        while(ret.indexOf(":") != -1) {
            ar.add(ret.substring(0, ret.indexOf(":")));
            ret = ret.substring(ret.indexOf(":")+1);
        }
        return ar;
    }
    public void unfriend(String name, String other) {
        String s = "f:u:" + name + ":" + other;
        ps.println(s);
    }
    public void signout(String name) {
        String s = "o:" + name;
        ps.println(s);
    }
}   
