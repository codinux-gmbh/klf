package net.codinux.log.status

import net.codinux.log.Defaults
import net.codinux.log.LogEvent
import net.codinux.log.LogField
import net.codinux.log.appender.Appender
import net.codinux.log.appender.ConsoleAppender
import net.codinux.log.classname.ClassNameResolver

open class StdOutStatusListener(
    protected open val appender: Appender = ConsoleAppender.Default,
    protected open val classNameResolver: ClassNameResolver = ClassNameResolver.Default,
) : StatusListener {

    override fun newStatus(status: Status) {
        val event = mapToLogEvent(status)

        appender.append(event)
    }

    protected open fun mapToLogEvent(status: Status): LogEvent {
        val classNameComponents = classNameResolver.getClassNameComponents(status.origin::class)
        val loggerName = classNameComponents.declaringClassName ?: classNameComponents.className

        val threadName = if (appender.loggedFields.contains(LogField.ThreadName)) Defaults.getCurrentThreadName() else null

        return LogEvent(status.timestamp, status.level, status.message, "klf Status $loggerName",
            threadName, status.exception)
    }

}