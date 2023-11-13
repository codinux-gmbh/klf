package net.codinux.log

import net.codinux.log.appender.Appender
import net.codinux.log.appender.ConsoleAppender
import java.lang.management.ManagementFactory
import kotlin.reflect.KClass


actual class Platform {

    actual companion object {

        actual fun createDefaultLoggerFactory() = JvmDefaults.createDefaultLoggerFactory()

        actual val systemDefaultAppender: Appender = ConsoleAppender.Default


        actual fun <T : Any> getLoggerName(forClass: KClass<T>) = JvmDefaults.getLoggerName(forClass)

        actual fun getCurrentThreadName() =
            JvmDefaults.getCurrentThreadName()

        actual fun lineSeparator(): String =
            System.lineSeparator()

        actual val isRunningInDebugMode: Boolean =
            // not 100 % reliable, but the best i could find, see e.g. https://stackoverflow.com/questions/28754627/check-whether-we-are-in-intellij-idea-debugger
            ManagementFactory.getRuntimeMXBean().inputArguments.any { it.contains("jdwp", true) }

    }

}