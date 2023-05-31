package net.codinux.log.appender

import android.util.Log
import net.codinux.log.LogLevel

class LogcatAppender : Appender {

  companion object {

    val Default = LogcatAppender()

  }


  override val logsThreadName = false

  override val logsException = true

  override fun append(level: LogLevel, message: String, loggerName: String, threadName: String?, exception: Throwable?) {
    when (level) {
      LogLevel.Fatal, LogLevel.Error -> Log.e(loggerName, message, exception)
      LogLevel.Warn -> Log.w(loggerName, message, exception)
      LogLevel.Info -> Log.i(loggerName, message, exception)
      LogLevel.Debug -> Log.d(loggerName, message, exception)
      LogLevel.Trace -> Log.v(loggerName, message, exception)
      LogLevel.Off -> { }
    }
  }

}