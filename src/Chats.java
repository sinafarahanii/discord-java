import java.io.Serializable;
import java.util.ArrayList;

public class  Chats implements Serializable {
    private ArrayList<Message> messages=new ArrayList<>();
    private Message lastMassage;
    public synchronized void addMessage(Message newMessage){
        messages.add(newMessage);
        //lastMassage = newMessage;
    }

    public Message getLastMassage() {
        return lastMassage;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    @Override
    public String toString(){
        String outPut="";
        for(int i = 0;i< messages.size();i++){
            outPut+=messages.get(i).toString()+"\n";
            lastMassage=messages.get(i);
        }
        return outPut;
    }
}
