package net.codinux.log

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlin.test.Test

class LoggerFactoryJavaAndNativeTest {

    companion object

    class InnerClass

    object InnerObject

    // TODO: when switching to Kotlin 1.9 also test data object

    data class InnerDataClass(
        val property: String = ""
    )


    @Test
    fun getLogger_Class() {
        val result = LoggerFactory.getLogger(LoggerFactoryJavaAndNativeTest::class).name

        assertThat(result).isEqualTo("net.codinux.log.LoggerFactoryJavaAndNativeTest")
    }

    @Test
    fun getLogger_CompanionObject() {
        val result = LoggerFactory.getLogger(Companion::class).name

        assertThat(result).isEqualTo("net.codinux.log.LoggerFactoryJavaAndNativeTest")
    }

    @Test
    fun getLogger_InnerClass() {
        val result = LoggerFactory.getLogger(InnerClass::class).name

        assertThat(result).isEqualTo("net.codinux.log.LoggerFactoryJavaAndNativeTest.InnerClass")
    }

    @Test
    fun getLogger_TopLevelObject() {
        val result = LoggerFactory.getLogger(TopLevelObject::class).name

        assertThat(result).isEqualTo("net.codinux.log.TopLevelObject")
    }

    @Test
    fun getLogger_InnerObject() {
        val result = LoggerFactory.getLogger(InnerObject::class).name

        assertThat(result).isEqualTo("net.codinux.log.LoggerFactoryJavaAndNativeTest.InnerObject")
    }

    @Test
    fun getLogger_DataClass() {
        val result = LoggerFactory.getLogger(InnerDataClass::class).name

        assertThat(result).isEqualTo("net.codinux.log.LoggerFactoryJavaAndNativeTest.InnerDataClass")
    }

}

object TopLevelObject