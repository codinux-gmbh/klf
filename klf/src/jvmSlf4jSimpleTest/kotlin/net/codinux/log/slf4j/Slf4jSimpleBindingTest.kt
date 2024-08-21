package net.codinux.log.slf4j

import org.junit.jupiter.api.Test
import org.slf4j.impl.SimpleLogger

class Slf4jSimpleBindingTest : Slf4jBindingTestBase() {

    @Test
    fun assertSlf4jBinding() {
        assertSlf4jBinding(SimpleLogger::class)
    }

}