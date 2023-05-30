package net.codinux.log.appender

import net.codinux.log.LogLevel


interface Appender {

  fun append(level: LogLevel, loggerName: String, message: String, exception: Throwable?)

}