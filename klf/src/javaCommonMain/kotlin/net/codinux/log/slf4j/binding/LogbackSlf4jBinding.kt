package net.codinux.log.slf4j.binding

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.LoggerContext
import net.codinux.log.LogLevel
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.impl.StaticLoggerBinder

open class LogbackSlf4jBinding : Slf4jBindingImplementation {

    override fun getLevel(logger: Logger): LogLevel? =
        getLogbackLogger(logger)?.level?.let { mapToKlfLogLevel(it) }

    override fun getLevel(loggerName: String): LogLevel? =
        getLogbackLogger(loggerName)?.level?.let { mapToKlfLogLevel(it) }


    open fun getLogbackLogger(logger: Logger): ch.qos.logback.classic.Logger? =
        logger as? ch.qos.logback.classic.Logger
            ?: getLogbackLogger(logger.name)

    open fun getLogbackLogger(loggerName: String): ch.qos.logback.classic.Logger? =
        (StaticLoggerBinder.getSingleton().loggerFactory as? LoggerContext)?.getLogger(loggerName)
            ?: LoggerFactory.getLogger(loggerName) as? ch.qos.logback.classic.Logger


    open fun mapToKlfLogLevel(level: Level): LogLevel = when (level) {
        Level.OFF -> LogLevel.Off
        Level.ERROR -> LogLevel.Error
        Level.WARN -> LogLevel.Warn
        Level.INFO -> LogLevel.Info
        Level.DEBUG -> LogLevel.Debug
        Level.TRACE, Level.ALL -> LogLevel.Trace
        else -> LogLevel.Trace
    }

    open fun mapToLogbackLogLevel(level: LogLevel): Level = when (level) {
        LogLevel.Off -> Level.OFF
        LogLevel.Error -> Level.ERROR
        LogLevel.Warn -> Level.WARN
        LogLevel.Info -> Level.INFO
        LogLevel.Debug -> Level.DEBUG
        LogLevel.Trace -> Level.TRACE
    }

}