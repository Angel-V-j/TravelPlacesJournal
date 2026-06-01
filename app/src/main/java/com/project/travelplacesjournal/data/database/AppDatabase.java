package com.project.travelplacesjournal.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.project.travelplacesjournal.data.dao.*;
import com.project.travelplacesjournal.data.entities.*;

@Database(
        entities = {
                User.class,
                Place.class,
                PlaceImage.class,
                Trip.class,
                TripPlace.class,
                Category.class,
                Review.class
        },
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract PlaceDao placeDao();

    public abstract PlaceImageDao placeImageDao();

    public abstract TripDao tripDao();

    public abstract TripPlaceDao tripPlaceDao();

    public abstract CategoryDao categoryDao();

    public abstract ReviewDao reviewDao();
}