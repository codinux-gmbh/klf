package net.codinux.log.slf4j

import net.codinux.log.LogLevel
import net.codinux.log.slf4j.binding.Slf4jSimpleSlf4jBinding
import org.slf4j.LoggerFactory
import kotlin.test.Test
import org.slf4j.simple.SimpleLogger

class Slf4j2Slf4jSimpleBindingTest : Slf4jBindingTestBase(Slf4jBinding.Slf4jSimple, Slf4jSimpleSlf4jBinding()) {

    @Test
    fun assertSlf4jBinding() {
        assertSlf4jBinding(SimpleLogger::class)
    }


    override fun setLevelOnLoggerBinding(loggerName: String, level: LogLevel) {
        Slf4jSimpleSlf4jBinding().setLevel(LoggerFactory.getLogger(loggerName), level)
    }

}