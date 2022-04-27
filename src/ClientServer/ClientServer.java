package ClientServer;


import CommandControl.InfoCommand;
import CommandControl.SentCommand;
import Exceptions.ServerException;
import UserDataManager.UserConsole;
import UserInfo.UserInfo;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class ClientServer {
    private UserInfo userInfo;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    private UserConsole.Prints prints;
    public ClientServer(UserConsole.Prints prints){
        this.prints = prints;
    }

    private Socket clientSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public void start() throws ServerException{
        try {
            clientSocket = new Socket("localhost", 4004);
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
            prints.print("Сервер подключён!");
        } catch (IOException e) {
            throw new ServerException();
        }
    }
    public void sendData(Object object) throws ServerException{
        try {
            out.writeObject(addClientName(object));
            out.flush();

        } catch (IOException e) {
            throw new ServerException();
        }


    }
    private Object addClientName(Object o){
        if (o instanceof SentCommand){
            ((SentCommand) o).setUserName(userInfo.getName());
        }
        return o;
    }

    public Object sendAndGetData(Object object) throws ServerException {
        try {
            out.writeObject(addClientName(object));
            out.flush();
            return (in.readObject());
       }
        catch (ClassNotFoundException e) {
            System.out.println("Wrong class!");
        }
        catch (IOException e) {
            throw new ServerException();
        }
        return null;
    }
    public void stop(){
        try {
            clientSocket.close();
            in.close();
            out.close();
        }
        catch (IOException e){
            prints.print("Ошибка соединения.");
        }

    }

}
