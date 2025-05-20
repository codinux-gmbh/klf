package net.codinux.log

import net.codinux.log.appender.Appender
import net.codinux.log.appender.ConsoleAppender
import java.lang.management.ManagementFactory
import kotlin.reflect.KClass

internal actual object Platform {

    actual fun createDefaultLoggerFactory() = JvmDefaults.createDefaultLoggerFactory()

    actual val systemDefaultAppender: Appender by lazy { ConsoleAppender.Default }


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

}