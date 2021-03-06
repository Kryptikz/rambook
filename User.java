import java.util.*;
import java.lang.Math.*;
public class User
{
    //INSTANCE FIELDS
    private String name;
    private int age;
    private String hometown;
    private String[] schools;
    private ArrayList<User> friendsList;
    private ArrayList<post> UserPosts = new ArrayList<post>();
    //CONSTRUCTOR - DONE FOR YOU
    //NOTE - it leaves the friendsList empty
    public User(String n, int a, String h, String[] s)
    {
        name = n;
        age = a;
        hometown = h;
        schools = s;
        friendsList = new ArrayList<User>(); 
        
    }//END Constructor
    
    public User()
    {
        name = "";
        age = 0;
        hometown = "";
        schools = new String[5];
        friendsList = new ArrayList<User>(); 
    }
    
    public void bulkAddFriends(ArrayList<User> u)
    {
        friendsList = u;
    }//END bulkAddFriends
    
    public boolean equals(User other) {
        if (name.equals(other.getName()) && age == other.getAge() && hometown.equals(other.getHometown())) {
            return true;
        } else {
            return false;
        } 
    }
    
    public void addFriend(User other) {
        friendsList.add(other);
    }
    
    public void unfriend(String friend) {
        for (int i=0; i<friendsList.size(); i++) {
            if (friendsList.get(i).getName().equals(friend)) {
                friendsList.remove(i);
            }   
        }
    } 
    
    public int countFriends() {
        return friendsList.size(); //this should work I think
    }
    
    public ArrayList<User> getMutualFriends(User other) {
        ArrayList<User> mutlist = new ArrayList<User>();
        for (User u1 : friendsList) {
            for (User u2 : other.getFriendsList()) {
                if (u1.equals(u2)) {
                    mutlist.add(u1);
                } 
            }
        }
        return mutlist;
    }

    public boolean getHometownFriends(String s1, String s2) {
        if(new client().getData(s1,"hometown",0) == new client().getData(s2,"hometown",0))
            return true;
        return false;
    }
    
    public ArrayList<User> getSchoolmates(int numSchools,ArrayList<String> s1, ArrayList<String> s2) {
        ArrayList<User> mutlist = new ArrayList<User>();
        boolean temp;
        for (User of : this.getFriendsList()) {
            temp = false;
            for (int i=0; i<this.schools.length; i++) {
                for (int i2=0; i2<of.getSchools().length; i2++) {
                    if(this.schools[i] != null && of.getSchools()[i2] != null)
                    {
                        if (this.schools[i].equals(of.getSchools()[i2])) {
                            temp=true;
                        }
                    }
                }
            }
            if (temp) {
                mutlist.add(of);
            }
        }
        return mutlist;
    }

    public String toString()
    {
        String retStr = "";
        retStr += "Name: \t\t" + name + "\n";
        retStr += "Age: \t\t" + age + "\n";
        retStr += "Hometown: \t" + hometown + "\n";
        for(int i = 1; i <= schools.length; i ++)
        {
            if (schools[i-1] != null) 
            retStr += "School " + i + ": " + schools[i-1] + "\n";
        }
        for (int i=0; i<friendsList.size(); i++) {
            retStr += "Friends with " + friendsList.get(i).getName() + "\n";
        }        
        return retStr;
    }//END toString
    
    public User suggestAFriend() {
        ArrayList<User> friendfriend = new ArrayList<User>();
        ArrayList<User> nonfriends = new ArrayList<User>();
        for (User f : friendsList) {
            for (User ff : f.getFriendsList()) {
                if (!f.equals(ff) && !f.equals(this)) {
                   nonfriends.add(ff); 
                }
                friendfriend.add(ff);
            }
        }
        ArrayList<User> nfht = new ArrayList<User>();
        for (User nf : nonfriends) {
            if (nf.getHometown().equals(this.hometown) && !nf.equals(this)) {
                nfht.add(nf);
            }
        }
        if (nfht.size()>1) {
            return (nfht.get((int)(Math.random()*nfht.size())));
        } else 
        if (nfht.size()==1) {
            return nfht.get(0);
        } else {
            return null;
        }
    }
    
    public String getName()
    {
        return name;
    }//END getName
    public int getAge() {
        return age;
    }
    public String getHometown() {
        return hometown;
    }
    public String[] getSchools() {
        return schools;
    }
    public ArrayList<User> getFriendsList() {
        return friendsList;
    }
    
    public void addPost(String image, String time, String name)
    {
        UserPosts.add(new post(image, time, name));
    }
    
    
}//END CLASS