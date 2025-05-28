package net.codinux.log.appender

import net.codinux.log.LogEvent
import net.codinux.log.LoggerFactory

open class ConsoleAppender : Appender {

  companion object {

    val Default = ConsoleAppender()

  }


  protected open val formatter = LoggerFactory.effectiveConfig.logEventFormatter


  override val logsThreadName = true

  override val logsException = true

  override fun append(event: LogEvent) {
    println(formatter.formatEvent(event))
  }

}