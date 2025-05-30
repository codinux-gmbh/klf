package net.codinux.log.slf4j

import assertk.assertThat
import assertk.assertions.isEqualTo
import net.codinux.log.LogLevel
import net.codinux.log.slf4j.binding.Log4j2Slf4jBinding
import org.apache.logging.log4j.LogManager
import kotlin.test.Test
import org.apache.logging.log4j.core.Logger
import org.apache.logging.log4j.core.LoggerContext
import org.apache.logging.slf4j.Log4jLogger

class Slf4j2Log4j2BindingTest : Slf4jBindingTestBase(Slf4jBinding.Log4j2) {

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


    override fun setLevelOnLoggerBinding(loggerName: String, level: LogLevel) {
        val mappedLevel = Log4j2Slf4jBinding().mapToLog4jLogLevel(level)

        val context = LogManager.getContext(false) as LoggerContext
        val config = context.configuration
        val loggerConfig = config.getLoggerConfig(loggerName)

        // If loggerConfig is for a parent logger, we need to add a specific one
        if (loggerConfig.name != loggerName) {
            val newLoggerConfig = org.apache.logging.log4j.core.config.LoggerConfig(loggerName, mappedLevel, true)
            newLoggerConfig.addAppender(loggerConfig.appenders.values.first(), mappedLevel, null)
            config.addLogger(loggerName, newLoggerConfig)
        } else {
            loggerConfig.level = mappedLevel
        }

        context.updateLoggers()
    }

}