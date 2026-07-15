package ec.edu.uisek.githubclient.services

import android.content.Context
import android.content.SharedPreferences

class AuthService(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("github_prefs", Context.MODE_PRIVATE)

    fun saveAuth(username: String, token: String) {
        sharedPreferences.edit()
            .putString("username", username.trim())
            .putString("token", token.trim())
            .apply()
    }

    fun getUsername(): String? = sharedPreferences.getString("username", null)
    fun getToken(): String? = sharedPreferences.getString("token", null)

    fun logout() {
        sharedPreferences.edit().clear().apply()
    }

    fun isLoggedIn(): Boolean = !getToken().isNullOrBlank()
}