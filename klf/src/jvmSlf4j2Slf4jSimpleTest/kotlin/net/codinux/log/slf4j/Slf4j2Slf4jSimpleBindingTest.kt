package net.codinux.log.slf4j

import kotlin.test.Test
import org.slf4j.simple.SimpleLogger

class Slf4j2Slf4jSimpleBindingTest : Slf4jBindingTestBase(Slf4jBinding.Slf4jSimple) {

    @Test
    fun assertSlf4jBinding() {
        assertSlf4jBinding(SimpleLogger::class)
    }

}