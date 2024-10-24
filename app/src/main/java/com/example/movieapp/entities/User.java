package com.example.movieapp.entities;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="user")
public class User {
    @PrimaryKey(autoGenerate = true)
    public int id;
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

    public User(int id, String username, Long phone, String email, Role role, String password) {
        this.id = id;
        this.username = username;
        this.phone = phone;
        this.email = email;
        this.role = role;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", phone=" + phone +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", password='" + password + '\'' +
                '}';
    }
}
