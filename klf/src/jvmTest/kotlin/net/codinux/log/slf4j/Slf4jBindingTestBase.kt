package net.codinux.log.slf4j

import assertk.assertThat
import assertk.assertions.isEqualByComparingTo
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.codinux.log.LogEvent
import net.codinux.log.LogLevel
import net.codinux.log.Logger
import net.codinux.log.LoggerFactory
import net.codinux.log.appender.Appender
import kotlin.reflect.KClass
import kotlin.reflect.jvm.jvmName
import kotlin.test.Test

abstract class Slf4jBindingTestBase(
    protected val slf4jBinding: Slf4jBinding,
    protected val rootLoggerName: String = "ROOT"
) {

    companion object {
        private const val Message = "Info message"
        private val LoggerName = Slf4jBindingTestBase::class.jvmName
    }


    protected abstract fun setLevelOnLoggerBinding(loggerName: String, level: LogLevel)


    @Test
    fun isSlf4jOnClasspath() {
        assertThat(Slf4jUtil.isSlf4jOnClasspath).isTrue()
    }

    @Test
    fun useSlf4j() {
        assertThat(Slf4jUtil.useSlf4j).isTrue()
    }

    @Test
    fun boundLoggingFramework() {
        assertThat(Slf4jUtil.boundLoggingFramework).isEqualByComparingTo(slf4jBinding)
    }

    @Test
    fun assertRootLoggerName() {
        val log = LoggerFactory.rootLogger

        assertThat(log.name).isEqualTo(rootLoggerName)
    }

    @Test
    fun append() {
        val mockAppender = mockk<Appender>()
        every { mockAppender.loggedFields } returns Appender.MinLoggedFields
        every { mockAppender.append(any()) } returns Unit

        LoggerFactory.addAppender(mockAppender)
        val log = LoggerFactory.getLogger(LoggerName)

        log.info { Message }

        // in tests logback is the bound logging framework -> we want to test if log messages gets directed to our mock Appender
        verify { mockAppender.append(LogEvent(LogLevel.Info, Message, LoggerName, null, null)) }
    }



    @Test
    fun getLogLevel() {
        val loggerName = LoggerName + ".Unique" // make sure there doesn't already exist an instance of this logger, Slf4jSimple tests would fail otherwise
        val expectedLevel = LogLevel.Trace

        setLevelOnLoggerBinding(loggerName, expectedLevel)

        val klfLogger = LoggerFactory.getLogger(loggerName)
        assertThat(klfLogger.level).isEqualTo(expectedLevel)
    }


    protected open fun assertSlf4jBinding(expectedBoundLoggerClass: KClass<*>) {
        val log = LoggerFactory.rootLogger

        val slf4jLogger = getSlf4jLogger(log)

        assertThat(slf4jLogger::class).isEqualTo(expectedBoundLoggerClass)
    }

    protected open fun getRootLoggersSlf4jLogger() =
        getSlf4jLogger(LoggerFactory.rootLogger)

    protected open fun getSlf4jLogger(log: Logger): org.slf4j.Logger {
        return getFieldValue(log as Slf4jLogger, "slf4jLogger")
    }

    protected open fun <T> getFieldValue(obj: Any, fieldName: String): T {
        val slf4jLoggerField = obj::class.java.getDeclaredField(fieldName)
        slf4jLoggerField.isAccessible = true

        return slf4jLoggerField.get(obj) as T
    }

}