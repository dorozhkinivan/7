package CommandControl;

import CommandControl.CommandTools.ArgumentsTool;
import CommandControl.CommandTools.CommandTool;
import Exceptions.CommandArgumentException;
import Exceptions.CommandsNotFoundException;
import Exceptions.ServerException;
import ScriptControl.ScriptDataManager;
import Exceptions.WorkerDataInputException;
import UserDataManager.UserConsole;

import java.io.IOException;

/**
 * Command, that runs commands from selected script.
 */
public class Execute_scriptCommand extends CommandAbstract {
    {
        description = "считать и исполнить скрипт из указанного файла.";
    }

    @CommandTool
    private ArgumentsTool argumentsTool;
    CommandController commandController;

    public Execute_scriptCommand(UserConsole.Prints prints, UserConsole.Input.InputCommand inputCommand, CommandController commandController) {
        argumentsTool = new ArgumentsTool();
        this.commandController = commandController;
        argumentsTool.setInputCommand(inputCommand);
        this.prints = prints;
    }
    /**
     * Runs command from console
     */
    @Override
    public void run() {
        if (argumentsTool.getArguments() != null && argumentsTool.getArguments().length > 0) {
            try {
                ScriptDataManager scriptDataManager = new ScriptDataManager(argumentsTool.getArguments()[0]);
                if (scriptDataManager.isOver()) {
                    prints.print("Файл пустой");
                    return;
                }
                while (!scriptDataManager.isOver())
                    commandController.runScriptCommand(scriptDataManager);
            }
            catch (ServerException e){
                prints.print("Ошибка подключения.");
            }
            catch (IOException fileNotFoundException) {
                prints.print("Такой файл не найден! Введите другой.");
                argumentsTool.setArguments(argumentsTool.getInputCommand().askArguments(toString()).getArguments());
                run();
            } catch (CommandsNotFoundException e) {
                prints.print("Команды не найдены! Введите название файла.");
                argumentsTool.setArguments(argumentsTool.getInputCommand().askArguments(toString()).getArguments());
                run();
            } catch (CommandArgumentException e) {
                prints.print("Аргумент/аргументы команд неправильны! Введите название файла.");
                argumentsTool.setArguments(argumentsTool.getInputCommand().askArguments(toString()).getArguments());
                run();
            }
            catch (WorkerDataInputException e) {
                prints.print("Ввод информации о рабочем ошибочен! Введите название файла.");
                argumentsTool.setArguments(argumentsTool.getInputCommand().askArguments(toString()).getArguments());
                run();
            }
            return;
        }
        prints.print("Вы не ввели файл с командами! Попробуйте ещё раз");
        argumentsTool.setArguments(argumentsTool.getInputCommand().askArguments(toString()).getArguments());
        run();

    }
    /**
     * Runs command from script file
     */
    @Override
    public void runInScriptMode() throws WorkerDataInputException, CommandArgumentException, ServerException {
        if (argumentsTool.getArguments() != null && argumentsTool.getArguments().length > 0) {
            try {
                ScriptDataManager scriptDataManager = new ScriptDataManager(argumentsTool.getArguments()[0]);
                if (!scriptDataManager.isOver())
                    return;
                while (!scriptDataManager.isOver())
                    commandController.runScriptCommand(scriptDataManager);
            } catch (IOException | CommandsNotFoundException | CommandArgumentException | WorkerDataInputException e) {
                throw new CommandArgumentException();
            }
            return;
        }
        throw new CommandArgumentException();
    }

    @Override
    public String toString() {
        return "execute_script";
    }
}
