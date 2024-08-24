package com.pettcare.app.sharedprefs

import android.content.Context

class SharedPreferences(context: Context) {

    private val sharedPrefs = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)

    fun storeString(key: String, value: String) {
        sharedPrefs.edit().putString(key, value).apply()
    }

    fun getString(key: String, defaultValue: String? = null) = sharedPrefs.getString(key, defaultValue)

    companion object {
        private const val FILE_NAME = "PettCareSharedPrefs"
        const val ID_KEY = "id"
        const val TOKEN_KEY = "token"
    }
}
