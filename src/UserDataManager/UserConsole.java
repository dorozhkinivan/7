package UserDataManager;

import CollectionClasses.*;
import CommandControl.CommandString;
import ValueControl.ValueException;
import Utility.EnumString;
import Utility.WorkerActions;
import Utility.WorkerValues;
import ValueControl.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

/**
 * Manage data from console
 */
public class UserConsole {
    public Input.InputWorker worker;
    public Input.InputCommand command;
    public Prints prints;
    public Input.PrintsAndAsks printsAndAsks;
    public UserConsole(Scanner scannerConsole) {
        Input input = new Input(scannerConsole);
        worker = input.new InputWorker();
        command = input.new InputCommand();
        printsAndAsks = input.new PrintsAndAsks();
        prints = new Prints();
    }
    /**
     * Only prints in console
     */
    public static class Prints {
        public void print(String text) {
            System.out.println(text);
        }
    }


    /**
     * Input and  possibility of output console
     */
    public static class Input {


        protected Scanner scannerConsole;

        private Input(Scanner scannerConsole) {
            this.scannerConsole = scannerConsole;
        }

        public class PrintsAndAsks{
            public void print(String text) {
                System.out.println(text);
            }
            public String ask(){
                return scannerConsole.nextLine();
            }
        }

        /**
         * Input user's worker data
         */
        public class InputWorker {
            /**
             * @return user's worker string which can be null
             */
            public String askWithNull() {
                String s = scannerConsole.nextLine();
                if (s == null || s.equals(""))
                    return null;
                return s;
            }
            /**
             * @return worker from console
             */
            public Worker askWorker() {
                Worker worker = new Worker();
                askAndSetName(worker);
                askAndSetCoordinates(worker);
                askAndSetSalary(worker);
                askAndSetPosition(worker);
                askAndSetStatus(worker);
                askAndSetOrganization(worker);
                return worker;
            }

            /**
             * set user's worker coordinates
             */
            private void askAndSetCoordinates(Worker worker) {
                System.out.println("Ввод координат.");
                Coordinates coordinates = new Coordinates();
                if (WorkerValues.values.get("coordinates").containsKey(TypeOfControl.NOTNULL)) {
                    askAndSetXCoordinate(coordinates);
                    askAndSetYCoordinate(coordinates);
                    try {
                        worker.safeSetCoordinates(coordinates);
                    } catch (ValueException ignore){}
                }
                // Code for Null possible field
            }

            /**
             * set user's worker status
             */
            private void askAndSetStatus(Worker worker) {
                EnumString<Status> enumString = new EnumString<>("статус", Status.values());
                System.out.println(enumString.toString());
                System.out.print(WorkerValues.getLimitsString("status"));
                try {
                    worker.safeSetStatus(enumString.getEnum(askWithNull()));
                } catch (ValueException e) {
                    if (e.getType() == TypeOfControl.NOTNULL) {
                        System.out.println("Значение не может быть равно Null.");
                    }
                    askAndSetStatus(worker);
                } catch (IllegalArgumentException e) {
                    System.out.println("Введите одно из предложенных значений");
                    askAndSetStatus(worker);
                }
            }
            /**
             * set user's worker address
             */
            private void askAndSetAddress(Organization organization) {
                System.out.println("Ввод почтового адреса.");
                Address address = new Address();
                if (WorkerValues.values.get("postalAddress").containsKey(TypeOfControl.NOTNULL)) {
                    askAndSetStreet(address);
                    askAndSetZipCode(address);
                    try {
                        organization.safeSetPostalAddress(address);
                    } catch (ValueException ignore){}
                }
                // Code for Null possible field
            }

            /**
             * set user's worker position
             */
            private void askAndSetPosition(Worker worker) {
                EnumString<Position> enumString = new EnumString<>("позиция", Position.values());
                System.out.println(enumString.toString());
                System.out.print(WorkerValues.getLimitsString("position"));
                try {
                    worker.safeSetPosition(enumString.getEnum(askWithNull()));
                } catch (ValueException e) {
                    if (e.getType() == TypeOfControl.NOTNULL) {
                        System.out.println("Значение не может быть равно Null.");
                    }
                    askAndSetPosition(worker);
                } catch (IllegalArgumentException e) {
                    System.out.println("Введите одно из предложенных значений");
                    askAndSetPosition(worker);
                }
            }

            /**
             * set user's worker salary
             */
            private void askAndSetSalary(Worker worker) {
                System.out.println("Введите зарплату. Требуется только числовое значение.");
                System.out.print(WorkerValues.getLimitsString("salary"));
                try {
                    worker.safeSetSalary(Double.parseDouble(scannerConsole.nextLine()));
                } catch (ValueException valueException) {
                    if (valueException.getType() == TypeOfControl.NUMBER) {
                        System.out.println("Значение вне диапазона.");
                    }
                    askAndSetSalary(worker);
                } catch (NumberFormatException e) {
                    System.out.println("Нужно ввести число!");
                    askAndSetSalary(worker);
                }
            }

            /**
             * set user's worker street
             */
            private void askAndSetStreet(Address address) {
                System.out.println("Введите улицу. ");
                System.out.println(WorkerValues.getLimitsString("street"));
                try {
                    address.safeSetStreet(askWithNull());
                } catch (ValueException e) {
                    if (e.getType() == TypeOfControl.NOTNULL) {
                        System.out.println("Поле не может быть null!");
                    }
                    askAndSetStreet(address);
                }
            }

