package ScriptControl;

import CollectionClasses.*;
import CommandControl.CommandString;

import Exceptions.CommandsNotFoundException;
import ValueControl.ValueException;
import Exceptions.WorkerDataInputException;
import Utility.WorkerActions;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Manage data from script file
 */
public class ScriptDataManager{
    Scanner scanner;
    public ScriptDataManager(String filename) throws FileNotFoundException {
        this.scanner = new Scanner(new File(filename));
    }
    /**
     * @return file is over or not
     */
    public Boolean isOver(){
        return !scanner.hasNextLine();
    }



    /**
     * @return worker from script
     * @throws WorkerDataInputException
     */
    public Worker getWorker() throws WorkerDataInputException {
        try{
            Worker worker = new Worker();
            worker.safeSetName(scanner.nextLine());
            worker.safeSetSalary(Double.parseDouble(scanner.nextLine()));
            Coordinates coordinates = new Coordinates();
            coordinates.safeSetX(Long.parseLong(scanner.nextLine()));
            coordinates.safeSetY(Float.parseFloat(scanner.nextLine()));
            worker.safeSetCoordinates(coordinates);
            Organization organization = new Organization();
            organization.safeSetAnnualTurnover(Float.parseFloat(scanner.nextLine()));
            Address address = new Address();
            address.safeSetZipCode(scanner.nextLine());
            address.safeSetStreet(scanner.nextLine());
            organization.safeSetPostalAddress(address);
            worker.safeSetOrganization(organization);
            worker.safeSetStatus(Status.valueOf(scanner.nextLine()));
            worker.safeSetPosition(Position.valueOf(scanner.nextLine()));
            return worker;
        }
        catch (InputMismatchException | ValueException | IllegalArgumentException e){
            throw new WorkerDataInputException();
        }
    }
    /**
     * @return CommandString from script
     * @throws CommandsNotFoundException
     */
    public CommandString getStringCommand() throws CommandsNotFoundException {
        String[] array = scanner.nextLine().split(" ");
        if (array.length == 0)
            throw new CommandsNotFoundException();
        if ((array.length == 2 && array[1].equals("")) || (array.length == 1))
            return new CommandString(array[0], null);

        return new CommandString(array[0], Arrays.copyOfRange(array, 1, array.length));
    }
}
