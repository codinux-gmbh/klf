package net.codinux.log.slf4j

import assertk.assertThat
import assertk.assertions.isEqualTo
import net.codinux.log.LogLevel
import net.codinux.log.slf4j.binding.JBossLoggingSlf4jBinding
import net.codinux.log.slf4j.binding.JavaUtilLogSlf4jBinding
import kotlin.test.Test
import java.util.logging.Logger

class Slf4j2JBossLoggingBindingTest : Slf4jBindingTestBase(Slf4jBinding.JBossLogging, JBossLoggingSlf4jBinding(), "ROOT") {


    @Test
    fun assertSlf4jBinding() {
        assertSlf4jBinding(org.slf4j.impl.Slf4jLogger::class)
    }

    @Test
    fun assertJBossLoggingRootLoggerName() {
        val slf4jLogger = getRootLoggersSlf4jLogger()

        val logger: Logger = getFieldValue(slf4jLogger, "logger")

        assertThat(logger.name).isEqualTo(Slf4jUtil.getLoggingFrameworkRootLoggerName(Slf4jBinding.JBossLogging))
    }


    override fun setLevelOnLoggerBinding(loggerName: String, level: LogLevel) {
        val julLogger = Logger.getLogger(loggerName)

        julLogger.level = JavaUtilLogSlf4jBinding().mapToJavaUtilLogLevel(level)
    }

}