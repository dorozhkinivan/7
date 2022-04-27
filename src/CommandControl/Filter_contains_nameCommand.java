package CommandControl;

import ClientServer.ClientServer;
import CollectionClasses.Worker;
import CommandControl.CommandTools.ArgumentsTool;
import CommandControl.CommandTools.CommandTool;
import Exceptions.CommandArgumentException;
import Exceptions.ServerException;
import Exceptions.WorkerDataInputException;
import Server.RequestsToWorkerDB;
import UserDataManager.UserConsole;

import java.util.HashSet;
import java.util.regex.Pattern;
/**
 * Command, that prints workers with names, that contains selected string.
 */
public class Filter_contains_nameCommand extends CommandAbstract{
    static private SentCommand         sentCommand = new SentCommand() {
        @Override
        public Object execute(RequestsToWorkerDB requestsToWorkerDB) {
            StringBuilder stringBuilder =new StringBuilder();
            for (Worker i : requestsToWorkerDB.getAllWorkers()){
                if (i.getName().contains(arguments[0])) {
                    stringBuilder.append(i);
                    stringBuilder.append("\n");
                }
            }
            return stringBuilder.toString();
        }
    };

    {
        description = "вывести элементы, значение поля name которых содержит заданную подстроку";
    }
    @CommandTool
    private ArgumentsTool argumentsTool;
    private ClientServer clientServer;
    public Filter_contains_nameCommand(UserConsole.Prints prints, UserConsole.Input.InputCommand inputCommand, ClientServer clientServer){
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
        if (argumentsTool.getArguments() != null && argumentsTool.getArguments().length > 0) {
            Object o = clientServer.sendAndGetData(sentCommand.addArguments(argumentsTool.getArguments()));
            if (o == null)
                prints.print("Команда не выполнена.");
                //throw new CommandArgumentException();
            else
                prints.print(((String)o));
            return;
        }
        argumentsTool.setArguments(argumentsTool.getInputCommand().askArguments(toString()).getArguments());
        run();
    }

    /**
     * Runs command from script file
     */
    @Override
    public void runInScriptMode() throws WorkerDataInputException, CommandArgumentException, ServerException {
        String pattern = argumentsTool.getArguments()[0];
        if (argumentsTool.getArguments() != null && argumentsTool.getArguments().length > 0) {
            Object o = clientServer.sendAndGetData(sentCommand.addArguments(argumentsTool.getArguments()));
            if (o == null)
                //prints.print("Команда не выполнена.");
                throw new CommandArgumentException();
            else
                prints.print(((String)o));
            return;
        }
        throw new CommandArgumentException();
    }

    @Override
    public String toString() {
        return "filter_contains_name";
    }
}
