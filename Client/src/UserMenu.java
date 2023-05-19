import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.regex.Pattern;

public class UserMenu {
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    public UserMenu(ObjectInputStream ois,ObjectOutputStream oos) throws WrongFormat, IOException, ClassNotFoundException {
        this.ois=ois;
        this.oos=oos;
        System.out.println("User Log in");
        firstMenu();
    }
    private void firstMenu() throws IOException, WrongFormat, ClassNotFoundException {
        while (true) {
            System.out.println(
                    "1. View Friend List\n" +
                    "2. View Group List\n"+
                    "3. View Friend Request\n" +
                    "4. Add Friend\n" +
                    "5. Settings\n" +
                            "6. Add Group\n" +
                            "7. LogOut");
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();
            if (input.equals("1")) {
                oos.writeObject("1");
                friendList();
            }else if(input.equals("2")){
                oos.writeObject("2");
                groupList();
            }
            else if (input.equals("3")) {
                oos.writeObject("3");
                friendRequest();
            } else if (input.equals("4")) {
                oos.writeObject("4");
                addFriend();
            } else if (input.equals("5")) {
                oos.writeObject("5");
                setting();
            } else if(input.equals("6")){
                oos.writeObject("6");
                addGroup();
            }
            else if(input.equals("7")){
                oos.writeObject(7);
                return;
            }
            else
                System.out.println("Wrong input.");
        }
    }

