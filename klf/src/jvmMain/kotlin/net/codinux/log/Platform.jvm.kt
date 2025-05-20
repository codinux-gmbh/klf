package net.codinux.log

import net.codinux.log.appender.Appender
import net.codinux.log.appender.ConsoleAppender
import java.lang.management.ManagementFactory
import kotlin.reflect.KClass

internal actual object Platform {

    actual fun createDefaultLoggerFactory() = JvmDefaults.createDefaultLoggerFactory()

    actual val systemDefaultAppender: Appender by lazy { ConsoleAppender.Default }

    private val isRunningOnAndroid by lazy { JvmDefaults.isClassAvailable("android.content.Context") }


    actual fun <T : Any> getLoggerName(forClass: KClass<T>) = JvmDefaults.getLoggerName(forClass)

    actual fun getLoggerNameFromCallingMethod(): String? =
        JvmDefaults.getLoggerNameFromCallingMethod()

    actual val appName: String? by lazy {
        try {
            val jarPath = Platform::class.java.protectionDomain
                .codeSource
                .location
                .toURI()
                .path

            jarPath.split('/').last { it.isNotBlank() }
        } catch (e: Throwable) { // TODO: log to error logger
            Log.error<Platform>(e) { "Could not get app name from jar name (is a security manager installed?)" }

            null
        }
    }


    private fun isDebuggingEnabled(): Boolean =
        try {
            JvmDefaults.isClassAvailable("java.lang.management.ManagementFactory") &&
                // not 100 % reliable, but the best i could find, see e.g. https://stackoverflow.com/questions/28754627/check-whether-we-are-in-intellij-idea-debugger
                ManagementFactory.getRuntimeMXBean().inputArguments.any { it.contains("jdwp", true) }
        } catch (e: Throwable) {
            ConsoleAppender.Default.append(LogLevel.Error, "Could not determine if debugging is enabled", "net.codinux.log.Platform.jvm", exception = e)
            false
        }

}