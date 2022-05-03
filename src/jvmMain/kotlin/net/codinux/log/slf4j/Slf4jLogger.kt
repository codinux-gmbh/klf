package net.codinux.log.slf4j

import net.codinux.log.Logger


open class Slf4jLogger(protected open val slf4jLogger: org.slf4j.Logger) : Logger {

    override val name: String
        get() = slf4jLogger.name


    override val isFatalEnabled: Boolean
        get() = isErrorEnabled

    override val isErrorEnabled: Boolean
        get() = slf4jLogger.isErrorEnabled

    override val isWarnEnabled: Boolean
        get() = slf4jLogger.isWarnEnabled

    override val isInfoEnabled: Boolean
        get() = slf4jLogger.isInfoEnabled

    override val isDebugEnabled: Boolean
        get() = slf4jLogger.isDebugEnabled

    override val isTraceEnabled: Boolean
        get() = slf4jLogger.isTraceEnabled


    override fun fatal(message: String, exception: Throwable?, vararg arguments: Any) {
        error(message, exception, *arguments)
    }

    override fun fatal(exception: Throwable?, messageSupplier: () -> String) {
        error(exception, messageSupplier)
    }


    override fun error(message: String, exception: Throwable?, vararg arguments: Any) {
        slf4jLogger.error(message, determineArguments(exception, arguments))
    }

    override fun error(exception: Throwable?, messageSupplier: () -> String) {
        if (slf4jLogger.isErrorEnabled) {
            slf4jLogger.error(messageSupplier(), exception)
        }
    }


    override fun warn(message: String, exception: Throwable?, vararg arguments: Any) {
        slf4jLogger.warn(message, determineArguments(exception, arguments))
    }

    override fun warn(exception: Throwable?, messageSupplier: () -> String) {
        if (slf4jLogger.isWarnEnabled) {
            slf4jLogger.warn(messageSupplier(), exception)
        }
    }


    override fun info(message: String, exception: Throwable?, vararg arguments: Any) {
        slf4jLogger.info(message, determineArguments(exception, arguments))
    }

    override fun info(exception: Throwable?, messageSupplier: () -> String) {
        if (slf4jLogger.isInfoEnabled) {
            slf4jLogger.info(messageSupplier(), exception)
        }
    }


    override fun debug(message: String, exception: Throwable?, vararg arguments: Any) {
        slf4jLogger.debug(message, determineArguments(exception, arguments))
    }

    override fun debug(exception: Throwable?, messageSupplier: () -> String) {
        if (slf4jLogger.isDebugEnabled) {
            slf4jLogger.debug(messageSupplier(), exception)
        }
    }


    override fun trace(message: String, exception: Throwable?, vararg arguments: Any) {
        slf4jLogger.trace(message, determineArguments(exception, arguments))
    }

    override fun trace(exception: Throwable?, messageSupplier: () -> String) {
        if (slf4jLogger.isTraceEnabled) {
            slf4jLogger.trace(messageSupplier(), exception)
        }
    }


    protected open fun determineArguments(exception: Throwable?, arguments: Array<out Any>): Array<out Any> {
        return if (exception != null) {
            if (arguments.isEmpty()) {
                arrayOf(exception)
            } else {
                val argumentsIncludingException: MutableList<Any> = mutableListOf(exception)
                argumentsIncludingException.addAll(0, arguments.toList())

                argumentsIncludingException.toTypedArray()
            }
        } else {
            arguments
        }
    }

}