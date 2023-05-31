package net.codinux.log.appender

import net.codinux.log.LogLevel

open class MessageFormatter {

    open fun formatMessage(message: String, exception: Throwable? = null): String {
        return if (exception != null) {
            "$message: ${exception.stackTraceToString()}"
        } else {
            message
        }
    }

    open fun formatMessage(level: LogLevel, message: String, loggerName: String, threadName: String? = null, exception: Throwable? = null): String {
        return "${threadName?.let { "[$it] " } ?: ""}$level $loggerName - ${formatMessage(message, exception)}"
    }

}