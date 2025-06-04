package net.codinux.log.slf4j

import assertk.assertThat
import assertk.assertions.isEqualTo
import net.codinux.log.LogLevel
import net.codinux.log.slf4j.binding.JavaUtilLogSlf4jBinding
import kotlin.test.Test
import org.slf4j.jul.JDK14LoggerAdapter
import java.util.logging.Logger

class Slf4j2JulBindingTest : Slf4jBindingTestBase(JavaUtilLogSlf4jBinding()) {


    @Test
    fun assertSlf4jBinding() {
        assertSlf4jBinding(JDK14LoggerAdapter::class)
    }

    @Test
    fun assertJulRootLoggerName() {
        val slf4jLogger = getRootLoggersSlf4jLogger()

        val logger: Logger = getFieldValue(slf4jLogger, "logger")

        assertThat(logger.name).isEqualTo(Slf4jUtil.getLoggingFrameworkRootLoggerName(Slf4jBinding.JavaUtilLog)) // logger name has to be ""
    }


    override fun setLevelOnLoggerBinding(loggerName: String, level: LogLevel) {
        val julLogger = Logger.getLogger(loggerName)

        julLogger.level = JavaUtilLogSlf4jBinding().mapToJavaUtilLogLevel(level)
    }

}