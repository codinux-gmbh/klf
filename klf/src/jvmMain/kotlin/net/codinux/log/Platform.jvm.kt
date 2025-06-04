package net.codinux.log

import net.codinux.log.appender.Appender
import net.codinux.log.appender.ConsoleAppender
import net.codinux.log.status.StatusManager

internal actual object Platform {

    actual fun createDefaultLoggerFactory() = JvmDefaults.createDefaultLoggerFactory()

    actual val systemDefaultAppender: Appender by lazy { ConsoleAppender.Default }


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
        } catch (e: Throwable) {
            StatusManager.newError(this, "Could not get app name from jar name (is a security manager installed?)", e)

            null
        }
    }

}