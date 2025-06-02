package net.codinux.log.appender

import net.codinux.collections.immutableSetOf
import net.codinux.log.LogEvent
import net.codinux.log.LogField


interface Appender {

  companion object {
    val MinLoggedFields = immutableSetOf(LogField.Message, LogField.Level, LogField.LoggerName)
    val MinLoggedFieldsAndException = MinLoggedFields + LogField.Exception
    val MinLoggedFieldsAndExceptionAndThreadName = MinLoggedFieldsAndException + LogField.ThreadName
  }


  val loggedFields: Set<LogField>

  fun append(event: LogEvent)

}