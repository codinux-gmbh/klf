package net.codinux.log

import assertk.assertThat
import assertk.assertions.isTrue
import net.codinux.kotlin.platform.Platform
import net.codinux.kotlin.platform.isJavaScript
import net.codinux.kotlin.platform.isJvmOrAndroid
import net.codinux.kotlin.platform.isNative
import net.codinux.log.test.WatchableAppender
import kotlin.test.BeforeTest
import kotlin.test.Test

class LogTest {

    companion object {

        private val appender: WatchableAppender

        init {
            LoggerFactory.config.rootLevel = LogLevel.Trace // so that by default all logs get written

            // otherwise on JVM slf4j's org.slf4j.helpers.NOPLoggerFactory is used. Loggers then have the name "NOP"
            LoggerFactory.initForTests(DefaultLoggerFactory())

            appender = WatchableAppender().apply {
                LoggerFactory.addAppender(this)
            }
        }
    }


    private val message = "Log message"

    private val loggerName: String = if (Platform.isJavaScript) LogTest::class.simpleName!! else "net.codinux.log.LogTest"

    private val exception = IllegalArgumentException("Just a test")


    @BeforeTest
    fun setUp() {
        appender.reset()
    }


    @Test
    fun infoWithGenericTyp() {
        Log.info<LogTest> { message }

        appender.assertHasExactlyOneLogEventWith(LogLevel.Info, message, loggerName)
    }

    @Test
    fun infoWithGenericTypAndException() {
        Log.info<LogTest>(exception) { message }

        appender.assertHasExactlyOneLogEventWith(LogLevel.Info, message, loggerName, exception = exception)
    }

    @Test
    fun infoWithLoggerClass() {
        Log.info(loggerClass = LogTest::class) { message }

        appender.assertHasExactlyOneLogEventWith(LogLevel.Info, message, loggerName)
    }

    @Test
    fun infoWithLoggerClassAndException() {
        Log.info(exception, LogTest::class) { message }

        appender.assertHasExactlyOneLogEventWith(LogLevel.Info, message, loggerName, exception = exception)
    }

    @Test
    fun infoWithLoggerName() {
        Log.info(loggerName = loggerName) { message }

        appender.assertHasExactlyOneLogEventWith(LogLevel.Info, message, loggerName)
    }

    @Test
    fun infoWithoutLoggerName() {
        LoggerFactory.config.defaultLoggerName = "app"

        Log.info { message }

        LoggerFactory.config.defaultLoggerName = null // reset for other tests

        appender.assertHasExactlyOneLogEventWith(LogLevel.Info, message, "app")
    }

    @Test
    fun infoWithoutLoggerName_useCallerMethodIfLoggerNameNotSetIsTrue() {
        LoggerFactory.config.useCallerMethodIfLoggerNameNotSet = true
        LoggerFactory.config.defaultLoggerName = "app" // should be ignored on Android and JVM

        Log.info { message }

        LoggerFactory.config.useCallerMethodIfLoggerNameNotSet = false // reset for other test methods
        LoggerFactory.config.defaultLoggerName = null // reset for other tests

        val expectedLoggerName = if (Platform.isJvmOrAndroid) "net.codinux.log.LogTest.infoWithoutLoggerName_useCallerMethodIfLoggerNameNotSetIsTrue"
                                else "app"
        appender.assertHasExactlyOneLogEventWith(LogLevel.Info, message, expectedLoggerName)
    }


    @Test
    fun errorWithGenericTypAndException() {
        Log.error<LogTest>(exception) { message }

        appender.assertHasExactlyOneLogEventWith(LogLevel.Error, message, loggerName, exception = exception)
    }

    @Test
    fun warnWithGenericTypAndException() {
        Log.warn<LogTest>(exception) { message }

        appender.assertHasExactlyOneLogEventWith(LogLevel.Warn, message, loggerName, exception = exception)
    }

    @Test
    fun debugWithGenericTypAndException() {
        Log.debug<LogTest>(exception) { message }

        appender.assertHasExactlyOneLogEventWith(LogLevel.Debug, message, loggerName, exception = exception)
    }

    @Test
    fun traceWithGenericTypAndException_LevelTraceEnabled() {
        if (Platform.isNative) { // on native we also have to set debugConfig as there Platform.isRunningInDebug mode is true as long as no release build is built
            LoggerFactory.debugConfig.rootLevel = LogLevel.Trace
        }

        Log.trace<LogTest>(exception) { message }

        if (Platform.isNative) {
            LoggerFactory.debugConfig.rootLevel = LogLevel.Debug // reset for other tests
        }

        appender.assertHasExactlyOneLogEventWith(LogLevel.Trace, message, loggerName, exception = exception)
    }

    @Test
    fun traceWithGenericTypAndException_LevelTraceDisabled() {
        LoggerFactory.config.rootLevel = LogLevel.Debug

        Log.trace<LogTest>(exception) { message }

        LoggerFactory.config.rootLevel = LogLevel.Trace // reset it for other tests

        assertThat(appender::hasNoLogEvents).isTrue()
    }

}