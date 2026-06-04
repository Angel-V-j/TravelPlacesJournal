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

    private String imageUri;

    public PlaceImage() {
    }

    public PlaceImage(int id, int placeId, String imageUri) {
        this.id = id;
        this.placeId = placeId;
        this.imageUri = imageUri;
    }

    public PlaceImage(int placeId, String imageUri) {
        this.placeId = placeId;
        this.imageUri = imageUri;
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

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
