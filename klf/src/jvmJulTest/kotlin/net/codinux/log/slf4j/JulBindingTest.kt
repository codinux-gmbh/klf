package net.codinux.log.slf4j

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlin.test.Test
import org.slf4j.impl.JDK14LoggerAdapter
import java.util.logging.Logger

class JulBindingTest : Slf4jBindingTestBase() {

    // JDK14LoggerFactory translates "ROOT" to ""
    override val rootLoggerName = ""

    @Test
    fun assertSlf4jBinding() {
        assertSlf4jBinding(JDK14LoggerAdapter::class)
    }

    @Test
    fun assertJulRootLoggerName() {
        val slf4jLogger = getRootLoggersSlf4jLogger()

        val logger: Logger = getFieldValue(slf4jLogger, "logger")

        assertThat(logger.name).isEqualTo(Slf4jUtil.getLoggingFrameworkRootLoggerName(Slf4jBinding.JUL)) // logger name has to be ""
    }

}