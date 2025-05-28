package net.codinux.log.appender

import net.codinux.log.LogEvent
import net.codinux.log.LoggerFactory
import platform.Foundation.NSLog


open class NSLogAppender : Appender {

    protected open val formatter = LoggerFactory.effectiveConfig.logEventFormatter


    override val loggedFields = Appender.MinLoggedFieldsAndExceptionAndThreadName

    override fun append(event: LogEvent) {
        NSLog(formatter.formatEvent(event))
    }

}