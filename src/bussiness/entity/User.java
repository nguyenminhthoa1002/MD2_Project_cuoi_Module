package bussiness.entity;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private int userID;
    private String userName;
    private String password;
    private String confirmPassword;
    private String fullName;
    private boolean permission;
    private Date date;
    private boolean status;
    private String email;
    private String phone;

    public User() {
    }

    public User(int userID, String userName, String password, String confirmPassword, String fullName, boolean permission, Date date, boolean status, String email, String phone) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.fullName = fullName;
        this.permission = permission;
        this.date = date;
        this.status = status;
        this.email = email;
        this.phone = phone;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isPermission() {
        return permission;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
