package Server;

import CommandControl.SentCommand;
import UserInfo.UserInfo;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;

public class ProcessingObject implements Runnable{
    private Object data;
    ObjectOutputStream out;
    RequestsToUsersDB requestsToUsersDB;
    SendAnswersController sendAnswersController;
    CollectionServer collectionServer;
    public ProcessingObject(Object data, ObjectOutputStream objectOutputStream, RequestsToUsersDB requestsToUsersDB, CollectionServer collectionServer, SendAnswersController sendAnswersController){
        this.data = data;
        this.out = objectOutputStream;
        this.collectionServer = collectionServer;
        this.requestsToUsersDB = requestsToUsersDB;
        this.sendAnswersController = sendAnswersController;
    }
    @Override
    public void run() {
        try {
            if (data instanceof UserInfo){
                boolean hasEqual = requestsToUsersDB.userNameExists(((UserInfo) data).getName());
                if (!hasEqual){
                    if (((UserInfo) data).getMode().equals(UserInfo.Mode.REGISTRATION)){
                        requestsToUsersDB.addUser((UserInfo) data);
                        out.writeObject(Boolean.TRUE);
                        out.flush();
                    }
                }
                else {
                    if(((UserInfo) data).getMode().equals(UserInfo.Mode.AUTHORIZATION)){
                        out.writeObject(requestsToUsersDB.passwordIsCorrect((UserInfo) data)); // Такой пользователь был.
                        out.flush();
                    }
                    if (((UserInfo) data).getMode().equals(UserInfo.Mode.REGISTRATION)){
                        out.writeObject(Boolean.FALSE);
                        out.flush();
                    }
                }

            }
            else if (data instanceof String){
                //check name unique in db
                out.writeObject(requestsToUsersDB.userNameExists((String) data));
                out.flush();
            }
            else if (data instanceof SentCommand){
                Object answer = collectionServer.executeCommand((SentCommand)data);
                //Server.LOGGER.log(Level.INFO, "Got command from " + clientSocket.getInetAddress().toString());
                if (answer != null) {
                    sendAnswersController.sendAnswer(answer);

                }

            }
            else {
                System.out.println("Wrong packet!!!");
            }
        }
        catch (IOException e){
            System.out.println("IO Exception");
        }
    }

}
