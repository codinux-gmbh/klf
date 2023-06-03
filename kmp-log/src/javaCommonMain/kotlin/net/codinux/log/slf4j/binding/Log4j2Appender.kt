package net.codinux.log.slf4j.binding

import net.codinux.log.appender.Appender
import org.apache.logging.log4j.core.LogEvent
import org.apache.logging.log4j.core.appender.AbstractAppender

open class Log4j2Appender(protected open val wrappedAppender: Appender)
    : AbstractAppender(wrappedAppender.javaClass.name, null, null, true, emptyArray())
{

    override fun append(event: LogEvent?) {
        if (event != null) {
            wrappedAppender.append(
                Log4j2Util.mapLevel(event.level),
                event.message.formattedMessage,
                event.loggerName,
                if (wrappedAppender.logsThreadName) event.threadName else null,
                if (wrappedAppender.logsException) event.thrown else null
            )
        }
    }

}