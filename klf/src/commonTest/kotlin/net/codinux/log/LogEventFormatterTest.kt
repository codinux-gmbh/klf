package net.codinux.log

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class LogEventFormatterTest {

    companion object {
        private const val Message = "Test Message"

        private const val ExceptionMessage = "Just a test, no animals have been harmed"

        private val RootLevel = LogLevel.Info

        private const val LoggerName = "com.example.TestLogger"

        private const val ThreadName = "WorkerThread #1"
    }


    private val underTest = LoggerFactory.effectiveConfig.logEventFormatter


    @Test
    fun messageWithoutException() {
        val result = underTest.formatMessage(Message, null)

        assertEquals(result, Message)
    }

    @Test
    fun messageWithUncheckedException() {
        val result = underTest.formatMessage(Message, IllegalArgumentException(ExceptionMessage))

        assertTrue(result.contains(Message))
        assertTrue(result.contains("IllegalArgumentException"))
        assertTrue(result.contains(ExceptionMessage))
        assertTrue(result.contains(Defaults.lineSeparator))
    }

    @Test
    fun messageWithThrowable() {
        val result = underTest.formatMessage(Message, Error(ExceptionMessage))

        assertTrue(result.contains(Message))
        assertTrue(result.contains("Error"))
        assertTrue(result.contains(ExceptionMessage))
        assertTrue(result.contains(Defaults.lineSeparator))
    }


    @Test
    fun loggerName() {
        val result = formatEvent(RootLevel, Message, LoggerName)

        assertTrue(result.contains(LoggerName))
        assertFalse(result.contains(Defaults.lineSeparator))
    }


    @Test
    fun withoutThreadName() {
        val result = formatEvent(RootLevel, Message, LoggerName, null)

        assertFalse(result.contains(ThreadName))
        assertFalse(result.contains(Defaults.lineSeparator))
    }

    @Test
    fun withThreadName() {
        val result = formatEvent(RootLevel, Message, LoggerName, ThreadName)

        assertTrue(result.contains(ThreadName))
        assertFalse(result.contains(Defaults.lineSeparator))
    }

    @Test
    fun withAllData() {
        val result = formatEvent(RootLevel, Message, LoggerName, ThreadName, IllegalArgumentException(ExceptionMessage))

        assertTrue(result.contains(RootLevel.name))
        assertTrue(result.contains(ThreadName))
        assertTrue(result.contains(LoggerName))
        assertTrue(result.contains(Message))
        assertTrue(result.contains("IllegalArgumentException"))
        assertTrue(result.contains(ExceptionMessage))
        assertTrue(result.contains(Defaults.lineSeparator))
    }


    @Test
    fun levelTrace() {
        val result = formatEvent(LogLevel.Trace, Message, LoggerName)

        assertTrue(result.contains(LogLevel.Trace.name))
        assertFalse(result.contains(LogLevel.Debug.name))
        assertFalse(result.contains(LogLevel.Info.name))
        assertFalse(result.contains(LogLevel.Warn.name))
        assertFalse(result.contains(LogLevel.Error.name))
    }

    @Test
    fun levelDebug() {
        val result = formatEvent(LogLevel.Debug, Message, LoggerName)

        assertFalse(result.contains(LogLevel.Trace.name))
        assertTrue(result.contains(LogLevel.Debug.name))
        assertFalse(result.contains(LogLevel.Info.name))
        assertFalse(result.contains(LogLevel.Warn.name))
        assertFalse(result.contains(LogLevel.Error.name))
    }

    @Test
    fun levelInfo() {
        val result = formatEvent(LogLevel.Info, Message, LoggerName)

        assertFalse(result.contains(LogLevel.Trace.name))
        assertFalse(result.contains(LogLevel.Debug.name))
        assertTrue(result.contains(LogLevel.Info.name))
        assertFalse(result.contains(LogLevel.Warn.name))
        assertFalse(result.contains(LogLevel.Error.name))
    }

    @Test
    fun levelWarn() {
        val result = formatEvent(LogLevel.Warn, Message, LoggerName)

        assertFalse(result.contains(LogLevel.Trace.name))
        assertFalse(result.contains(LogLevel.Debug.name))
        assertFalse(result.contains(LogLevel.Info.name))
        assertTrue(result.contains(LogLevel.Warn.name))
        assertFalse(result.contains(LogLevel.Error.name))
    }

    @Test
    fun levelError() {
        val result = formatEvent(LogLevel.Error, Message, LoggerName)

        assertFalse(result.contains(LogLevel.Trace.name))
        assertFalse(result.contains(LogLevel.Debug.name))
        assertFalse(result.contains(LogLevel.Info.name))
        assertFalse(result.contains(LogLevel.Warn.name))
        assertTrue(result.contains(LogLevel.Error.name))
    }

    private fun formatEvent(level: LogLevel, message: String, loggerName: String, threadName: String? = null, exception: Throwable? = null): String =
        underTest.formatEvent(LogEvent(level, message, loggerName, threadName, exception))

}