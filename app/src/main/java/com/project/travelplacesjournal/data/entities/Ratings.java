package com.project.travelplacesjournal.data.entities;

public enum Ratings implements Adaptable {
    ALL("All", 0),
    ONE("1+", 1),
    TWO("2+", 2),
    THREE("3+", 3),
    FOUR("4+", 4),
    FIVE("5", 5);

    private final String name;
    private final int minRating;
    Ratings(String name, int minRating){
        this.name = name;
        this.minRating = minRating;
    }

    @Override
    public String getName(){
        return name;
    }

    public int getMinRating() {
        return minRating;
    }
}
