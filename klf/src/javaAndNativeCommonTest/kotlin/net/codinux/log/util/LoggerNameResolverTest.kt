package net.codinux.log.util

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlin.test.Test

class LoggerNameResolverTest {

    @Test
    fun normalCase_AppendsMethodNameToClassNameAndRemovesKt() {
        val result = LoggerNameResolver.getLoggerNameFromMethod("org.example.ui.App", "main")

        assertThat(result).isEqualTo("org.example.ui.App.main")
    }

    @Test
    fun removesKtFromClassName() {
        val result = LoggerNameResolver.getLoggerNameFromMethod("org.example.ui.AppKt", "main")

        assertThat(result).isEqualTo("org.example.ui.App.main")
    }

    @Test
    fun findsThatMethodNameEqualsClassName() {
        val result = LoggerNameResolver.getLoggerNameFromMethod("org.example.ui.AppKt", "App")

        assertThat(result).isEqualTo("org.example.ui.App")
    }

    @Test
    fun removesInnerClassSuffixes() {
        val result = LoggerNameResolver.getLoggerNameFromMethod("org.example.ui.App\$2\$1\$2", "main")

        assertThat(result).isEqualTo("org.example.ui.App.main")
    }
    
    @Test
    fun extractsMethodNameFromClassNameForCoroutineFunctions_invoke() {
        val result = LoggerNameResolver.getLoggerNameFromMethod("org.example.ui.screens.MainScreenKt\$MainScreen\$3", "invoke")

        assertThat(result).isEqualTo("org.example.ui.screens.MainScreen")
    }

    @Test
    fun extractsMethodNameFromClassNameForCoroutineFunctions_invokeSuspend() {
        val result = LoggerNameResolver.getLoggerNameFromMethod("org.example.ui.AppKt\$App\$2\$1\$2", "invokeSuspend")

        assertThat(result).isEqualTo("org.example.ui.App")
    }

}