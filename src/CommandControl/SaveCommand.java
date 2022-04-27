package CommandControl;

import CollectionClasses.Worker;
import Server.FileManager.CollectionFileAbstract;
import UserDataManager.UserConsole;

import java.util.HashSet;
/**
 * Command, that saves collection to file
 */
public class SaveCommand extends CommandAbstract {
    {
        description = "сохранить коллекцию в файл";
    }
    CollectionFileAbstract collectionFileAbstract;
    HashSet<Worker> collection;

    public SaveCommand(UserConsole.Prints prints,CollectionFileAbstract collectionFileAbstract, HashSet<Worker> collection){
        this.prints = prints;
        this.collection = collection;
        this.collectionFileAbstract = collectionFileAbstract;
    }
    /**
     * Runs command from console
     */
    @Override
    public void run() {
        collectionFileAbstract.OutputCollection(collection);
        prints.print("Коллекция сохранена.");
    }

    /**
     * Runs command from script file
     */
    @Override
    public void runInScriptMode(){
        collectionFileAbstract.OutputCollection(collection);
    }

    @Override
    public String toString() {
        return "save";
    }
}
