package com.project.travelplacesjournal.data.dao;

import androidx.room.*;

import com.project.travelplacesjournal.data.entities.TripPlace;

import java.util.List;

@Dao
public interface TripPlaceDao {

    @Insert
    void insert(TripPlace tripPlace);

    @Delete
    void delete(TripPlace tripPlace);

    @Query("SELECT * FROM TripPlace WHERE tripId = :tripId")
    List<TripPlace> getByTrip(int tripId);

    @Query("SELECT * FROM TripPlace WHERE placeId = :placeId")
    List<TripPlace> getByPlace(int placeId);
}