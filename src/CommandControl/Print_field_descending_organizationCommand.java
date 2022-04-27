package CommandControl;

import ClientServer.ClientServer;
import CollectionClasses.Worker;
import Exceptions.CommandArgumentException;
import Exceptions.ServerException;
import Exceptions.WorkerDataInputException;
import Server.RequestsToWorkerDB;
import UserDataManager.UserConsole;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
/**
 * Command, that prints the values of  organization of all elements in descending order
 */
public class Print_field_descending_organizationCommand extends CommandAbstract {
    static private SentCommand sentCommand = new SentCommand() {
        @Override
        public Object execute(RequestsToWorkerDB requestsToWorkerDB) {
            StringBuilder stringBuilder = new StringBuilder();
            HashSet<Worker> collection = requestsToWorkerDB.getAllWorkers();
            collection.stream().sorted(Comparator.reverseOrder()).forEach(worker -> {stringBuilder.append(worker.getOrganization().toString());stringBuilder.append("\n");});
            return stringBuilder.toString();
        }
    };
    {
        description = "вывести значения поля organization всех элементов в порядке убывания";
    }
    private ClientServer clientServer;
    public Print_field_descending_organizationCommand(UserConsole.Prints prints, ClientServer clientServer){
        this.clientServer = clientServer;
        this.prints = prints;

    }
    @Override
    public String toString() {
        return "print_field_descending_organization";
    }
    /**
     * Runs command from console
     */
    @Override
    public void run() throws ServerException {
        prints.print((String) clientServer.sendAndGetData(sentCommand));
    }



    /**
     * Runs command from script file
     */
    @Override
    public void runInScriptMode() throws WorkerDataInputException, CommandArgumentException, ServerException {
        prints.print((String) clientServer.sendAndGetData(sentCommand));
    }
}
