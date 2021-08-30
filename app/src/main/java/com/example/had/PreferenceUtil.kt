package com.example.had

import android.content.Context
import android.content.SharedPreferences

/*class PreferenceUtil(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("my_id", Context.MODE_PRIVATE)

    fun getString(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }
    fun setString(key: String, str: String) {
        prefs.edit().putString(key, str).apply()
    }
}*/

object PreferenceUtil {
    private val MY_ACCOUNT : String = "account"

    fun setUserId(context: Context, input: String) {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_ID", input)
        editor.commit()
    }

    fun getUserId(context: Context): String {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return prefs.getString("MY_ID", "").toString()
    }

    fun setAutoLogin(context: Context, input:String){
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_LOGIN", input)
        editor.commit()
    }
    fun getAutoLogin(context: Context): String {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return prefs.getString("MY_LOGIN", "").toString()
    }

    fun setRecentWords(context: Context, values: ArrayList<DataSearch>) {
        val prefs = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val set : Set<String> = HashSet()
        set.plus(values)
        editor.putStringSet("RE_WORD", set)
        editor.apply()
        editor.commit()
    }

    /*fun getRecentWords(context: Context): ArrayList<String> {
        val prefs = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val set : Set<String> = prefs.getStringSet("RE_WORD", null)
        return ArrayList(set)
    }*/

    fun clearUser(context: Context) {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = prefs.edit()
        editor.clear()
        editor.commit()
    }
}