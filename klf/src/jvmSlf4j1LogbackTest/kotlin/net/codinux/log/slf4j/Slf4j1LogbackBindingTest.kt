package net.codinux.log.slf4j

import ch.qos.logback.classic.Logger
import kotlin.test.Test

class Slf4j1LogbackBindingTest : Slf4jBindingTestBase(Slf4jBinding.Logback) {

    @Test
    fun assertSlf4jBinding() {
        assertSlf4jBinding(Logger::class)
    }

}