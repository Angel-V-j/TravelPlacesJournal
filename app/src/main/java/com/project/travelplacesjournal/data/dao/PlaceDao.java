package com.project.travelplacesjournal.data.dao;

import androidx.room.*;

import com.project.travelplacesjournal.data.entities.Place;

import java.util.List;

@Dao
public interface PlaceDao {

    @Insert
    long insert(Place place);

    @Update
    void update(Place place);

    @Delete
    void delete(Place place);

    @Query("SELECT * FROM places")
    List<Place> getAll();

    @Query("SELECT * FROM places WHERE id = :id")
    Place getById(int id);

    @Query("SELECT * FROM places WHERE userId = :userId")
    List<Place> getByUser(int userId);

    @Query("SELECT * FROM places WHERE isPublic = 1")
    List<Place> getPublicPlaces();

    @Query(" SELECT * FROM places WHERE userId = :userId " +
            "ORDER BY visitDate DESC")
    List<Place> getUserPlacesSorted(int userId);

    @Query("SELECT * FROM places WHERE categoryId = :categoryId")
    List<Place> filterByCategory(int categoryId);

    @Query("SELECT * FROM places WHERE rating >= :rating")
    List<Place> filterByRating(int rating);

    @Query("SELECT * FROM places WHERE name LIKE '%' || :search || '%' " +
            "OR description LIKE '%' || :search || '%'")
    List<Place> search(String search);
}