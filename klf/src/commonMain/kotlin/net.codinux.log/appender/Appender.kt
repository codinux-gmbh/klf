package net.codinux.log.appender

import net.codinux.log.LogEvent


interface Appender {

  val logsThreadName: Boolean

  val logsException: Boolean

  fun append(event: LogEvent)

}