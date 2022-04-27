package Server;

import CollectionClasses.*;
import UserInfo.UserInfo;

import java.sql.*;
import java.util.HashSet;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;

public class RequestsToUsersDB {
    private Connection connection;
    private ReentrantLock reentrantLock;
    public RequestsToUsersDB(ReentrantLock reentrantLock){
        this.reentrantLock = reentrantLock;
        try {
            reentrantLock.lock();
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:7340/studs", "s335067", "vyi143");
            reentrantLock.unlock();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public boolean userNameExists(String name){
        reentrantLock.lock();
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
            reentrantLock.unlock();
            return result;
        }
        catch (SQLException e){
            e.printStackTrace();
            Server.LOGGER.log(Level.WARNING, "SQL exception.");
            reentrantLock.unlock();
            return false;
        }

    }

    public boolean passwordIsCorrect(UserInfo userInfo){
        reentrantLock.lock();
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
            reentrantLock.unlock();
            return result;
        }
        catch (SQLException e){
            e.printStackTrace();
            Server.LOGGER.log(Level.WARNING, "SQL exception.");
            reentrantLock.unlock();
            return false;
        }
    }

    public void addUser(UserInfo userInfo){
        reentrantLock.lock();
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
        reentrantLock.unlock();
    }

}
