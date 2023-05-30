package net.codinux.log.appender

import net.codinux.log.LogLevel


open class ConsoleAppender : Appender {

  companion object {

    val Default = ConsoleAppender()

  }


  protected open val formatter = MessageFormatter()


  override fun append(level: LogLevel, loggerName: String, message: String, exception: Throwable?) {
    println(formatter.formatMessage(level, loggerName, message, exception))
  }

}