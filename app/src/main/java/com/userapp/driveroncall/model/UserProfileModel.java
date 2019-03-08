package com.userapp.driveroncall.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfileModel {

    @SerializedName("users_id")
    @Expose
    private int usersId;
    @SerializedName("users_first_name")
    @Expose
    private String usersFirstName;
    @SerializedName("users_last_name")
    @Expose
    private String usersLastName;
    @SerializedName("users_contact_no")
    @Expose
    private String usersContactNo;
    @SerializedName("users_email")
    @Expose
    private String usersEmail;
    @SerializedName("users_password")
    @Expose
    private String usersPassword;
    @SerializedName("users_birth_date")
    @Expose
    private Object usersBirthDate;
    @SerializedName("users_address")
    @Expose
    private String usersAddress;
    @SerializedName("users_pincode")
    @Expose
    private String usersPincode;


    public int getUsersId() {
        return usersId;
    }

    public void setUsersId(int usersId) {
        this.usersId = usersId;
    }

    public String getUsersFirstName() {
        return usersFirstName;
    }

    public void setUsersFirstName(String usersFirstName) {
        this.usersFirstName = usersFirstName;
    }

    public String getUsersLastName() {
        return usersLastName;
    }

    public void setUsersLastName(String usersLastName) {
        this.usersLastName = usersLastName;
    }

    public String getUsersContactNo() {
        return usersContactNo;
    }

    public void setUsersContactNo(String usersContactNo) {
        this.usersContactNo = usersContactNo;
    }

    public String getUsersEmail() {
        return usersEmail;
    }

    public void setUsersEmail(String usersEmail) {
        this.usersEmail = usersEmail;
    }

    public String getUsersPassword() {
        return usersPassword;
    }

    public void setUsersPassword(String usersPassword) {
        this.usersPassword = usersPassword;
    }

    public Object getUsersBirthDate() {
        return usersBirthDate;
    }

    public void setUsersBirthDate(Object usersBirthDate) {
        this.usersBirthDate = usersBirthDate;
    }

    public String getUsersAddress() {
        return usersAddress;
    }

    public void setUsersAddress(String usersAddress) {
        this.usersAddress = usersAddress;
    }

    public String getUsersPincode() {
        return usersPincode;
    }

    public void setUsersPincode(String usersPincode) {
        this.usersPincode = usersPincode;
    }
}
