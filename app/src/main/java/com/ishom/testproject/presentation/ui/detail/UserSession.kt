package com.ishom.testproject.presentation.ui.detail

import android.content.Context
import android.content.SharedPreferences

class UserSession(private val context: Context) {

    companion object {
        private const val PARAM_ID = "_id"
        private const val PARAM_SESSION = "_session"
    }

    private var _userId: String? = null
    var userId: String?
        get() {
            return _userId
        }
        set(value) {
            getSharePref().edit().putString(PARAM_ID, value).apply()
            _userId = value
        }

    init {
        _userId = getSharePref().getString(PARAM_ID, "")
    }

    private fun getSharePref(): SharedPreferences {
        return context.getSharedPreferences(PARAM_SESSION, Context.MODE_PRIVATE)
    }
}