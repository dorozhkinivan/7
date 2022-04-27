package CommandControl;

import ClientServer.ClientServer;
import CollectionClasses.Position;
import CollectionClasses.Worker;
import CommandControl.CommandTools.ArgumentsTool;
import CommandControl.CommandTools.CommandTool;
import CommandControl.CommandTools.WorkerRequiredTool;
import Exceptions.CommandArgumentException;
import Exceptions.ServerException;
import Exceptions.WorkerDataInputException;
import Server.RequestsToWorkerDB;
import UserDataManager.UserConsole;
import Utility.EnumString;
import java.util.HashSet;
/**
 * Command, that prints workers with position, which are equal with selected.
 */
public class Filter_by_positionCommand extends CommandAbstract{
    static private SentCommand sentCommand = new SentCommand() {
        @Override
        public Object execute(RequestsToWorkerDB requestsToWorkerDB) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Worker i : requestsToWorkerDB.getAllWorkers()) {
                if (arguments[0].equals(i.getPosition().toString())) {
                    stringBuilder.append(i);
                    stringBuilder.append("\n");
                }
            }
            return stringBuilder.toString();
        }
    };

    {
        description = "вывести элементы, значение поля position которых равно заданному";
    }
    @CommandTool
    private ArgumentsTool argumentsTool;
    private ClientServer clientServer;
    private Position position;
    public Filter_by_positionCommand(UserConsole.Prints prints, UserConsole.Input.InputCommand inputCommand, ClientServer clientServer) {
        argumentsTool = new ArgumentsTool();
        this.prints = prints;
        this.clientServer = clientServer;
        argumentsTool.setInputCommand(inputCommand);


    }

    @Override
    public String toString() {
        return "filter_by_position";
    }
    /**
     * Runs command from console
     */
    @Override
    public void run() throws ServerException {
        EnumString<Position> enumString = new EnumString<>("позиция", Position.values());
        if (argumentsTool.getArguments() != null && argumentsTool.getArguments().length > 0) {
            try {
                position = enumString.getEnum(argumentsTool.getArguments()[0]);
                Object o = clientServer.sendAndGetData(sentCommand.addArguments(argumentsTool.getArguments()));
                if (o == null)
                    prints.print("Команда не выполнена.");
                else
                    prints.print(((String)o));
                return;
            } catch (IllegalArgumentException e) {
                prints.print("Введите одно из значений ");
                prints.print(enumString.toString());
                argumentsTool.setArguments(argumentsTool.getInputCommand().askArguments(toString()).getArguments());
                run();
            }
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
        EnumString<Position> enumString = new EnumString<>("позиция", Position.values());
        try {
            if (argumentsTool.getArguments() != null && argumentsTool.getArguments().length > 0) {
                position = enumString.getEnum(argumentsTool.getArguments()[0]);
                Object o = clientServer.sendAndGetData(sentCommand.addArguments(argumentsTool.getArguments()));
                if (o == null)
                //prints.print("Команда не выполнена.");
                    throw new CommandArgumentException();
                else
                    prints.print(((String)o));
                return;
            }
                throw new CommandArgumentException();
        } catch (IllegalArgumentException e) {
            throw new CommandArgumentException();
        }
    }


}
