package net.codinux.log

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class JvmDefaultsTest {

    companion object

    class InnerClass

    object InnerObject

    // TODO: when switching to Kotlin 1.9 also test data object

    data class InnerDataClass(
        val property: String = ""
    )


    @Test
    fun getLoggerName_Class() {
        val result = JvmDefaults.getLoggerName(JvmDefaultsTest::class)

        assertThat(result).isEqualTo("net.codinux.log.JvmDefaultsTest")
    }

    @Test
    fun getLoggerName_CompanionObject() {
        val result = JvmDefaults.getLoggerName(JvmDefaultsTest.Companion::class)

        assertThat(result).isEqualTo("net.codinux.log.JvmDefaultsTest")
    }

    @Test
    fun getLoggerName_InnerClass() {
        val result = JvmDefaults.getLoggerName(JvmDefaultsTest.InnerClass::class)

        assertThat(result).isEqualTo("net.codinux.log.JvmDefaultsTest.InnerClass")
    }

    @Test
    fun getLoggerName_TopLevelObject() {
        val result = JvmDefaults.getLoggerName(TopLevelObject::class)

        assertThat(result).isEqualTo("net.codinux.log.TopLevelObject")
    }

    @Test
    fun getLoggerName_InnerObject() {
        val result = JvmDefaults.getLoggerName(JvmDefaultsTest.InnerObject::class)

        assertThat(result).isEqualTo("net.codinux.log.JvmDefaultsTest.InnerObject")
    }

    @Test
    fun getLoggerName_DataClass() {
        val result = JvmDefaults.getLoggerName(InnerDataClass::class)

        assertThat(result).isEqualTo("net.codinux.log.JvmDefaultsTest.InnerDataClass")
    }

}

object TopLevelObject