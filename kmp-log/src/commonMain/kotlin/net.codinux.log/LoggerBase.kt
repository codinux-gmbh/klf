package net.codinux.log

import kotlin.jvm.JvmOverloads


abstract class LoggerBase @JvmOverloads constructor(
    override val name: String,
    override var level: LogLevel? = null
) : Logger {

    companion object {
        /**
         * The default logger of all [LoggerBase] implementations.
         *
         * If [LoggerBase.level] is set to null than [LoggerFactory.DefaultLevel] will be used.
         */
        val DefaultLevel: LogLevel? = null
    }



    abstract fun log(level: LogLevel, message: String, exception: Throwable?)


    override val isErrorEnabled get() = isEnabled(LogLevel.Error)

    override val isWarnEnabled get() = isEnabled(LogLevel.Warn)

    override val isInfoEnabled get() = isEnabled(LogLevel.Info)

    override val isDebugEnabled get() = isEnabled(LogLevel.Debug)

    override val isTraceEnabled get() = isEnabled(LogLevel.Trace)


    override fun error(message: String, exception: Throwable?) {
        logIfEnabled(LogLevel.Error, exception, message)
    }

    override fun error(exception: Throwable?, messageSupplier: () -> String) {
        logIfEnabled(LogLevel.Error, exception, messageSupplier)
    }


    override fun warn(message: String, exception: Throwable?) {
        logIfEnabled(LogLevel.Warn, exception, message)
    }

    override fun warn(exception: Throwable?, messageSupplier: () -> String) {
        logIfEnabled(LogLevel.Warn, exception, messageSupplier)
    }


    override fun info(message: String, exception: Throwable?) {
        logIfEnabled(LogLevel.Info, exception, message)
    }

    override fun info(exception: Throwable?, messageSupplier: () -> String) {
        logIfEnabled(LogLevel.Info, exception, messageSupplier)
    }


    override fun debug(message: String, exception: Throwable?) {
        logIfEnabled(LogLevel.Debug, exception, message)
    }

    override fun debug(exception: Throwable?, messageSupplier: () -> String) {
        logIfEnabled(LogLevel.Debug, exception, messageSupplier)
    }


    override fun trace(message: String, exception: Throwable?) {
        logIfEnabled(LogLevel.Trace, exception, message)
    }

    override fun trace(exception: Throwable?, messageSupplier: () -> String) {
        logIfEnabled(LogLevel.Trace, exception, messageSupplier)
    }


    open fun logIfEnabled(level: LogLevel, exception: Throwable? = null, message: String) {
        if (isEnabled(level)) {
            log(level, message, exception)
        }
    }

    open fun logIfEnabled(level: LogLevel, exception: Throwable? = null, messageSupplier: () -> String) {
        if (isEnabled(level)) {
            log(level, messageSupplier(), exception)
        }
    }

}