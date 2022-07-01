import java.io.IOException;
import java.io.Serializable;

public  class FriendRequest implements Serializable {
    private User sender;
    private User getter;

    public User getSender() {
        return sender;
    }

    public FriendRequest(User getter, User sender) throws IOException, ClassNotFoundException {
        this.sender = sender;
        this.getter=getter;
        getter.receiveNewFriendRequest(this);
    }

    public void accept() throws IOException, ClassNotFoundException {
        ReadAndWriteUsers readAndWriteUsers = ReadAndWriteUsers.newReadAndWriteUsers();
        readAndWriteUsers.updateUser(sender);
        Chats chat = new Chats();
        sender.addToFriend(getter,chat);
        getter.addToFriend(sender,chat);
    }
}
