package net.codinux.log.appender

import net.codinux.log.LogEvent
import net.codinux.log.LoggerFactory

open class ConsoleAppender : Appender {

  companion object {

    val Default = ConsoleAppender()

  }


  protected open val formatter = LoggerFactory.effectiveConfig.logEventFormatter


  override val loggedFields = Appender.MinLoggedFieldsAndExceptionAndThreadName

  override fun append(event: LogEvent) {
    println(formatter.formatEvent(event))
  }

}