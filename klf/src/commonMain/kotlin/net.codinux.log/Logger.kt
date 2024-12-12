package net.codinux.log


interface Logger {

    val name: String

    /**
     * The logger specific log level. If not set / set to null then default log level set in [LoggerFactory.effectiveConfig.rootLevel] will be used.
     */
    var level: LogLevel?


    val isErrorEnabled: Boolean

    val isWarnEnabled: Boolean

    val isInfoEnabled: Boolean

    val isDebugEnabled: Boolean

    val isTraceEnabled: Boolean

    fun getEffectiveLevel(): LogLevel =
        level ?: LoggerFactory.effectiveConfig.rootLevel

    fun isEnabled(level: LogLevel) = level.priority >= getEffectiveLevel().priority


    // TODO: add overloads for programming languages that don't support default parameters - but in an extra artefact like klf-java to not ruin API

    fun error(message: String, exception: Throwable? = null)

    fun error(exception: Throwable? = null, messageSupplier: () -> String)


    fun warn(message: String, exception: Throwable? = null)

    fun warn(exception: Throwable? = null, messageSupplier: () -> String)


    fun info(message: String, exception: Throwable? = null)

    fun info(exception: Throwable? = null, messageSupplier: () -> String)


    fun debug(message: String, exception: Throwable? = null)

    fun debug(exception: Throwable? = null, messageSupplier: () -> String)


    fun trace(message: String, exception: Throwable? = null)

    fun trace(exception: Throwable? = null, messageSupplier: () -> String)

}