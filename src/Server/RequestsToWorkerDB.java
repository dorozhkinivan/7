package Server;

import CollectionClasses.*;

import java.sql.*;
import java.util.HashSet;
import java.util.logging.Level;

public class RequestsToWorkerDB {
    private Connection connection;
    public RequestsToWorkerDB(){
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:7340/studs", "s335067", "vyi143");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public boolean workersDBIsEmpty(){
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement("SELECT COUNT(*) AS c FROM workers");
            ResultSet resultSet =preparedStatement.executeQuery();
            resultSet.next();
            boolean result = true;
            if (resultSet.getInt("c") > 0)
                result = false;
            preparedStatement.close();
            return result;
        }
        catch (SQLException e){
            Server.LOGGER.log(Level.WARNING, "SQL exception.");
            return false;
        }
    }
    public void addWorker(Worker worker, String userName){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO workers (nameP, dateP, salaryP, statusP, positionP, xCoordinateP, yCoordinateP, annualTurnOverP, streetP, zipCodeP, userName) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1, worker.getName());
            preparedStatement.setDate(2, new Date(System.currentTimeMillis()));
            preparedStatement.setDouble(3, worker.getSalary());
            preparedStatement.setString(4, worker.getStatus().toString());
            preparedStatement.setString(5, worker.getPosition().toString());
            preparedStatement.setLong(6, worker.getCoordinates().getX());
            preparedStatement.setFloat(7, worker.getCoordinates().getY());
            preparedStatement.setFloat(8, worker.getOrganization().getAnnualTurnover());
            preparedStatement.setString(9, worker.getOrganization().getPostalAddress().getStreet());
            preparedStatement.setString(10, worker.getOrganization().getPostalAddress().getZipCode());
            preparedStatement.setString(11, userName);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e){
            e.printStackTrace();
            Server.LOGGER.log(Level.WARNING, "SQL exception.");
        }
    }
    public boolean checkIfWorkerExists(int id){
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement("SELECT idP FROM workers WHERE idP = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet =preparedStatement.executeQuery();
            boolean result = false;
            if (resultSet.next())
                result = true;
            preparedStatement.close();
            return result;
        }
        catch (SQLException e){
            e.printStackTrace();
            Server.LOGGER.log(Level.WARNING, "SQL exception.");
        }
        return false;
    }
    public void updateWorkerIfUserNamesEqual(Worker worker, String name, int oldID){
        PreparedStatement preparedStatement;
        try {
            System.out.println(oldID);
            preparedStatement = connection.prepareStatement("UPDATE workers SET nameP = ?, dateP = ?, salaryP = ?, statusP = ?, positionP = ?, xCoordinateP = ?, yCoordinateP = ?, annualTurnOverP = ?, streetP = ?, zipCodeP = ? WHERE idP = ? AND userName = ?");
            preparedStatement.setString(1, worker.getName());
            preparedStatement.setDate(2, new Date(System.currentTimeMillis()));
            preparedStatement.setDouble(3, worker.getSalary());
            preparedStatement.setString(4, worker.getStatus().toString());
            preparedStatement.setString(5, worker.getPosition().toString());
            preparedStatement.setLong(6, worker.getCoordinates().getX());
            preparedStatement.setFloat(7, worker.getCoordinates().getY());
            preparedStatement.setFloat(8, worker.getOrganization().getAnnualTurnover());
            preparedStatement.setString(9, worker.getOrganization().getPostalAddress().getStreet());
            preparedStatement.setString(10, worker.getOrganization().getPostalAddress().getZipCode());
            preparedStatement.setInt(11, oldID);
            preparedStatement.setString(12, name);
            preparedStatement.executeUpdate();
            preparedStatement.close();

        }
        catch (SQLException e){
            e.printStackTrace();
            Server.LOGGER.log(Level.WARNING, "SQL exception.");
        }
    }
    public HashSet<Worker> getAllWorkers(){
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM workers")){
            ResultSet resultSet = preparedStatement.executeQuery();
            HashSet<Worker> collection = new HashSet<>();
            while (resultSet.next()){
                Worker worker = new Worker();
                worker.setCreationDate(resultSet.getDate("dateP"));
                worker.setId(resultSet.getInt("idP"));
                worker.setName(resultSet.getString("nameP"));
                worker.setStatus(Enum.valueOf(Status.class, resultSet.getString("statusP")));
                worker.setPosition(Enum.valueOf(Position.class, resultSet.getString("positionP")));
                worker.setSalary(resultSet.getDouble("salaryP"));
                Coordinates coordinates = new Coordinates();
                coordinates.setX(resultSet.getLong("xCoordinateP"));
                coordinates.setY(resultSet.getFloat("yCoordinateP"));
                worker.setCoordinates(coordinates);
                Organization organization = new Organization();
                organization.setAnnualTurnover(resultSet.getFloat("annualTurnOverP"));
                Address address = new Address();
                address.setStreet(resultSet.getString("streetP"));
                address.setZipCode(resultSet.getString("zipCodeP"));
                organization.setPostalAddress(address);
                worker.setOrganization(organization);
                collection.add(worker);
            }

            return collection;
        }
        catch (SQLException e){
            e.printStackTrace();
            Server.LOGGER.log(Level.WARNING, "SQL exception.");
        }
        return null;
    }
    public void removeWorkerIfUserNamesEqual(int id, String name){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM workers WHERE idP = ? AND userName = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e){
            e.printStackTrace();
            Server.LOGGER.log(Level.WARNING, "SQL exception.");
        }
    }
    public void removeGraterWorkersIfUserNamesEqual(Worker worker, String name){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM workers WHERE salaryP > ? AND userName = ?");
            preparedStatement.setDouble(1, worker.getSalary());
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e){
            e.printStackTrace();
            Server.LOGGER.log(Level.WARNING, "SQL exception.");
        }
    }
    public void removeLowerWorkersIfUserNamesEqual(Worker worker, String name){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM workers WHERE salaryP < ? AND userName = ?");
            preparedStatement.setDouble(1, worker.getSalary());
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e){
            e.printStackTrace();
            Server.LOGGER.log(Level.WARNING, "SQL exception.");
        }
    }
    public void removeWorkersIfUserNamesEqual(String name){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM workers WHERE userName = ?");
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e){
            e.printStackTrace();
            Server.LOGGER.log(Level.WARNING, "SQL exception.");
        }
    }




}
