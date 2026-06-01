package com.project.travelplacesjournal.data.entities;

import androidx.room.Entity;

@Entity(
        primaryKeys = {"tripId", "placeId"}
)
public class TripPlace {

    private int tripId;

    private int placeId;
}