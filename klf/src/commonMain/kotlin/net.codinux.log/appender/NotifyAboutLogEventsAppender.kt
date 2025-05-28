package net.codinux.log.appender

import net.codinux.log.LogEvent
import net.codinux.log.LogField

open class NotifyAboutLogEventsAppender(
    includeThreadName: Boolean = false,
    includeException: Boolean = true,
    protected open val logEvent: (LogEvent) -> Unit
) : Appender {

    override val loggedFields = buildSet {
        addAll(Appender.MinLoggedFields)

        if (includeThreadName) {
            add(LogField.ThreadName)
        }
        if (includeException) {
            add(LogField.Exception)
        }
    }


    override fun append(event: LogEvent) {
        logEvent(event)
    }

}