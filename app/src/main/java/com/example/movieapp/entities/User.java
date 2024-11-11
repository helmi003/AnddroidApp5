package com.example.movieapp.entities;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName="user")
public class User implements Serializable {
    @PrimaryKey()
    @NonNull
    public String id;
    @ColumnInfo()
    public String username;
    @ColumnInfo()
    public Long phone;
    @ColumnInfo()
    public String email;
    @ColumnInfo()
    public Role role = Role.USER;
    @ColumnInfo()
    public String password;
    @ColumnInfo(name = "is_blocked")
    public boolean isBlocked = false;
    @ColumnInfo(name = "is_verified")
    public boolean isVerified = false;

    public User(@NonNull String id, String username, Long phone, String email, Role role, String password, boolean isBlocked, boolean isVerified) {
        this.id = id;
        this.username = username;
        this.phone = phone;
        this.email = email;
        this.role = role;
        this.password = password;
        this.isBlocked = isBlocked;
        this.isVerified = isVerified;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", phone=" + phone +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", password='" + password + '\'' +
                ", isBlocked=" + isBlocked +
                ", isVerified=" + isVerified +
                '}';
    }
}
