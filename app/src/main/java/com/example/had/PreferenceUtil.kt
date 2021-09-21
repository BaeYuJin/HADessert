package com.example.had

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import com.example.had.dataclass.DataSearch
import org.json.JSONArray
import org.json.JSONException
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.nio.charset.Charset

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
            Log.d("values ", values[i].word)
            jArray.put(values[i].word)
        }

        if(values.isNotEmpty()){
            editor.putString(key, jArray.toString())
        } else{
          editor.putString(key, null)
        }

        editor.apply()
    }

    fun isRecentWordNull(context: Context, key : String) : Boolean {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val json = prefs.getString(key, null)
        return json == null
    }

    fun getRecentWords(context: Context, key : String, list : MutableList<DataSearch>) {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val json = prefs.getString(key, null)
        if (json != null) {
            try {
                val a = JSONArray(json)
                for (i in 0 until a.length()) {
                    val str = a.optString(i)
                    if (str != null) {
                        list.add(DataSearch(str))
                    }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

    }


    fun clearUser(context: Context) {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = prefs.edit()
        editor.clear()
        editor.commit()
    }

    fun BitmaptoString(context:Context, bmp : Bitmap){
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        var outputStream= ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        val byte = outputStream.toByteArray()

        editor.putString("profile", Base64.encodeToString(byte, Base64.DEFAULT))

        Log.d("PreferenceUtil", "profile Updated")
        editor.apply()
    }

    fun StringtoBitmap(context: Context) : Bitmap {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val str = prefs.getString("profile", null)
        if(str != null) {
            val byte = Base64.decode(str, Base64.DEFAULT)
            val bmp = BitmapFactory.decodeByteArray(byte, 0, byte.size)
            Log.d("PreferenceUtil", "get Bitmap from PreferenceUtil")
            return bmp
        }
        else{
            val bmp = BitmapFactory.decodeResource(context.resources, R.drawable.profile_image);
            return bmp
        }
    }

    fun setImage(context: Context, view: ImageView){
        Log.d("PreferenceUtil", "set Image from PreferenceUtil")
        view.setImageBitmap(StringtoBitmap(context))
    }
}