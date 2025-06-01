package net.codinux.log.slf4j.binding

import net.codinux.log.LogLevel
import org.slf4j.Logger
import java.util.logging.Level

open class JavaUtilLogSlf4jBinding : Slf4jBindingImplementation {

    override val rootLoggerName = "" // buried in private class RootLogger


    override fun getLevel(logger: Logger): LogLevel? =
        getJulLogger(logger)?.level?.let { mapToKlfLogLevel(it) }

    override fun getLevel(loggerName: String): LogLevel? =
        getJulLogger(loggerName)?.level?.let { mapToKlfLogLevel(it) }

    override fun setLevel(logger: Logger, level: LogLevel?): Boolean =
        getJulLogger(logger)?.let { julLogger ->
            julLogger.level = level?.let { mapToJavaUtilLogLevel(it) }
            true
        }
            ?: false


    // there are two JDK14LoggerAdapter implementations:
    // - org.slf4j.impl.JDK14LoggerAdapter for slf4j 1
    // - org.slf4j.jul.JDK14LoggerAdapter for slf4j 2
    // -> it's hard to provide a universal implementation for both, so we use
    //    LogManager to get java.util.logging.Logger instance
    open fun getJulLogger(logger: Logger) = getJulLogger(logger.name)

    open fun getJulLogger(loggerName: String): java.util.logging.Logger? =
        java.util.logging.LogManager.getLogManager().getLogger(loggerName)


    open fun mapToKlfLogLevel(level: Level): LogLevel = when (level) {
        Level.OFF -> LogLevel.Off
        Level.SEVERE -> LogLevel.Error
        Level.WARNING -> LogLevel.Warn
        Level.INFO -> LogLevel.Info
        Level.FINE, Level.CONFIG -> LogLevel.Debug
        Level.FINER, Level.FINEST, Level.ALL -> LogLevel.Trace
        else -> LogLevel.Trace
    }

    open fun mapToJavaUtilLogLevel(level: LogLevel): Level = when (level) {
        LogLevel.Off -> Level.OFF
        LogLevel.Error -> Level.SEVERE
        LogLevel.Warn -> Level.WARNING
        LogLevel.Info -> Level.INFO
        LogLevel.Debug -> Level.FINE
        LogLevel.Trace -> Level.FINER
    }

}