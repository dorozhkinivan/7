package ValueControl;

import java.lang.reflect.Field;
/**
 * abstract controller
 */
abstract public class ValueControllerAbstract {
    protected Field field;
    protected Checks check;
    public Checks getCheck(){
        return check;
    }
    protected ValueControllerAbstract(Field field){
        this.field = field;
    }
    //abstract public void check(T value)throws NumberNotInRangeException, LengthNotInRangeException, NotUniqueException, IsNullException;
    abstract public String toString();
}
