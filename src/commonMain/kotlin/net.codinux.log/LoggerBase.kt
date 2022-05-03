package net.codinux.log

import net.codinux.log.Logger.Companion.DefaultLevel


abstract class LoggerBase(
    override val name: String,
    open var level: LogLevel = DefaultLevel
) : Logger {


    abstract fun log(level: LogLevel, message: String, exception: Throwable?, vararg arguments: Any)


    override val isFatalEnabled get() = isEnabled(LogLevel.Fatal)

    override val isErrorEnabled get() = isEnabled(LogLevel.Error)

    override val isWarnEnabled get() = isEnabled(LogLevel.Warn)

    override val isInfoEnabled get() = isEnabled(LogLevel.Info)

    override val isDebugEnabled get() = isEnabled(LogLevel.Debug)

    override val isTraceEnabled get() = isEnabled(LogLevel.Trace)

    open fun isEnabled(level: LogLevel) = level.priority <= this.level.priority


    override fun fatal(message: String, exception: Throwable?, vararg arguments: Any) {
        logIfEnabled(LogLevel.Fatal, exception, { message }, *arguments)
    }

    override fun fatal(exception: Throwable?, messageSupplier: () -> String) {
        logIfEnabled(LogLevel.Fatal, exception, messageSupplier)
    }


    override fun error(message: String, exception: Throwable?, vararg arguments: Any) {
        logIfEnabled(LogLevel.Error, exception, { message }, *arguments)
    }

    override fun error(exception: Throwable?, messageSupplier: () -> String) {
        logIfEnabled(LogLevel.Error, exception, messageSupplier)
    }


    override fun warn(message: String, exception: Throwable?, vararg arguments: Any) {
        logIfEnabled(LogLevel.Warn, exception, { message }, *arguments)
    }

    override fun warn(exception: Throwable?, messageSupplier: () -> String) {
        logIfEnabled(LogLevel.Warn, exception, messageSupplier)
    }


    override fun info(message: String, exception: Throwable?, vararg arguments: Any) {
        logIfEnabled(LogLevel.Info, exception, { message }, *arguments)
    }

    override fun info(exception: Throwable?, messageSupplier: () -> String) {
        logIfEnabled(LogLevel.Info, exception, messageSupplier)
    }


    override fun debug(message: String, exception: Throwable?, vararg arguments: Any) {
        logIfEnabled(LogLevel.Debug, exception, { message }, *arguments)
    }

    override fun debug(exception: Throwable?, messageSupplier: () -> String) {
        logIfEnabled(LogLevel.Debug, exception, messageSupplier)
    }


    override fun trace(message: String, exception: Throwable?, vararg arguments: Any) {
        logIfEnabled(LogLevel.Trace, exception, { message }, *arguments)
    }

    override fun trace(exception: Throwable?, messageSupplier: () -> String) {
        logIfEnabled(LogLevel.Trace, exception, messageSupplier)
    }


    open fun logIfEnabled(level: LogLevel, exception: Throwable? = null, message: () -> String, vararg arguments: Any) {
        if (isEnabled(level)) {
            log(level, message(), exception, *arguments)
        }
    }

}