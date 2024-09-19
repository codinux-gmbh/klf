package net.codinux.log

import net.codinux.log.appender.Appender
import net.codinux.log.appender.ConsoleAppender
import java.lang.management.ManagementFactory
import kotlin.reflect.KClass


actual class Platform {

    actual companion object {

        actual fun createDefaultLoggerFactory() = JvmDefaults.createDefaultLoggerFactory()

        actual val systemDefaultAppender: Appender = ConsoleAppender.Default

        private val isRunningOnAndroid by lazy { isClassAvailable("android.content.Context") }


        actual fun <T : Any> getLoggerName(forClass: KClass<T>) = JvmDefaults.getLoggerName(forClass)

        actual fun getCurrentThreadName() =
            JvmDefaults.getCurrentThreadName()

        actual fun lineSeparator(): String =
            System.lineSeparator()

        actual val isRunningInDebugMode: Boolean =
            isRunningOnAndroid == false && // due to a bug in Gradle(?) Android library doesn't get published so that Android calls this JVM code leading to crashes when running on an Android device
                isDebuggingEnabled()

        actual val appName: String? by lazy {
            val jarPath = Platform::class.java.protectionDomain
                .codeSource
                .location
                .toURI()
                .path

            jarPath.split('/').last { it.isNotBlank() }
        }


        private fun isDebuggingEnabled(): Boolean =
            try {
                isClassAvailable("java.lang.management.ManagementFactory") &&
                    // not 100 % reliable, but the best i could find, see e.g. https://stackoverflow.com/questions/28754627/check-whether-we-are-in-intellij-idea-debugger
                    ManagementFactory.getRuntimeMXBean().inputArguments.any { it.contains("jdwp", true) }
            } catch (e: Throwable) {
                false
            }

        private fun isClassAvailable(qualifiedClassName: String): Boolean {
            try {
                Class.forName(qualifiedClassName)

                return true
            } catch (ignored: Exception) { }

            return false
        }

    }

}