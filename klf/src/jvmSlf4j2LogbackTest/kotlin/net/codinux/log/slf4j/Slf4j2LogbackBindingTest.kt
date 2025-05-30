package net.codinux.log.slf4j

import ch.qos.logback.classic.Logger
import net.codinux.log.LogLevel
import net.codinux.log.slf4j.binding.LogbackSlf4jBinding
import org.slf4j.LoggerFactory
import kotlin.test.Test

class Slf4j2LogbackBindingTest : Slf4jBindingTestBase(Slf4jBinding.Logback) {

    @Test
    fun assertSlf4jBinding() {
        assertSlf4jBinding(Logger::class)
    }


    override fun setLevelOnLoggerBinding(loggerName: String, level: LogLevel) {
        val logbackLogger = LoggerFactory.getLogger(loggerName) as Logger

        logbackLogger.level = LogbackSlf4jBinding().mapToLogbackLogLevel(level)
    }

}