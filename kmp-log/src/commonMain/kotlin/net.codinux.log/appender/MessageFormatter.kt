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
        // may add format specifiers as logback has:
        // %d{yyyy-MM-dd HH:mm:ss,SSS} %-5p [%c{3.}] (%t) %s%e%n
        // p = level
        // c = category name (= logger name)
        // t = thread name
        // s = Simple message (Renders just the log message, with no exception trace)
        // e = Exception
        // n = Newline
        return "${threadName?.let { "[$it] " } ?: ""}$level $loggerName - ${formatMessage(message, exception)}"
    }

}