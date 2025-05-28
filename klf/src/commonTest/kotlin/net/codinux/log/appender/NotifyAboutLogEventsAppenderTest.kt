package net.codinux.log.appender

import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import net.codinux.log.Log
import net.codinux.log.LogEvent
import net.codinux.log.LogLevel
import net.codinux.log.LoggerFactory
import kotlin.test.Test

class NotifyAboutLogEventsAppenderTest {

    companion object {
        private val loggerName = "main"

        private val exceptionMessage = "Just a test"
    }

    @Test
    fun append_InfoWithoutThreadName() {
        val receivedEvents = mutableListOf<LogEvent>()
        LoggerFactory.addAppender(NotifyAboutLogEventsAppender { receivedEvents.add(it) })

        Log.info(loggerName = loggerName) { "Info" }

        assertThat(receivedEvents).hasSize(1)
        assertThat(receivedEvents.first().level).isEqualTo(LogLevel.Info)
        assertThat(receivedEvents.first().message).isEqualTo("Info")
        assertThat(receivedEvents.first().loggerName).isEqualTo(loggerName)
        assertThat(receivedEvents.first().threadName).isNull()
        assertThat(receivedEvents.first().exception).isNull()
    }

    @Test
    fun append_WarnWithoutException() {
        val receivedEvents = mutableListOf<LogEvent>()
        LoggerFactory.addAppender(NotifyAboutLogEventsAppender(true, false) { receivedEvents.add(it) })

        Log.warn(Exception(exceptionMessage), loggerName) { "Warn" }

        assertThat(receivedEvents).hasSize(1)
        assertThat(receivedEvents.first().level).isEqualTo(LogLevel.Warn)
        assertThat(receivedEvents.first().message).isEqualTo("Warn")
        assertThat(receivedEvents.first().loggerName).isEqualTo(loggerName)
        assertThat(receivedEvents.first().threadName).isNotNull()
        assertThat(receivedEvents.first().exception).isNull()
    }

    @Test
    fun append_ErrorWithException() {
        val receivedEvents = mutableListOf<LogEvent>()
        LoggerFactory.addAppender(NotifyAboutLogEventsAppender(true) { receivedEvents.add(it) })

        Log.error(Exception(exceptionMessage), loggerName) { "Error" }

        assertThat(receivedEvents).hasSize(1)
        assertThat(receivedEvents.first().level).isEqualTo(LogLevel.Error)
        assertThat(receivedEvents.first().message).isEqualTo("Error")
        assertThat(receivedEvents.first().loggerName).isEqualTo(loggerName)
        assertThat(receivedEvents.first().threadName).isNotNull()
        assertThat(receivedEvents.first().exception).isNotNull()
        assertThat(receivedEvents.first().exception!!.message).isEqualTo(exceptionMessage)
    }

}