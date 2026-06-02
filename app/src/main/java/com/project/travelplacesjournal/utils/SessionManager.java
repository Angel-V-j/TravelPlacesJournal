package com.project.travelplacesjournal.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "TravelDiarySession";

    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_USER_ID = "current_user_id";
    private static final String KEY_IS_ADMIN = "is_admin";

    private final SharedPreferences pref;
    private final SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        this.pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.editor = pref.edit();
    }
    public void createLoginSession(int userId, boolean isAdmin) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putInt(KEY_USER_ID, userId);
        editor.putBoolean(KEY_IS_ADMIN, isAdmin);
        editor.apply();
    }
    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }
    public int getUserId() {
        return pref.getInt(KEY_USER_ID, -1);
    }
    public boolean isAdmin() {
        return pref.getBoolean(KEY_IS_ADMIN, false);
    }
    public void logoutUser() {
        editor.clear();
        editor.apply();
    }
}
