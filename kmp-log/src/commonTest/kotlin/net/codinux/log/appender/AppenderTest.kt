package net.codinux.log.appender

import net.codinux.log.DefaultLoggerFactory
import net.codinux.log.LogLevel
import net.codinux.log.LoggerFactory
import net.codinux.log.test.LogEvent
import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class AppenderTest {

    init {
        // otherwise on JVM slf4j gets used
        LoggerFactory.setLoggerFactory(DefaultLoggerFactory())
    }


    @Test
    @JsName("logsThreadName_disabled")
    fun `logsThreadName disabled`() {
        val appendedLogs = mutableListOf<LogEvent>()
        val appender = createAppender(appendedLogs, false)
        LoggerFactory.addAppender(appender)

        LoggerFactory.getLogger("test").info { "Info" }

        assertEquals(1, appendedLogs.size)

        val appendedLog = appendedLogs.first()
        assertNull(appendedLog.threadName)
    }

    @Test
    @JsName("logsThreadName_enabled")
    fun `logsThreadName enabled`() {
        val appendedLogs = mutableListOf<LogEvent>()
        val appender = createAppender(appendedLogs, true)
        LoggerFactory.addAppender(appender)

        LoggerFactory.getLogger("test").info { "Info" }

        assertEquals(1, appendedLogs.size)

        val appendedLog = appendedLogs.first()
        assertNotNull(appendedLog.threadName)
    }


    @Test
    @JsName("logsException_disabled")
    fun `logsException disabled`() {
        val appendedLogs = mutableListOf<LogEvent>()
        val appender = createAppender(appendedLogs, logsException = false)
        LoggerFactory.addAppender(appender)

        LoggerFactory.getLogger("test").error(Exception()) { "Error" }

        assertEquals(1, appendedLogs.size)

        val appendedLog = appendedLogs.first()
        assertNull(appendedLog.exception)
    }

    @Test
    @JsName("logsException_enabled")
    fun `logsException enabled`() {
        val appendedLogs = mutableListOf<LogEvent>()
        val appender = createAppender(appendedLogs, logsException = true)
        LoggerFactory.addAppender(appender)

        LoggerFactory.getLogger("test").error(Exception()) { "Error" }

        assertEquals(1, appendedLogs.size)

        val appendedLog = appendedLogs.first()
        assertNotNull(appendedLog.exception)
    }


    private fun createAppender(appendedLogs: MutableList<LogEvent>, logsThreadName: Boolean = false, logsException: Boolean = false) = object : Appender {

        override val logsThreadName = logsThreadName

        override val logsException = logsException

        override fun append(level: LogLevel, message: String, loggerName: String, threadName: String?, exception: Throwable?) {
            appendedLogs.add(LogEvent(level, message, loggerName, threadName, exception))
        }

    }
}