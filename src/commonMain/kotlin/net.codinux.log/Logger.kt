package net.codinux.log


interface Logger {

    val name: String


    val isFatalEnabled: Boolean

    val isErrorEnabled: Boolean

    val isWarnEnabled: Boolean

    val isInfoEnabled: Boolean

    val isDebugEnabled: Boolean

    val isTraceEnabled: Boolean


    fun fatal(message: String, exception: Throwable? = null, vararg arguments: Any)

    fun fatal(exception: Throwable? = null, messageSupplier: () -> String)


    fun error(message: String, exception: Throwable? = null, vararg arguments: Any)

    fun error(exception: Throwable? = null, messageSupplier: () -> String)


    fun warn(message: String, exception: Throwable? = null, vararg arguments: Any)

    fun warn(exception: Throwable? = null, messageSupplier: () -> String)


    fun info(message: String, exception: Throwable? = null, vararg arguments: Any)

    fun info(exception: Throwable? = null, messageSupplier: () -> String)


    fun debug(message: String, exception: Throwable? = null, vararg arguments: Any)

    fun debug(exception: Throwable? = null, messageSupplier: () -> String)


    fun trace(message: String, exception: Throwable? = null, vararg arguments: Any)

    fun trace(exception: Throwable? = null, messageSupplier: () -> String)

}