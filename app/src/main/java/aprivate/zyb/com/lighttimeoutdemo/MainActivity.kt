package aprivate.zyb.com.lighttimeoutdemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import aprivate.zyb.com.lighttimeoutdemo.SRPreferences.Companion.READ_LIGHT_TIME
import aprivate.zyb.com.lighttimeoutdemo.SRPreferences.Companion.READ_LIGHT_TIME_SYSTER


class MainActivity : AppCompatActivity() {
    internal var mTextView: TextView? = null
    internal var mTTSTimer: TTSTimer = object : TTSTimer() {
        override fun onTick(millisUntilFinished: Long) {
            mTextView!!.text = millisUntilFinished.toString()
        }

        override fun onFinish() {
            mTextView!!.text = "0"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mTextView = findViewById(R.id.textView) as TextView?
    }

    override fun onResume() {
        super.onResume()
        WindowStatusHelp.syster(false)
    }

    override fun onStop() {
        super.onStop()
        mTTSTimer.stop()
        WindowStatusHelp.syster(true)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun onViewClicked(view: View) {
        mTTSTimer.stop()
        when (view.id) {
            R.id.button_1 -> {
                WindowStatusHelp.setWindowLightTime(this, 5)
                mTTSTimer.setMillisInFuture((SRPreferences.instance.getInt(READ_LIGHT_TIME, 0) * 3 * 1000).toLong()).start()
            }
            R.id.button_01 -> {
                WindowStatusHelp.setWindowLightTime(this, -1)
                mTTSTimer.setMillisInFuture((30 * 60 * 1000).toLong()).start()
            }
            R.id.button_2 -> {
                WindowStatusHelp.setWindowLightTime(this, 10)
                mTTSTimer.setMillisInFuture((SRPreferences.instance.getInt(READ_LIGHT_TIME, 0) * 3 * 1000).toLong()).start()
            }
            R.id.button_0 -> {
                WindowStatusHelp.setWindowLightTime(this, 0)
                mTTSTimer.setMillisInFuture(SRPreferences.instance.getInt(READ_LIGHT_TIME_SYSTER, 10 * 60 * 1000).toLong()).start()
            }
        }
    }

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, MainActivity::class.java)
            context.startActivity(starter)
        }
    }
}
