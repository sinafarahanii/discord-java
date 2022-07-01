import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public  class  User implements Serializable {
    private  transient static final String BLUE="\u001B[34m";
    private transient static final String RESET="\u001B[0m";
    private transient static final String RED="\u001B[31m";
    private transient static final String GREEN="\u001B[32m";
    private transient static final String YELLOW="\u001b[33m";
    private transient static final String MAGENTA="\u001b[35m";
    private transient static final String CYAN="\u001b[36m";
    private transient static final String BRIGHT_BLACK="\u001b[30;1m";
    private HashMap<User,Chats> friendList = new HashMap<>();
    private ArrayList<FriendRequest> friendRequests = new ArrayList<>();
    private ArrayList<Server> servers = new ArrayList<Server>();
    private Status status=Status.Online;
    private String userName;
    private String password;
    private String email;
    private String phoneNumber;
    protected boolean state = false;
    public User(String userName, String password, String email) {
        this.userName=userName;
        this.password=password;
        this.email=email;
    }

    public User(String userName, String password, String email, String phoneNumber) {
        this.userName=userName;
        this.password=password;
        this.email=email;
        this.phoneNumber=phoneNumber;
    }

    public void online(){
        state = true;
    }

    public void offline(){
        state=false;
    }
    public void changeStatus(int command) throws IOException, ClassNotFoundException {
        if (command == 0)
            return;
        if (command == 1)
            status = Status.Online;
        if (command == 2)
            status = Status.Idle;
        if (command == 3)
            status = Status.Invisible;
        if (command == 4)
            status = Status.DoNotDisturb;
        ReadAndWriteUsers.newReadAndWriteUsers().updateUser(this);
    }

    public void changePassword(String newPass) throws IOException, ClassNotFoundException {
        if(newPass.equals("0")){
            return;
        }
        password=newPass;
        ReadAndWriteUsers.newReadAndWriteUsers().updateUser(this);
    }

    public void changeImage() {
    }

    public int getFriendRequests() {
        return friendRequests.size();
    }

    public String viewFriendRequests() throws IOException, ClassNotFoundException {
        String outPut = "";
        outPut += ("0: return\n");
        for (int i = 0; i < friendRequests.size(); i++) {
            outPut += ((i + 1) + ": " + friendRequests.get(i).getSender().getUserName() + "\n");
        }
        return outPut;
    }

    public void handleRequest(int command, int command2) throws IOException, ClassNotFoundException {
        if (command2 == 1) {
            friendRequests.get(command - 1).accept();
        }
        friendRequests.remove(command - 1);
        ReadAndWriteUsers readAndWriteUsers = ReadAndWriteUsers.newReadAndWriteUsers();
        readAndWriteUsers.updateUser(this);
    }


    public void addToFriend(User newFriend, Chats newChat){
        friendList.put(newFriend,newChat);
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<User> getFriendList() {
        Set<User> keySet = friendList.keySet();
        ArrayList<User> friends = new ArrayList<User>(keySet);
        return friends;
    }
    public boolean state(){
        return state;
    }

    public Status getStatus() {
        return status;
    }
    public void receiveNewFriendRequest(FriendRequest newFriendRequest) throws IOException, ClassNotFoundException {
        boolean spam = false;
        for(int i = 0;i<friendRequests.size();i++)
            if(friendRequests.get(i).getSender().getUserName().equals(newFriendRequest.getSender().getUserName()))
                spam=true;
        Set<User> keySet = friendList.keySet();
        ArrayList<User> friends = new ArrayList<User>(keySet);
        for(int i = 0;i<friends.size();i++){
            if(friends.get(i).getUserName().equals(newFriendRequest.getSender().getUserName()));
            spam=true;
        }
        if(spam)
            return;
        friendRequests.add(newFriendRequest);
        ReadAndWriteUsers.newReadAndWriteUsers().updateUser(this);
    }

    public String printFriendList() {
        String outPut="";
        outPut+=((String) (BLUE+"Your friend list:"+RESET+"\n"));
        int index=1;
        Set<User> keySet = friendList.keySet();
        ArrayList<User> friends = new ArrayList<User>(keySet);
        for(User user:(friends)){
            outPut+=((String) (CYAN+index+"."+user.getUserName()+" :"+RESET));
            if(!user.state()){
                outPut+=((String) (BRIGHT_BLACK+"offline"+RESET+"\n"));
            }else if(user.state()){
                if(user.getStatus().equals(Status.Online)){
                    outPut+=((String) (GREEN+"online"+RESET+"\n"));
                }else if(user.getStatus().equals(Status.Idle)){
                    outPut+=((String) (YELLOW+"idle"+RESET+"\n"));
                }else if(user.getStatus().equals(Status.Invisible)){
                    outPut+=((String) (MAGENTA+"invisible"+RESET+"\n"));
                }else if(user.getStatus().equals(Status.DoNotDisturb)){
                    outPut+=((String) (RED+"Do Not Disturb"+RESET+"\n"));
                }
            }
            index++;
        }
        return outPut;
    }

    public String viewChat(User user) {
        return friendList.get(user).toString();
    }

    public Chats addToChat(User user) {
        return friendList.get(user);
    }
}
