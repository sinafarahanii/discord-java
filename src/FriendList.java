//import java.util.ArrayList;
//
//public class FriendList{
//    private ArrayList<User> friendList;
//    private static final String BLUE="\u001B[34m";
//    private static final String RESET="\u001B[0m";
//    private static final String RED="\u001B[31m";
//    private static final String GREEN="\u001B[32m";
//    private static final String YELLOW="\\u001b[33m";
//    private static final String MAGENTA="\\u001b[35m";
//    private static final String CYAN="\\u001b[36m";
//    private static final String BRIGHT_BLACK="\\u001b[30;1m";
//    public FriendList() {
//
//    }
//
//    public void add(User newFriend){
//        friendList.add(newFriend);
//    }
//
//    public String print() {
//        String outPut="";
//        outPut+=((String) (BLUE+"Your friend list:"+RESET));
//        int index=1;
//        for(User user:friendList){
//            outPut+=((String) (CYAN+index+"."+user.getUserName()+" :"));
//            if(!user.state()){
//                outPut+=((String) (BRIGHT_BLACK+"offline"+RESET));
//            }else if(user.state()){
//                if(user.getStatus().equals(Status.Online)){
//                    outPut+=((String) (GREEN+"online"+RESET));
//                }else if(user.getStatus().equals(Status.Idle)){
//                    outPut+=((String) (YELLOW+"idle"+RESET));
//                }else if(user.getStatus().equals(Status.Invisible)){
//                    outPut+=((String) (MAGENTA+"invisible"+RESET));
//                }else if(user.getStatus().equals(Status.DoNotDisturb)){
//                    outPut+=((String) (RED+"Do Not Disturb"+RESET));
//                }
//            }
//            index++;
//        }
//        return outPut;
//    }
//}
