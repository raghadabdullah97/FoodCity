package com.example.foodcity.util
import android.content.Context


class MySharedPref  constructor( context: Context) {

    private val prefs = context.getSharedPreferences("prefKey", Context.MODE_PRIVATE)

    //ProfileFragment "concatenate with"getString(R.string.your_email_is)" and display "Email" :
    fun getString(key: String, defaultVal: String = "") = prefs.getString(key, defaultVal)

    // read , used in "NearbyAdapter" in"longitude,latitude,":
    fun getDouble(key: String, defaultVal: Float = 0.0f) =
        prefs.getFloat(key, defaultVal).toDouble()

    fun getFloat(key: String, defaultVal: Float = 0.0f) = prefs.getFloat(key, defaultVal)

    fun getInt(key: String, defaultVal: Int = 0) = prefs.getInt(key, defaultVal)

    fun getBoolean(key: String, defaultVal: Boolean = false) = prefs.getBoolean(key, defaultVal)

    fun getStrSet(key: String, defaultVal: HashSet<String>? = null) =
        prefs.getStringSet(key, defaultVal)


    fun setString(key: String, query: String?) {
        if (!query.isNullOrEmpty()) {
            prefs.edit().putString(key, query).apply()
        }

    }



    fun setFloat(key: String, query: Float) {
        prefs.edit().putFloat(key, query).apply()
    }

    // store , used in "NearbyAdapter" in"longitude,latitude,":
    fun setDouble(key: String, query: Double) {
        prefs.edit().putFloat(key, query.toFloat()).apply()
    }

    fun setInt(key: String, query: Int) {
        prefs.edit().putInt(key, query).apply()
    }

    fun setBoolean(key: String, query: Boolean) {
        prefs.edit().putBoolean(key, query).apply()
    }

    fun setStrSet(key: String, query: HashSet<String>?) {
        if (query != null)
            prefs.edit().putStringSet(key, query).apply()
    }

   // remove from MySharedPreference  :
    fun clearByKey(key: String) {
        prefs.edit().remove(key).apply()
    }
    // delete all MySharedPreference  :
    fun clearAllValue() {
        prefs.edit().clear().apply()
    }


}







