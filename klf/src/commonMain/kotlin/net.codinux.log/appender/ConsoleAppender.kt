package net.codinux.log.appender

import net.codinux.log.LogLevel
import net.codinux.log.LoggerFactory


open class ConsoleAppender : Appender {

  companion object {

    val Default = ConsoleAppender()

  }


  protected open val formatter = LoggerFactory.effectiveConfig.logEventFormatter


  override val logsThreadName = true

  override val logsException = true

  override fun append(level: LogLevel, message: String, loggerName: String, threadName: String?, exception: Throwable?) {
    println(formatter.formatEvent(level, message, loggerName, threadName, exception))
  }

}