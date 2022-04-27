package CommandControl.CommandTools;

import UserDataManager.UserConsole;

import java.io.Serializable;

/**
 * Tool, that makes it possible for command to work with arguments.
 * What is tool? {@link CommandTool}
 */
public class ArgumentsTool implements Serializable {
    private UserConsole.Input.InputCommand inputCommand;
    private String[] arguments;

    public UserConsole.Input.InputCommand getInputCommand() {
        return inputCommand;
    }

    public void setInputCommand(UserConsole.Input.InputCommand inputCommand) {
        this.inputCommand = inputCommand;
    }

    public String[] getArguments() {
        return arguments;
    }

    public void setArguments(String[] arguments) {
        this.arguments = arguments;
    }
}
