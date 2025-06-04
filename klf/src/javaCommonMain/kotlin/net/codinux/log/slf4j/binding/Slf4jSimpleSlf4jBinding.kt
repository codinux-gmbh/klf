package net.codinux.log.slf4j.binding

import net.codinux.log.JvmDefaults
import net.codinux.log.LogLevel
import net.codinux.log.status.StatusManager
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.spi.LocationAwareLogger
import java.lang.reflect.Field

open class Slf4jSimpleSlf4jBinding : Slf4jBindingImplementation {

    companion object {
        const val OffInt = LocationAwareLogger.ERROR_INT + 10 // implementation details from org.slf4j.impl.SimpleLogger
    }


    protected open var triedToGetCurrentLogLevelField = false

    protected open var currentLogLevelField: Field? = null


    override val rootLoggerName = null


    override fun getLevel(logger: Logger): LogLevel? =
        (getCurrentLogLevelField(logger)?.get(logger) as? Int)?.let { levelInt ->
            mapToKlfLogLevel(levelInt)
        }

    override fun getLevel(loggerName: String): LogLevel? =
        getLevel(LoggerFactory.getLogger(loggerName))

    override fun setLevel(logger: Logger, level: LogLevel?): Boolean =
        // setting logger's level via system property "org.slf4j.simpleLogger.log.<logger.name>" doesn't work
        // reliably as it has to be set before first SimpleLogger instance with that logger name is created
        getCurrentLogLevelField(logger)?.let { currentLogLevelField ->
            val mappedLevel: Int = level?.let { mapToSlf4jSimpleLogLevelInt(level) }
                // do not set currentLogLevel to null. The defaultLogLevel. If that doesn't work, use SimpleLogger's default level
                ?: System.getProperty("org.slf4j.simpleLogger.defaultLogLevel", null)?.let { mapToSlf4jSimpleLogLevelStringToIntInt(it) }
                ?: LocationAwareLogger.INFO_INT // SimpleLogger's default level, see SimpleLogger implementation

            currentLogLevelField.set(logger, mappedLevel)
            true
        }
            ?: false


    open fun mapToKlfLogLevel(levelInt: Int): LogLevel = when (levelInt) {
        OffInt -> LogLevel.Off
        LocationAwareLogger.ERROR_INT -> LogLevel.Error
        LocationAwareLogger.WARN_INT -> LogLevel.Warn
        LocationAwareLogger.INFO_INT -> LogLevel.Info
        LocationAwareLogger.DEBUG_INT -> LogLevel.Debug
        LocationAwareLogger.TRACE_INT -> LogLevel.Trace
        else -> LogLevel.Off
    }

    open fun mapToSlf4jSimpleLogLevelInt(level: LogLevel): Int = when (level) {
        LogLevel.Off -> OffInt
        LogLevel.Error -> LocationAwareLogger.ERROR_INT
        LogLevel.Warn -> LocationAwareLogger.WARN_INT
        LogLevel.Info -> LocationAwareLogger.INFO_INT
        LogLevel.Debug -> LocationAwareLogger.DEBUG_INT
        LogLevel.Trace -> LocationAwareLogger.TRACE_INT
    }

    open fun mapToSlf4jSimpleLogLevelStringToIntInt(level: String): Int? = when (level) {
        // for values see class documentation of SimpleLogger
        "off" -> OffInt
        "error" -> LocationAwareLogger.ERROR_INT
        "warn" -> LocationAwareLogger.WARN_INT
        "info" -> LocationAwareLogger.INFO_INT
        "debug" -> LocationAwareLogger.DEBUG_INT
        "trace" -> LocationAwareLogger.TRACE_INT
        else -> null
    }

    open fun mapToKlfLogLevel(level: String): LogLevel? = when (level) {
        "off" -> LogLevel.Off
        "error" -> LogLevel.Error
        "warn" -> LogLevel.Warn
        "info" -> LogLevel.Info
        "debug" -> LogLevel.Debug
        "trace" -> LogLevel.Trace
        else -> null
    }

    open fun mapToSlf4jSimpleLogLevelString(level: LogLevel): String = when (level) {
        LogLevel.Off -> "off"
        LogLevel.Error -> "error"
        LogLevel.Warn -> "warn"
        LogLevel.Info -> "info"
        LogLevel.Debug -> "debug"
        LogLevel.Trace -> "trace"
    }


    protected open fun getCurrentLogLevelField(logger: Logger): Field? {
        if (triedToGetCurrentLogLevelField) {
            return currentLogLevelField
        }

        triedToGetCurrentLogLevelField = true

        getCurrentLogLevelFieldForClass(logger.javaClass)?.let {
            return it
        }

        val simpleLoggerClass = JvmDefaults.getClassOrNull("org.slf4j.simple.SimpleLogger") // slf4j 2
            ?: JvmDefaults.getClassOrNull("org.slf4j.impl.SimpleLogger") // slf4j 1

        return simpleLoggerClass?.let { getCurrentLogLevelFieldForClass(it) }
    }

    protected open fun getCurrentLogLevelFieldForClass(simpleLoggerClass: Class<*>): Field? = try {
        simpleLoggerClass.getDeclaredField("currentLogLevel").also {
            it.isAccessible = true

            currentLogLevelField = it
        }
    } catch (e: Throwable) {
        StatusManager.newError(this, "Could not get currentLogLevel field of SimpleLogger class, " +
                "therefore cannot get or set Slf4jSimple Loggers' LogLevel", e)

        null
    }

}