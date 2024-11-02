package com.example.movieapp.Models;


public class Showtime {
    private String roomName; // Name of the room
    private String showtime; // Show time in a specific format (e.g., "HH:mm")

    // No-argument constructor (required for Firebase)
    public Showtime() {
    }

    public Showtime(String roomName, String showtime) {
        this.roomName = roomName;
        this.showtime = showtime;
    }

    // Getters and Setters
    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getShowtime() {
        return showtime;
    }

    public void setShowtime(String showtime) {
        this.showtime = showtime;
    }
}

