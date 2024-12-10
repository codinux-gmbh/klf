package net.codinux.log.appender

import net.codinux.log.LogLevel

open class LogEvent(
    open val level: LogLevel,
    open val message: String,
    open val loggerName: String,
    open val threadName: String?,
    open val exception: Throwable?
) {
    override fun toString() = "$level [$loggerName] $message"
}