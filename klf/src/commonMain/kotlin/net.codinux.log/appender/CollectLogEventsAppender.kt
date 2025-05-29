package net.codinux.log.appender

import net.codinux.log.LogEvent
import net.codinux.log.LogField

/**
 * A simply [Appender] that collects all log events in [collectedLogEvents].
 */
open class CollectLogEventsAppender(
    override val loggedFields: Set<LogField> = Appender.MinLoggedFieldsAndException,
    open val collectedLogEvents: MutableList<LogEvent> = mutableListOf() // TODO: use thread-safe collection
) : Appender {

    override fun append(event: LogEvent) {
        collectedLogEvents.add(event)
    }

}