package CommandControl;
/**
 * String value of command name and its arguments
 */
public class CommandString {
    private String commandName;
    private String[] arguments;

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public void setArguments(String[] arguments) {
        this.arguments = arguments;
    }

    public CommandString(String commandName, String[] arguments) {
        this.commandName = commandName;
        this.arguments = arguments;
    }

    public String getCommandName() {
        return commandName;
    }

    public String[] getArguments() {
        return arguments;
    }
}
