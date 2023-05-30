package net.codinux.log.appender

import net.codinux.log.LogLevel
import platform.Foundation.NSLog


open class NSLogAppender : Appender {

    override fun append(level: LogLevel, loggerName: String, message: String, exception: Throwable?) {
        NSLog("[$level] $loggerName $message${exception ?: ""}")
    }

}