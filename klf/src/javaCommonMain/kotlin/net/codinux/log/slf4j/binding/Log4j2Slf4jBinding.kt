package net.codinux.log.slf4j.binding

import net.codinux.log.LogLevel
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.slf4j.Logger

open class Log4j2Slf4jBinding : Slf4jBindingImplementation {

    override fun getLevel(logger: Logger): LogLevel? =
        getLog4j2Logger(logger)?.level?.let { mapToKlfLogLevel(it) }

    override fun getLevel(loggerName: String): LogLevel? =
        getLog4j2Logger(loggerName)?.level?.let { mapToKlfLogLevel(it) }


    open fun getLog4j2Logger(logger: Logger): org.apache.logging.log4j.Logger? =
        logger as? org.apache.logging.log4j.Logger // should never be true
//            ?: (logger as? org.apache.logging.slf4j.Log4jLogger)?.let { getLog4jLogger(it) }
            ?: getLog4j2Logger(logger.name)

    open fun getLog4j2Logger(loggerName: String): org.apache.logging.log4j.Logger? =
        LogManager.getLogger(loggerName)


    open fun mapToKlfLogLevel(level: Level): LogLevel = when (level) {
        Level.OFF -> LogLevel.Off
        Level.FATAL, Level.ERROR -> LogLevel.Error
        Level.WARN -> LogLevel.Warn
        Level.INFO -> LogLevel.Info
        Level.DEBUG -> LogLevel.Debug
        Level.TRACE, Level.ALL -> LogLevel.Trace
        else -> LogLevel.Trace
    }

    open fun mapToLog4jLogLevel(level: LogLevel): Level = when (level) {
        LogLevel.Off -> Level.OFF
        LogLevel.Error -> Level.ERROR
        LogLevel.Warn -> Level.WARN
        LogLevel.Info -> Level.INFO
        LogLevel.Debug -> Level.DEBUG
        LogLevel.Trace -> Level.TRACE
    }

}