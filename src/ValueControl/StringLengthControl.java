package ValueControl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;

/**
 * String length controller
 */
public class StringLengthControl extends ValueControllerAbstract {
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface CompareStringsValues {
        int lengthMax() default Integer.MAX_VALUE;

        int lengthMin() default Integer.MIN_VALUE;
    }


    private int minLen;
    private int maxLen;

    public StringLengthControl(Field field) {
        super(field);
        CompareStringsValues compareNumbersValues = field.getAnnotation(CompareStringsValues.class);
        minLen = compareNumbersValues.lengthMin();
        maxLen = compareNumbersValues.lengthMax();

    }

    {
        check = (Object value) -> {

            ByteBuffer buffer = ByteBuffer.allocate(1);

            if (value != null){
            String stringValue = (String) value;

                int length = stringValue.length();
                if (maxLen < length)
                    throw new ValueException(TypeOfControl.STRING);
                if (minLen > length)
                    throw new ValueException(TypeOfControl.STRING);

        }};
    }

    @Override
    public String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            if (minLen != Integer.MIN_VALUE) {
                stringBuffer.append("Минимальная длина: " + Integer.toString(minLen) + " ");
            }
            if (maxLen != Integer.MAX_VALUE) {
                stringBuffer.append("Максимальная длина: " + Integer.toString(maxLen) + " ");
            }
            return stringBuffer.toString();
    }
}
