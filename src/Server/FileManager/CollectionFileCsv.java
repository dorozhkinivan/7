package Server.FileManager;

import CollectionClasses.Worker;
import Server.CollectionServer;
import ValueControl.ValueException;
import Utility.WorkerActions;
import com.opencsv.CSVReader;
import com.opencsv.bean.*;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.*;
import java.util.*;

/**
 * Manage csv file with collection
 */
public class CollectionFileCsv extends CollectionFileAbstract {
    private String fileName;
    public CollectionFileCsv(String fileName){
        this.fileName = fileName;
    }

    /**
     * @return collection from csv file
     */
    @Override
    public HashSet<Worker> InputCollection(){
        if (fileName == null)
            System.out.println("Путь к файлу не найден!");
        else {
            try (CSVReader csvReader = new CSVReader(new FileReader(fileName))) {
                HashSet<Worker> collectionGet = new HashSet<>();
                List<Worker> list = new CsvToBeanBuilder<Worker>(csvReader).withType(Worker.class).withFieldAsNull(CSVReaderNullFieldIndicator.EMPTY_SEPARATORS).build().parse();
                collectionGet.addAll(list);

                for (Worker w : collectionGet){
                    CollectionServer.CheckWorkerValues(w);
                }
                return collectionGet;
            } catch (IOException fileNotFoundException) {
                System.out.println("Файл не найден");
            }
            catch (ValueException e){
                System.out.println("Неверные значения в файле!");
            }
        }
        return new HashSet<>();
    }
    /**
     * write collection to csv file
     */
    @Override
    public void OutputCollection(Collection<Worker> collection) {
        if (fileName == null){
            System.out.println("Путь к файлу не задан.");
            return;
        }

         ArrayList<Worker> list = new ArrayList<Worker>();
        list.addAll(collection);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            new StatefulBeanToCsvBuilder<Worker>(bufferedWriter).build().write(list);
            System.out.println("Коллекция записана в файл.");
            //beanToCsv
        }
        catch (IOException e){System.out.println("Ошибка с файлом.");}
        catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e){System.out.println("Ошибка записи csv.");}
    }

//    private boolean checkCollection(Collection<Worker> collection){
//        for (Worker w : collection){
//            int countUnique = 0;
//            for (Worker worker1 : collection){
//                if (worker1.getId().equals(w.getId())) {
//                    countUnique++;
//                }
//            }
//            if (countUnique != 1 || !ChecksValuesInReadyWorker.CheckUserWorker(w))
//                return false;
//        }
//        return true;
//    }
}
