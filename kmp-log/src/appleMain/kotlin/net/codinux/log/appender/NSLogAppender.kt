package net.codinux.log.appender

import net.codinux.log.LogLevel
import platform.Foundation.NSLog


open class NSLogAppender : Appender {

    protected open val formatter = MessageFormatter()

    override fun append(level: LogLevel, loggerName: String, message: String, exception: Throwable?) {
        NSLog(formatter.formatMessage(level, loggerName, message, exception))
    }

}