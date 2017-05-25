package aprivate.zyb.com.lighttimeoutdemo

import android.os.Handler
import android.os.Message
import android.os.SystemClock

/**
 * Created by zhouyibo on 2017/5/17.
 */

abstract class TTSTimer {
    private var mMillisInFuture: Long = 0
    private val mCountdownInterval: Long = 1000
    private var mStopTimeInFuture: Long = 0
    private val mPauseTimeInFuture: Long = 0
    private var isStop = false
    private var isPause = false

    fun setMillisInFuture(millisInFuture: Long): TTSTimer {
        stop()
        mMillisInFuture = millisInFuture
        return this
    }

    @Synchronized private fun start(millisInFuture: Long): TTSTimer {
        isStop = false
        if (millisInFuture <= 0) {
            onFinish()
            return this
        }
        mStopTimeInFuture = SystemClock.elapsedRealtime() + millisInFuture
        mHandler.sendMessage(mHandler.obtainMessage(MSG))
        return this
    }

    /**
     * 开始倒计时
     */
    @Synchronized fun start() {
        start(mMillisInFuture)
    }

    /**
     * 停止倒计时
     */
    @Synchronized fun stop() {
        isStop = true
        mHandler.removeMessages(MSG)
    }

    /**
     * 重新开始
     */
    @Synchronized fun restart() {
        if (isStop || !isPause)
            return
        isPause = false
        start(mPauseTimeInFuture)
    }

    /**
     * 倒计时间隔回调

     * @param millisUntilFinished 剩余毫秒数
     */
    abstract fun onTick(millisUntilFinished: Long)

    /**
     * 倒计时结束回调
     */
    abstract fun onFinish()

    private val mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            synchronized(this@TTSTimer) {
                if (isStop || isPause) {
                    return
                }
                val millisLeft = mStopTimeInFuture - SystemClock.elapsedRealtime()
                if (millisLeft <= 0) {
                    onFinish()
                } else {
                    val lastTickStart = SystemClock.elapsedRealtime()
                    onTick(millisLeft)
                    var delay = lastTickStart + mCountdownInterval - SystemClock.elapsedRealtime()
                    while (delay < 0)
                        delay += mCountdownInterval
                    sendMessageDelayed(obtainMessage(MSG), delay)
                }
            }
        }
    }
    companion object {
        private val MSG = 1
    }
}
