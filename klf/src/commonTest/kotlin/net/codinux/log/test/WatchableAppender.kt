package net.codinux.log.test

import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import net.codinux.log.LogEvent
import net.codinux.log.LogField
import net.codinux.log.LogLevel
import net.codinux.log.appender.Appender
import net.codinux.log.collection.toImmutableList

class WatchableAppender : Appender {

    private val _appendedLogEvents = mutableListOf<LogEvent>()

    val appendedLogEvents: List<LogEvent>
        get() = _appendedLogEvents.toImmutableList()

    override var loggedFields: MutableSet<LogField> = mutableSetOf(LogField.Message, LogField.Level, LogField.LoggerName, LogField.Exception)


    override fun append(event: LogEvent) {
        _appendedLogEvents.add(event)
    }


    val hasNoLogEvents: Boolean
        get() = _appendedLogEvents.isEmpty()

    fun assertHasExactlyOneLogEventWith(level: LogLevel, message: String, loggerName: String, exception: Throwable? = null) {
        assertThat(_appendedLogEvents).hasSize(1)

        val event = _appendedLogEvents.first()
        assertThat(event.level).isEqualTo(level)
        assertThat(event.message).isEqualTo(message)
        assertThat(event.loggerName).isEqualTo(loggerName)
        assertThat(event.exception).isEqualTo(exception)
    }

    fun reset() {
        _appendedLogEvents.clear()
    }

}