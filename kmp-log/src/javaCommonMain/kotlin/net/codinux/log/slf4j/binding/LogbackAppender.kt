package net.codinux.log.slf4j.binding

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.classic.spi.IThrowableProxy
import ch.qos.logback.classic.spi.ThrowableProxy
import ch.qos.logback.core.UnsynchronizedAppenderBase
import net.codinux.log.LogLevel
import net.codinux.log.appender.Appender

open class LogbackAppender(protected open val wrappedAppender: Appender) : UnsynchronizedAppenderBase<ILoggingEvent>() {

    protected open val levelMapping = mapOf(
        Level.ERROR to LogLevel.Error,
        Level.WARN to LogLevel.Warn,
        Level.INFO to LogLevel.Info,
        Level.DEBUG to LogLevel.Debug,
        Level.TRACE to LogLevel.Trace,
        Level.ALL to LogLevel.Trace,
        Level.OFF to LogLevel.Off
    )

    init {
        this.start()
    }


    override fun append(event: ILoggingEvent?) {
        if (event != null) {
            wrappedAppender.append(
                levelMapping[event.level] ?: LogLevel.Off,
                event.formattedMessage,
                event.loggerName,
                if (wrappedAppender.logsThreadName) event.threadName else null,
                if (wrappedAppender.logsException) getThrowable(event) else null
            )
        }
    }


    protected open fun getThrowable(event: ILoggingEvent): Throwable? {
        return event.throwableProxy?.let { proxy ->
            if (proxy is ThrowableProxy) {
                return proxy.throwable
            } else {
                return tryToInstantiateThrowable(proxy)
            }
        }
    }

    protected open fun tryToInstantiateThrowable(proxy: IThrowableProxy): Throwable? {
        try {
            val throwableClass = Class.forName(proxy.className)

            val throwable = throwableClass.declaredConstructors.firstOrNull { it.parameterCount == 1 && it.parameterTypes[0] == String::class.java }?.let { constructor ->
                constructor.newInstance(proxy.message) as Throwable
            } ?: throwableClass.getDeclaredConstructor().newInstance() as Throwable

            throwable.stackTrace = proxy.stackTraceElementProxyArray.map { it.stackTraceElement }.toTypedArray()

            return throwable
        } catch (e: Exception) {
            // TODO: add ErrorLogger / StateLogger
            println("Could not get Throwable from IThrowableProxy: $e")
        }

        return null
    }
}