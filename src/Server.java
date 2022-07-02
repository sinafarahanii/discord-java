import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Server implements Serializable {
    private  transient static final String BLUE="\u001B[34m";
    private transient static final String RESET="\u001B[0m";
    private transient static final String RED="\u001B[31m";
    private transient static final String GREEN="\u001B[32m";
    private transient static final String YELLOW="\u001b[33m";
    private transient static final String MAGENTA="\u001b[35m";
    private transient static final String CYAN="\u001b[36m";
    private transient static final String BRIGHT_BLACK="\u001b[30;1m";
    private final User creator;
    private String name;
    private HashMap<Channel,Chats> channels = new HashMap<>();
    private HashMap<User, Access> users = new HashMap<>();

    public Server(User creator, String name){
        setName(name);
        this.creator=creator;
        users.put(creator, new Access("Creator", true,true,true,true,true,true,true));
    }

    public String printUsersList(){
        String outPut="0. return\n";
        outPut+=((String) (BLUE+"Server users:"+RESET+"\n"));
        Set<User> keySet = users.keySet();
        ArrayList<User> serverUsers = new ArrayList<User>(keySet);
        int index = 1;
        for(User user:(serverUsers)){
            outPut+=((String) (CYAN+index+"."+user.getUserName()+" "+users.get(user).getRole()+":"+RESET));
            if(!user.isStatusSet() && !user.isState()){
                outPut+=((String) (BRIGHT_BLACK+"offline"+RESET+"\n"));
            }
            else if(!user.isStatusSet()){
                outPut+=((String) (GREEN+"online"+RESET+"\n"));
            }
            else if(user.isState()){
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

    public User getCreator(){
        return creator;
    }
    public Access getAccess(User user){
        return users.get(user);
    }
    public String printChannels(){
        String output="0. return\n";
        int index=1;
        for(Channel ch:channels.keySet()){
            output+=index+"."+ch.getName()+" | "+ch.getType()+"\n";
            index++;
        }
        return output;
    }

    public void addChannel(String name, User channelCreator){
        Channel channel = new Channel(name);
        channel.setPinMessage(new Message(channelCreator,"Welcome to "+name));
        channels.put(channel,new Chats());

    }
    public ArrayList<User> getUsers() {
        Set<User> user=users.keySet();
        ArrayList<User> chan=new ArrayList<>(user);
        return chan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name=name;
    }
    public ArrayList<Channel> getChannels() {
        Set<Channel> ch=channels.keySet();
        ArrayList<Channel> chan=new ArrayList<>(ch);
        return chan;
    }
    public Chats addToChat(int channel){
        return channels.get(getChannels().get(channel));
    }

    public void addUser(User user){
        users.put(user,new Access("member",false,false,false,false,false,false,false));
    }

    public void removeChannel(int channel) {
        channels.remove(getChannels().get(channel));

    }

    public void pinMessage(int channel, Message message) {
        getChannels().get(channel).setPinMessage(message);
    }

    public void removeUser(int selectedUSer) {
        users.remove(getUsers().get(selectedUSer));
    }

    public void changeRole(int selectedUSer, Access access) {
        users.put(getUsers().get(selectedUSer),access);
    }

    public void removeThisUser(User user) {
        users.remove(user);
    }

    public String viewChannel(int channel) {
        return channels.get(getChannels().get(channel)).toString()+"\n Pin: "+getChannels().get(channel).getPinMessage()+"\n";
    }
}
