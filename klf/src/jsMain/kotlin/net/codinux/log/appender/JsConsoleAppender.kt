package net.codinux.log.appender

import net.codinux.log.LogLevel

open class JsConsoleAppender : Appender {

  protected open val formatter = MessageFormatter()


  override val logsThreadName = true

  override val logsException = true

  override fun append(level: LogLevel, message: String, loggerName: String, threadName: String?, exception: Throwable?) {
    val formattedMessage = formatter.formatMessage(level, message, loggerName, threadName, exception)

    when (level) {
      LogLevel.Error -> console.error(formattedMessage, exception)
      LogLevel.Warn -> console.warn(formattedMessage, exception)
      LogLevel.Info -> console.info(formattedMessage, exception)
      LogLevel.Debug, LogLevel.Trace -> console.log(formattedMessage, exception) // why is there no console.debug() method?
      LogLevel.Off -> { }
    }
  }

}