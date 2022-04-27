package CommandControl;
import CommandControl.CommandTools.ArgumentsTool;
import CommandControl.CommandTools.CommandTool;
import CommandControl.CommandTools.WorkerRequiredTool;
import Exceptions.CommandArgumentException;
import Exceptions.CommandsNotFoundException;
import Exceptions.ServerException;
import ScriptControl.ScriptDataManager;
import Exceptions.WorkerDataInputException;
import UserDataManager.UserConsole;

import java.lang.reflect.Field;
import java.util.ArrayList;
/**
 * An instrument to select the right command to run.
 */
public class CommandController {
    private ArrayList<CommandAbstract> commands = new ArrayList<>();

    public void addCommands(CommandAbstract... commands){
        for (CommandAbstract command : commands)
            this.commands.add(command);
    }

    public ArrayList<CommandAbstract> getCommands(){
        return commands;
    }

    /**
     * Selects which command to run from console
     */
    public void runCommandFromUserConsole(UserConsole.Prints prints, UserConsole.Input.InputCommand inputCommand){
        CommandString askedCommand = inputCommand.askCommandAndArguments();
        for (CommandAbstract command : commands){
            if (command.toString().equals(askedCommand.getCommandName())){
                try {
                    ArgumentsTool argumentsTool;
                    Field field = command.getClass().getDeclaredField("argumentsTool");
                    if (field.isAnnotationPresent(CommandTool.class)) {
                        field.setAccessible(true);
                        argumentsTool = (ArgumentsTool) field.get(command);
                        argumentsTool.setArguments(askedCommand.getArguments());
                    }
                }
                catch (NoSuchFieldException ignore) {}
                catch (IllegalAccessException e)
                {
                    System.out.println("//No acess!");
                }
                try {
                    command.run();
                }
                catch (ServerException e){
                    prints.print("Ошибка сервера при исполнении этой команды.");
                }

                return;
            }
        }
        prints.print("Название команды некорректно!");
        runCommandFromUserConsole(prints, inputCommand);
    }

    /**
     * Selects which command to run from script
     */
    public void runScriptCommand(ScriptDataManager scriptDataManager) throws ServerException, CommandsNotFoundException, CommandArgumentException, WorkerDataInputException {
        CommandString commandString = scriptDataManager.getStringCommand();
        for (CommandAbstract command : commands){
            if (command.toString().equals(commandString.getCommandName())){
                try {
                    ArgumentsTool argumentsTool;
                    Field field = command.getClass().getDeclaredField("argumentsTool");
                    if (field.isAnnotationPresent(CommandTool.class)){
                        field.setAccessible(true);
                        argumentsTool = (ArgumentsTool) field.get(command);
                        argumentsTool.setArguments(commandString.getArguments());
                    }
                }
                catch (NoSuchFieldException e) {}
                catch (IllegalAccessException e){System.out.println("//No acess!");}
                try {
                    WorkerRequiredTool workerRequiredTool;
                    Field field1 = command.getClass().getDeclaredField("workerRequiredTool");
                    if (field1.isAnnotationPresent(CommandTool.class))
                    {
                        field1.setAccessible(true);
                        workerRequiredTool = (WorkerRequiredTool)field1.get(command);
                        workerRequiredTool.setScriptDataManager(scriptDataManager);
                    }
                }
                catch (NoSuchFieldException e) {}
                catch (IllegalAccessException e)
                {
                    System.out.println("//No acess!");
                }
                    command.runInScriptMode();

                return;
            }
        }
        System.out.println(commandString.getCommandName());
        throw new CommandsNotFoundException();
    }
}

