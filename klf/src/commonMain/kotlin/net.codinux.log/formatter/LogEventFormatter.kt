package net.codinux.log.formatter

import net.codinux.log.LogEvent

interface LogEventFormatter {

    companion object {
        val Default = DefaultLogEventFormatter()
    }


    fun formatMessage(message: String, exception: Throwable? = null): String

    fun formatEvent(event: LogEvent): String

}