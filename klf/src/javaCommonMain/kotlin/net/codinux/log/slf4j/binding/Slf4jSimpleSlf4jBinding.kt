package net.codinux.log.slf4j.binding

import net.codinux.log.JvmDefaults
import net.codinux.log.LogLevel
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.spi.LocationAwareLogger
import java.lang.reflect.Field

open class Slf4jSimpleSlf4jBinding : Slf4jBindingImplementation {

    protected open var triedToGetCurrentLogLevelField = false

    protected open var currentLogLevelField: Field? = null


    override fun getLevel(logger: Logger): LogLevel? =
        (getCurrentLogLevelField(logger)?.get(logger) as? Int)?.let { levelInt ->
            mapToKlfLogLevel(levelInt)
        }

    override fun getLevel(loggerName: String): LogLevel? =
        getLevel(LoggerFactory.getLogger(loggerName))


    open fun mapToKlfLogLevel(levelInt: Int): LogLevel = when (levelInt) {
        LocationAwareLogger.ERROR_INT + 10 -> LogLevel.Off // implementation details from org.slf4j.impl.SimpleLogger
        LocationAwareLogger.ERROR_INT -> LogLevel.Error
        LocationAwareLogger.WARN_INT -> LogLevel.Warn
        LocationAwareLogger.INFO_INT -> LogLevel.Info
        LocationAwareLogger.DEBUG_INT -> LogLevel.Debug
        LocationAwareLogger.TRACE_INT -> LogLevel.Trace
        else -> LogLevel.Off
    }

    open fun mapToSlf4jSimpleLogLevelString(level: LogLevel): String = when (level) {
        // for values see class documentation of SimpleLogger
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
        // TODO: log on error logger
        null
    }

}