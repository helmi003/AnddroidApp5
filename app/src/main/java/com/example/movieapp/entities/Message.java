package com.example.movieapp.entities;

import com.google.firebase.firestore.ServerTimestamp;
import java.util.Date;

public class Message {

    private String senderId;
    private String message;
    private String role;
    private Date timestamp;

    // No-argument constructor required by Firebase
    public Message() {
    }

    public Message(String senderId, String message, String role, Date timestamp) {
        this.senderId = senderId;
        this.message = message;
        this.role = role;
        this.timestamp = timestamp;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @ServerTimestamp
    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
