package net.codinux.log.slf4j

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Slf4jUtilTest {

    @Test
    fun isSlf4jOnClasspath() {
        // tests pull logback on the classpath, therefor slf4j is available
        assertThat(Slf4jUtil.isSlf4jOnClasspath).isTrue()
    }

    @Test
    fun useSlf4j() {
        // tests pull logback on the classpath, therefor slf4j is available
        assertThat(Slf4jUtil.useSlf4j).isTrue()
    }

    @Test
    fun boundLoggingFramework() {
        // tests pull logback on the classpath
        assertThat(Slf4jUtil.boundLoggingFramework).isEqualByComparingTo(Slf4jBinding.Logback)
    }

}