package net.codinux.log.appender

import android.os.Build
import android.util.Log
import net.codinux.log.Cache
import net.codinux.log.LogEvent
import net.codinux.log.LogLevel

open class LogcatAppender : Appender {

  companion object {

    /**
     * Before API 26 there was a max log tag length of 23:
     * "An IllegalArgumentException is thrown if the tag.length() > 23 for Nougat (7.0) and prior releases (API <= 25),
     * there is no tag limit of concern after this API level."
     * [https://developer.android.com/reference/android/util/Log#isLoggable(java.lang.String,%20int)](https://developer.android.com/reference/android/util/Log#isLoggable(java.lang.String,%20int))
     */
    const val MaxAndroidLogTagSizeBeforeApi26 = 23


    val Default = LogcatAppender()

  }


  override val logsThreadName = false

  override val logsException = true


  protected open val loggerNameAbbreviator = LoggerNameAbbreviator()

  protected open val abbreviatedLoggerTagCache = Cache<String, String>()


  override fun append(event: LogEvent) {
    val tag = getLoggerTag(event.loggerName)
    val message = event.message
    val exception = event.exception

    when (event.level) {
      LogLevel.Error -> Log.e(tag, message, exception)
      LogLevel.Warn -> Log.w(tag, message, exception)
      LogLevel.Info -> Log.i(tag, message, exception)
      LogLevel.Debug -> Log.d(tag, message, exception)
      LogLevel.Trace -> Log.v(tag, message, exception)
      LogLevel.Off -> { }
    }
  }

  protected open fun getLoggerTag(loggerName: String): String =
    if (Build.VERSION.SDK_INT in 1 .. 25) {
      abbreviatedLoggerTagCache.getOrPut(loggerName) {
        loggerNameAbbreviator.getLoggerTagOfMaxLength(loggerName, MaxAndroidLogTagSizeBeforeApi26)
      }
    } else {
      loggerName
    }

}