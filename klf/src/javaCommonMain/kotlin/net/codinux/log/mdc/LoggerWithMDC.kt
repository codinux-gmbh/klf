package net.codinux.log.mdc

import net.codinux.log.Logger

open class LoggerWithMDC(
    protected open val delegate: Logger,
    protected open val mdc: Map<String, String>
) : Logger by delegate {


    override fun error(message: String, exception: Throwable?) {
        if (isErrorEnabled) {
            runWithMdc(mdc) {
                delegate.error(message, exception)
            }
        }
    }

    override fun error(exception: Throwable?, messageSupplier: () -> String) {
        if (isErrorEnabled) {
            runWithMdc(mdc) {
                delegate.error(exception, messageSupplier)
            }
        }
    }


    override fun warn(message: String, exception: Throwable?) {
        if (isWarnEnabled) {
            runWithMdc(mdc) {
                delegate.warn(message, exception)
            }
        }
    }

    override fun warn(exception: Throwable?, messageSupplier: () -> String) {
        if (isWarnEnabled) {
            runWithMdc(mdc) {
                delegate.warn(exception, messageSupplier)
            }
        }
    }


    override fun info(message: String, exception: Throwable?) {
        if (isInfoEnabled) {
            runWithMdc(mdc) {
                delegate.info(message, exception)
            }
        }
    }

    override fun info(exception: Throwable?, messageSupplier: () -> String) {
        if (isInfoEnabled) {
            runWithMdc(mdc) {
                delegate.info(exception, messageSupplier)
            }
        }
    }


    override fun debug(message: String, exception: Throwable?) {
        if (isDebugEnabled) {
            runWithMdc(mdc) {
                delegate.debug(message, exception)
            }
        }
    }

    override fun debug(exception: Throwable?, messageSupplier: () -> String) {
        if (isDebugEnabled) {
            runWithMdc(mdc) {
                delegate.debug(exception, messageSupplier)
            }
        }
    }


    override fun trace(message: String, exception: Throwable?) {
        if (isTraceEnabled) {
            runWithMdc(mdc) {
                delegate.trace(message, exception)
            }
        }
    }

    override fun trace(exception: Throwable?, messageSupplier: () -> String) {
        if (isTraceEnabled) {
            runWithMdc(mdc) {
                delegate.trace(exception, messageSupplier)
            }
        }
    }


}