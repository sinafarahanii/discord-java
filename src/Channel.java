import java.io.Serializable;

public  class Channel implements Serializable {
    private String name;
    private Type type;
    private Message pinMessage;
    public Channel(String name){
        this.name=name;
        type=Type.Text;
    }
    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public String getPinMessage() {
        return pinMessage.getText();
    }

    public void setPinMessage(Message pinMessage) {
        this.pinMessage = pinMessage;
    }
}
