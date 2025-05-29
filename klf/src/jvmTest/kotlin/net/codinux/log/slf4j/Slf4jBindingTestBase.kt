package net.codinux.log.slf4j

import assertk.assertThat
import assertk.assertions.isEqualByComparingTo
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import net.codinux.log.Logger
import net.codinux.log.LoggerFactory
import kotlin.reflect.KClass
import kotlin.test.Test

abstract class Slf4jBindingTestBase(
    protected val slf4jBinding: Slf4jBinding,
    protected val rootLoggerName: String = "ROOT"
) {


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