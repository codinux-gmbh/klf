package net.codinux.log.appender

import net.codinux.log.LogEvent
import net.codinux.log.LogField


interface Appender {

  companion object {
    val MinLoggedFields = setOf(LogField.Message, LogField.Level, LogField.LoggerName) // TODO: make immutable
    val MinLoggedFieldsAndException = MinLoggedFields + LogField.Exception
    val MinLoggedFieldsAndExceptionAndThreadName = MinLoggedFieldsAndException + LogField.ThreadName
  }


  val loggedFields: Set<LogField>

  fun append(event: LogEvent)

}