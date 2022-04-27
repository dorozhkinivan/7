package Utility;

import CollectionClasses.Address;
import CollectionClasses.Coordinates;
import CollectionClasses.Organization;
import CollectionClasses.Worker;
import ValueControl.*;
import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Worker opportunities
 */
public class WorkerValues {
    final static public HashMap<String, HashMap<TypeOfControl, ValueControllerAbstract>> values = WorkerValues.getValues();

    private ArrayList<Field> fields = new ArrayList<>();
    private static HashMap<String, HashMap<TypeOfControl, ValueControllerAbstract>> getValues(){
        HashMap<String, HashMap<TypeOfControl, ValueControllerAbstract>> valuesControls = new HashMap<>();
        ArrayList<Field> fields = new ArrayList<>();
        CollectionUtils.addAll(fields, Worker.class.getDeclaredFields());
        CollectionUtils.addAll(fields, Organization.class.getDeclaredFields());
        CollectionUtils.addAll(fields, Coordinates.class.getDeclaredFields());
        CollectionUtils.addAll(fields, Address.class.getDeclaredFields());
        for (Field i : fields){
            HashMap<TypeOfControl, ValueControllerAbstract> hashMap = new HashMap<>();
            if (i.isAnnotationPresent(UniqueControl.IsUnique.class))
                hashMap.put(TypeOfControl.UNIQUE, new UniqueControl(i));
            if (i.isAnnotationPresent(NullControl.NotNullInTheEnd.class))
                hashMap.put(TypeOfControl.NOTNULL, new NullControl(i));
            if (i.isAnnotationPresent(StringLengthControl.CompareStringsValues.class))
                hashMap.put(TypeOfControl.STRING, new StringLengthControl(i));
            if (i.isAnnotationPresent(NumberValueControl.CompareNumbersValues.class))
                hashMap.put(TypeOfControl.NUMBER, new NumberValueControl(i));
            valuesControls.put(i.getName(), hashMap);
        }
        return valuesControls;
    }
    /**
     * @return Arraylist with check lambda
     */
    public static ArrayList<Checks> getChecks(String name){
        HashMap<TypeOfControl, ValueControllerAbstract> valueControllers = values.get(name);
        ArrayList<Checks> checks = new ArrayList<>();
        for(Map.Entry<TypeOfControl, ValueControllerAbstract> i : valueControllers.entrySet()) {
            checks.add(i.getValue().getCheck());
        }
        return checks;
    }
    /**
     * @return String limits for field
     */
    public static String getLimitsString(String name){
        HashMap<TypeOfControl, ValueControllerAbstract> valueControllers = values.get(name);
        StringBuffer stringBuffer =new StringBuffer();
        for(Map.Entry<TypeOfControl, ValueControllerAbstract> i : valueControllers.entrySet()) {
            stringBuffer.append(i.getValue().toString());
        }
        stringBuffer.append(" -> ");
        return stringBuffer.toString();
    }


}
