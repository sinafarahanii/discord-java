import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;

public class UserHandler {
    private final ObjectInputStream ois;
    private final ObjectOutputStream oos;
    private final User user;
    public UserHandler(ObjectInputStream ois, ObjectOutputStream oos, User user) {
        this.ois=ois;
        user.online();
        this.oos=oos;
        this.user=user;
        try {
            firstMenu();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        user.offline();
    }

    private void firstMenu() throws IOException, ClassNotFoundException {
        while(true){
            int command = 0;
            try {
                command = Integer.parseInt((String.valueOf( ois.readObject())));
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            if(command==1){
                try {
                    friendListBusiness();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if(command==2){

            }
            else if(command==4){
                sendFriendRequest();
            }
            else if(command==3){
                try {
                    viewFriendRequests();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(command==5){
                setting();
            }
            else if(command==6){
                break;
            }
        }
    }

    private void friendListBusiness() throws IOException, ClassNotFoundException {
        if(user.getFriendList().size()==0) {
            oos.writeObject(0);
            return;
        }
        oos.writeObject(1);
        oos.writeObject(user.printFriendList());
        int command = Integer.parseInt(String.valueOf(ois.readObject()));
        if(command<0 || command > user.getFriendList().size()){
            oos.writeObject(0);
            return;
        }
        oos.writeObject(1);
        if(command==0){
            return;
        }
        int command2 = Integer.parseInt(String.valueOf(ois.readObject()));
        if(command2==0)
            return;
        else if(command2==1){

        }
        else{
            oos.writeObject(user.viewChat(user.getFriendList().get(command-1)));
            //UpdateChat updateChat = new UpdateChat(user,user.addToChat(user.getFriendList().get(command-1)),oos);
            //Thread thread = new Thread(updateChat);
            //thread.start();
            while(true){
                if(!((boolean) ois.readObject())) {
                    //thread.stop();
                    ReadAndWriteUsers.newReadAndWriteUsers().updateUser(user);
                    ReadAndWriteUsers.newReadAndWriteUsers().updateUser(user.getFriendList().get(command-1));
                    return;
                }
                user.addToChat(user.getFriendList().get(command-1)).addMessage(new Message(user, (String.valueOf( ois.readObject()))));
            }
        }
    }
    private void groupList(){
        try {
            if (user.getServers().size() == 0) {
                oos.writeObject(0);
                return;
            }
            oos.writeObject(user.printServers());
            int server=Integer.parseInt(String.valueOf(ois.readObject()));
            if(server<0 || server>user.getServers().size()){
                oos.writeObject(0);
                return;
            }
            else if(server==0){
                return;
            }
            int command=Integer.parseInt(String.valueOf(ois.readObject()));
            if(command==0){
                return;
            }
            else if(command==1){
                oos.writeObject(user.getServers().get(server-1).printUsersList());
                return;
            }
            else if(command==2){
                oos.writeObject(user.getServers().get(server-1).printChannels());
                int channel=Integer.parseInt(String.valueOf(ois.readObject()));
                if(channel==0){
                    return;
                }else if(channel<0 || channel>user.getServers().get(server).getChannels().size()){
                    oos.writeObject(0);
                }else{
                    if(user.getServers().get(server-1).getChannels().get(channel-1).getType().equals(Type.Text)) {
                        oos.writeObject(user.viewChannelChat(user.getServers().get(server - 1), channel - 1));
                        while (true) {
                            if (!((boolean) ois.readObject())) {
                                ReadAndWriteUsers.newReadAndWriteUsers().updateUser(user);
                                ReadAndWriteUsers.newReadAndWriteUsers().updateUser(user.getFriendList().get(command - 1));
                                return;
                            }
                            user.getServers().get(server-1).addToChat(channel-1).addMessage(new Message(user,String.valueOf(ois.readObject())));
                        }
                    }else{
                    }
                }
            }else if(command==3){
                int setting=Integer.parseInt(String.valueOf(ois.readObject()));
                if(setting==0){
                    return;
                }else if(setting==1){
                    User addedPerson=ReadAndWriteUsers.newReadAndWriteUsers().findUser(String.valueOf(ois.readObject()));
                    if(addedPerson!=null) {
                        user.getServers().get(server - 1).addUser(addedPerson);
                        oos.writeObject(true);
                    }else{
                        oos.writeObject(false);
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void viewFriendRequests() throws IOException {
        while (true) {
            if (user.getFriendRequests() == 0) {
                oos.writeObject(0);
                return;
            }
            oos.writeObject(1);
            try {
                oos.writeObject(user.viewFriendRequests());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                int command = Integer.parseInt(String.valueOf(ois.readObject()));
                if(command==0){
                    return;
                }
                else{
                    int command2=Integer.parseInt(String.valueOf(ois.readObject()));
                    if(command> user.getFriendRequests() || (command2!=1 && command2!=2)){
                        oos.writeObject("Wrong Input.");
                    }
                    else {
                        user.handleRequest(command,command2);
                        if(command2==1)
                            oos.writeObject("Friend added.");
                        else
                            oos.writeObject("Request denied.");
                    }
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    private void sendFriendRequest() throws IOException, ClassNotFoundException {
        System.out.println("Ongoing friend request.");
        String friendName = String.valueOf(ois.readObject());
        System.out.println("String received: "+friendName);
        ReadAndWriteUsers readAndWriteUsers = ReadAndWriteUsers.newReadAndWriteUsers();
        User friend = readAndWriteUsers.findUser(friendName);
        if(friend==null){
            try {
                System.out.println("Failed");
                oos.writeObject(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        System.out.println("Succeed");
        oos.writeObject(1);
        new FriendRequest(friend, user);
    }

    private void setting() throws IOException, ClassNotFoundException {
        while (true){
            try {
                int command = Integer.parseInt(String.valueOf(ois.readObject()));
                if (command == 0)
                    return;
                else if (command == 1) {
                    changeStatus();
                } else if (command == 2) {
                    changePassword();
                } else if (command == 3) {
                    changeImage();
                }
            } catch (NumberFormatException | IOException | ClassNotFoundException e) {
                return;
            }
        }
    }

    private void changeStatus() {
        try {
            int command = Integer.parseInt(String.valueOf(ois.readObject()));
            user.changeStatus(command);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void changePassword() throws IOException, ClassNotFoundException {
        String newPass = String.valueOf(ois.readObject());
        user.changePassword(newPass);
    }

    private void changeImage() {
        user.changeImage();
    }
}
