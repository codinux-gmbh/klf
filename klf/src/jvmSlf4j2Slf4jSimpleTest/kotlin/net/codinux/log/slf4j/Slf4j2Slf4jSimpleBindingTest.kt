package net.codinux.log.slf4j

import net.codinux.log.LogLevel
import net.codinux.log.slf4j.binding.Slf4jSimpleSlf4jBinding
import kotlin.test.Test
import org.slf4j.simple.SimpleLogger

class Slf4j2Slf4jSimpleBindingTest : Slf4jBindingTestBase(Slf4jBinding.Slf4jSimple) {

    @Test
    fun assertSlf4jBinding() {
        assertSlf4jBinding(SimpleLogger::class)
    }


    override fun setLevelOnLoggerBinding(loggerName: String, level: LogLevel) {
        // this has to be called before SimpleLogger for this loggerName is instantiated for the first time!
        // see class documentation of SimpleLogger
        System.setProperty("org.slf4j.simpleLogger.log.$loggerName",
            Slf4jSimpleSlf4jBinding().mapToSlf4jSimpleLogLevelString(level))
    }

}