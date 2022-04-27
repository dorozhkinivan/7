package Server;

import CollectionClasses.Worker;
import UserInfo.UserInfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseJava {
    private String URL;
    private String userName;
    private String password;
    public void connect(){
        try{
            Connection connection = DriverManager.getConnection(URL, userName, password);
        }
        catch (SQLException e){

        }
    }

    public void addUser(UserInfo userInfo){

    }




}
