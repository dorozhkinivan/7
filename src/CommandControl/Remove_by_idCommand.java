package CommandControl;

import ClientServer.ClientServer;
import CollectionClasses.Worker;
import CommandControl.CommandTools.ArgumentsTool;
import CommandControl.CommandTools.CommandTool;
import Exceptions.CommandArgumentException;
import Exceptions.ServerException;
import Server.RequestsToWorkerDB;
import UserDataManager.UserConsole;
import Utility.WorkerActions;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;

/**
 * Command, that deletes worker with selected id
 */
public class Remove_by_idCommand extends CommandAbstract{
    static private SentCommand sentCommand = new SentCommand() {
        @Override
        public Object execute(RequestsToWorkerDB requestsToWorkerDB) {
            requestsToWorkerDB.removeWorkerIfUserNamesEqual(Integer.parseInt(arguments[0]), this.getUserName());
            return null;
        }
    };
    {
        description = "удалить элемент из коллекции по его id";
    }
    private ClientServer clientServer;
    @CommandTool
    private ArgumentsTool argumentsTool;

    public Remove_by_idCommand(UserConsole.Prints prints, UserConsole.Input.InputCommand inputCommand, ClientServer clientServer) {
        argumentsTool = new ArgumentsTool();
        this.prints = prints;
        this.clientServer = clientServer;
        argumentsTool.setInputCommand(inputCommand);

    }
    /**
     * Runs command from console
     */
    @Override
    public void run() throws ServerException {
        if (argumentsTool.getArguments() != null && argumentsTool.getArguments().length >= 1) {
            try {
                Integer id = Integer.parseInt(argumentsTool.getArguments()[0]);
                boolean exists = (Boolean) clientServer.sendAndGetData(new CheckIfWorkerIdExistsCommand(id));

                if (!exists) {
                    prints.print("Такого рабочего нет!");
                    argumentsTool.setArguments(argumentsTool.getInputCommand().askArguments(toString()).getArguments());
                    run();
                } else {
                    clientServer.sendData(sentCommand.addArguments(argumentsTool.getArguments()));
                }
            } catch (NumberFormatException e) {
                prints.print("Нужно ввести id!");
                argumentsTool.setArguments(argumentsTool.getInputCommand().askArguments(toString()).getArguments());
                run();
            }

            return;
        }
        prints.print("ID не может быть null");
        argumentsTool.setArguments(argumentsTool.getInputCommand().askArguments(toString()).getArguments());
        run();

    }


    /**
     * Runs command from script file
     */
    @Override
    public void runInScriptMode() throws CommandArgumentException, ServerException {
        if (argumentsTool.getArguments() != null && argumentsTool.getArguments().length >= 1) {
            try {
                int id = Integer.parseInt(argumentsTool.getArguments()[0]);
                boolean exists = (Boolean) clientServer.sendAndGetData(new CheckIfWorkerIdExistsCommand(id));
                if (!exists) {
                    throw new CommandArgumentException();
                } else {
                    clientServer.sendData(sentCommand.addArguments(argumentsTool.getArguments()));
                }
            } catch (NumberFormatException e) {
                throw new CommandArgumentException();
            }
            return;
        }
            throw new CommandArgumentException();

    }

    @Override
    public String toString() {
        return "remove_by_id";
    }
}
