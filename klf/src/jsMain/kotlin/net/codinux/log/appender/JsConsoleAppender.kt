package net.codinux.log.appender

import net.codinux.log.LogLevel
import net.codinux.log.formatter.LogEventFormatter

open class JsConsoleAppender : Appender {

  protected open val formatter = LogEventFormatter.Default


  override val logsThreadName = true

  override val logsException = true

  override fun append(level: LogLevel, message: String, loggerName: String, threadName: String?, exception: Throwable?) {
    val formattedMessage = formatter.formatEvent(level, message, loggerName, threadName, exception)

    when (level) {
      LogLevel.Error -> {
        if (exception == null) {
          console.error(formattedMessage)
        } else {
          console.error(formattedMessage, exception)
        }
      }
      LogLevel.Warn -> {
        if (exception == null) {
          console.warn(formattedMessage)
        } else {
          console.warn(formattedMessage, exception)
        }
      }
      LogLevel.Info -> {
        if (exception == null) {
          console.info(formattedMessage)
        } else {
          console.info(formattedMessage, exception)
        }
      }
      LogLevel.Debug, LogLevel.Trace -> { // why is there no console.debug() method?
        if (exception == null) {
          console.log(formattedMessage)
        } else {
          console.log(formattedMessage, exception)
        }
      }
      LogLevel.Off -> { }
    }
  }

}