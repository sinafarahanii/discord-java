import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ApplicationServer {

    public static void main(String[] args) {

        try (ServerSocket welcomingSocket = new ServerSocket(1234)) {
            while (true) {
                Socket user = welcomingSocket.accept();
                ObjectInputStream ois = new ObjectInputStream(user.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(user.getOutputStream());
                ClientHandler client = new ClientHandler(ois, oos, user);
                Thread thread = new Thread(client);
                thread.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

