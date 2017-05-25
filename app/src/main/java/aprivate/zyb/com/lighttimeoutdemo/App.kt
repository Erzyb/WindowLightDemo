package aprivate.zyb.com.lighttimeoutdemo

import android.app.Application

/**
 * Created by zhouyibo on 2017/5/25.
 */

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        app = this
    }

    companion object {
        var app: App? = null
    }
}
