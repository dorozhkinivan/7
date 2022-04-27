package ValueControl;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
/**
 * Number value controller
 */
public class NumberValueControl extends ValueControllerAbstract {
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface CompareNumbersValues {
        double valueMax() default Long.MAX_VALUE;

        boolean inclusivelyMax() default true;

        double valueMin() default Long.MIN_VALUE;

        boolean inclusivelyMin() default true;
    }

    private double min;
    private double max;
    private boolean incMin;
    private boolean incMax;

    {
        check = (Object value) -> {
            if (value != null) {
                Number numberValue = (Number) value;
                if (incMin)
                    if (numberValue.doubleValue() < min)
                        throw new ValueException(TypeOfControl.NUMBER);
                if (!incMin)
                    if (numberValue.doubleValue() <= min)
                        throw new ValueException(TypeOfControl.NUMBER);
                if (incMax)
                    if (numberValue.doubleValue() > max)
                        throw new ValueException(TypeOfControl.NUMBER);
                if (!incMax)
                    if (numberValue.doubleValue() >= max)
                        throw new ValueException(TypeOfControl.NUMBER);


            }    };
    }


    public NumberValueControl(Field field) {
        super(field);
        CompareNumbersValues compareNumbersValues = field.getAnnotation(CompareNumbersValues.class);
        min = compareNumbersValues.valueMin();
        max = compareNumbersValues.valueMax();
        incMax = compareNumbersValues.inclusivelyMax();
        incMin = compareNumbersValues.inclusivelyMin();
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (min != Long.MIN_VALUE) {
            stringBuffer.append("Минимальное значение: " + Double.toString(min) + " ");
            if (incMin)
                stringBuffer.append("включительно. ");
            else
                stringBuffer.append("не включительно. ");
        }
        if (max != Long.MAX_VALUE) {
            stringBuffer.append("Максимальное значение: " + Double.toString(max) + " ");
            if (incMax)
                stringBuffer.append("включительно. ");
            else
                stringBuffer.append("не включительно. ");
        }
        return stringBuffer.toString();

    }

    public int getMaxInt() {
        if (max > Integer.MAX_VALUE)
            return Integer.MAX_VALUE;
        return (int) max;
    }

    public int getMinInt() {
        if (min < Integer.MIN_VALUE)
            return Integer.MIN_VALUE;
        return (int) min;
    }
}