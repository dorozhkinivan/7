package CollectionClasses;

import ValueControl.ValueException;
import ValueControl.Checks;
import ValueControl.NullControl;
import ValueControl.NumberValueControl;
import Utility.WorkerValues;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvRecurse;

import java.io.Serializable;
import java.util.Objects;
/**
 * Worker's organization
 */
public class Organization implements Serializable {
    @NumberValueControl.CompareNumbersValues(valueMin = 0, inclusivelyMin = false)
    @CsvBindByName
    @NullControl.NotNullInTheEnd
    private Float annualTurnover; //Поле может быть null, Значение поля должно быть больше 0
    @CsvRecurse
    @NullControl.NotNullInTheEnd
    private Address postalAddress; //Поле не может быть null

    public Float getAnnualTurnover() {
        return annualTurnover;
    }

    /**
     * Set annual turnover, if value is correct
     * @param annualTurnover
     * @throws ValueException
     */
    public void safeSetAnnualTurnover(Float annualTurnover) throws ValueException {
        for (Checks i : WorkerValues.getChecks("annualTurnover"))
            i.check(annualTurnover);
        this.annualTurnover = annualTurnover;
    }

    public Address getPostalAddress() {
        return postalAddress;
    }
    /**
     * Set postal address, if value is correct
     * @param postalAddress
     * @throws ValueException
     */
    public void safeSetPostalAddress(Address postalAddress) throws ValueException {
        for (Checks i : WorkerValues.getChecks("postalAddress"))
            i.check(postalAddress);
        this.postalAddress = postalAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organization)) return false;
        Organization that = (Organization) o;
        return Objects.equals(annualTurnover, that.annualTurnover) && Objects.equals(postalAddress, that.postalAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(annualTurnover, postalAddress);
    }

    @Override
    public String toString() {
        return "Годовой оборот: " +((annualTurnover == null) ? "null" : annualTurnover) + "\n" +
                "Почтовый адрес: " +"\n" + ((postalAddress.toString() == null) ? "null" : postalAddress.toString()) + "\n";
    }

    public void setAnnualTurnover(Float annualTurnover) {
        this.annualTurnover = annualTurnover;
    }

    public void setPostalAddress(Address postalAddress) {
        this.postalAddress = postalAddress;
    }
}