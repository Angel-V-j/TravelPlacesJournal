package com.project.travelplacesjournal.data.dao;

import androidx.room.*;

import com.project.travelplacesjournal.data.entities.Trip;

import java.util.List;

@Dao
public interface TripDao {

    @Insert
    long insert(Trip trip);

    @Update
    void update(Trip trip);

    @Delete
    void delete(Trip trip);

    @Query("SELECT * FROM trips")
    List<Trip> getAll();

    @Query("SELECT * FROM trips WHERE id = :id")
    Trip getById(int id);

    @Query("SELECT * FROM trips WHERE userId = :userId")
    List<Trip> getByUser(int userId);
}