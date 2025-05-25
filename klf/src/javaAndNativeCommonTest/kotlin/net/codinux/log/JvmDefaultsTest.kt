package net.codinux.log

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlin.test.Test

class JvmDefaultsTest {

    companion object

    class InnerClass

    object InnerObject

    // TODO: when switching to Kotlin 1.9 also test data object

    data class InnerDataClass(
        val property: String = ""
    )


    @Test
    fun getLogger_Class() {
        val result = LoggerFactory.getLogger(JvmDefaultsTest::class).name

        assertThat(result).isEqualTo("net.codinux.log.JvmDefaultsTest")
    }

    @Test
    fun getLogger_CompanionObject() {
        val result = LoggerFactory.getLogger(JvmDefaultsTest.Companion::class).name

        assertThat(result).isEqualTo("net.codinux.log.JvmDefaultsTest")
    }

    @Test
    fun getLogger_InnerClass() {
        val result = LoggerFactory.getLogger(JvmDefaultsTest.InnerClass::class).name

        assertThat(result).isEqualTo("net.codinux.log.JvmDefaultsTest.InnerClass")
    }

    @Test
    fun getLogger_TopLevelObject() {
        val result = LoggerFactory.getLogger(TopLevelObject::class).name

        assertThat(result).isEqualTo("net.codinux.log.TopLevelObject")
    }

    @Test
    fun getLogger_InnerObject() {
        val result = LoggerFactory.getLogger(JvmDefaultsTest.InnerObject::class).name

        assertThat(result).isEqualTo("net.codinux.log.JvmDefaultsTest.InnerObject")
    }

    @Test
    fun getLogger_DataClass() {
        val result = LoggerFactory.getLogger(InnerDataClass::class).name

        assertThat(result).isEqualTo("net.codinux.log.JvmDefaultsTest.InnerDataClass")
    }

}

object TopLevelObject