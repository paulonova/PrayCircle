package se.android.praycircle.praycircle.objects;

/**
 * Created by Paulo Vila Nova on 2016-11-19.
 */

public class User {

    private String userName;
    private int userId;
    private String userEmail;
    private String userPassword;

    

    public User() {
    }

    public User(int userId, String userName, String userEmail, String userPassword){
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
