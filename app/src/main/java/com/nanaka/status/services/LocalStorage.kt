package com.nanaka.status.services

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class LocalStorage {
    companion object {
        private lateinit var prefs: SharedPreferences

        fun init(activity: Activity){
            prefs = activity.getPreferences(Context.MODE_PRIVATE)
        }

        var username: String
            get() = prefs.getString("username", null) ?: ""
            set(value) = prefs.edit().putString("username", value).apply()

        var token: String
            get() = prefs.getString("token", null) ?: ""
            set(value) = prefs.edit().putString("token", value).apply()
    }
}