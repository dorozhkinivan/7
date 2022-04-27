package CommandControl;

import CollectionClasses.Worker;
import CommandControl.CommandTools.ArgumentsTool;
import CommandControl.CommandTools.CommandTool;
import CommandControl.CommandTools.WorkerRequiredTool;
import Server.CollectionServer;
import Server.RequestsToWorkerDB;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;

public class CheckIfWorkerIdExistsCommand extends SentCommand{
        public CheckIfWorkerIdExistsCommand(Integer id){
            super(new String[]{id.toString()});
    }
    @Override
    public Object execute(RequestsToWorkerDB requestsToWorkerDB) {
        try {
            if (arguments != null && arguments[0] != null) {
                Integer id = Integer.valueOf(arguments[0]);
                for (Worker worker : requestsToWorkerDB.getAllWorkers()) {
                    if (worker.getId() != null)
                        if (worker.getId().equals(id))
                            return Boolean.TRUE;
                }
            }
        }
        catch (NumberFormatException e){}
        return Boolean.FALSE;
    }

}
