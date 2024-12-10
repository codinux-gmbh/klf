package net.codinux.log.appender

import net.codinux.log.LogLevel

open class NotifyAboutLogEventsAppender(
    includeThreadName: Boolean = false,
    includeException: Boolean = true,
    protected open val logEvent: (LogEvent) -> Unit
) : Appender {

    override val logsThreadName = includeThreadName

    override val logsException = includeException

    override fun append(level: LogLevel, message: String, loggerName: String, threadName: String?, exception: Throwable?) {
        logEvent(LogEvent(level, message, loggerName, threadName, exception))
    }

}