package com.project.travelplacesjournal.data.dao;

import androidx.room.*;

import com.project.travelplacesjournal.data.entities.PlaceImage;

import java.util.List;

@Dao
public interface PlaceImageDao {

    @Insert
    long insert(PlaceImage image);

    @Update
    void update(PlaceImage image);

    @Delete
    void delete(PlaceImage image);

    @Query("SELECT * FROM place_images WHERE placeId = :placeId")
    List<PlaceImage> getByPlaceId(int placeId);

    @Query("DELETE FROM place_images WHERE placeId = :placeId")
    void deleteByPlaceId(int placeId);
}