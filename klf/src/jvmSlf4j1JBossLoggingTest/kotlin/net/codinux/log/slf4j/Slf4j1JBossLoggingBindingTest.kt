package net.codinux.log.slf4j

import assertk.assertThat
import assertk.assertions.isEqualTo
import net.codinux.log.LogLevel
import net.codinux.log.slf4j.binding.JBossLoggingSlf4jBinding
import net.codinux.log.slf4j.binding.JavaUtilLogSlf4jBinding
import org.jboss.logging.Logger
import org.jboss.slf4j.JBossLoggerAdapter
import kotlin.test.Test

class Slf4j1JBossLoggingBindingTest : Slf4jBindingTestBase(Slf4jBinding.JBossLogging, JBossLoggingSlf4jBinding(), "ROOT") {


    @Test
    fun assertSlf4jBinding() {
        assertSlf4jBinding(JBossLoggerAdapter::class)
    }

    @Test
    fun assertJBossLoggingRootLoggerName() {
        val slf4jLogger = getRootLoggersSlf4jLogger()

        val logger: Logger = getFieldValue(slf4jLogger, "logger")

        assertThat(logger.name).isEqualTo(Slf4jUtil.getLoggingFrameworkRootLoggerName(Slf4jBinding.JBossLogging))
    }


    override fun setLevelOnLoggerBinding(loggerName: String, level: LogLevel) {
        val julLogger = java.util.logging.Logger.getLogger(loggerName)

        julLogger.level = JavaUtilLogSlf4jBinding().mapToJavaUtilLogLevel(level)
    }

}