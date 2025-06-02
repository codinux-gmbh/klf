package net.codinux.log.appender

import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import net.codinux.log.DefaultLoggerFactory
import net.codinux.log.LogField
import net.codinux.log.LogLevel
import net.codinux.log.LoggerFactory
import net.codinux.log.test.WatchableAppender
import kotlin.js.JsName
import kotlin.test.BeforeTest
import kotlin.test.Test

class AppenderTest {

    companion object {

        private val appender: WatchableAppender

        init {
            // otherwise on JVM slf4j's org.slf4j.helpers.NOPLoggerFactory is used. Loggers then have the name "NOP"
            LoggerFactory.initForTests(DefaultLoggerFactory())

            LoggerFactory.config.rootLevel = LogLevel.Trace // so that by default all logs get written

            appender = WatchableAppender().apply {
                LoggerFactory.addAppender(this)
            }
        }
    }


    @BeforeTest
    fun setUp() {
        appender.reset()
    }


    @Test
    @JsName("logsThreadName_disabled")
    fun `logsThreadName disabled`() {
        configureAppender(false)

        LoggerFactory.getLogger("test").info { "Info" }

        assertThat(appender.appendedLogEvents).hasSize(1)

        val appendedLog = appender.appendedLogEvents.first()
        assertThat(appendedLog::threadName).isNull()
    }

    @Test
    @JsName("logsThreadName_enabled")
    fun `logsThreadName enabled`() {
        configureAppender(true)

        LoggerFactory.getLogger("test").info { "Info" }

        assertThat(appender.appendedLogEvents).hasSize(1)

        val appendedLog = appender.appendedLogEvents.first()
        assertThat(appendedLog::threadName).isNotNull()
    }


    @Test
    @JsName("logsException_disabled")
    fun `logsException disabled`() {
        configureAppender(logsException = false)

        LoggerFactory.getLogger("test").error(Exception()) { "Error" }

        assertThat(appender.appendedLogEvents).hasSize(1)

        val appendedLog = appender.appendedLogEvents.first()
        assertThat(appendedLog::exception).isNull()
    }

    @Test
    @JsName("logsException_enabled")
    fun `logsException enabled`() {
        configureAppender(logsException = true)

        LoggerFactory.getLogger("test").error(Exception()) { "Error" }

        assertThat(appender.appendedLogEvents).hasSize(1)

        val appendedLog = appender.appendedLogEvents.first()
        assertThat(appendedLog::exception).isNotNull()
    }


    private fun configureAppender(logsThreadName: Boolean = false, logsException: Boolean = false) = appender.apply {
        if (logsThreadName) this.loggedFields.add(LogField.ThreadName) else this.loggedFields.remove(LogField.ThreadName)
        if (logsException) this.loggedFields.add(LogField.Exception) else this.loggedFields.remove(LogField.Exception)

        // add it so that LoggerFactoryBase.doesAnyAppenderLogThreadName gets set
        LoggerFactory.addAppender(appender)
    }
}