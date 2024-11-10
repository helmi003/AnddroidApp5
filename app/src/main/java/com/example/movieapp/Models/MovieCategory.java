package com.example.movieapp.Models;

public enum MovieCategory {
    ACTION, DRAMA, COMEDY, HORROR, ROMANCE, THRILLER, DOCUMENTARY, SCI_FI, ANIMATION;

    // This method can be used to convert a String to the corresponding enum
    public static MovieCategory fromString(String category) {
        switch (category) {
            case "ACTION":
                return ACTION;
            case "DRAMA":
                return DRAMA;
            case "COMEDY":
                return COMEDY;
            case "HORROR":
                return HORROR;
            case "ROMANCE":
                return ROMANCE;
            case "THRILLER":
                return THRILLER;
            case "DOCUMENTARY":
                return DOCUMENTARY;
            case "SCI_FI":
                return SCI_FI;
            case "ANIMATION":
                return ANIMATION;
            default:
                return null; // or throw an exception if necessary
        }
    }
}
