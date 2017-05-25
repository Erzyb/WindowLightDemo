package aprivate.zyb.com.lighttimeoutdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button

class Main2Activity : AppCompatActivity() {

    internal var mTimeout: Button? = null
    internal var mLight: Button? = null
    private val mContext = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        mTimeout = findViewById(R.id.timeout) as Button?
        mLight = findViewById(R.id.light) as Button?
        mTimeout!!.setOnClickListener { MainActivity.start(mContext) }
        mLight!!.setOnClickListener { LightValueActivity.start(mContext) }
    }

}
