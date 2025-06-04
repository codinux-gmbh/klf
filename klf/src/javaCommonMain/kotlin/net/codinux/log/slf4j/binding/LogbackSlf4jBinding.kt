package net.codinux.log.slf4j.binding

import ch.qos.logback.classic.Level
import net.codinux.log.LogLevel
import net.codinux.log.slf4j.Slf4jBinding
import org.slf4j.Logger
import org.slf4j.LoggerFactory

open class LogbackSlf4jBinding : Slf4jBindingAdapter {

    override val binding = Slf4jBinding.Logback

    override val rootLoggerName = ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME // equals "ROOT"


    override fun getLevel(logger: Logger): LogLevel? =
        getLogbackLogger(logger)?.level?.let { mapToKlfLogLevel(it) }

    override fun getLevel(loggerName: String): LogLevel? =
        getLogbackLogger(loggerName)?.level?.let { mapToKlfLogLevel(it) }

    override fun setLevel(logger: Logger, level: LogLevel?): Boolean =
        getLogbackLogger(logger)?.let { logbackLogger ->
            logbackLogger.level = level?.let { mapToLogbackLogLevel(it) } // except for root logger Logback accepts null for level
            true
        }
            ?: false


    open fun getLogbackLogger(logger: Logger): ch.qos.logback.classic.Logger? =
        logger as? ch.qos.logback.classic.Logger
            ?: getLogbackLogger(logger.name)

    open fun getLogbackLogger(loggerName: String): ch.qos.logback.classic.Logger? =
//        (StaticLoggerBinder.getSingleton().loggerFactory as? LoggerContext)?.getLogger(loggerName) // does work in Logback 1.2, but not in Logback 1.5
            LoggerFactory.getLogger(loggerName) as? ch.qos.logback.classic.Logger


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