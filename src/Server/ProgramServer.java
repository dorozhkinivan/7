package Server;

import CollectionClasses.Worker;
import Server.FileManager.CollectionFileAbstract;
import Server.FileManager.CollectionFileCsv;

import java.util.HashSet;
import java.util.Scanner;

public class ProgramServer {
    public static void main(String[] args){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("here");
        }
        ServerWork serverWork = new ServerWork();
        serverWork.start();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.equals("save")) {
                    //CollectionServer.saveCollection();
                } else if (line.equals("exit")) {
                    System.out.println("Завершение работы");
                    serverWork.stopServer();
                    break;
                } else System.out.println("Введите команды exit или save.");

            }
        }
    }

    private static class ServerWork extends Thread {
        private Server server;
        private CollectionServer collectionServer;

        {
            collectionServer = new CollectionServer();
            server = new Server(collectionServer);
        }

        public void stopServer() {
            server.stop();
        }

        @Override
        public void run() {
            server.start();
        }
    }
}
