package net.codinux.log

import net.codinux.log.test.TestPlatform
import net.codinux.log.test.WatchableAppender
import kotlin.test.Test
import kotlin.test.assertTrue

class LogTest {

    init {
        // otherwise on JVM slf4j's org.slf4j.helpers.NOPLoggerFactory is used. Loggers then have the name "NOP"
        LoggerFactory.setLoggerFactory(DefaultLoggerFactory())

        LoggerFactory.RootLevel = LogLevel.Trace // so that by default all logs get written
    }


    private val message = "Log message"

    private val loggerName: String = if (TestPlatform.isRunningInJs) LogTest::class.simpleName!! else "net.codinux.log.LogTest"

    private val exception = IllegalArgumentException("Just a test")


    @Test
    fun infoWithGenericTyp() {
        val appender = addAppender()

        Log.info<LogTest> { message }

        assertTrue(appender.hasExactlyOneLogEventWith(LogLevel.Info, message, loggerName))
    }

    @Test
    fun infoWithGenericTypAndException() {
        val appender = addAppender()

        Log.info<LogTest>(exception) { message }

        assertTrue(appender.hasExactlyOneLogEventWith(LogLevel.Info, message, loggerName, exception = exception))
    }

    @Test
    fun infoWithLoggerClass() {
        val appender = addAppender()

        Log.info(loggerClass = LogTest::class) { message }

        assertTrue(appender.hasExactlyOneLogEventWith(LogLevel.Info, message, loggerName))
    }

    @Test
    fun infoWithLoggerClassAndException() {
        val appender = addAppender()

        Log.info(exception, LogTest::class) { message }

        assertTrue(appender.hasExactlyOneLogEventWith(LogLevel.Info, message, loggerName, exception = exception))
    }

    @Test
    fun infoWithLoggerName() {
        val appender = addAppender()

        Log.info(loggerName = loggerName) { message }

        assertTrue(appender.hasExactlyOneLogEventWith(LogLevel.Info, message, loggerName))
    }

    @Test
    fun infoWithoutLoggerName() {
        LoggerFactory.defaultLoggerName = "app"
        val appender = addAppender()

        Log.info { message }

        assertTrue(appender.hasExactlyOneLogEventWith(LogLevel.Info, message, "app"))
    }


    @Test
    fun errorWithGenericTypAndException() {
        val appender = addAppender()

        Log.error<LogTest>(exception) { message }

        assertTrue(appender.hasExactlyOneLogEventWith(LogLevel.Error, message, loggerName, exception = exception))
    }

    @Test
    fun warnWithGenericTypAndException() {
        val appender = addAppender()

        Log.warn<LogTest>(exception) { message }

        assertTrue(appender.hasExactlyOneLogEventWith(LogLevel.Warn, message, loggerName, exception = exception))
    }

    @Test
    fun debugWithGenericTypAndException() {
        val appender = addAppender()

        Log.debug<LogTest>(exception) { message }

        assertTrue(appender.hasExactlyOneLogEventWith(LogLevel.Debug, message, loggerName, exception = exception))
    }

    @Test
    fun traceWithGenericTypAndException_LevelTraceEnabled() {
        val appender = addAppender()

        Log.trace<LogTest>(exception) { message }

        assertTrue(appender.hasExactlyOneLogEventWith(LogLevel.Trace, message, loggerName, exception = exception))
    }

    @Test
    fun traceWithGenericTypAndException_LevelTraceDisabled() {
        LoggerFactory.RootLevel = LogLevel.Debug
        val appender = addAppender()

        Log.trace<LogTest>(exception) { message }

        assertTrue(appender.hasNoLogEvents)
    }


    private fun addAppender() = WatchableAppender().apply {
        LoggerFactory.addAppender(this)
    }

}