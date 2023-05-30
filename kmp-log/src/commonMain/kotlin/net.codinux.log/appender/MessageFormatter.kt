package net.codinux.log.appender

import net.codinux.log.LogLevel

open class MessageFormatter {

    open fun formatMessage(message: String, exception: Throwable?): String {
        return if (exception != null) {
            "$message: ${exception.stackTraceToString()}"
        } else {
            message
        }
    }

    open fun formatMessage(level: LogLevel, loggerName: String, message: String, exception: Throwable?): String {
        return "[$level] $loggerName - ${formatMessage(message, exception)}"
    }

}