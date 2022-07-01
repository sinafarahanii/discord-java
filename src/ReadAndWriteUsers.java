import javax.swing.text.html.HTMLDocument;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReadAndWriteUsers {
    private static ReadAndWriteUsers singleInstance= null;
    private ObjectInputStream in;
    private ArrayList<User> users;
    private ReadAndWriteUsers() throws IOException, ClassNotFoundException {
        FileInputStream check = new FileInputStream("users.bin");
        users = new ArrayList<>();
        if(check.available()!=0) {
            System.out.println("obama1");
            in = new ObjectInputStream(new FileInputStream("users.bin"));
            users=(ArrayList) in.readObject();
            //while (in.available()>0) {
                //System.out.println("obama2");
                //Object read=in.readObject();
                //User user=(User) read;
                //users.add(user);
            //}
            in.close();
        }
        else
            users = new ArrayList<>();
        check.close();
    }

    public static ReadAndWriteUsers newReadAndWriteUsers() throws IOException, ClassNotFoundException {
        if(singleInstance == null)
            singleInstance = new ReadAndWriteUsers();
        return singleInstance;
    }
    public synchronized User findUser(String userName) throws IOException, ClassNotFoundException {
        for(User u:users){
            if(u.getUserName().equals(userName)){
                return u;
            }
        }
        return null;
    }
    public synchronized List<User> getUserList() throws IOException, ClassNotFoundException {
        return users;
    }
    public synchronized void addUser(User user) throws IOException {
        users.add(user);
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("users.bin",false));
        out.writeObject(users);
        out.close();
    }

    public synchronized void updateUser(User updatedUser) throws IOException {
        Iterator<User> it = users.iterator();
        while (it.hasNext()){
            if(((User)(it.next())).getUserName().equals(updatedUser.getUserName()))
                it.remove();
        }
        users.add(updatedUser);
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("users.bin",false));
        out.writeObject(users);
        //for(User u:users){
            //out.writeObject(u);
        //}
        out.close();
    }
}
