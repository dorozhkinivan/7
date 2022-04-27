package CommandControl;

import ClientServer.ClientServer;
import CollectionClasses.Worker;
import Exceptions.ServerException;
import Server.CollectionServer;
import Server.RequestsToWorkerDB;
import UserDataManager.UserConsole;

import java.util.HashSet;
/**
 * Command, that delete all elements in collection.
 */
public class ClearCommand extends CommandAbstract{
    static private SentCommand sentCommand = new SentCommand() {
        @Override
        public Object execute(RequestsToWorkerDB requestsToWorkerDB) {
            requestsToWorkerDB.removeWorkersIfUserNamesEqual(this.getUserName());
            return null;
        }
    };

    {
        description = "очистить коллекцию";
    }
    private ClientServer clientServer;
    public ClearCommand(UserConsole.Prints prints, ClientServer clientServer) {
        this.prints = prints;
        this.clientServer = clientServer;

    }
    /**
     * Runs command from console
     */
    @Override
    public void run() throws ServerException {
        clientServer.sendData(sentCommand);

    }
    /**
     * Runs command from script file
     */
    @Override
    public void runInScriptMode() throws ServerException {
        clientServer.sendData(sentCommand);
    }



    @Override
    public String toString() {
        return "clear";
    }
}
