import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ServerHandler {
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private User user;
    private Server server;
    private Access userAccess;

    public ServerHandler(ObjectOutputStream oos, ObjectInputStream ois, User user, Server server) throws IOException, ClassNotFoundException {
        this.ois=ois;
        this.oos=oos;
        this.user=user;
        this.server=server;
        userAccess=server.getAccess(user);
        serverMenu();
    }

    private void serverMenu() throws IOException, ClassNotFoundException {
        while (true) {
            int command = Integer.parseInt(String.valueOf(ois.readObject()));
            if (command == 0) {
                return;
            } else if (command == 1) {
                int selectedUSer = selectUser();
                if (selectedUSer == -1) {
                    continue;
                } else {
                    modifyUsers(selectedUSer);
                }
            } else if (command == 2) {
                int channel = selectChannel();
                if (channel == -1) {
                    continue;
                } else {
                    if (server.getChannels().get(channel).getType().equals(Type.Text)) {
                        oos.writeObject(server.viewChannel(channel));
                        while (true) {
                            if (!((boolean) ois.readObject())) {
                                ReadAndWriteUsers.newReadAndWriteUsers().updateUser(user);
                                break;
                            }
                            server.addToChat(channel).addMessage(new Message(user, String.valueOf(ois.readObject())));
                        }
                    } else {
                        continue;
                    }
                }
            } else if (command == 3) {
                int setting = Integer.parseInt(String.valueOf(ois.readObject()));
                if (setting == 0) {
                    System.out.println("Client forfeits the request to add new member to server");
                    continue;
                } else if (setting == 1) {
                    System.out.println("Client continues with his request.");
                    User addedPerson = ReadAndWriteUsers.newReadAndWriteUsers().findUser(String.valueOf(ois.readObject()));
                    if (addedPerson != null && server.getUsers().contains(user)) {
                        server.addUser(addedPerson);
                        addedPerson.addServer(server);
                        oos.writeObject(true);
                    } else {
                        oos.writeObject(false);
                    }
                }
            } else if (command == 4) {
                modifyServer();
            }
            ReadAndWriteUsers.newReadAndWriteUsers().updateUser(user);
        }
    }

    private void modifyUsers(int selectedUSer) throws IOException, ClassNotFoundException {
        while (true){
            int command = Integer.parseInt(String.valueOf(ois.readObject()));
            if (command == 0) {
                return;
            }
            if(command == 1){
                if (!(userAccess.isCanRemoveUser())) {
                    oos.writeObject(0);
                    continue;
                }
                oos.writeObject(1);
                server.removeUser(selectedUSer);
                server.getUsers().get(selectedUSer).removeServer(server);
            }
            if(command == 2){
                if (!(server.getCreator().getUserName().equals(user.getUserName()))) {
                    oos.writeObject(0);
                    continue;
                }
                oos.writeObject(1);
                server.changeRole(selectedUSer, new Access(String.valueOf(ois.readObject()),(boolean) ois.readObject(),(boolean) ois.readObject(),(boolean) ois.readObject(),(boolean) ois.readObject(),(boolean) ois.readObject(),(boolean) ois.readObject(),(boolean) ois.readObject()));
            }
        }
    }


    private void modifyServer() throws IOException, ClassNotFoundException {
        while (true) {
            String command =(String.valueOf(ois.readObject()));
            if (command.equals("0")) {
                return;
            }
            if (command.equals("1")) {
                if (!(userAccess.isCanChangeServerName())) {
                    oos.writeObject(0);
                    continue;
                }
                oos.writeObject(1);
                server.addChannel(String.valueOf(ois.readObject()),user);
            } else if (command.equals("2")) {
                if (!(userAccess.isCanRemoveChannel())) {
                    oos.writeObject(0);
                    continue;
                }
                oos.writeObject(1);
                int channel = selectChannel();
                if (channel == -1)
                    continue;
                server.removeChannel(channel);
            }else if (command.equals("3")) {
                if (!(userAccess.isCanChangeServerName())) {
                    oos.writeObject(0);
                    continue;
                }
                oos.writeObject(1);
                server.setName(String.valueOf(ois.readObject()));
            }else if (command.equals("4")) {
                if (!(userAccess.isCanPinMassage())) {
                    oos.writeObject(0);
                    continue;
                }
                oos.writeObject(1);
                int channel = selectChannel();
                if(channel==-1){
                    continue;
                }
                server.pinMessage(channel,new Message(user, String.valueOf(ois)));
            }
            else if(command.equals("5")){
                server.removeThisUser(user);
                user.removeServer(server);
            }
        }
    }

    private int selectUser() throws IOException, ClassNotFoundException {
        oos.writeObject(server.printUsersList());
        int user = Integer.parseInt(String.valueOf(ois.readObject()));
        if(user<0 || user>server.getUsers().size()){
            oos.writeObject(0);
            return -1;
        }
        oos.writeObject(1);
        if(user==0)
            return -1;
        return user-1;
    }

    private int selectChannel() throws IOException, ClassNotFoundException {
        oos.writeObject(server.printChannels());
        int channel=Integer.parseInt(String.valueOf(ois.readObject()));
        if(channel<0 || channel>server.getChannels().size()){
            oos.writeObject(0);
            return -1;
        }
        oos.writeObject(1);
        if(channel==0) {
            return -1;
        }
        return channel-1;
    }
}
