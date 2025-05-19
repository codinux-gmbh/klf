package net.codinux.log.appender

import net.codinux.log.LogLevel
import net.codinux.log.formatter.MessageFormatter
import platform.Foundation.NSLog


open class NSLogAppender : Appender {

    protected open val formatter = MessageFormatter()


    override val logsThreadName = true

    override val logsException = true

    override fun append(level: LogLevel, message: String, loggerName: String, threadName: String?, exception: Throwable?) {
        NSLog(formatter.formatMessage(level, message, loggerName, threadName, exception))
    }

}