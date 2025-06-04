package net.codinux.log.slf4j

import assertk.assertThat
import assertk.assertions.isEqualByComparingTo
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.codinux.log.*
import net.codinux.log.appender.Appender
import net.codinux.log.slf4j.binding.Slf4jBindingAdapter
import kotlin.reflect.KClass
import kotlin.reflect.jvm.jvmName
import kotlin.test.Test

abstract class Slf4jBindingTestBase(
    protected val bindingAdapter: Slf4jBindingAdapter,
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
        assertThat(Slf4jUtil.boundLoggingFramework).isEqualByComparingTo(bindingAdapter.binding)
    }

    @Test
    fun assertRootLoggerName() {
        val log = LoggerFactory.rootLogger

        assertThat(log.name).isEqualTo(RootLogger.RootLoggerName)
        assertThat((log as Slf4jLogger).slf4jLogger.name).isEqualTo(bindingAdapter.slf4jRootLoggerName)
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
        val loggerName = LoggerName
        val expectedLevel = LogLevel.Trace

        setLevelOnLoggerBinding(loggerName, expectedLevel)

        val klfLogger = LoggerFactory.getLogger(loggerName)
        assertThat(klfLogger.level).isEqualTo(expectedLevel)
    }

    @Test
    fun setLogLevel() {
        val expectedLevel = LogLevel.Debug
        val loggerName = LoggerName
        val klfLogger = LoggerFactory.getLogger(loggerName)

        klfLogger.level = expectedLevel

        assertThat(bindingAdapter.getLevel(loggerName)).isEqualTo(expectedLevel)
    }


    protected open fun assertSlf4jBinding(expectedBoundLoggerClass: KClass<*>) {
        val log = LoggerFactory.rootLogger

        val slf4jLogger = getSlf4jLogger(log)

        assertThat(slf4jLogger::class).isEqualTo(expectedBoundLoggerClass)
    }

    protected open fun getRootLoggersSlf4jLogger() =
        getSlf4jLogger(LoggerFactory.rootLogger)

    protected open fun getSlf4jLogger(log: Logger): org.slf4j.Logger {
        return getFieldValue(log as Slf4jLogger, "slf4jLogger", Slf4jLogger::class)
    }

    protected open fun <T> getFieldValue(obj: Any, fieldName: String, objClass: KClass<*> = obj::class): T {
        val slf4jLoggerField = objClass.java.getDeclaredField(fieldName)
        slf4jLoggerField.isAccessible = true

        return slf4jLoggerField.get(obj) as T
    }

}