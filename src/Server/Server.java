package Server;

import CommandControl.SentCommand;
import UserInfo.UserInfo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.*;

public class Server {
    public static Logger LOGGER;
    private static FileHandler fileHandler;

    static {
        LOGGER = Logger.getLogger(Server.class.getName());
        LogManager.getLogManager().reset();
        try {
            fileHandler = new FileHandler("info.config");
            LOGGER.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
        } catch (IOException e) {
            System.out.println("File logger error.");
        }

    }
    private static final RequestsToUsersDB requestsToUsersDB = new RequestsToUsersDB();
    private Socket clientSocket;
    private ServerSocket server;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private CollectionServer collectionServer;

    public Server(CollectionServer collectionServer) {
        this.collectionServer = collectionServer;
    }


    public void stop() {
        LOGGER.log(Level.INFO, "Stop server");
        try {
            if (in != null)
                in.close();
        } catch (IOException ignore) {
        }
        try {
            if (out != null)
                out.close();
        } catch (IOException ignore) {
        }
        try {
            if (server != null)
                server.close();
        } catch (IOException ignore) {
            System.out.println("wtf");
        }

    }

    public void start() {
        try {
            server = new ServerSocket(4004);
            LOGGER.log(Level.INFO, "Start server");
            System.out.println("Команда exit - завершение работы, save - сохранение коллекции. Логгер в файле info.config");
            newClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void newClient() throws IOException {
        clientSocket = server.accept();
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());
        while (true) {
            try {
                Object data = in.readObject();
                if (data instanceof UserInfo){
                    if (!requestsToUsersDB.userNameExists(((UserInfo) data).getName())){
                        requestsToUsersDB.addUser((UserInfo) data);
                        out.writeObject(Boolean.FALSE);// Такого пользователя не было.
                        out.flush();
                    }
                    else {
                        out.writeObject(Boolean.TRUE); // Такой пользователь был.
                        out.flush();
                    }

                }
                else if (data instanceof String){
                    //check name unique in db
                    out.writeObject(requestsToUsersDB.userNameExists((String) data));
                    out.flush();
                }
                else if (data instanceof SentCommand){
                    Object answer = collectionServer.executeCommand((SentCommand)data);
                    LOGGER.log(Level.INFO, "Got command from " + clientSocket.getInetAddress().toString());
                    if (answer != null) {
                        sendAnswer(answer);
                    }
                }
                else {
                    System.out.println("Wrong packet!!!");
                }

            } catch (ClassNotFoundException e) {
                LOGGER.log(Level.WARNING, "Got wrong data from " + clientSocket.getInetAddress().toString());
            } catch (SocketException e) {
                break;
            }
        }
    }

    private void sendAnswer(Object answer) throws IOException {
        LOGGER.log(Level.INFO, "Server sends answer");
        out.writeObject(answer);
        out.flush();
    }
}