package net.codinux.log.appender

import net.codinux.log.LogLevel

open class JsConsoleAppender : Appender {

  override fun append(level: LogLevel, loggerName: String, message: String, exception: Throwable?) {
    when (level) {
      LogLevel.Fatal, LogLevel.Error -> console.error(message, exception)
      LogLevel.Warn -> console.warn(message, exception)
      LogLevel.Info -> console.info(message, exception)
      LogLevel.Debug, LogLevel.Trace -> console.log(message, exception)
      LogLevel.Off -> { }
    }
  }

}