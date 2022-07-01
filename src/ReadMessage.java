import java.io.IOException;
import java.io.ObjectInputStream;

public class ReadMessage implements Runnable{
    private ObjectInputStream ois;

    public ReadMessage(ObjectInputStream ois) {
        this.ois = ois;
    }

    @Override
    public void run() {
        while(true){
            try {
                Message msg =(Message) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
