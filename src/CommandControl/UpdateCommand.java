package CommandControl;

import ClientServer.ClientServer;
import CollectionClasses.Worker;
import CommandControl.CommandTools.ArgumentsTool;
import CommandControl.CommandTools.CommandTool;
import CommandControl.CommandTools.WorkerRequiredTool;
import Exceptions.CommandArgumentException;
import Exceptions.ServerException;
import Exceptions.WorkerDataInputException;
import Server.CollectionServer;
import Server.RequestsToWorkerDB;
import UserDataManager.UserConsole;
import Utility.WorkerActions;

import java.util.HashSet;

/**
 * Command, that deletes worker from collection and adds users one with the same id
 */
public class UpdateCommand extends CommandAbstract{
    static private SentCommand sentCommand;
    static {

        sentCommand = new SentCommand() {
            @Override
            public Object execute(RequestsToWorkerDB requestsToWorkerDB) {
                //CollectionServer.setDateAndId(worker);
                requestsToWorkerDB.updateWorkerIfUserNamesEqual(worker, this.getUserName(), Integer.parseInt(arguments[0]));
                return null;
            }
        };
    }
    {
        description = "обновить значение элемента коллекции, id которого равен заданному";
    }
    private ClientServer clientServer;
    @CommandTool
    private ArgumentsTool argumentsTool;
    @CommandTool
    private WorkerRequiredTool workerRequiredTool;

    public UpdateCommand(UserConsole.Prints prints, UserConsole.Input.InputCommand inputCommand, UserConsole.Input.InputWorker inputWorker, ClientServer clientServer) {
        workerRequiredTool = new WorkerRequiredTool();
        argumentsTool = new ArgumentsTool();
        this.clientServer = clientServer;
        this.prints = prints;
        argumentsTool.setInputCommand(inputCommand);
        workerRequiredTool.setInputWorker(inputWorker);

    }


    /**
     * Runs command from console
     */
    @Override
    public void run() throws ServerException {
        if (argumentsTool.getArguments() != null && argumentsTool.getArguments().length >= 1) {
            try {
                int id = Integer.parseInt(argumentsTool.getArguments()[0]);
                boolean exists = (Boolean) clientServer.sendAndGetData(new CheckIfWorkerIdExistsCommand(id));
                if (!exists) {
                    prints.print("Такого рабочего нет!");
                    argumentsTool.setArguments(argumentsTool.getInputCommand().askArguments(toString()).getArguments());
                    run();
                } else {
                    clientServer.sendData(sentCommand.addWorker(workerRequiredTool.getInputWorker().askWorker()).addArguments(argumentsTool.getArguments()));
                }
            } catch (NumberFormatException e) {
                prints.print("Нужно ввести id!");
                argumentsTool.setArguments(argumentsTool.getInputCommand().askArguments(toString()).getArguments());
                run();
            }
            return;
        }
        prints.print("ID не может быть null.");
        argumentsTool.setArguments(argumentsTool.getInputCommand().askArguments(toString()).getArguments());
        run();
    }

    /**
     * Runs command from script file
     */
    @Override
    public void runInScriptMode() throws WorkerDataInputException, CommandArgumentException, ServerException {
        if (argumentsTool.getArguments() != null && argumentsTool.getArguments().length >= 1) {
            try {
                int id = Integer.parseInt(argumentsTool.getArguments()[0]);
                boolean exists = (Boolean) clientServer.sendAndGetData(new CheckIfWorkerIdExistsCommand(id));
                if (!exists) {
                    throw new CommandArgumentException();
                } else {
                    clientServer.sendData(sentCommand.addWorker(workerRequiredTool.getScriptDataManager().getWorker()).addArguments(argumentsTool.getArguments()));

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
        return "update";
    }
}
