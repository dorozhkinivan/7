package CollectionClasses;

import ValueControl.ValueException;
import ValueControl.Checks;
import ValueControl.NullControl;
import ValueControl.StringLengthControl;
import Utility.WorkerValues;
import com.opencsv.bean.CsvBindByName;

import java.io.Serializable;
import java.util.Objects;
/**
 * Worker's organization address
 */
public class Address implements Serializable {
    @CsvBindByName
    @NullControl.NotNullInTheEnd
    private String street; //Поле не может быть null

    @StringLengthControl.CompareStringsValues(lengthMax = 19)
    @CsvBindByName
    @NullControl.NotNullInTheEnd
    private String zipCode; //Длина строки не должна быть больше 19, Поле может быть null
    /**
     * Set street, if value is correct
     * @param street
     * @throws ValueException
     */
    public void safeSetStreet(String street) throws ValueException {
        for (Checks i : WorkerValues.getChecks("street"))
            i.check(street);
        this.street = street;
    }

    public String getStreet() {
        return street;
    }
    /**
     * Set zipCode, if value is correct
     * @param zipCode
     * @throws ValueException
     */
    public void safeSetZipCode(String zipCode) throws ValueException {
        for (Checks i : WorkerValues.getChecks("zipCode"))
            i.check(zipCode);
        this.zipCode = zipCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return Objects.equals(street, address.street) && Objects.equals(zipCode, address.zipCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, zipCode);
    }

    @Override
    public String toString() {
        return "улица: " + ((street == null) ? "null" : street) + "\n" +
                "zip код: " + ((zipCode == null) ? "null" : zipCode);
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}