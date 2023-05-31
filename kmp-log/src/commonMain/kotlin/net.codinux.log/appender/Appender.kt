package net.codinux.log.appender

import net.codinux.log.LogLevel


interface Appender {

  val logsThreadName: Boolean

  val logsException: Boolean

  fun append(level: LogLevel, message: String, loggerName: String, threadName: String? = null, exception: Throwable? = null)

}