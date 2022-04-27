package Server;

import CommandControl.SentCommand;
import UserInfo.UserInfo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.*;

public class Server {
    public static Logger LOGGER;
    private static final ReentrantLock reentrantLock = new ReentrantLock();

    public static ReentrantLock getReentrantLock() {
        return reentrantLock;
    }

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
    private static final RequestsToUsersDB requestsToUsersDB = new RequestsToUsersDB(reentrantLock);
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
        ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            SendAnswersController sendAnswersController = new SendAnswersController(out);
            while (true) {
                try {
                    executorService.submit(new ProcessingObject(in.readObject(), out, requestsToUsersDB, collectionServer, sendAnswersController));
                } catch (ClassNotFoundException e) {
                    LOGGER.log(Level.WARNING, "Got wrong data from " + clientSocket.getInetAddress().toString());
                } catch (SocketException e) {
                    break;
                }

            }
        }
        finally {
            executorService.shutdown();
        }
    }

}