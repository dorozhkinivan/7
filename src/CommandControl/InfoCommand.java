package CommandControl;

import ClientServer.ClientServer;
import CollectionClasses.Worker;
import Exceptions.ServerException;
import Server.RequestsToWorkerDB;
import UserDataManager.UserConsole;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
/**
 * Command, that prints information about collection.
 */
public class InfoCommand extends CommandAbstract{
    static private SentCommand sentCommand = new SentCommand() {
        @Override
        public Object execute(RequestsToWorkerDB requestsToWorkerDB) {
            HashSet<Worker> collection = requestsToWorkerDB.getAllWorkers();
            return "Тип данных: рабочие, Длина: " + collection.size() + ( (collection.size()==0) ? "Коллекция пуста." : (" Дата создания первого элемента: " + new SimpleDateFormat("yyyy-MM-dd").format(collection.stream().min(Comparator.comparing(Worker::getCreationDate)).get().getCreationDate())  + " Самый высокая зарплата: " + collection.stream().max(Comparator.naturalOrder()).get().getSalary()));
        }
    };

    {
        description = "вывести в стандартный поток вывода информацию о коллекции";
    }
    private ClientServer clientServer;
    public InfoCommand(UserConsole.Prints prints, ClientServer clientServer) {
        this.prints = prints;
        this.clientServer = clientServer;

    }



    /**
     * Runs command from script file
     */
    @Override
    public void runInScriptMode() throws ServerException {
        Object o = clientServer.sendAndGetData(sentCommand);
        if (o == null)
            prints.print("Команда не выполнена.");
            //throw new CommandArgumentException();
        else
            prints.print(((String)o));

    }

    /**
     * Runs command from console
     */
    @Override
    public void run() throws ServerException{
        prints.print((String) clientServer.sendAndGetData(sentCommand));
    }

    @Override
    public String toString() {
        return "info";
    }
}
