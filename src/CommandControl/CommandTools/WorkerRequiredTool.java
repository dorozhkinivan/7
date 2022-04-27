package CommandControl.CommandTools;

import CollectionClasses.Worker;
import ScriptControl.ScriptDataManager;
import UserDataManager.UserConsole;

import java.io.Serializable;

/**
 * Tool, that makes it possible for command to ask user worker`s data without arguments.
 * What is tool? {@link CommandTool}
 */
public class WorkerRequiredTool implements Serializable {
    private UserConsole.Input.InputWorker inputWorker;
    private ScriptDataManager scriptDataManager;

//    public Worker getWorker() {
//        return worker;
//    }
//
//    public void setWorker(Worker worker) {
//        this.worker = worker;
//    }
//
//    private Worker worker;
    public UserConsole.Input.InputWorker getInputWorker() {
        return inputWorker;
    }

    public void setInputWorker(UserConsole.Input.InputWorker inputWorker) {
        this.inputWorker = inputWorker;
    }

    public ScriptDataManager getScriptDataManager() {
        return scriptDataManager;
    }

    public void setScriptDataManager(ScriptDataManager scriptDataManager) {
        this.scriptDataManager = scriptDataManager;
    }
}
