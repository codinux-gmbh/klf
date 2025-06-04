package net.codinux.log.slf4j.binding

import net.codinux.log.LogLevel
import org.apache.log4j.Level
import org.apache.log4j.LogManager
import org.slf4j.Logger

open class Log4j1Slf4jBinding : Slf4jBindingAdapter {

    override val rootLoggerName = "root" // hard coded in RootLogger


    override fun getLevel(logger: Logger): LogLevel? =
        getLog4j1Logger(logger)?.level?.let { mapToKlfLogLevel(it) }

    override fun getLevel(loggerName: String): LogLevel? =
        getLog4j1Logger(loggerName)?.level?.let { mapToKlfLogLevel(it) }

    override fun setLevel(logger: Logger, level: LogLevel?): Boolean =
        getLog4j1Logger(logger)?.let { log4j1Logger ->
            log4j1Logger.level = level?.let { mapToLog4j1LogLevel(level) }
            true
        }
            ?: false


    open fun getLog4j1Logger(logger: Logger): org.apache.log4j.Logger? =
        getLog4j1Logger(logger.name)

    open fun getLog4j1Logger(loggerName: String): org.apache.log4j.Logger? =
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

    open fun mapToLog4j1LogLevel(level: LogLevel): Level = when (level) {
        LogLevel.Off -> Level.OFF
        LogLevel.Error -> Level.ERROR
        LogLevel.Warn -> Level.WARN
        LogLevel.Info -> Level.INFO
        LogLevel.Debug -> Level.DEBUG
        LogLevel.Trace -> Level.TRACE
    }

}