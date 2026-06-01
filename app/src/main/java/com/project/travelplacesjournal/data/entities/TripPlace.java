package com.project.travelplacesjournal.data.entities;

import androidx.room.Entity;

@Entity(
        primaryKeys = {"tripId", "placeId"}
)
public class TripPlace {

    private int tripId;

    private int placeId;

    public TripPlace(int tripId, int placeId) {
        this.tripId = tripId;
        this.placeId = placeId;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }
}
