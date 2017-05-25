package aprivate.zyb.com.lighttimeoutdemo

import android.app.Activity
import android.provider.Settings
import android.view.WindowManager

/**
 * 页面亮度帮助类
 */
internal object WindowStatusHelp {
    //    public static void noLimitScreen(Activity activity) {
    //        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
    //        attrs.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
    //        activity.getWindow().setAttributes(attrs);
    //    }
    //
    //    public static void limitScreen(Activity activity) {
    //        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
    //        attrs.flags &= ~WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
    //        activity.getWindow().setAttributes(attrs);
    //    }
    //
    //    public static void showStatusBar(Activity activity, View view) {
    //        view.setVisibility(View.VISIBLE);
    //        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
    //        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
    //        attrs.flags &= ~WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
    //        activity.getWindow().setAttributes(attrs);
    //    }
    //
    //    public static void hideStatusBar(Activity activity, View view) {
    //        view.setVisibility(View.GONE);
    //        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
    //        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
    //        attrs.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
    //        activity.getWindow().setAttributes(attrs);
    //    }


    /*
     * 屏幕亮度状态
     */
    private fun changeAppBrightness(context: Activity) {
        val window = context.window
        val lp = window.attributes
        if (isAutomatic) {
            lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE
        } else {
            lp.screenBrightness = (if (screenLightValue <= 0) 1 else screenLightValue) / 255f
        }
        window.attributes = lp
    }

    val screenLightValue: Int
        get() = SRPreferences.instance.getInt(SRPreferences.READ_LIGHT_VOLUE, 122)

    fun setScreenLightValue(activity: Activity, value: Int) {
        SRPreferences.instance.setInt(SRPreferences.READ_LIGHT_VOLUE, value)
        changeAppBrightness(activity)
    }

    private val screenLightMode: Int
        get() = SRPreferences.instance.getInt(SRPreferences.READ_LIGHT_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC)

    fun setScreenLightMode(activity: Activity, screenLightMode: Int) {
        SRPreferences.instance.setInt(SRPreferences.READ_LIGHT_MODE, screenLightMode)
        changeAppBrightness(activity)
    }

    val isAutomatic: Boolean
        get() = screenLightMode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC

    /*****************************
     * 屏幕透明度状态
     */
    //    public static void setWindowAlpha(Activity activity, float f) {
    //        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
    //        lp.alpha = f; //0.0-1.0
    //        activity.getWindow().setAttributes(lp);
    //    }
    fun syster(reset: Boolean) {
        try {
            if (reset) {
                Settings.System.putInt(App.app!!.contentResolver, Settings.System.SCREEN_OFF_TIMEOUT,
                        SRPreferences.instance.getInt(SRPreferences.READ_LIGHT_TIME_SYSTER, 10))
            } else {
                SRPreferences.instance.setInt(SRPreferences.READ_LIGHT_TIME_SYSTER,
                        Settings.System.getInt(App.app!!.contentResolver, Settings.System.SCREEN_OFF_TIMEOUT))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun setWindowLightTime(activity: Activity, value: Int) {
        SRPreferences.instance.setInt(SRPreferences.READ_LIGHT_TIME, value)
        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        when (value) {
            0 -> syster(true)
            5 -> Settings.System.putInt(App.app!!.contentResolver, Settings.System.SCREEN_OFF_TIMEOUT, 15 * 1000)
            10 -> Settings.System.putInt(App.app!!.contentResolver, Settings.System.SCREEN_OFF_TIMEOUT, 30 * 1000)
            -1 -> activity.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }
}
