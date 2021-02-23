
package com.mamawaestate.android.com.moringaschool.mamawaestate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Accounts {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private com.mamawaestate.android.com.moringaschool.mamawaestate.Data data;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Accounts() {
    }

    /**
     * 
     * @param data
     * @param success
     * @param status
     */
    public Accounts(String success, Integer status, com.mamawaestate.android.com.moringaschool.mamawaestate.Data data) {
        super();
        this.success = success;
        this.status = status;
        this.data = data;
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

    public com.mamawaestate.android.com.moringaschool.mamawaestate.Data getData() {
        return data;
    }

    public void setData(com.mamawaestate.android.com.moringaschool.mamawaestate.Data data) {
        this.data = data;
    }

}
