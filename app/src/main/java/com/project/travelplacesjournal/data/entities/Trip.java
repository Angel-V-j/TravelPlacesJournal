package com.project.travelplacesjournal.data.entities;

import androidx.room.*;

@Entity(
        tableName = "trips",
        foreignKeys = @ForeignKey(
                entity = User.class,
                parentColumns = "id",
                childColumns = "userId",
                onDelete = ForeignKey.CASCADE
        )
)
public class Trip {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int userId;

    private String name;

    private String description;

    private String startDate;

    private String endDate;

    private String coverImagePath;

    public Trip() {
    }

    public Trip(int id, int userId, String name, String description, String startDate, String endDate, String coverImagePath) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.coverImagePath = coverImagePath;
    }

    public Trip(int userId, String name, String description, String startDate, String endDate, String coverImagePath) {
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.coverImagePath = coverImagePath;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCoverImagePath() {
        return coverImagePath;
    }

    public void setCoverImagePath(String coverImagePath) {
        this.coverImagePath = coverImagePath;
    }
}