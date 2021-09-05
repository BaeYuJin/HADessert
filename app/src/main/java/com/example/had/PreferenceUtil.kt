package com.example.had

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.had.dataclass.DataSearch
import org.json.JSONArray
import org.json.JSONException

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

    /*fun setRecentWords(context: Context, values: MutableList<DataSearch>) {
        val prefs = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val set : Set<String> = HashSet()
        set.plus(values)
        editor.putStringSet("RE_WORD", set)
        editor.apply()
        editor.commit()
    }

    fun getRecentWords(context: Context): MutableList<String?> {
        val prefs = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val set : String? = prefs.getString("RE_WORD", null)
        return mutableListOf(set)
    }
*/

    fun setRecentWords(context: Context, key : String, values: MutableList<DataSearch>) {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val jArray = JSONArray()
        for(i in 0 until values.size){
            Log.d("values ", values[i].toString())
            jArray.put(values[i])
        }

        if(values.isNotEmpty()){
            editor.putString(key, jArray.toString())
        } else{
          editor.putString(key, null)
        }

        editor.commit()
    }

    fun getRecentWords(context: Context, key : String): MutableList<DataSearch> {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val json = prefs.getString(key, null)
        val list = mutableListOf<DataSearch>()
        if (json != null) {
            try {
                val a = JSONArray(json)
                for (i in 0 until a.length()) {
                    val str = a.optString(i) as DataSearch
                    if (str != null) {
                        list.add(str)
                    }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return list
    }


    fun clearUser(context: Context) {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = prefs.edit()
        editor.clear()
        editor.commit()
    }
}