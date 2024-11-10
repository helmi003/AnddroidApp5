package com.example.movieapp.Models;

public enum Room {
    ROOM_A("Room A", 100),
    ROOM_B("Room B", 150),
    ROOM_C("Room C", 200),
    ROOM_D("Room D", 50);

    private final String name;
    private final int numberOfPlaces;

    Room(String name, int numberOfPlaces) {
        this.name = name;
        this.numberOfPlaces = numberOfPlaces;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    @Override
    public String toString() {
        return name + " (" + numberOfPlaces + " places)";
    }
}

