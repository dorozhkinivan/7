package Server.FileManager;

import CollectionClasses.Worker;

import java.util.Collection;
import java.util.HashSet;
/**
 * Abstract class for collection file management
 */
public abstract class CollectionFileAbstract {
    protected String fileName;
    /**
     * @return collection from file
     */
    abstract public HashSet<Worker> InputCollection();
    /**
     * write collection to file
     */
    abstract public void OutputCollection(Collection<Worker> collection);
}
