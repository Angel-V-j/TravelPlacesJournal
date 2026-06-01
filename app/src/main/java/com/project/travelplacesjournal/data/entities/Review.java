package com.project.travelplacesjournal.data.entities;

import androidx.room.*;

@Entity(
        tableName = "reviews"
)
public class Review {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int placeId;

    private int userId;

    private int rating;

    private String comment;

    private String createdAt;

    public Review() {
    }

    public Review(int id, int placeId, int userId, int rating, String comment, String createdAt) {
        this.id = id;
        this.placeId = placeId;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public Review(int placeId, int userId, int rating, String comment, String createdAt) {
        this.placeId = placeId;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}