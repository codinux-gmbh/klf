package net.codinux.log.slf4j

import net.codinux.log.LogLevel
import net.codinux.log.Logger
import net.codinux.log.appender.AppenderCollection
import org.slf4j.LoggerFactory


open class Slf4jLogger(
    open val slf4jLogger: org.slf4j.Logger,
    protected open val appenderCollection: AppenderCollection,
    level: LogLevel? = null
) : Logger {

    constructor(name: String, appenderCollection: AppenderCollection, level: LogLevel? = null)
            : this(LoggerFactory.getLogger(name), appenderCollection, level)


    override val name: String
        get() = slf4jLogger.name

    override var level: LogLevel?
        get() = Slf4jUtil.getLevel(slf4jLogger)
        set (value) {
            Slf4jUtil.setLevel(slf4jLogger, value)
        }

    init {
        if (level != null) {
            this.level = level
        }
    }


    override val isErrorEnabled: Boolean get() = slf4jLogger.isErrorEnabled

    override val isWarnEnabled: Boolean get() = slf4jLogger.isWarnEnabled

    override val isInfoEnabled: Boolean get() = slf4jLogger.isInfoEnabled

    override val isDebugEnabled: Boolean get() = slf4jLogger.isDebugEnabled

    override val isTraceEnabled: Boolean get() = slf4jLogger.isTraceEnabled


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
        appenderCollection.appendToAppenders(level, name, message, exception)
    }

}