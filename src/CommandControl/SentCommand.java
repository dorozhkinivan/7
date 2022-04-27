package CommandControl;

import CollectionClasses.Worker;
import Server.RequestsToWorkerDB;

import java.io.Serializable;
import java.util.HashSet;

abstract public class SentCommand implements Serializable {
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private static final long serialVersionUID = 1L;
    public SentCommand(Worker worker){
        this.worker =worker;
    }
    public SentCommand(String[] arguments){
        this.arguments =arguments;
    }
    public SentCommand(String[] arguments, Worker worker){
        this.arguments =arguments;
        this.worker = worker;
    }
    public SentCommand(){}

    abstract public Object execute(RequestsToWorkerDB requestsToWorkerDB);

    protected Worker worker;
    protected String[] arguments;
    public String[] getArguments() {
        return arguments;
    }

    public void setArguments(String[] arguments) {
        this.arguments = arguments;
    }

    public SentCommand addWorker(Worker worker) {
        this.worker = worker;
        return this;
    }
    public SentCommand addArguments(String[] arguments) {
        this.arguments = arguments;
        return this;
    }
}
