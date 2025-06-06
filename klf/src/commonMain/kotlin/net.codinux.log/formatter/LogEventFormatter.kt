package net.codinux.log.formatter

import net.codinux.log.LogLevel

interface LogEventFormatter {

    companion object {
        val Default = DefaultLogEventFormatter()
    }


    fun formatMessage(message: String, exception: Throwable? = null): String


    fun formatEvent(level: LogLevel, message: String, loggerName: String) = formatEvent(level, message, loggerName, null)

    fun formatEvent(level: LogLevel, message: String, loggerName: String, threadName: String?) = formatEvent(level, message, loggerName, threadName, null)

    fun formatEvent(level: LogLevel, message: String, loggerName: String, threadName: String? = null, exception: Throwable? = null): String

}