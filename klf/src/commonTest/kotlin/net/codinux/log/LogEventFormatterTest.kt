package net.codinux.log

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.doesNotContain
import assertk.assertions.isEqualTo
import kotlin.test.Test

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

        assertThat(result).isEqualTo(Message)
    }

    @Test
    fun messageWithUncheckedException() {
        val result = underTest.formatMessage(Message, IllegalArgumentException(ExceptionMessage))

        assertThat(result).contains(Message)
        assertThat(result).contains("IllegalArgumentException")
        assertThat(result).contains(ExceptionMessage)
        assertThat(result).contains(Defaults.lineSeparator)
    }

    @Test
    fun messageWithThrowable() {
        val result = underTest.formatMessage(Message, Error(ExceptionMessage))

        assertThat(result).contains(Message)
        assertThat(result).contains("Error")
        assertThat(result).contains(ExceptionMessage)
        assertThat(result).contains(Defaults.lineSeparator)
    }


    @Test
    fun loggerName() {
        val result = formatEvent(RootLevel, Message, LoggerName)

        assertThat(result).contains(LoggerName)
        assertThat(result).doesNotContain(Defaults.lineSeparator)
    }


    @Test
    fun withoutThreadName() {
        val result = formatEvent(RootLevel, Message, LoggerName, null)

        assertThat(result).doesNotContain(ThreadName)
        assertThat(result).doesNotContain(Defaults.lineSeparator)
    }

    @Test
    fun withThreadName() {
        val result = formatEvent(RootLevel, Message, LoggerName, ThreadName)

        assertThat(result).contains(ThreadName)
        assertThat(result).doesNotContain(Defaults.lineSeparator)
    }

    @Test
    fun withAllData() {
        val result = formatEvent(RootLevel, Message, LoggerName, ThreadName, IllegalArgumentException(ExceptionMessage))

        assertThat(result).contains(RootLevel.name)
        assertThat(result).contains(ThreadName)
        assertThat(result).contains(LoggerName)
        assertThat(result).contains(Message)
        assertThat(result).contains("IllegalArgumentException")
        assertThat(result).contains(ExceptionMessage)
        assertThat(result).contains(Defaults.lineSeparator)
    }


    @Test
    fun levelTrace() {
        val result = formatEvent(LogLevel.Trace, Message, LoggerName)

        assertThat(result).contains(LogLevel.Trace.name)
        assertThat(result).doesNotContain(LogLevel.Debug.name)
        assertThat(result).doesNotContain(LogLevel.Info.name)
        assertThat(result).doesNotContain(LogLevel.Warn.name)
        assertThat(result).doesNotContain(LogLevel.Error.name)
    }

    @Test
    fun levelDebug() {
        val result = formatEvent(LogLevel.Debug, Message, LoggerName)

        assertThat(result).doesNotContain(LogLevel.Trace.name)
        assertThat(result).contains(LogLevel.Debug.name)
        assertThat(result).doesNotContain(LogLevel.Info.name)
        assertThat(result).doesNotContain(LogLevel.Warn.name)
        assertThat(result).doesNotContain(LogLevel.Error.name)
    }

    @Test
    fun levelInfo() {
        val result = formatEvent(LogLevel.Info, Message, LoggerName)

        assertThat(result).doesNotContain(LogLevel.Trace.name)
        assertThat(result).doesNotContain(LogLevel.Debug.name)
        assertThat(result).contains(LogLevel.Info.name)
        assertThat(result).doesNotContain(LogLevel.Warn.name)
        assertThat(result).doesNotContain(LogLevel.Error.name)
    }

    @Test
    fun levelWarn() {
        val result = formatEvent(LogLevel.Warn, Message, LoggerName)

        assertThat(result).doesNotContain(LogLevel.Trace.name)
        assertThat(result).doesNotContain(LogLevel.Debug.name)
        assertThat(result).doesNotContain(LogLevel.Info.name)
        assertThat(result).contains(LogLevel.Warn.name)
        assertThat(result).doesNotContain(LogLevel.Error.name)
    }

    @Test
    fun levelError() {
        val result = formatEvent(LogLevel.Error, Message, LoggerName)

        assertThat(result).doesNotContain(LogLevel.Trace.name)
        assertThat(result).doesNotContain(LogLevel.Debug.name)
        assertThat(result).doesNotContain(LogLevel.Info.name)
        assertThat(result).doesNotContain(LogLevel.Warn.name)
        assertThat(result).contains(LogLevel.Error.name)
    }

    private fun formatEvent(level: LogLevel, message: String, loggerName: String, threadName: String? = null, exception: Throwable? = null): String =
        underTest.formatEvent(LogEvent(level, message, loggerName, threadName, exception))

}