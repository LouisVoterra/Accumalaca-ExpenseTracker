package com.accumalaca.expensestracking.util

import android.content.Context
import android.content.SharedPreferences

// buat kunci kamar user, biar dia gak nyasar tiap buka app wkwk
class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    fun saveLoginSession(username: String) {
        prefs.edit().putString("LOGGED_USER", username).apply()
    }

    fun getLoggedInUser(): String? {
        return prefs.getString("LOGGED_USER", null)
    }

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}
