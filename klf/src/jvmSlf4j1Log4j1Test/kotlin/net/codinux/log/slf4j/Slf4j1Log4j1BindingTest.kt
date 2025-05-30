package net.codinux.log.slf4j

import assertk.assertThat
import assertk.assertions.isEqualTo
import net.codinux.log.LogLevel
import net.codinux.log.slf4j.binding.Log4j1Slf4jBinding
import org.apache.log4j.LogManager
import org.apache.log4j.Logger
import org.slf4j.impl.Log4jLoggerAdapter
import kotlin.test.Test

// Log4jLoggerFactory translates "ROOT" to "root"
class Slf4j1Log4j1BindingTest : Slf4jBindingTestBase(Slf4jBinding.Log4j1, Log4j1Slf4jBinding(), "root") {


    @Test
    fun assertSlf4jBinding() {
        assertSlf4jBinding(Log4jLoggerAdapter::class)
    }

    @Test
    fun assertLog4j2RootLoggerName() {
        val slf4jLogger = getRootLoggersSlf4jLogger()

        val logger: Logger = getFieldValue(slf4jLogger, "logger")

        assertThat(logger.name).isEqualTo(Slf4jUtil.getLoggingFrameworkRootLoggerName(Slf4jBinding.Log4j1)) // logger name has to be "root"
    }


    override fun setLevelOnLoggerBinding(loggerName: String, level: LogLevel) {
        val log4j1Logger = LogManager.getLogger(loggerName)

        log4j1Logger.level = Log4j1Slf4jBinding().mapToLog4j1LogLevel(level)
    }

}