package net.codinux.log.slf4j

import net.codinux.log.LogLevel
import net.codinux.log.slf4j.binding.Slf4jSimpleSlf4jBinding
import org.slf4j.LoggerFactory
import org.slf4j.impl.SimpleLogger
import kotlin.test.Test

class Slf4j1Slf4jSimpleBindingTest : Slf4jBindingTestBase(Slf4jBinding.Slf4jSimple, Slf4jSimpleSlf4jBinding()) {

    @Test
    fun assertSlf4jBinding() {
        assertSlf4jBinding(SimpleLogger::class)
    }


    override fun setLevelOnLoggerBinding(loggerName: String, level: LogLevel) {
        Slf4jSimpleSlf4jBinding().setLevel(LoggerFactory.getLogger(loggerName), level)
    }

}