            /**
             * set user's worker zip code
             */
            private void askAndSetZipCode(Address address) {
                System.out.println("Введите zip код. ");
                System.out.println(WorkerValues.getLimitsString("zipCode"));
                try {
                    address.safeSetZipCode(askWithNull());
                } catch (ValueException e) {
                    if (e.getType() == TypeOfControl.NOTNULL) {
                        System.out.println("Поле не может быть null!");
                    }
                    if (e.getType() == TypeOfControl.STRING) {
                        System.out.println("Невозможная длина строки!");
                    }
                    askAndSetZipCode(address);
                }
            }


            /**
             * set user's worker name
             */
            private void askAndSetName(Worker worker) {
                System.out.println("Введите имя. ");
                System.out.print(WorkerValues.getLimitsString("name"));
                try {
                    worker.safeSetName(askWithNull());
                } catch (ValueException valueException) {
                    if (valueException.getType() == TypeOfControl.STRING) {
                        System.out.println("Длина введённой строки вне диапазона.");
                    }
                    if (valueException.getType() == TypeOfControl.NOTNULL) {
                        System.out.println("Поле не может быть null!");
                    }
                    askAndSetName(worker);

                }
            }

            /**
             * set user's worker X coordinate
             */
            private void askAndSetXCoordinate(Coordinates coordinates) {
                System.out.println("Введите координату X. Требуется только числовое значение.");
                System.out.print(WorkerValues.getLimitsString("x"));
                try {
                    coordinates.safeSetX(Long.parseLong(scannerConsole.nextLine()));
                } catch (NumberFormatException e) {
                    System.out.println("Нужно ввести число!");
                    askAndSetXCoordinate(coordinates);
                } catch (ValueException valueException) {
                    if (valueException.getType() == TypeOfControl.NUMBER) {
                        System.out.println("Введённое значение вне диапазона.");
                    }
                    askAndSetXCoordinate(coordinates);
                }

            }

            /**
             * set user's worker Y coordinate
             */
            private void askAndSetYCoordinate(Coordinates coordinates) {
                System.out.print("Введите координату Y. Требуется только числовое значение. -> ");
                try {
                    coordinates.safeSetY(Long.parseLong(scannerConsole.nextLine()));
                } catch (NumberFormatException e) {
                    System.out.println("Нужно ввести число!");
                    askAndSetXCoordinate(coordinates);
                } catch (ValueException ignored) {
                }
            }

            /**
             * set user's worker annual turnover
             */
            private void askAndSetAnnualTurnover(Organization organization) {
                System.out.println("Введите ежегодный оборот.");
                System.out.println(WorkerValues.getLimitsString("annualTurnover"));
                try {
                    String st = askWithNull();
                    organization.safeSetAnnualTurnover((st != null) ? Float.parseFloat(st) : null);
                } catch (NumberFormatException e) {
                    System.out.println("Нужно ввести число!");
                    askAndSetAnnualTurnover(organization);
                } catch (ValueException e) {
                    if (e.getType() == TypeOfControl.NOTNULL) {
                        System.out.println("Значение не может быть Null.");
                    }
                    if (e.getType() == TypeOfControl.NUMBER) {
                        System.out.println("Введённое значение вне диапазона.");
                    }
                    askAndSetAnnualTurnover(organization);
                }
            }

            /**
             * set user's worker organization
             */
            private void askAndSetOrganization(Worker worker) {
                System.out.println("Ввод Организации.");
                Organization organization = new Organization();
                if (WorkerValues.values.get("organization").containsKey(TypeOfControl.NOTNULL)) {
                    askAndSetAnnualTurnover(organization);
                    askAndSetAddress(organization);
                    try {
                        worker.safeSetOrganization(organization);
                    } catch (ValueException ignore){}
                    return;
                }
                System.out.println("Если вы хотите присвоить значение, отличное от Null, введите любую строку, кроме пустой.");
                if (askWithNull() == null) {
                    try {
                        worker.safeSetOrganization(null);
                    } catch (ValueException ignore) {
                    }
                    return;
                }
                askAndSetAnnualTurnover(organization);
                askAndSetAddress(organization);
            }
        }

        /**
         * Input user's command and its arguments
         */
        public class InputCommand {
            /**
             * @return user's command and arguments
             */
            public CommandString askCommandAndArguments() {
                System.out.print("Введите команду с/без аргументами/ов ->");
                String[] array = scannerConsole.nextLine().split(" ");
                if (array.length == 0) {
                    System.out.print("Введите команду! -> ");
                    return askCommandAndArguments();
                }
                if ((array.length == 2 && array[1].equals("")) || (array.length == 1)) {
                    return new CommandString(array[0], null);
                }
                return new CommandString(array[0], Arrays.copyOfRange(array, 1, array.length));
            }

            /**
             * @return user's arguments
             */
            public CommandString askArguments(String commandString) {
                System.out.print("Введите верные аргументы! ->");
                String[] array = scannerConsole.nextLine().split(" ");
                if (array.length == 0)
                    return new CommandString(commandString, null);
                return new CommandString(commandString, array);
            }
        }

    }
}
