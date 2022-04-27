package CommandControl;

import ClientServer.ClientServer;
import UserDataManager.UserConsole;
/**
 * Command, that stops program
 */
public class ExitCommand extends CommandAbstract {
    {
        description = "завершить программу (без сохранения в файл)";
    }
    private ClientServer clientServer;
    public ExitCommand(UserConsole.Prints prints, ClientServer clientServer) {
        this.prints = prints;
        this.clientServer = clientServer;
    }
    /**
     * Runs command from console
     */
    @Override
    public void run() {
        prints.print("Работа завершена!");
        System.exit(0);
    }

    /**
     * Runs command from script file
     */
    @Override
    public void runInScriptMode() {
        prints.print("Работа завершена!");
        System.exit(0);
    }

    @Override
    public String toString() {
        return "exit";
    }
}
