package com.example.android.simpleloginvolley;

/**
 * Created by Hesham on 12/3/2017.
 */

public class Contact {

    private int userId, value;
    private String  meterId, timestamp;

    public Contact(int sUserId, String sMeterId, int sValue, String sTimestamp){
        userId = sUserId;
        meterId = sMeterId;
        value = sValue;
        timestamp = sTimestamp;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getMeterId() {
        return meterId;
    }

    public void setMeterId(String meterId) {
        this.meterId = meterId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
