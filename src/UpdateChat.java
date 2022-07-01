import java.io.IOException;
import java.io.ObjectOutputStream;

public class UpdateChat implements Runnable{
    private User user;
    private Chats chat;
    private ObjectOutputStream oos;
    public UpdateChat(User user, Chats chat, ObjectOutputStream oos) {
        this.chat=chat;
        this.user=user;
        this.oos=oos;
    }

    @Override
    public void run() {
        if(chat.getMessages().size()!=0) {
            int i = chat.getMessages().indexOf(chat.getLastMassage());
            while (true) {
                for (; i < chat.getMessages().size(); i++) {
                    try {
                        oos.writeObject(chat.getMessages().get(i));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
