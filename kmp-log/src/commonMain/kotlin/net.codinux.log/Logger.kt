package net.codinux.log


interface Logger {

    val name: String


    val isFatalEnabled: Boolean

    val isErrorEnabled: Boolean

    val isWarnEnabled: Boolean

    val isInfoEnabled: Boolean

    val isDebugEnabled: Boolean

    val isTraceEnabled: Boolean


    // TODO: add overloads for programming languages that don't support default parameters - but in an extra artefact like kmp-log-java to not ruin API

    fun fatal(message: String, exception: Throwable? = null)

    fun fatal(exception: Throwable? = null, messageSupplier: () -> String)


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