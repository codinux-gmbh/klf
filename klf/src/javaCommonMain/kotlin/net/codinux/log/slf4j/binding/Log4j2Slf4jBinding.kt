package net.codinux.log.slf4j.binding

import net.codinux.log.LogLevel
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.core.LoggerContext
import org.slf4j.Logger

open class Log4j2Slf4jBinding : Slf4jBindingAdapter {

    override val rootLoggerName = LogManager.ROOT_LOGGER_NAME // equals ""


    override fun getLevel(logger: Logger): LogLevel? =
        getLog4j2Logger(logger)?.level?.let { mapToKlfLogLevel(it) }

    override fun getLevel(loggerName: String): LogLevel? =
        getLog4j2Logger(loggerName)?.level?.let { mapToKlfLogLevel(it) }

    override fun setLevel(logger: Logger, level: LogLevel?): Boolean {
        setLevel(logger.name, level?.let { mapToLog4jLogLevel(level) })

        return true
    }


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

    protected open fun setLevel(loggerName: String, level: Level?) {
        val context = LogManager.getContext(false) as LoggerContext
        val config = context.configuration
        val loggerConfig = config.getLoggerConfig(loggerName)

        // If loggerConfig is for a parent logger, we need to add a specific one
        if (loggerConfig.name != loggerName) {
            val newLoggerConfig = org.apache.logging.log4j.core.config.LoggerConfig(loggerName, level, true)
            newLoggerConfig.addAppender(loggerConfig.appenders.values.first(), level, null)
            config.addLogger(loggerName, newLoggerConfig)
        } else {
            loggerConfig.level = level
        }

        context.updateLoggers()
    }

}