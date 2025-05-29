package net.codinux.log.slf4j

import kotlin.test.Test
import org.slf4j.impl.Reload4jLoggerAdapter

// Log4jLoggerFactory translates "ROOT" to ""
class Reload4jBindingTest : Slf4jBindingTestBase(Slf4jBinding.Reload4j, "root") {


    @Test
    fun assertSlf4jBinding() {
        assertSlf4jBinding(Reload4jLoggerAdapter::class)
    }

}