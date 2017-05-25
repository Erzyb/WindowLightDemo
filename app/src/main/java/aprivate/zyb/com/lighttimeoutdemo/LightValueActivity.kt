package aprivate.zyb.com.lighttimeoutdemo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.widget.CheckBox
import android.widget.SeekBar
import butterknife.ButterKnife

class LightValueActivity : AppCompatActivity() {
    internal var mCheckbox: CheckBox? = null
    internal var mSeekbar: SeekBar? = null
    private val mContext = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_light_value)
        ButterKnife.bind(this)
        initView()
    }

    private fun initView() {
        mSeekbar = findViewById(R.id.seekbar_read_sunlight) as SeekBar?
        mCheckbox = findViewById(R.id.checkbox_read_followsystem) as CheckBox?
        mSeekbar!!.progress = WindowStatusHelp.screenLightValue
        mSeekbar!!.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                WindowStatusHelp.setScreenLightValue(mContext as Activity, progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                WindowStatusHelp.setScreenLightMode(mContext as Activity, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL)
                mCheckbox!!.isChecked = false
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })
        mCheckbox!!.setOnCheckedChangeListener { _, isChecked ->
            WindowStatusHelp.setScreenLightMode(mContext as Activity, if (isChecked) Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC else Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL)
            mCheckbox!!.isChecked = isChecked
        }
        mCheckbox!!.isChecked = WindowStatusHelp.isAutomatic
    }

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, LightValueActivity::class.java)
            context.startActivity(starter)
        }
    }
}
