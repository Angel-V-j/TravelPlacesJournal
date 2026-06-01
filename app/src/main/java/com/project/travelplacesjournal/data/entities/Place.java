package com.project.travelplacesjournal.data.entities;

import androidx.room.*;

@Entity(
        tableName = "places",
        foreignKeys = @ForeignKey(
                entity = User.class,
                parentColumns = "id",
                childColumns = "userId",
                onDelete = ForeignKey.CASCADE
        )
)
public class Place {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int userId;

    private int categoryId;

    private String name;

    private String description;

    private double latitude;

    private double longitude;

    private String visitDate;

    private int rating;

    private String notes;

    private boolean isPublic;

    public Place() {
    }

    public Place(int id, int userId, int categoryId, String name, String description, double latitude, double longitude, String visitDate, int rating, String notes, boolean isPublic) {
        this.id = id;
        this.userId = userId;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.visitDate = visitDate;
        this.rating = rating;
        this.notes = notes;
        this.isPublic = isPublic;
    }

    public Place(int userId, int categoryId, String name, String description, double latitude, double longitude, String visitDate, int rating, String notes, boolean isPublic) {
        this.userId = userId;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.visitDate = visitDate;
        this.rating = rating;
        this.notes = notes;
        this.isPublic = isPublic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }
}