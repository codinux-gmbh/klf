package net.codinux.log.slf4j

import assertk.assertThat
import assertk.assertions.isEqualTo
import net.codinux.log.Logger
import net.codinux.log.LoggerFactory
import org.junit.jupiter.api.Test
import kotlin.reflect.KClass

abstract class Slf4jBindingTestBase {

    protected open val rootLoggerName: String = "ROOT"


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