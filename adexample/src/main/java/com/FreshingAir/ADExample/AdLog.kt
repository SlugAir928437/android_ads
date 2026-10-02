package com.FreshingAir.ADExample

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * 极简日志缓冲：把广告回调按时间顺序记录下来，方便在主页直接观察回调时序，
 * 同时输出到 Logcat（tag = AdExample）。
 */
object AdLog {

    private const val TAG = "AdExample"
    private const val MAX_LINES = 300

    private val lines = ArrayList<String>()
    private val timeFormat = SimpleDateFormat("HH:mm:ss.SSS", Locale.CHINA)

    /** 记录一行日志。 */
    @JvmStatic
    fun append(message: String) {
        Log.i(TAG, message)
        synchronized(lines) {
            lines.add("${timeFormat.format(Date())}  $message")
            while (lines.size > MAX_LINES) {
                lines.removeAt(0)
            }
        }
    }

    /** 当前日志快照，按时间从早到晚。 */
    fun snapshot(): List<String> = synchronized(lines) { ArrayList(lines) }

    fun clear() = synchronized(lines) { lines.clear() }
}
