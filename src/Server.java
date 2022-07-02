import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Server {
    private  transient static final String BLUE="\u001B[34m";
    private transient static final String RESET="\u001B[0m";
    private transient static final String RED="\u001B[31m";
    private transient static final String GREEN="\u001B[32m";
    private transient static final String YELLOW="\u001b[33m";
    private transient static final String MAGENTA="\u001b[35m";
    private transient static final String CYAN="\u001b[36m";
    private transient static final String BRIGHT_BLACK="\u001b[30;1m";
    private User creator;
    private String name;
    private HashMap<Channel,Chats> channels = new HashMap<>();
    private HashMap<User, Access> users = new HashMap<>();
    public String printUsersList(){
        String outPut="";
        outPut+=((String) (BLUE+"Server users:"+RESET+"\n"));
        Set<User> keySet = users.keySet();
        ArrayList<User> serverUsers = new ArrayList<User>(keySet);
        for(User user:(serverUsers)){
            outPut+=((String) (CYAN+"."+user.getUserName()+" :"+RESET));
            if(!user.state()){
                outPut+=((String) (BRIGHT_BLACK+"offline"+RESET+"\n"));
            }else if(user.state()) {
                if (user.getStatus().equals(Status.Online)) {
                    outPut += ((String) (GREEN + "online" + RESET + "\n"));
                } else if (user.getStatus().equals(Status.Idle)) {
                    outPut += ((String) (YELLOW + "idle" + RESET + "\n"));
                } else if (user.getStatus().equals(Status.Invisible)) {
                    outPut += ((String) (MAGENTA + "invisible" + RESET + "\n"));
                } else if (user.getStatus().equals(Status.DoNotDisturb)) {
                    outPut += ((String) (RED + "Do Not Disturb" + RESET + "\n"));
                }
            }
        }
        return outPut;
    }
    public String printChannels(){
        String output="";
        int index=1;
        for(Channel ch:channels.keySet()){
            output+=index+"."+ch.getName()+" | "+ch.getType();
            index++;
        }
        return output;
    }

    public String getName() {
        return name;
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
}