    private void friendList() throws IOException {
        try {
            if(Integer.parseInt(String.valueOf(ois.readObject()))==0){
                System.out.println("You have no friends");
                return;
            }
            Scanner sc = new Scanner(System.in);
            System.out.println(String.valueOf(ois.readObject()));
            String inp = sc.nextLine();
            int input = Integer.parseInt(inp);
            oos.writeObject(input);
            if(Integer.parseInt(String.valueOf(ois.readObject()))==0)
            {
                System.out.println("Wrong input");
                return;
            }
            if(input==0)
                return;
            System.out.println("0. Return\n" +
                    "1. View profile\n" +
                    "2. View Chat");
            inp = sc.nextLine();
            int input2 = Integer.parseInt(inp);
            if(input2<0 || input2 >= 3){
                System.out.println("Wrong input");
                return;
            }
            oos.writeObject(input2);
            if(input2==0){
                return;
            }
            if(input2==1){
                return;
            }
            if(input2==2){
                System.out.println(ois.readObject());
                System.out.println("When done write #exit");
                //UpdateChat updateChat = new UpdateChat(ois);
                //Thread thread = new Thread(updateChat);
                //thread.start();
                while (true){
                    inp = sc.nextLine();
                    if(inp.contains("#exit")){
                        oos.writeObject(false);
                        //thread.stop();
                        return;
                    }
                    oos.writeObject(true);
                    oos.writeObject(inp);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void groupList() {
        try {
            if (ois.readObject().equals(0)) {
                System.out.println("You don't have any group!");
                System.out.println("0. Return");
                Scanner scanner = new Scanner(System.in);
                scanner.nextLine();
                return;
            }
            System.out.println(ois.readObject());
            Scanner scanner = new Scanner(System.in);
            String com = scanner.nextLine();
            int command = Integer.parseInt(com);
            oos.writeObject(command);
            if (String.valueOf(ois.readObject()).equals("0")) {
                System.out.println("Wrong input");
                return;
            }
            if (command == 0) {
                return;
            }
            serverMenu();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void addGroup() throws IOException {
        System.out.println("Enter server name");
        Scanner scanner = new Scanner(System.in);
        String serverName = scanner.nextLine();
        oos.writeObject(serverName);
    }

    private void friendRequest() throws IOException, ClassNotFoundException {
        while(true) {
            int valid = Integer.parseInt(String.valueOf(ois.readObject()));
            Scanner sc = new Scanner(System.in);
            if(valid==0){
                System.out.println("You have no friend request at this time.\n" +
                        "0. return");
                String st = sc.nextLine();
                break;
            }
            System.out.println(String.valueOf(ois.readObject()));
            String com = sc.nextLine();
            try {
                int command = Integer.parseInt(com);
                oos.writeObject(command);
                if (command == 0)
                    return;
                System.out.println("1. Accept    2. Deny");
                String command2 = sc.nextLine();
                if (!(command2.equals("2")) & !(command2.equals("1")))
                    System.out.println("Wrong Action");
                else
                    oos.writeObject(Integer.valueOf(command2));
                System.out.println(ois.readObject());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                System.out.println("Wrong input");
            }
        }
    }

    private void addFriend() throws IOException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Write you future friend's username");
        String input = sc.nextLine();
        oos.writeObject(input);
        int success = Integer.parseInt(String.valueOf(ois.readObject()));
        if(success==1)
            System.out.println("Request sent successfully");
        else
            System.out.println("Username not found");
    }

    private void setting() throws IOException, WrongFormat {
        while (true) {
            System.out.println("0. return\n" +
                    "1. Change status\n" +
                    "2. Change passcode\n" +
                    "3. Insert profile picture");
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();
            if (input.equals("1")) {
                oos.write(1);
                System.out.println("Select your status: \n" +
                        "0. return\n" +
                        "1. Online,\n" +
                        "2. Idle,\n" +
                        "3. Invisible,\n" +
                        "4. DoNotDisturb");
                String input2 = sc.nextLine();
                if (input2.equals("1")) {
                    oos.write(1);
                } else if (input2.equals("2")) {
                    oos.write(2);
                } else if (input2.equals("3")) {
                    oos.write(3);
                } else if (input2.equals("4")) {
                    oos.write(4);
                } else
                    oos.write(0);
            } else if (input.equals("2")) {
                oos.write(2);
                System.out.println("Please enter your new password: ");
                String pas1 = sc.nextLine();
                try {
                    if (!Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", pas1)) {
                        throw new WrongFormat("password");
                    }
                    System.out.println("Please enter your new password again: ");
                    String pas2 = sc.nextLine();
                    if (!(pas1.equals(pas2))) {
                        System.out.println("Fields dont match.");
                    } else
                        oos.writeObject(pas1);
                } catch (WrongFormat e) {
                    e.printStackTrace();
                    System.out.println("Password does not match required style.");
                    oos.writeObject(0);
                }
            } else if (input.equals("3")) {
                oos.writeObject(3);
            } else if (input.equals("0")) {
                oos.writeObject(0);
                return;
            } else System.out.println("Wrong input!");
        }
    }

    private void serverMenu() throws IOException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        String com;
        int command;
        boolean isJoined=true;
        while (isJoined){
            System.out.println("0.Return\n" +
                    "1. View users\n" +
                    "2. View channels\n" +
                    "3. Add user\n" +
                    "4. Settings");
            com=sc.nextLine();
            if(com.equals("0")){
                oos.writeObject(0);
                return;
            }
            else if(com.equals("1")){
                oos.writeObject(1);
                System.out.println(ois.readObject());
                com = sc.nextLine();
                command = Integer.parseInt(com);
                oos.writeObject(command);
                if(Integer.parseInt(String.valueOf(ois.readObject()))==0){
                    System.out.println("Wrong input");
                    continue;
                }
                if(command==0)
                    continue;
                modifyUsers();
                continue;
            }
            else if(com.equals("2")){
                oos.writeObject(2);
                if(!selectChannel())
                    continue;
                System.out.println(ois.readObject());
                System.out.println("When done write #exit");
                while (true){
                    com = sc.nextLine();
                    if(com.contains("#exit")){
                        oos.writeObject(false);
                        //thread.stop();
                        break;
                    }
                    oos.writeObject(true);
                    oos.writeObject(com);
                }
                continue;
            }
            else if(com.equals("3")){
                oos.writeObject(3);
                System.out.println("0. Return\n1. Add user\n");
                com=sc.nextLine();
                if(com.equals("0")){
                    oos.writeObject(0);
                    continue;
                }
                else if(com.equals("1")){
                    oos.writeObject(1);
                    System.out.println("Enter your future serverMate username");
                    com=sc.nextLine();
                    oos.writeObject(com);
                    if((boolean) ois.readObject())
                        System.out.println("Succeed");
                    else
                        System.out.println("Failed");
                    continue;
                }
                else {
                    System.out.println("Wrong input.");
                    oos.writeObject(0);
                    continue;
                }
            }
            else if (com.equals("4")){
                oos.writeObject(4);
                while (true){
                    System.out.println("0. Return\n" +
                            "1. Add channel\n" +
                            "2. Remove channel\n" +
                            "3. Change server name\n" +
                            "4. Pin name\n" +
                            "5. Quit server!");
                    com= sc.nextLine();
                    if(com.equals("0")){
                        oos.writeObject(0);
                        break;
                    }

                    else if(com.equals("1")){
                        oos.writeObject(1);
                        if(Integer.parseInt(String.valueOf(ois.readObject()))==0){
                            System.out.println("No access");
                            continue;
                        }
                        System.out.println("Enter new channel's name: ");
                        oos.writeObject(sc.nextLine());
                        System.out.println("Channel added.");
                        continue;
                    }
                    else if(com.equals("2")){
                        oos.writeObject(2);
                        if(Integer.parseInt(String.valueOf(ois.readObject()))==0){
                            System.out.println("No access");
                            continue;
                        }
                        selectChannel();
                        System.out.println("Channel removed.");
                        continue;
                    }
                    else if(com.equals("3")){
                        oos.writeObject(3);
                        if(Integer.parseInt(String.valueOf(ois.readObject()))==0){
                            System.out.println("No access");
                            continue;
                        }
                        System.out.println("Enter server's new name:");
                        com = sc.nextLine();
                        oos.writeObject(com);
                        System.out.println("Name updated.");
                        continue;
                    }
                    else if(com.equals("4")){
                        oos.writeObject(4);
                        if(Integer.parseInt(String.valueOf(ois.readObject()))==0){
                            System.out.println("No access");
                            continue;
                        }
                        if(!selectChannel()){
                            continue;
                        }
                        System.out.println("Write message you wish to pin.");
                        com= sc.nextLine();
                        oos.writeObject(com);
                        System.out.println("Message pinned in selected channel");
                        continue;
                    }
                    else if(com.equals("5")){
                        oos.writeObject(5);
                        System.out.println("You left that nasty server:)))");
                        isJoined=false;
                        break;
                    }
                    else {
                        System.out.println("Wrong input");
                    }
                }
            }
            else
                System.out.println("Wrong input");
        }
    }

    private void modifyUsers() throws IOException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        String com;
        int command;
        while (true){
            System.out.println("0. Return \n" +
                    "1. Remove User\n" +
                    "2. Change User role");
            com= sc.nextLine();
            if(!(com.equals("0")||com.equals("1")||com.equals("2"))){
                System.out.println("Wrong input!");
                continue;
            }
            command=Integer.parseInt(com);
            if(command==0){
                oos.writeObject(0);
                return;
            }
            else if(command==1){
                oos.writeObject(1);
                if(Integer.parseInt(String.valueOf(ois.readObject()))==0){
                    System.out.println("No access");
                    continue;
                }
                System.out.println("User removed.");
                continue;
            }
            oos.writeObject(2);
            if(Integer.parseInt(String.valueOf(ois.readObject()))==0){
                System.out.println("No access");
                continue;
            }
            System.out.println("Enter role: ");
            String role = sc.nextLine();
            oos.writeObject(role);
            boolean op1=false;
            System.out.println("Let them creat new channels?\n1.Yes\n2.No");
            if(sc.nextLine().equals("1"))
                op1=true;
            else{
                op1=false;
            }
            oos.writeObject(op1);
            System.out.println("Let them remove channels?\n1.Yes\n2.No");
            if(sc.nextLine().equals("1"))
                op1=true;
            else{
                op1=false;
            }
            oos.writeObject(op1);
            System.out.println("Let them remove users?\n1.Yes\n2.No");
            if(sc.nextLine().equals("1"))
                op1=true;
            else{
                op1=false;
            }
            oos.writeObject(op1);
            System.out.println("Let them change server name?\n1.Yes\n2.No");
            if(sc.nextLine().equals("1"))
                op1=true;
            else{
                op1=false;
            }
            oos.writeObject(op1);
            System.out.println("Let them limit access to channels?\n1.Yes\n2.No");
            if(sc.nextLine().equals("1"))
                op1=true;
            else{
                op1=false;
            }
            oos.writeObject(op1);
            System.out.println("Let them limit access of users?\n1.Yes\n2.No");
            if(sc.nextLine().equals("1"))
                op1=true;
            else{
                op1=false;
            }
            oos.writeObject(op1);
            System.out.println("Let them pin messages?\n1.Yes\n2.No");
            if(sc.nextLine().equals("1"))
                op1=true;
            else{
                op1=false;
            }
            oos.writeObject(op1);
            System.out.println("Role modified successfully.");
        }
    }

    private boolean selectChannel() throws IOException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.println(ois.readObject());
        String com = sc.nextLine();
        int command = Integer.parseInt(com);
        oos.writeObject(command);
        if(Integer.parseInt(String.valueOf(ois.readObject()))==0){
            System.out.println("Wrong input");
            return false;
        }
        if(command==0)
            return false;
        return true;
    }
}
