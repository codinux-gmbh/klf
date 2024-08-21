package net.codinux.log.slf4j

import org.apache.log4j.Logger
import org.slf4j.impl.Reload4jLoggerAdapter
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Log4j1BindingTest : Slf4jBindingTestBase() {

    // Log4jLoggerFactory translates "ROOT" to ""
    override val rootLoggerName = "root"

    @Test
    fun assertSlf4jBinding() {
        assertSlf4jBinding(Reload4jLoggerAdapter::class)
    }

    @Test
    fun assertLog4j2RootLoggerName() {
        val slf4jLogger = getRootLoggersSlf4jLogger()

        val logger: Logger = getFieldValue(slf4jLogger, "logger")

        assertEquals(logger.name, Slf4jUtil.getLoggingFrameworkRootLoggerName(Slf4jBinding.Log4j1)) // logger name has to be "root"
    }

}