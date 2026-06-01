package com.project.travelplacesjournal.data.dao;

import androidx.room.*;

import com.project.travelplacesjournal.data.entities.Review;

import java.util.List;

@Dao
public interface ReviewDao {

    @Insert
    long insert(Review review);

    @Update
    void update(Review review);

    @Delete
    void delete(Review review);

    @Query("SELECT * FROM reviews WHERE placeId = :placeId")
    List<Review> getReviewsForPlace(int placeId);

    @Query("SELECT AVG(rating) FROM reviews WHERE placeId = :placeId")
    Double getAverageRating(int placeId);
}