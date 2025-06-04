package net.codinux.log.slf4j

import net.codinux.log.LogLevel
import net.codinux.log.slf4j.binding.Log4j1Slf4jBinding
import net.codinux.log.slf4j.binding.Reload4jSlf4jBinding
import org.apache.log4j.LogManager
import org.slf4j.impl.Reload4jLoggerAdapter
import kotlin.test.Test

class Slf4j1Reload4jBindingTest : Slf4jBindingTestBase(Slf4jBinding.Reload4j, Reload4jSlf4jBinding()) {


    @Test
    fun assertSlf4jBinding() {
        assertSlf4jBinding(Reload4jLoggerAdapter::class)
    }


    override fun setLevelOnLoggerBinding(loggerName: String, level: LogLevel) {
        val log4j1Logger = LogManager.getLogger(loggerName)

        log4j1Logger.level = Log4j1Slf4jBinding().mapToLog4j1LogLevel(level)
    }

}