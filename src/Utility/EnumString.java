package Utility;
/**
 * String possibilities of Enum
 */
public class EnumString<T extends Enum<T>> {
    private String name;
    private T[] values;

    public EnumString(String name, T[] values) {
        this.name = name;
        this.values = values;
    }

    /**
     * @return enum's constants in string
     */
    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Введите поле ").append(name).append(": \n");
        if (values.length == 0)
            return "//Нет элементов для выбора!!!";
        if (values.length == 1) {
            stringBuffer.append(values[0]);
            return stringBuffer.toString();
        }
        for (int i = 0; i < (values.length - 1); i++) {
            stringBuffer.append(values[i].toString());
            stringBuffer.append(" , ");
        }
        stringBuffer.append(values[values.length - 1]);
        return stringBuffer.toString();
    }
    /**
     * @return enum value
     */
    public T getEnum(String parse) throws IllegalArgumentException {
        if (parse == null)
            return null;
        for (T i : values) {
            if (i.toString().equals(parse))
                return i;
        }
        throw new IllegalArgumentException();
    }

}