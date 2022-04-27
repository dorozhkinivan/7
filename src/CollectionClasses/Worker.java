package CollectionClasses;

import ValueControl.ValueException;
import Utility.WorkerValues;
import ValueControl.*;
import com.opencsv.bean.CsvDate;
import com.opencsv.bean.CsvRecurse;
import com.opencsv.bean.CsvBindByName;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
/**
 * Worker, collection's element
 */
public class Worker implements Serializable, Comparable<Worker> {

    @CsvBindByName
    @NullControl.NotNullInTheEnd
    @NumberValueControl.CompareNumbersValues(valueMin = 0, inclusivelyMin = false)
    @UniqueControl.IsUnique
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @CsvBindByName
    @NullControl.NotNullInTheEnd
    @StringLengthControl.CompareStringsValues(lengthMin = 1)
    private String name; //Поле не может быть null, Строка не может быть пустой

     @CsvRecurse
     @NullControl.NotNullInTheEnd
     private Organization organization; //Поле не может быть null

    @CsvRecurse
    @NullControl.NotNullInTheEnd
    private Coordinates coordinates;//Поле не может быть null

    @CsvBindByName
    @CsvDate
    @NullControl.NotNullInTheEnd
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @NumberValueControl.CompareNumbersValues(valueMin = 0, inclusivelyMin = false)
    @CsvBindByName
    private double salary; //Значение поля должно быть больше 0

    //@CsvBindByName
    //@NullControl.NotNullInTheEnd
    private Position position; //Поле не может быть null

    @CsvBindByName
    @NullControl.NotNullInTheEnd
    private Status status; //Поле может быть null

    public Worker(){
    }

    public Integer getId() {
        return id;
    }

    /**
     * Set id, if value is correct
     * @param id
     * @throws ValueException
     */
    public void safeSetId(Integer id, Collection<?> collection) throws ValueException {
        UniqueControl uniqueControl = (UniqueControl) WorkerValues.values.get("id").get(TypeOfControl.UNIQUE);
        uniqueControl.setCollection(collection);
//        for (Checks i : WorkerValues.getChecks("id"))
//            i.check(id);
        this.id = id;
    }

    public String getName() {
        return name;
    }

    /**
     * Set name, if value is correct
     * @param name
     * @throws ValueException
     */
    public void safeSetName(String name) throws ValueException {
        for (Checks i : WorkerValues.getChecks("name"))
            i.check(name);
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Set coordinates, if value is correct
     * @param coordinates
     * @throws ValueException
     */
    public void safeSetCoordinates(Coordinates coordinates) throws ValueException {
        for (Checks i : WorkerValues.getChecks("coordinates"))
            i.check(coordinates);
        this.coordinates = coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }
    /**
     * Set creation date, if value is correct
     * @param creationDate
     * @throws ValueException
     */
    public void safeSetCreationDate(Date creationDate) throws ValueException {
        for (Checks i : WorkerValues.getChecks("creationDate"))
            i.check(creationDate);
        this.creationDate = creationDate;
    }

    public double getSalary() {
        return salary;
    }

    /**
     * Set salary, if value is correct
     * @param salary
     * @throws ValueException
     */
    public void safeSetSalary(double salary) throws ValueException {

        for (Checks i : WorkerValues.getChecks("salary"))
            i.check(salary);
        this.salary = salary;
    }

    public Position getPosition() {
        return position;
    }

    /**
     * Set position, if value is correct
     * @param position
     * @throws ValueException
     */
    public void safeSetPosition(Position position) throws ValueException {
        for (Checks i : WorkerValues.getChecks("position"))
            i.check(position);
        this.position = position;
    }

    public Status getStatus() {
        return status;
    }

    /**
     * Set status, if value is correct
     * @param status
     * @throws ValueException
     */
    public void safeSetStatus(Status status) throws ValueException {
        for (Checks i : WorkerValues.getChecks("status"))
            i.check(status);
        this.status = status;
    }

    public Organization getOrganization() {
        return organization;
    }

    /**
     * Set organization, if value is correct
     * @param organization
     * @throws ValueException
     */
    public void safeSetOrganization(Organization organization) throws ValueException {
        for (Checks i : WorkerValues.getChecks("organization"))
            i.check(organization);
        this.organization = organization;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Worker)) return false;
        Worker worker = (Worker) o;
        return Double.compare(worker.salary, salary) == 0 && Objects.equals(id, worker.id) && Objects.equals(name, worker.name) && Objects.equals(coordinates, worker.coordinates) && Objects.equals(creationDate, worker.creationDate) && position == worker.position && status == worker.status && Objects.equals(organization, worker.organization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, salary, position, status, organization);
    }

    @Override
    public String toString() {
        return  "id: " + ((id == null) ? "null" : id) + "\n" +
                "имя: " + ((name == null) ? "null" : name) + "\n" +
                "координаты: " + "\n" +((coordinates == null) ? "null" : coordinates.toString()) + "\n" +
                "дата создания: " + ((creationDate == null) ? "null" : (new SimpleDateFormat("yyyy-MM-dd")).format(creationDate)) + "\n" +
                "зарплата: " + salary + "\n" +
                "должность: " + ((position == null) ? "null" : position.toString()) + "\n" +
                "статус: " + ((status == null) ? "null" : status.toString()) + "\n" +
                "организация: " + "\n" +((organization == null) ? "null" : organization.toString());
    }

    @Override
    public int compareTo(Worker o) {
        if (this.salary == o.salary)
            return 0;
        if (this.salary - o.salary > 0)
            return 1;
        return -1;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}