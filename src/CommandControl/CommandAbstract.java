package CommandControl;

import Exceptions.CommandArgumentException;
import Exceptions.ServerException;
import Exceptions.WorkerDataInputException;
import UserDataManager.UserConsole;

/**
 * All commands are extended of this class.
 */
abstract public class CommandAbstract{
    /**
     * Runs command from console
     */
    abstract public void run() throws ServerException;

    /**
     * Runs command from script file
     */
    abstract public void runInScriptMode() throws WorkerDataInputException, CommandArgumentException, ServerException;

    abstract public String toString();
    protected String description;
    public String getDescription(){
        return description;
    }
    protected UserConsole.Prints prints;

}
