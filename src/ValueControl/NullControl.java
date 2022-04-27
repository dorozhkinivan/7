package ValueControl;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
/**
 * Null values controller
 */
public class NullControl extends ValueControllerAbstract {
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface NotNullInTheEnd {
    }

    public NullControl(Field field) {
        super(field);
    }

    {
        check = (Object value) -> {
            if (value == null)
                throw new ValueException(TypeOfControl.NOTNULL);
        };
    }

    @Override
    public String toString() {
        return "Это поле нужно обязательно ввести.";
    }
}
