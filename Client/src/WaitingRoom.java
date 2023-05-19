import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class WaitingRoom {
    public static boolean checkIfIsValid(String userName, String password, String email, String phoneNumber) throws WrongFormat {
        if(!Pattern.matches("^[A-Za-z0-9]{6,}$",userName)){
            throw new WrongFormat("username");
        }else if(!Pattern.matches( "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$",password)){
            throw new WrongFormat("password");
        }else if(!Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$",email)){
            throw new WrongFormat("email");
        }if(!phoneNumber.equals("")){
            if(!(Pattern.matches("^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$",phoneNumber))) {
                throw new WrongFormat("phone number");
            }
        }
        System.out.println("Correct Format.");
        return true;
    }
    public static void main(String[] args) throws IOException, WrongFormat {
        Scanner scanner=new Scanner(System.in);
        //Socket socket = null;
        //try {
            //socket = new Socket("127.0.0.1", 1234);
        //} catch (IOException e) {
            //e.printStackTrace();
        //}
        //ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
        //ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
        while(true){
            try {
                Socket socket=connect();
                ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
                System.out.println("1:Log in\n2:Sign up\n3:Exit");
                int command = scanner.nextInt();
                scanner.nextLine();
                if (command == 1) {
                    System.out.println("Enter your username:");
                    String userName = scanner.nextLine();
                    System.out.println("Enter your password:");
                    String passWord = scanner.nextLine();
                    //System.out.println("Sent to DB");
                    String data=userName+" "+passWord+" #login";
                    oos.writeObject(data);
                    boolean login= (boolean)ois.readObject();
                    //System.out.println("Received from DB");
                    if(!login){
                        System.out.println("Username or password are incorrect");
                    }
                    else{

                        UserMenu userMenu = new UserMenu(ois,oos);
                    }
                }else if(command==2){
                    System.out.println("Enter your username:");
                    String userName = scanner.nextLine();
                    System.out.println("Enter your password:");
                    String passWord = scanner.nextLine();
                    System.out.println("Enter your email:");
                    String email=scanner.nextLine();
                    System.out.println("Enter your phone number:");
                    String phoneNumber=scanner.nextLine();
                    try {

                        if (!checkIfIsValid(userName, passWord, email, phoneNumber)) {
                            System.out.println(ois.readObject().toString());
                        }
                        String data=userName+" "+passWord+" "+email+" "+phoneNumber+" #signup";
                        System.out.println("Sent to DB");
                        oos.writeObject(data);
                        if(((boolean) ois.readObject())==(false)){
                            System.out.println("This user already exists!");
                        }
                        else{
                            System.out.println("Your account has been created successfully!");
                            UserMenu userMenu = new UserMenu(ois,oos);
                        }
                    } catch (WrongFormat e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                else if(command==3){
                    oos.writeObject("#exit");
                    oos.close();
                    ois.close();
                    socket.close();
                    break;
                }else{
                    System.out.println("Wrong input!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
//            } catch (WrongFormat e) {
//                e.printStackTrace();
            }
        }
    }
    private static Socket connect(){
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", 1234);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socket;
    }
}
