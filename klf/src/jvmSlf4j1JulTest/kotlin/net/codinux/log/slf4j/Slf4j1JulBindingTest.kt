package net.codinux.log.slf4j

import assertk.assertThat
import assertk.assertions.isEqualTo
import net.codinux.log.LogLevel
import net.codinux.log.slf4j.binding.JavaUtilLogSlf4jBinding
import kotlin.test.Test
import org.slf4j.impl.JDK14LoggerAdapter
import java.util.logging.Logger

// JDK14LoggerFactory translates "ROOT" to ""
class Slf4j1JulBindingTest : Slf4jBindingTestBase(Slf4jBinding.JUL, "") {


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


    override fun setLevelOnLoggerBinding(loggerName: String, level: LogLevel) {
        val julLogger = Logger.getLogger(loggerName)

        julLogger.level = JavaUtilLogSlf4jBinding().mapToJavaUtilLogLevel(level)
    }

}