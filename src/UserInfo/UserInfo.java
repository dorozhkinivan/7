package UserInfo;

import ClientServer.ClientServer;
import Exceptions.ServerException;
import UserDataManager.UserConsole;
import org.apache.commons.codec.digest.DigestUtils;
import static org.apache.commons.codec.digest.MessageDigestAlgorithms.SHA_384;

import java.io.Serializable;
import java.util.Objects;

public class UserInfo implements Serializable {
    private String name;
    private String passHashed;

    public String getPassHashed() {
        return passHashed;
    }

    private UserInfo(String name, String passHashed) {
        this.name = name;
        this.passHashed = passHashed;
    }

    private static String passwordEnter (String name, Mode mode, ClientServer clientServer, UserConsole.Input.PrintsAndAsks printsAndAsks) throws ServerException{
        printsAndAsks.print("Введите пароль.");
        String pass = new DigestUtils(SHA_384).digestAsHex(printsAndAsks.ask() + "SALT!");
        UserInfo userInfo = new UserInfo(name, pass);
        switch (mode){
            case REGISTRATION:
                if ((Boolean) clientServer.sendAndGetData(userInfo)){
                    printsAndAsks.print("Данное имя пользователя было занято. Пройдите регистрацию заново ещё раз.");
                }
                return pass;
            case AUTHORIZATION:
                if (!(Boolean) clientServer.sendAndGetData(userInfo))
                    passwordEnter(name, mode, clientServer, printsAndAsks);
                else return pass;
            default:
                return null;
        }
    }


    public static UserInfo newUserInfo(ClientServer clientServer, UserConsole.Input.PrintsAndAsks printsAndAsks){
        try {
            printsAndAsks.print("Вход/регистрация. \nВведите имя пользователя.");
            String nameStr = printsAndAsks.ask();
            Mode mode;
            if((Boolean)clientServer.sendAndGetData(nameStr)){
                printsAndAsks.print("Введите пароль, для входа в аккаунт +" + nameStr);
                mode = Mode.AUTHORIZATION;
            }
            else {
                printsAndAsks.print("Придумайте пароль.");
                mode =Mode.REGISTRATION;
            }
            return new UserInfo(nameStr, passwordEnter(nameStr,mode, clientServer, printsAndAsks));
        }
        catch (ServerException serverException){
            System.out.println("Ошибка сервера.");
            System.exit(0);
            return null;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserInfo)) return false;
        UserInfo userInfo = (UserInfo) o;
        return Objects.equals(name, userInfo.name) && Objects.equals(passHashed, userInfo.passHashed);
    }

    public String getName() {
        return name;
    }


    private enum Mode{
        REGISTRATION, AUTHORIZATION;
    }

}
