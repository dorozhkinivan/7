package ValueControl;

/**
 * This exception is thrown in safe setter, when value is not correct
 */
public class ValueException extends Exception{
    /**
     * Type of value exception
     */
    private TypeOfControl type;
    public ValueException(TypeOfControl type){
        this.type = type;
    }

    public TypeOfControl getType() {
        return type;
    }
}
