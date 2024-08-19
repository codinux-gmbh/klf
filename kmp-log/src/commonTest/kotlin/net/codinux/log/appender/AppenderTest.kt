package net.codinux.log.appender

import net.codinux.log.DefaultLoggerFactory
import net.codinux.log.LogLevel
import net.codinux.log.LoggerFactory
import net.codinux.log.test.WatchableAppender
import kotlin.js.JsName
import kotlin.test.*

class AppenderTest {

    companion object {

        private val appender: WatchableAppender

        init {
            // otherwise on JVM slf4j's org.slf4j.helpers.NOPLoggerFactory is used. Loggers then have the name "NOP"
            LoggerFactory.setLoggerFactory(DefaultLoggerFactory())

            LoggerFactory.RootLevel = LogLevel.Trace // so that by default all logs get written

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

        assertEquals(1, appender.appendedLogEvents.size)

        val appendedLog = appender.appendedLogEvents.first()
        assertNull(appendedLog.threadName)
    }

    @Test
    @JsName("logsThreadName_enabled")
    fun `logsThreadName enabled`() {
        configureAppender(true)

        LoggerFactory.getLogger("test").info { "Info" }

        assertEquals(1, appender.appendedLogEvents.size)

        val appendedLog = appender.appendedLogEvents.first()
        assertNotNull(appendedLog.threadName)
    }


    @Test
    @JsName("logsException_disabled")
    fun `logsException disabled`() {
        configureAppender(logsException = false)

        LoggerFactory.getLogger("test").error(Exception()) { "Error" }

        assertEquals(1, appender.appendedLogEvents.size)

        val appendedLog = appender.appendedLogEvents.first()
        assertNull(appendedLog.exception)
    }

    @Test
    @JsName("logsException_enabled")
    fun `logsException enabled`() {
        configureAppender(logsException = true)

        LoggerFactory.getLogger("test").error(Exception()) { "Error" }

        assertEquals(1, appender.appendedLogEvents.size)

        val appendedLog = appender.appendedLogEvents.first()
        assertNotNull(appendedLog.exception)
    }


    private fun configureAppender(logsThreadName: Boolean = false, logsException: Boolean = false) = appender.apply {
        this.logsThreadName = logsThreadName
        this.logsException = logsException

        // add it so that LoggerFactoryBase.doesAnyAppenderLogThreadName gets set
        LoggerFactory.addAppender(appender)
    }
}