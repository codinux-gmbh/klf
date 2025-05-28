package net.codinux.log.slf4j

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.apache.logging.log4j.core.Logger
import org.apache.logging.slf4j.Log4jLogger
import org.junit.jupiter.api.Test

class Log4j2BindingTest : Slf4jBindingTestBase() {

    @Test
    fun assertSlf4jBinding() {
        assertSlf4jBinding(Log4jLogger::class)
    }

    @Test
    fun assertLog4j2RootLoggerName() {
        val slf4jLogger = getRootLoggersSlf4jLogger()

        val logger: Logger = getFieldValue(slf4jLogger, "logger")

        assertThat(logger.name).isEqualTo(Slf4jUtil.getLoggingFrameworkRootLoggerName(Slf4jBinding.Log4j2)) // logger name has to be ""
    }

}