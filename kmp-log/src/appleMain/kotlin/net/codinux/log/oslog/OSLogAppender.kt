package net.codinux.log.oslog

import kotlinx.cinterop.ptr
import net.codinux.log.LogLevel
import net.codinux.log.appender.Appender
import platform.Foundation.NSBundle
import platform.darwin.*

open class OSLogAppender : Appender {

    override fun append(level: LogLevel, loggerName: String, message: String, exception: Throwable?) {
        val type = getType(level)
        if (type == null) {
            return
        }

        val bundleIdentifier = NSBundle.mainBundle.bundleIdentifier
        val indexOfLastDot = loggerName.lastIndexOf('.')

        val (subsystem, category) = if (bundleIdentifier != null) { // in tests, ... bundleIdentifier is null
            bundleIdentifier to loggerName
        } else if (indexOfLastDot > -1) {
            loggerName.substring(0, indexOfLastDot) to loggerName.substring(indexOfLastDot + 1)
        } else {
            loggerName to ""
        }
        val logger = os_log_create(subsystem, category) // TODO: cache loggers

        _os_log_internal(__dso_handle.ptr, logger, type, message) // TODO: append exception to message
    }

    protected open fun getType(level: LogLevel): os_log_type_t? = when (level) {
        LogLevel.Fatal -> OS_LOG_TYPE_FAULT
        LogLevel.Error -> OS_LOG_TYPE_ERROR
        LogLevel.Warn -> OS_LOG_TYPE_DEFAULT
        LogLevel.Info -> OS_LOG_TYPE_INFO
        LogLevel.Debug -> OS_LOG_TYPE_DEBUG
        LogLevel.Trace -> OS_LOG_TYPE_DEBUG
        LogLevel.Off -> null
    }

}