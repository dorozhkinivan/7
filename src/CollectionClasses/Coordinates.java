package CollectionClasses;

import ValueControl.ValueException;
import ValueControl.Checks;
import ValueControl.NumberValueControl;
import Utility.WorkerValues;
import com.opencsv.bean.CsvBindByName;

import java.io.Serializable;
import java.util.Objects;
/**
 * Worker's coordinates
 */
public class Coordinates implements Serializable {
    @CsvBindByName
    @NumberValueControl.CompareNumbersValues(valueMax = 689)
    private long x = 1; //Максимальное значение поля: 689
    @CsvBindByName
    private float y = 2;


    public long getX() {
        return x;
    }

    /**
     * Set x coordinate, if value is correct
     * @param x
     * @throws ValueException
     */
    public void safeSetX(long x) throws ValueException {
        for (Checks i : WorkerValues.getChecks("x"))
            i.check(x);
        this.x = x;
    }

    public float getY() {
        return y;
    }

    /**
     * Set y coordinate, if value is correct
     * @param y
     * @throws ValueException
     */
    public void safeSetY(float y) throws ValueException {
        for (Checks i : WorkerValues.getChecks("y"))
            i.check(y);
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinates)) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x && Float.compare(that.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return  "x: " + x + "\n" +
                "y: " + y;
    }

    public void setX(long x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}