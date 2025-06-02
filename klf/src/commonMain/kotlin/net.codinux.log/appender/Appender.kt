package net.codinux.log.appender

import net.codinux.collections.immutableSetOf
import net.codinux.collections.toImmutableSet
import net.codinux.log.LogEvent
import net.codinux.log.LogField


interface Appender {

  companion object {
    val MinLoggedFields: Set<LogField> = immutableSetOf(LogField.Message, LogField.Level, LogField.LoggerName)
    val MinLoggedFieldsAndException: Set<LogField> = (MinLoggedFields + LogField.Exception).toImmutableSet()
    val MinLoggedFieldsAndExceptionAndThreadName: Set<LogField> = (MinLoggedFieldsAndException + LogField.ThreadName).toImmutableSet()
  }


  val loggedFields: Set<LogField>

  fun append(event: LogEvent)

}