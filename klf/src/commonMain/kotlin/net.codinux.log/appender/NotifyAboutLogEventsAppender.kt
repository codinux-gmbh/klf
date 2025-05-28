package net.codinux.log.appender

import net.codinux.log.LogEvent

open class NotifyAboutLogEventsAppender(
    includeThreadName: Boolean = false,
    includeException: Boolean = true,
    protected open val logEvent: (LogEvent) -> Unit
) : Appender {

    override val logsThreadName = includeThreadName

    override val logsException = includeException

    override fun append(event: LogEvent) {
        logEvent(event)
    }

}