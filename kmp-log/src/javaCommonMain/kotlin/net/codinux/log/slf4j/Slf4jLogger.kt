package net.codinux.log.slf4j

import net.codinux.log.LogLevel
import net.codinux.log.Logger
import net.codinux.log.appender.AppenderContainer
import org.slf4j.LoggerFactory


open class Slf4jLogger(
    protected open val slf4jLogger: org.slf4j.Logger,
    protected open val appenderContainer: AppenderContainer,
    override var level: LogLevel? = null
) : Logger {

    constructor(name: String, appenderContainer: AppenderContainer, level: LogLevel? = null)
            : this(LoggerFactory.getLogger(name), appenderContainer, level)


    override val name: String
        get() = slf4jLogger.name


    override val isErrorEnabled: Boolean
        get() = isLevelSetAndEnabled(LogLevel.Error) ?: slf4jLogger.isErrorEnabled

    override val isWarnEnabled: Boolean
        get() = isLevelSetAndEnabled(LogLevel.Warn) ?: slf4jLogger.isWarnEnabled

    override val isInfoEnabled: Boolean
        get() = isLevelSetAndEnabled(LogLevel.Info) ?: slf4jLogger.isInfoEnabled

    override val isDebugEnabled: Boolean
        get() = isLevelSetAndEnabled(LogLevel.Debug) ?: slf4jLogger.isDebugEnabled

    override val isTraceEnabled: Boolean
        get() = isLevelSetAndEnabled(LogLevel.Trace) ?: slf4jLogger.isTraceEnabled

    /**
     * Only calls [isEnabled] if [level] is set.
     */
    protected open fun isLevelSetAndEnabled(level: LogLevel) = this.level?.let {
        isEnabled(level)
    }


    override fun error(message: String, exception: Throwable?) {
        if (isErrorEnabled) {
            slf4jLogger.error(message, exception)

            callAdditionalAppenders(LogLevel.Error, message, exception)
        }
    }

    override fun error(exception: Throwable?, messageSupplier: () -> String) {
        if (isErrorEnabled) {
            error(messageSupplier(), exception)
        }
    }


    override fun warn(message: String, exception: Throwable?) {
        if (isWarnEnabled) {
            slf4jLogger.warn(message, exception)

            callAdditionalAppenders(LogLevel.Warn, message, exception)
        }
    }

    override fun warn(exception: Throwable?, messageSupplier: () -> String) {
        if (isWarnEnabled) {
            warn(messageSupplier(), exception)
        }
    }


    override fun info(message: String, exception: Throwable?) {
        if (isInfoEnabled) {
            slf4jLogger.info(message, exception)

            callAdditionalAppenders(LogLevel.Info, message, exception)
        }
    }

    override fun info(exception: Throwable?, messageSupplier: () -> String) {
        if (isInfoEnabled) {
            info(messageSupplier(), exception)
        }
    }


    override fun debug(message: String, exception: Throwable?) {
        if (isDebugEnabled) {
            slf4jLogger.debug(message, exception)

            callAdditionalAppenders(LogLevel.Debug, message, exception)
        }
    }

    override fun debug(exception: Throwable?, messageSupplier: () -> String) {
        if (isDebugEnabled) {
            debug(messageSupplier(), exception)
        }
    }


    override fun trace(message: String, exception: Throwable?) {
        if (isTraceEnabled) {
            slf4jLogger.trace(message, exception)

            callAdditionalAppenders(LogLevel.Trace, message, exception)
        }
    }

    override fun trace(exception: Throwable?, messageSupplier: () -> String) {
        if (isTraceEnabled) {
            trace(messageSupplier(), exception)
        }
    }


    protected open fun callAdditionalAppenders(level: LogLevel, message: String, exception: Throwable?) {
        appenderContainer.appendToAppenders(level, name, message, exception)
    }

}