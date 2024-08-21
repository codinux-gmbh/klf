package net.codinux.log.appender

import net.codinux.log.LogLevel


open class ConsoleAppender : Appender {

  companion object {

    val Default = ConsoleAppender()

  }


  protected open val formatter = MessageFormatter()


  override val logsThreadName = true

  override val logsException = true

  override fun append(level: LogLevel, message: String, loggerName: String, threadName: String?, exception: Throwable?) {
    println(formatter.formatMessage(level, message, loggerName, threadName, exception))
  }

}