package Server;

import CollectionClasses.*;
import UserInfo.UserInfo;

import java.sql.*;
import java.util.HashSet;
import java.util.logging.Level;

public class RequestsToUsersDB {
    private Connection connection;
    public RequestsToUsersDB(){
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:7340/studs", "s335067", "vyi143");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public boolean userNameExists(String name){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) AS c FROM users WHERE name = ?");
            preparedStatement.setString(1, name);
            ResultSet resultSet =preparedStatement.executeQuery();
            resultSet.next();
            int k = resultSet.getInt("c");
            System.out.println(k);
            boolean result = false;
            if (k > 0)
                result = true;
            preparedStatement.close();
            return result;
        }
        catch (SQLException e){
            e.printStackTrace();
            Server.LOGGER.log(Level.WARNING, "SQL exception.");
            return false;
        }
    }

    public boolean passwordIsCorrect(UserInfo userInfo){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) AS c FROM users WHERE name = ? AND pass = ?");
            preparedStatement.setString(1, userInfo.getName());
            preparedStatement.setString(2, userInfo.getPassHashed());
            ResultSet resultSet =preparedStatement.executeQuery();
            resultSet.next();
            boolean result = false;
            if (resultSet.getInt("c") > 0)
                result = true;
            preparedStatement.close();
            return result;
        }
        catch (SQLException e){
            e.printStackTrace();
            Server.LOGGER.log(Level.WARNING, "SQL exception.");            return false;
        }
    }

    public void addUser(UserInfo userInfo){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (name, pass) VALUES (?, ?)");
            preparedStatement.setString(1, userInfo.getName());
            preparedStatement.setString(2, userInfo.getPassHashed());
            preparedStatement.executeUpdate();
            preparedStatement.close();

        }
        catch (SQLException e){
            e.printStackTrace();
            Server.LOGGER.log(Level.WARNING, "SQL exception.");
        }
    }

}
