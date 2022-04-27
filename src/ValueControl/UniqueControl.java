package ValueControl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Objects;
/**
 * Unique values controller
 */
public class UniqueControl extends ValueControllerAbstract {
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface IsUnique {}


    private Collection<? extends Object> collection;

    public void setCollection(Collection<? extends Object> collection) {
        this.collection = collection;
    }

    public UniqueControl(Field field) {
        super(field);
    }

    {
        check = (Object value) -> {
            if (value != null) {
                try {
                    int count = 0;

                    for (Object o : collection) {
                        Field fieldCollection = o.getClass().getDeclaredField(field.getName());
                        fieldCollection.setAccessible(true);
                        if (Objects.equals(fieldCollection.get(o), value)) {
                            count++;
                        }
                    }
                    if (count != 1 && count != 0)
                        throw new ValueException(TypeOfControl.UNIQUE);

                } catch (NoSuchFieldException e) {
                    System.out.println("NO FIELD in unique!");
                } catch (IllegalAccessException e) {
                    System.out.println("NO Access in unique!");
                }
            }};
    }


    @Override
    public String toString() {
        return "Это поле должно быть уникальным.";
    }
}
