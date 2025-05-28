package net.codinux.log.slf4j

import ch.qos.logback.classic.Logger
import kotlin.test.Test

class LogbackBindingTest : Slf4jBindingTestBase() {

    @Test
    fun assertSlf4jBinding() {
        assertSlf4jBinding(Logger::class)
    }

}