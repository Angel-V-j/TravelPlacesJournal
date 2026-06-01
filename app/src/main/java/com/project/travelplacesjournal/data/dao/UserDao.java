package com.project.travelplacesjournal.data.dao;

import androidx.room.*;

import com.project.travelplacesjournal.data.entities.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    long insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    @Query("SELECT * FROM users WHERE id = :id")
    User getById(int id);

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    User getByEmail(String email);

    @Query("UPDATE users SET blocked = :blocked WHERE id = :userId")
    void setBlocked(int userId, boolean blocked);
}