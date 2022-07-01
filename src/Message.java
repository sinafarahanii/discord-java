import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {
    private User sender;
    private String text;
    private User recipient;
    private String type;
    private LocalDateTime localDateTime;
    public Message(User sender, String text) {
        this.sender = sender;
        this.text = text;
        //this.recipient = recipient;
        //this.type=type;
        localDateTime=LocalDateTime.now();
    }

    @Override
    public String toString(){
        return (sender.getUserName()+" "+localDateTime+":\n"+text);
    }

    public User getSender() {
        return sender;
    }

    public String getText() {
        return text;
    }

    public User getRecipient() {
        return recipient;
    }

    public String getType() {
        return type;
    }
}
