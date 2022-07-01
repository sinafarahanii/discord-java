import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final ObjectInputStream ois;
    private final ObjectOutputStream oos;
    private Socket client;
    private boolean isLoggedIn;
    private String name;

    public ClientHandler(ObjectInputStream ois, ObjectOutputStream oos, Socket client) {
        this.ois = ois;
        this.oos = oos;
        this.client = client;
        this.isLoggedIn = true;
    }

    @Override
    public void run() {
        try {
            String data = (String) ois.readObject();
            boolean login = false;
            System.out.println("Received from Client " + data);
            if (data.contains("#login")) {
                System.out.println("Login request received");
                String[] splits = data.split(" ");
                login = logIn(splits[0], splits[1], oos, ois);
                System.out.println(login);
                oos.writeObject(login);
                System.out.println("Sent to client.");
            } else if (data.contains("#signup")) {
                System.out.println("SignUp request received");
                String[] splits = data.split(" ");
                login = (signUp(splits[0], splits[1], splits[2], splits[3]));
                oos.writeObject(login);
                System.out.println("Sent to client.");
            }
            else if(data.contains("#exit")){
                ois.close();
                oos.close();
                client.close();
            }
            if (login) {
                String[] splits = data.split(" ");
                System.out.println("User login");
                User user = ReadAndWriteUsers.newReadAndWriteUsers().findUser(splits[0]);
                UserHandler userHandler = new UserHandler(ois,oos,user);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private boolean logIn(String userName, String password, ObjectOutputStream oos, ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ReadAndWriteUsers readAndWriteUsers = ReadAndWriteUsers.newReadAndWriteUsers();
        User attempt = readAndWriteUsers.findUser(userName);
        if (attempt == null)
            return false;
        if (attempt.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    private boolean signUp(String userName, String password, String email, String phoneNumber) throws IOException, ClassNotFoundException {
        ReadAndWriteUsers readAndWriteUsers = ReadAndWriteUsers.newReadAndWriteUsers();
        if (!(readAndWriteUsers.findUser(userName) == (null)))
            return false;
        User newUser;
        if (phoneNumber.equals(null)) {
            newUser = new User(userName, password, email);
        } else
            newUser = new User(userName, password, email, phoneNumber);
        readAndWriteUsers.addUser(newUser);
        return true;
    }
}
