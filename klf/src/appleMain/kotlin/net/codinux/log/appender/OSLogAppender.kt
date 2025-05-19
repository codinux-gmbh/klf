@file:OptIn(ExperimentalForeignApi::class)

package net.codinux.log.appender

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ptr
import net.codinux.log.LogLevel
import net.codinux.log.Cache
import net.codinux.log.LoggerFactory
import platform.Foundation.NSBundle
import platform.darwin.*

open class OSLogAppender : Appender {

    protected open val loggerCache = Cache<String, os_log_t>()

    protected open val formatter = LoggerFactory.effectiveConfig.logEventFormatter


    override val logsThreadName = false

    override val logsException = true

    override fun append(level: LogLevel, message: String, loggerName: String, threadName: String?, exception: Throwable?) {
        val type = getType(level)
        if (type == null) {
            return
        }

        val logger = loggerCache.getOrPut(loggerName) { createLogger(loggerName) }

        _os_log_internal(__dso_handle.ptr, logger, type, formatter.formatMessage(message, exception))
    }

    private fun createLogger(loggerName: String): os_log_t {
        val bundleIdentifier = NSBundle.mainBundle.bundleIdentifier
        val indexOfLastDot = loggerName.lastIndexOf('.')

        val (subsystem, category) = if (bundleIdentifier != null) { // in tests, ... bundleIdentifier is null
            bundleIdentifier to loggerName
        } else if (indexOfLastDot > -1) {
            loggerName.substring(0, indexOfLastDot) to loggerName.substring(indexOfLastDot + 1)
        } else {
            loggerName to ""
        }

        return os_log_create(subsystem, category)
    }

    protected open fun getType(level: LogLevel): os_log_type_t? = when (level) {
        LogLevel.Error -> OS_LOG_TYPE_ERROR
        LogLevel.Warn -> OS_LOG_TYPE_DEFAULT
        LogLevel.Info -> OS_LOG_TYPE_INFO
        LogLevel.Debug -> OS_LOG_TYPE_DEBUG
        LogLevel.Trace -> OS_LOG_TYPE_DEBUG
        LogLevel.Off -> null
    }

}