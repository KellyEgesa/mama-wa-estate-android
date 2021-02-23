
package com.mamawaestate.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserSignUp {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private UserData userData;

    /**
     * No args constructor for use in serialization
     * 
     */
    public UserSignUp() {
    }

    /**
     * 
     * @param userData
     * @param success
     * @param status
     */
    public UserSignUp(String success, Integer status, UserData userData) {
        super();
        this.success = success;
        this.status = status;
        this.userData = userData;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

}
