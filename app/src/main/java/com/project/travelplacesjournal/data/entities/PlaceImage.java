package com.project.travelplacesjournal.data.entities;

import androidx.room.*;

@Entity(
        tableName = "place_images",
        foreignKeys = @ForeignKey(
                entity = Place.class,
                parentColumns = "id",
                childColumns = "placeId",
                onDelete = ForeignKey.CASCADE
        )
)
public class PlaceImage {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int placeId;

    private String imagePath;

    public PlaceImage() {
    }

    public PlaceImage(int id, int placeId, String imagePath) {
        this.id = id;
        this.placeId = placeId;
        this.imagePath = imagePath;
    }

    public PlaceImage(int placeId, String imagePath) {
        this.placeId = placeId;
        this.imagePath = imagePath;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
