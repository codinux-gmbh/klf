package net.codinux.log.appender

import net.codinux.log.LogEvent
import net.codinux.log.LogLevel
import net.codinux.log.LoggerFactory

open class JsConsoleAppender : Appender {

  protected open val formatter = LoggerFactory.effectiveConfig.logEventFormatter


  override val logsThreadName = true

  override val logsException = true

  override fun append(event: LogEvent) {
    val formattedMessage = formatter.formatEvent(event)
    val exception = event.exception

    when (event.level) {
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