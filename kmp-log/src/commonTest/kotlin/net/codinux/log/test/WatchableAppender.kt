package net.codinux.log.test

import net.codinux.log.LogLevel
import net.codinux.log.appender.Appender
import net.codinux.log.collection.toImmutableList

class WatchableAppender : Appender {

    private val _appendedLogEvents = mutableListOf<LogEvent>()

    val appendedLogEvents: List<LogEvent>
        get() = _appendedLogEvents.toImmutableList()

    override val logsThreadName = false // we ignore the threadName

    override val logsException = true


    override fun append(level: LogLevel, message: String, loggerName: String, threadName: String?, exception: Throwable?) {
        _appendedLogEvents.add(LogEvent(level, message, loggerName, threadName, exception))
    }


    val hasNoLogEvents: Boolean
        get() = _appendedLogEvents.isEmpty()

    fun hasExactlyOneLogEventWith(level: LogLevel, message: String, loggerName: String, exception: Throwable? = null): Boolean =
        _appendedLogEvents.size == 1 &&
                with(_appendedLogEvents.first()) {
                    this.level == level && this.message == message && this.loggerName == loggerName && this.exception == exception
                }

}