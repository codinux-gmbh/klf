package net.codinux.log.test

import net.codinux.log.LogLevel

data class LogEvent(
    val level: LogLevel,
    val message: String,
    val loggerName: String,
    val threadName: String? = null,
    val exception: Throwable? = null
)
