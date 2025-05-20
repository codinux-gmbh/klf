package net.codinux.log

import net.codinux.log.appender.Appender
import net.codinux.log.collection.*

abstract class LoggerFactoryBase : ILoggerFactory {

    protected open val appenders = ConcurrentSet<Appender>()

    protected open var immutableAppenders = ImmutableList<Appender>()

    override var doesAnyAppenderLogThreadName: Boolean = false
        protected set


    override fun addAppender(appender: Appender) {
        appenders.add(appender)
        immutableAppenders = appenders.toList().toImmutableList() // make a copy, don't pass mutable state to the outside

        this.doesAnyAppenderLogThreadName = appenders.any { it.logsThreadName }
    }

    override fun getAppenders(): Collection<Appender> =
        immutableAppenders

    override fun appendToAppenders(level: LogLevel, loggerName: String, message: String, exception: Throwable?) {
        val threadName = if (doesAnyAppenderLogThreadName) Platform.getCurrentThreadName() else null

        immutableAppenders.fastForEach { appender ->
            appender.append(
                level,
                message,
                loggerName,
                if (appender.logsThreadName) threadName else null,
                if (appender.logsException) exception else null
            )
        }
    }

}