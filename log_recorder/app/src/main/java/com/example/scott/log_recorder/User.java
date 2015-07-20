package com.example.scott.log_recorder;

/**
 * Created by Scott on 7/16/2015.
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    private String username, password;
    private UserDataType userType;

    public enum UserDataType {
        INVALID, VALID, ErrSqlExec, ErrNoData, ErrMultiData
    }

    public String enum2String() {
        return enum2String(this.userType);
    }
        public String enum2String(UserDataType userType) {
        switch(userType) {
            case INVALID:
                return "INVALID";
            case VALID:
                return "VALID";
            case ErrSqlExec:
                return "ErrSqlExec";
            case ErrNoData:
                return "ErrNoData";
            case ErrMultiData:
                return "ErrMultiData";
            default:
                return "Unknown";
        }
    }

    public User() {
        this.userType = UserDataType.INVALID;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.userType = UserDataType.INVALID;
    }

    public User (String username, String password, UserDataType userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserType(UserDataType userType) {
        this.userType = userType;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public UserDataType getUserType() {
        return this.userType;
    }

    public void update (User user) {
        this.username = user.username;
        this.password = user.password;
        this.userType = user.userType;
    }

    public void update (UserDataType userType) {
        this.setUserType(userType);
    }

    public void update (String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void update (String username, String password, UserDataType userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public boolean isValidUsernamePattern() {
        return isValidUsernamePattern(username);
    }

    public boolean isValidUsernamePattern(String username) {
        String str = username;

        //remove space in the begining and end
        str.replace("^\\s+", "");
        str.replace("\\s+$", "");


        String pattern = "\\s";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);

        if(m.find()) {
            return false;
        }

        if(str.length() == 0) {
            return false;
        }

        return true;

        /*
        for(int i=1; i<5; i++) {
            if(Integer.parseInt(m.group(i)) < 0 || Integer.parseInt(m.group(i)) > 255) {
                return false;
            }
        }
        */

    }

    public boolean isValidPasswordPattern() {
        return isValidPasswordPattern(password);
    }

    public boolean isValidPasswordPattern(String password) {
        String str = password;

        //remove space in the begining and end
        str.replace("^\\s+", "");
        str.replace("\\s+$", "");


        String pattern = "\\s";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);

        if(m.find()) {
            return false;
        }

        if(str.length() == 0) {
            return false;
        }

        return true;

    }

    public void clear() {
        userType = UserDataType.INVALID;
        username = "";
        password = "";
    }

}
