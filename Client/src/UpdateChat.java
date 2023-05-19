import java.io.IOException;
import java.io.ObjectInputStream;

public class UpdateChat implements Runnable{
    ObjectInputStream ois;
    public UpdateChat(ObjectInputStream ois) {
        this.ois=ois;
    }

    @Override
    public void run() {
        while(true){
            try {
                System.out.println(ois.readObject());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
