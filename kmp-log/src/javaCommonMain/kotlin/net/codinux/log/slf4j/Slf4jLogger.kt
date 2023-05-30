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


    override fun fatal(message: String, exception: Throwable?) {
        error(message, exception)
    }

    override fun fatal(exception: Throwable?, messageSupplier: () -> String) {
        error(exception, messageSupplier)
    }


    override fun error(message: String, exception: Throwable?) {
        slf4jLogger.error(message, exception)
    }

    override fun error(exception: Throwable?, messageSupplier: () -> String) {
        if (slf4jLogger.isErrorEnabled) {
            slf4jLogger.error(messageSupplier(), exception)
        }
    }


    override fun warn(message: String, exception: Throwable?) {
        slf4jLogger.warn(message, exception)
    }

    override fun warn(exception: Throwable?, messageSupplier: () -> String) {
        if (slf4jLogger.isWarnEnabled) {
            slf4jLogger.warn(messageSupplier(), exception)
        }
    }


    override fun info(message: String, exception: Throwable?) {
        slf4jLogger.info(message, exception)
    }

    override fun info(exception: Throwable?, messageSupplier: () -> String) {
        if (slf4jLogger.isInfoEnabled) {
            slf4jLogger.info(messageSupplier(), exception)
        }
    }


    override fun debug(message: String, exception: Throwable?) {
        slf4jLogger.debug(message, exception)
    }

    override fun debug(exception: Throwable?, messageSupplier: () -> String) {
        if (slf4jLogger.isDebugEnabled) {
            slf4jLogger.debug(messageSupplier(), exception)
        }
    }


    override fun trace(message: String, exception: Throwable?) {
        slf4jLogger.trace(message, exception)
    }

    override fun trace(exception: Throwable?, messageSupplier: () -> String) {
        if (slf4jLogger.isTraceEnabled) {
            slf4jLogger.trace(messageSupplier(), exception)
        }
    }

}