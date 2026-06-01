package com.project.travelplacesjournal.data.dao;

import androidx.room.*;

import com.project.travelplacesjournal.data.entities.Category;

import java.util.List;

@Dao
public interface CategoryDao {

    @Insert
    long insert(Category category);

    @Update
    void update(Category category);

    @Delete
    void delete(Category category);

    @Query("SELECT * FROM categories")
    List<Category> getAll();

    @Query("SELECT * FROM categories WHERE id = :id")
    Category getById(int id);
}