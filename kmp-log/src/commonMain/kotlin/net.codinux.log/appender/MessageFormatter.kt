package net.codinux.log.appender

import net.codinux.log.LogLevel

open class MessageFormatter {

    open fun formatMessage(level: LogLevel, loggerName: String, message: String, exception: Throwable?): String {
        val formattedMessage = StringBuilder("[$level] $loggerName - $message")

        if (exception != null) {
            formattedMessage.append(": $exception\n${exception.stackTraceToString()}")
        }

        return formattedMessage.toString()
    }

}