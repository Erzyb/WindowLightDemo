package aprivate.zyb.com.lighttimeoutdemo

import android.content.Context
import android.content.SharedPreferences


/**
 * Created by zyb on 2016/9/12.
 * SharedPreferences类
 */
class SRPreferences private constructor(context: Context) {
    private val preferences: SharedPreferences
    private val editor: SharedPreferences.Editor

    init {
        preferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE)
        editor = preferences.edit()
    }

    internal fun setInt(key: String, value: Int) {
        editor.putInt(key, value).commit()
    }

    internal fun getInt(key: String, defValue: Int): Int {
        return preferences.getInt(key, defValue)
    }

    companion object {
        private var ourInstance: SRPreferences? = null
        internal val TAG = "windowlight"
        internal val READ_LIGHT_MODE = "lightmode"
        internal val READ_LIGHT_VOLUE = "lightvolue"
        internal val READ_LIGHT_TIME = "lighttime"
        internal val READ_LIGHT_TIME_SYSTER = "lighttimesyster"

        internal val instance: SRPreferences
            get() {
                if (ourInstance == null) {
                    ourInstance = SRPreferences(App.app!!)
                }
                return ourInstance as SRPreferences
            }
    }
}
