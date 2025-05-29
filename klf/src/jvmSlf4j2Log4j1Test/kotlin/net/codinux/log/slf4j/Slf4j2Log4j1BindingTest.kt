package net.codinux.log.slf4j

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlin.test.Test
import org.apache.log4j.Logger
import org.slf4j.reload4j.Reload4jLoggerAdapter

// Log4jLoggerFactory translates "ROOT" to "root"
class Slf4j2Log4j1BindingTest : Slf4jBindingTestBase(Slf4jBinding.Reload4j, "root") {


    @Test
    fun assertSlf4jBinding() {
        assertSlf4jBinding(Reload4jLoggerAdapter::class)
    }

    @Test
    fun assertLog4j2RootLoggerName() {
        val slf4jLogger = getRootLoggersSlf4jLogger()

        val logger: Logger = getFieldValue(slf4jLogger, "logger")

        assertThat(logger.name).isEqualTo(Slf4jUtil.getLoggingFrameworkRootLoggerName(Slf4jBinding.Log4j1)) // logger name has to be "root"
    }

}