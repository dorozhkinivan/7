package CommandControl;

import CollectionClasses.Worker;
import Exceptions.CommandArgumentException;
import Exceptions.WorkerDataInputException;
import UserDataManager.UserConsole;

import java.util.ArrayList;
import java.util.HashSet;
/**
 * Command, that prints information about commands.
 */
public class HelpCommand extends CommandAbstract{
    {
        description = "вывести справку по доступным командам";
    }
    private ArrayList<CommandAbstract> commands;

    public HelpCommand(UserConsole.Prints prints, ArrayList<CommandAbstract> commands){
        this.prints = prints;
        this.commands = commands;
    }
    @Override
    public String toString() {
        return "help";
    }
    /**
     * Runs command from console
     */
    @Override
    public void run() {
        commands.stream().forEach(commandAbstract -> prints.print(commandAbstract.toString() + " - " + commandAbstract.getDescription()));
    }

    /**
     * Runs command from script file
     */
    @Override
    public void runInScriptMode() throws WorkerDataInputException, CommandArgumentException {
        commands.stream().forEach(commandAbstract -> prints.print(commandAbstract.toString() + " - " + commandAbstract.getDescription()));
    }
}
