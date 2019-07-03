package com.esphereal.bair.funloot.util

import android.content.Context
import android.preference.PreferenceManager

class PrefUtil {
    companion object {
        private const val ID_TOKEN = "idToken"
        fun getIdToken(context: Context): String {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getString(ID_TOKEN, "")!!
        }
        fun setIdToken(token: String, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putString(ID_TOKEN, token)
            editor.apply()
        }
    }
}